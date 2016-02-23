package org.lucifer.abchat.service.impl;

import org.apache.lucene.morphology.Morphology;
import org.lucifer.abchat.dao.ChatDao;
import org.lucifer.abchat.dao.SemanticLinkDao;
import org.lucifer.abchat.dao.SemanticNodeDao;
import org.lucifer.abchat.domain.Bot;
import org.lucifer.abchat.domain.Chat;
import org.lucifer.abchat.domain.Cospeaker;
import org.lucifer.abchat.domain.SemanticNetwork;
import org.lucifer.abchat.dto.MessageDTO;
import org.lucifer.abchat.morphology.GrammaticalBase;
import org.lucifer.abchat.morphology.MorphologySingleton;
import org.lucifer.abchat.service.SemanticNetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Transactional
@Service
public class SemanticNetworkServiceImpl extends BaseServiceImpl<SemanticNetwork> implements SemanticNetworkService {
    private Morphology morphology = MorphologySingleton.getInstance();

    @Autowired
    private SemanticLinkDao semanticLinkDao;

    @Autowired
    private SemanticNodeDao semanticNodeDao;

    @Autowired
    private ChatDao chatDao;


    public void createSN(MessageDTO msg) {
        Bot bot = findBot(msg.getChatId());
        SemanticNetwork network = new SemanticNetwork(bot);
        divideIntoNodesAndSave(msg.getMessage(), network);
    }

    private void divideIntoNodesAndSave(String message, SemanticNetwork network) {
        String[] words = message.split("( )*([.,!?:;])( )*| ");
        List<GrammaticalBase> gramatical = new ArrayList<GrammaticalBase>();
        Map<String, String> mappedWords = fromMorphology(words, gramatical);
    }

    private Map<String, String> fromMorphology(String[] words, List<GrammaticalBase> gramatical) {
        Map<String, String> mappedWords = new TreeMap<String, String>();
        for (int i = 0; i < words.length; i++) {
            List<String> info = morphology.getMorphInfo(words[i].toLowerCase());
            //Find grammatical base
            for (String inf : info) {
                if ((inf.contains(" С ") || inf.contains(" МС ")) && inf.contains("им")) {
                    String w = "";
                    if (inf.contains("мр")) {
                        w = findVerb(words, "мр");
                    } else if (inf.contains("жр")) {
                        w = findVerb(words, "жр");
                    } else if (inf.contains("мн")) {
                        w = findVerb(words, "мн");
                    } else if (inf.contains("ср")) {
                        w = findVerb(words, "ср");
                    } else if (!inf.contains("ср") && !inf.contains("мр") && !inf.contains("жр")) {
                        w = findVerb(words, "ед");
                    }

                    if (!w.equals("")) {
                        mappedWords.put(words[i], "subj");
                        mappedWords.put(w, "pred");
                        gramatical.add(new GrammaticalBase(words[i], w));
                    }

                }
            }
        }

        //Find other attributes
        for (int i = 0; i < words.length; i++) {
            List<String> info = morphology.getMorphInfo(words[i].toLowerCase());
            //Find grammatical base
            for (String inf : info) {
                if ((inf.contains(" С ") || inf.contains(" МС ")) && !mappedWords.containsKey(words[i])) {
                    mappedWords.put(words[i], "obj");
                } else if ((inf.contains(" П ") || inf.contains("ПРИЧАСТИЕ") || inf.contains("КР_ПРИЧ") || inf.contains("КР_ПРИЛ") || inf.contains("ЧИСЛ-П") || inf.contains("МС-П")) && !mappedWords.containsKey(words[i])) {
                    mappedWords.put(words[i], "feature");
                } else if ((inf.contains(" Н") || inf.contains("ДЕЕПРИЧАСТИЕ")) && !mappedWords.containsKey(words[i])) {
                    mappedWords.put(words[i], "image");
                } else if (inf.contains(" ЧИСЛ ") && !mappedWords.containsKey(words[i])) {
                    mappedWords.put(words[i], "count");
                }
            }
        }
        return mappedWords;
    }

    /*private Semantic createSemantic(Map<String, String> mappedWords, List<GrammaticalBase> grammatical, String[] words) {
        Semantic web = new Semantic();
        web.addWord("peak");
        web.addWord("");
        for (Map.Entry<String, String> s : mappedWords.entrySet()) {
            web.addWord(s.getKey());
        }
        for (GrammaticalBase base : grammatical) {
            web.addLink("peak", "predicate", base.verb);
            web.addLink(base.verb, "subject", base.noun);
            for (Map.Entry<String, String> entry : mappedWords.entrySet()) {
                boolean isNotGrammaticalBase = true;
                for (GrammaticalBase check : grammatical) {
                    if (entry.getKey().equals(check.noun) || entry.getKey().equals(check.verb)) {
                        isNotGrammaticalBase = false;
                    }
                }
                if (isNotGrammaticalBase) {
                    String closestBase = findClosest(entry, words, mappedWords);
                    web.addLink(closestBase, entry.getValue(), entry.getKey());
                }
            }
        }
        return web;
    }*/

    private String findClosest(Map.Entry<String, String> word, String[] words, Map<String, String> mappedWords) {
        int index = -1;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word.getKey())) {
                index = i;
            }
        }
        if (index == -1) return "";
        int minDistance = Integer.MAX_VALUE;
        String resword = "";
        for (Map.Entry<String, String> entry : mappedWords.entrySet()) {
            for (int i = 0; i < words.length; i++) {
                if (words[i].equals(entry.getKey())) {
                    if (word.getValue().equals("feature") || word.getValue().equals("count")) {
                        if (entry.getValue().equals("obj") || entry.getValue().equals("subj")) {
                            List<String> infoAboutWord = morphology.getMorphInfo(word.getKey());
                            List<String> infoAboutNoun = morphology.getMorphInfo(entry.getKey());
                            for (String infw : infoAboutWord) {
                                for (String infn : infoAboutNoun) {
                                    if (infw.contains("мр") && infn.contains("мр")
                                            || infw.contains("жр") && infn.contains("жр")
                                            || infw.contains("ср") && infn.contains("ср")
                                            || infw.contains("мн") && infn.contains("мн")) {
                                        int distance = Math.abs(i - index);
                                        if (distance < minDistance) {
                                            minDistance = distance;
                                            resword = words[i];
                                        }
                                    }
                                }
                            }
                        }
                    } else if (word.getValue().equals("image")) {
                        if (entry.getValue().equals("pred")) {
                            int distance = Math.abs(i - index);
                            if (distance < minDistance) {
                                minDistance = distance;
                                resword = words[i];
                            }
                        }
                    }
                }
            }
        }
        return resword;
    }

    private String findVerb(String[] words, String param) {
        for (String word : words) {
            List<String> information = morphology.getMorphInfo(word);
            for (String inf : information) {
                if (inf.contains(" Г ") && inf.contains(param)) {
                    return word;
                }
            }
        }
        return "";
    }

    private Bot findBot(Long chatId) {
        Chat chat = chatDao.findById(chatId);
        for (Cospeaker csp : chat.getCospeakers()) {
            if (csp.getBot() != null) {
                return csp.getBot();
            }
        }
        return null;
    }
}
