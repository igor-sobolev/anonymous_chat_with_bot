package org.lucifer.abchat.service.impl;

import org.apache.lucene.morphology.Morphology;
import org.lucifer.abchat.dao.ChatDao;
import org.lucifer.abchat.dao.SemanticLinkDao;
import org.lucifer.abchat.domain.*;
import org.lucifer.abchat.dto.MessageDTO;
import org.lucifer.abchat.morphology.MorphInfo;
import org.lucifer.abchat.morphology.MorphUtils;
import org.lucifer.abchat.morphology.MorphologySingleton;
import org.lucifer.abchat.morphology.semantic.GrammaticalBase;
import org.lucifer.abchat.morphology.semantic.Semantic;
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
    private ChatDao chatDao;


    public void createSN(MessageDTO msg) {
        Bot bot = findBot(msg.getChatId());
        SemanticNetwork network = new SemanticNetwork(bot);
        divideIntoNodesAndSave(msg.getMessage(), network);
    }

    private void divideIntoNodesAndSave(String message,
                                        SemanticNetwork network) {
        String[] words = message.split("( )*([.,!?:;])( )*| ");
        List<GrammaticalBase> gramatical = getGrammatical(words);
        Map<String, String> mappedWords = mapWords(words, gramatical);
        createNetwork(words, mappedWords, gramatical, network);
    }

    private void createNetwork(String[] words, Map<String, String> mappedWords, List<GrammaticalBase> gramatical,
                               SemanticNetwork network) {
        Map<String, String> linksToWords = linkMappedWords(mappedWords, words);
        List<String> used = saveGrammaticalWIthLinks(gramatical, network, linksToWords, mappedWords);
        saveOtherAttrWithLinks(network, used, mappedWords, linksToWords);
    }

    private void saveOtherAttrWithLinks(SemanticNetwork network,
                                        List<String> used,
                                        Map<String, String> mappedWords,
                                        Map<String, String> linksToWords) {
        for (Map.Entry<String, String> entry : mappedWords.entrySet()) {
            if (!used.contains(entry.getKey())) {

                used.add(entry.getKey());
            }
        }
    }

    private List<String>
    saveGrammaticalWIthLinks(List<GrammaticalBase> gramatical,
                             SemanticNetwork network,
                             Map<String, String> linksToGrammatical,
                             Map<String, String> mappedWords) {
        List<String> used = new ArrayList<String>();
        for (GrammaticalBase gb : gramatical) {
            SemanticNode predicate = createPredicate(network, gb.predicate);
            SemanticNode subject = createSubject(network, gb.subject, predicate);
            used.add(gb.predicate);
            used.add(gb.subject);
            for (Map.Entry<String, String> entry : linksToGrammatical.entrySet()) {
                if (entry.getValue().equals(gb.predicate)) {
                    createNode(network, entry.getKey(), predicate, mappedWords.get(entry.getKey()));
                    used.add(entry.getKey());
                } else if (entry.getValue().equals(gb.subject)) {
                    createNode(network, entry.getKey(), subject, mappedWords.get(entry.getKey()));
                    used.add(entry.getKey());
                }
            }
        }
        return used;
    }

    private SemanticNode createNode(SemanticNetwork network, String value, SemanticNode parent, String link) {
        SemanticNode node = new SemanticNode();
        node.setValue(value);
        node.setNetwork(network);
        SemanticLink sLink = new SemanticLink();
        sLink.setValue(link);
        sLink.setNode(parent);
        sLink.setLinkedNode(node);
        semanticLinkDao.save(sLink);
        return node;
    }

    private Map<String, String> linkMappedWords(Map<String, String> mappedWords, String[] words) {
        Map<String, String> map = new TreeMap<String, String>();
        for (Map.Entry<String, String> entry : mappedWords.entrySet()) {
            if (!entry.getValue().equals(Semantic.PREDICATE) && !entry.getValue().equals(Semantic.SUBJECT)) {
                map.put(entry.getKey(), findClosest(entry, words, mappedWords));
            }
        }
        return map;
    }

    private String findClosest(Map.Entry<String, String> word, String[] words, Map<String, String> mappedWords) {
        int index = -1;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word.getKey())) {
                index = i;
            }
        }
        if (index == -1) return MorphInfo.DEFAULT;
        int minDistance = Integer.MAX_VALUE;
        String resword = MorphInfo.DEFAULT;
        for (Map.Entry<String, String> entry : mappedWords.entrySet()) {
            for (int i = 0; i < words.length; i++) {
                if (words[i].equals(entry.getKey())) {
                    if (word.getValue().equals(Semantic.FEATURE) || word.getValue().equals(Semantic.COUNTER)) {
                        if (entry.getValue().equals(Semantic.OBJECT) || entry.getValue().equals(Semantic.SUBJECT)) {
                            List<MorphInfo> infoAboutWord = MorphUtils.parse(morphology.getMorphInfo(word.getKey()));
                            List<MorphInfo> infoAboutNoun = MorphUtils.parse(morphology.getMorphInfo(entry.getKey()));
                            for (MorphInfo infw : infoAboutWord) {
                                for (MorphInfo infn : infoAboutNoun) {
                                    if (infw.getGender().equals(infn.getGender())
                                            && infw.getCount().equals(infn.getCount())) {
                                        int distance = Math.abs(i - index);
                                        if (distance < minDistance) {
                                            minDistance = distance;
                                            resword = words[i];
                                        }
                                    }
                                }
                            }
                        }
                    } else if (word.getValue().equals(Semantic.IMAGE)) {
                        if (entry.getValue().equals(Semantic.PREDICATE)) {
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

    private SemanticNode createSubject(SemanticNetwork network, String subject,
                                       SemanticNode predicate) {
        SemanticNode node = createNode(network, subject, predicate, Semantic.SUBJECT);
        /*SemanticNode node = new SemanticNode();
        node.setValue(subject);
        node.setNetwork(network);
        SemanticLink link = new SemanticLink();
        link.setValue(Semantic.SUBJECT);
        link.setNode(predicate);
        link.setLinkedNode(node);
        semanticLinkDao.save(link);*/
        return node;
    }

    private SemanticNode createPredicate(SemanticNetwork network, String predicate) {
        SemanticNode node = new SemanticNode();
        node.setValue(predicate);
        node.setPeak(true);
        node.setNetwork(network);
        return node;
    }

    private Map<String, String> mapWords(String[] words, List<GrammaticalBase> gramatical) {
        Map<String, String> mappedWords = new TreeMap<String, String>();
        for (GrammaticalBase gb : gramatical) {
            mappedWords.put(gb.predicate, Semantic.PREDICATE);
            mappedWords.put(gb.subject, Semantic.SUBJECT);
        }
        for (int i = 0; i < words.length; i++) {
            List<MorphInfo> infoList = MorphUtils.parse(morphology
                    .getMorphInfo(words[i].toLowerCase()));
            for (MorphInfo info : infoList) {
                if (canBeObject(info) && !mappedWords.containsKey(words[i])) {
                    mappedWords.put(words[i], Semantic.OBJECT);
                } else if (canBeImage(info) && !mappedWords.containsKey(words[i])) {
                    mappedWords.put(words[i], Semantic.IMAGE);
                } else if (canBeFeature(info) && !mappedWords.containsKey(words[i])) {
                    mappedWords.put(words[i], Semantic.FEATURE);
                } else if (canBeCounter(info) && !mappedWords.containsKey(words[i])) {
                    mappedWords.put(words[i], Semantic.COUNTER);
                }
            }
        }
        return mappedWords;
    }

    private boolean havePairForFeature(MorphInfo ext, String[] words) {
        for (String word : words) {
            List<MorphInfo> infoList = MorphUtils.parse(morphology.getMorphInfo(word));
            for (MorphInfo info : infoList) {
                if (info.getGender().equals(ext.getGender()) && info.getCount().equals(ext.getCount())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canBeCounter(MorphInfo info) {
        return info.checkPartOfSpeech(MorphInfo.CHISL);
    }

    private boolean canBeImage(MorphInfo info) {
        return info.checkPartOfSpeech(MorphInfo.N)
                || info.checkPartOfSpeech(MorphInfo.DEEPRICHASTIE);
    }

    private boolean canBeObject(MorphInfo info) {
        return info.checkPartOfSpeech(MorphInfo.S)
                || info.checkPartOfSpeech(MorphInfo.MS);
    }

    private boolean canBeFeature(MorphInfo info) {
        return info.checkPartOfSpeech(MorphInfo.P)
                || info.checkPartOfSpeech(MorphInfo.PRICHASTIE)
                || info.checkPartOfSpeech(MorphInfo.KR_PRICHASTIE)
                || info.checkPartOfSpeech(MorphInfo.KR_PRIL)
                || info.checkPartOfSpeech(MorphInfo.CHISL_P)
                || info.checkPartOfSpeech(MorphInfo.MS_P);
    }

    private List<GrammaticalBase> getGrammatical(String[] words) {
        List<GrammaticalBase> list = new ArrayList<GrammaticalBase>();
        for (int i = 0; i < words.length; i++) {
            List<MorphInfo> infoList = MorphUtils.parse(morphology
                    .getMorphInfo(words[i].toLowerCase()));
            for (MorphInfo info : infoList) {
                if ((info.checkPartOfSpeech(MorphInfo.S)
                        || info.checkPartOfSpeech(MorphInfo.MS))
                        && info.checkNCase(MorphInfo.IM)) {
                    String verb = findVerb(words, info.getGender());
                    if (verb != null) {
                        list.add(new GrammaticalBase(words[i], verb));
                    }
                }
            }
        }
        return list;
    }

    private String findVerb(String[] words, String param) {
        for (String word : words) {
            List<MorphInfo> infoList = MorphUtils.parse(morphology
                    .getMorphInfo(word));
            for (MorphInfo info : infoList) {
                if (info.checkPartOfSpeech(MorphInfo.G)
                        && info.getGender().equals(param)) {
                    return word;
                }
            }
        }
        return null;
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
