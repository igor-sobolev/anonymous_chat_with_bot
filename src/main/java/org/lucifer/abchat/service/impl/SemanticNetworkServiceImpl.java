package org.lucifer.abchat.service.impl;

import org.apache.lucene.morphology.Morphology;
import org.lucifer.abchat.dao.ChatDao;
import org.lucifer.abchat.dao.SemanticLinkDao;
import org.lucifer.abchat.domain.*;
import org.lucifer.abchat.dto.MessageDTO;
import org.lucifer.abchat.morphology.MorphInfo;
import org.lucifer.abchat.morphology.MorphUtils;
import org.lucifer.abchat.morphology.MorphologySingleton;
import org.lucifer.abchat.morphology.semantic.Semantic;
import org.lucifer.abchat.service.SemanticNetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class SemanticNetworkServiceImpl extends BaseServiceImpl<SemanticNetwork> implements SemanticNetworkService {
    private static final String I = "я";
    private static final String YOU = "ты";
    private static final String NOT = "не";
    private static final String IF = "бы";
    private static final String SPACE = " ";

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
        String[] words = message.toLowerCase().split("( )*([.,!?:;])( )*| ");
        if (countVerbs(words) == 1) {
            findSPO(words, network);
        }
    }

    private void findSPO(String[] words, SemanticNetwork network) {
        int predIndex = findVerb(words);
        if (predIndex == -1) return;
        int subjIndex = findSubject(words, predIndex);
        if (subjIndex == -1) return;
        int objIndex = findObject(words, subjIndex, predIndex);
        createNetwork(words, predIndex, subjIndex, objIndex, network);
    }

    private void createNetwork(String[] words, int predIndex, int subjIndex,
                               int objIndex, SemanticNetwork network) {
        String fullPred = getFullPredicate(words, predIndex);
        SemanticNode predicate = createPredicate(fullPred, network);
        SemanticNode subject = createNode(words[subjIndex], predicate,
                Semantic.SUBJECT, network);
        linkFeatures(words, subject, subjIndex, network);

        if (objIndex != -1) {
            String fullObj = getFullObject(words, objIndex);
            SemanticNode object = createNode(fullObj, predicate,
                    Semantic.OBJECT, network);
            linkFeatures(words, object, objIndex, network);
        }
    }

    private void linkFeatures(String[] words, SemanticNode parent, int index,
                              SemanticNetwork network) {
        for (int i = index - 1; i >= 0; i--) {
            if (canBeFeature(words[i]) && suitFeature(words[i], words[index])) {
                createNode(words[i], parent, Semantic.FEATURE, network);
            }
        }
    }

    private boolean suitFeature(String feat, String word) {
        List<MorphInfo> featList = MorphUtils
                .parse(morphology.getMorphInfo(feat));
        List<MorphInfo> wordList = MorphUtils
                .parse(morphology.getMorphInfo(word));
        for (MorphInfo featInfo : featList) {
            for (MorphInfo wordInfo : wordList) {
                if (canBeFeature(featInfo)) {
                    if (featInfo.getCount().equals(MorphInfo.MN)
                            && featInfo.checkCount(wordInfo.getCount())
                            && featInfo.checkNCase(wordInfo.getnCase())) {
                        return true;
                    } else {
                        if (featInfo.checkGender(wordInfo.getGender())
                                && featInfo.checkNCase(wordInfo.getnCase())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean canBeFeature(String word) {
        List<MorphInfo> infoList = MorphUtils
                .parse(morphology.getMorphInfo(word));
        for (MorphInfo info : infoList) {
            if (canBeFeature(info)) {
                return true;
            }
        }
        return false;
    }

    private String getFullObject(String[] words, int objIndex) {
        if (!havePrepForIndex(words, objIndex)) {
            return words[objIndex];
        } else {
            for (int i = objIndex - 1; i >= 0; i--) {
                if (isPreposition(words[i])) {
                    return words[i] + SPACE + words[objIndex];
                }
            }
        }
        return words[objIndex];
    }

    private boolean isPreposition(String word) {
        List<MorphInfo> infoList = MorphUtils
                .parse(morphology.getMorphInfo(word));
        for (MorphInfo info : infoList) {
            if (info.checkPartOfSpeech(MorphInfo.PREDL)) {
                return true;
            }
        }
        return false;
    }

    private String getFullPredicate(String[] words, int predIndex) {
        StringBuilder sb;
        if (predIndex > 0 && words[predIndex - 1].equals(NOT)) {
            sb = new StringBuilder(NOT);
            sb.append(SPACE).append(words[predIndex]);
        } else {
            sb = new StringBuilder(words[predIndex]);
        }
        for (int i = predIndex + 1; i < words.length; i++) {
            if (words[i].equals(IF)) {
                sb.append(SPACE).append(IF);
            }
            if (isInfinitive(words[i])) {
                sb.append(SPACE).append(words[i]);
            }
        }
        return sb.toString();
    }

    private boolean isInfinitive(String word) {
        List<MorphInfo> infoList = MorphUtils
                .parse(morphology.getMorphInfo(word));
        for (MorphInfo info : infoList) {
            if (info.checkPartOfSpeech(MorphInfo.INFINITIV)) {
                return true;
            }
        }
        return false;
    }

    private SemanticNode createNode(String word, SemanticNode parent,
                                    String link, SemanticNetwork network) {
        SemanticNode node = new SemanticNode();
        node.setNetwork(network);
        node.setValue(word);
        SemanticLink sLink = new SemanticLink();
        sLink.setLinkedNode(node);
        sLink.setNode(parent);
        sLink.setValue(link);
        semanticLinkDao.save(sLink);
        return node;
    }

    private SemanticNode createPredicate(String word,
                                         SemanticNetwork network) {
        SemanticNode node = new SemanticNode();
        node.setNetwork(network);
        node.setValue(word);
        node.setPeak(true);
        return node;
    }

    private int findObject(String[] words, int subjIndex, int predIndex) {
        for (int i = 0; i < words.length; i++) {
            List<MorphInfo> infoList = MorphUtils
                    .parse(morphology.getMorphInfo(words[i]));
            for (MorphInfo info : infoList) {
                if (canBeObject(info) && i != subjIndex && i != predIndex) {
                    return i;
                }
            }
        }
        return -1;
    }

    private String getPredCount(int predIndex, String[] words) {
        List<MorphInfo> pred = MorphUtils
                .parse(morphology.getMorphInfo(words[predIndex]));
        for (MorphInfo pinfo : pred) {
            if (pinfo.checkPartOfSpeech(MorphInfo.G)) {
                return pinfo.getCount();
            }
        }
        return null;
    }

    private boolean havePrepForIndex(String[] words, int index) {
        boolean thisWord = true;
        for (int i = index - 1; i >= 0; i--) {
            List<MorphInfo> infoList = MorphUtils
                    .parse(morphology.getMorphInfo(words[i]));
            for (MorphInfo info : infoList) {
                if (info.checkPartOfSpeech(MorphInfo.PREDL) && thisWord) {
                    return true;
                }
                if (shouldStopFindingPrep(info)) {
                    thisWord = false;
                }
            }
        }
        return false;
    }

    private boolean shouldStopFindingPrep(MorphInfo info) {
        return info.checkPartOfSpeech(MorphInfo.S)
                || info.checkPartOfSpeech(MorphInfo.MS)
                || info.checkPartOfSpeech(MorphInfo.G);
    }

    private int findSubject(String[] words, int predIndex) {
        for (int i = 0; i <= words.length; i--) {
            List<MorphInfo> infoList = MorphUtils
                    .parse(morphology.getMorphInfo(words[i]));
            for (MorphInfo info : infoList) {                                   //TODO refactor
                if (canBeSubject(info)) {
                    if (words[i].equals(I) || words[i].equals(YOU)) {
                        return i;
                    }
                    if (info.checkCount(MorphInfo.MN)) {
                        if (info.checkCount(getPredCount(predIndex, words))) {
                            if (!havePrepForIndex(words, i) && i != predIndex) {
                                return i;
                            }
                        }
                    } else {
                        if (info.checkGender(getPredGender(words[predIndex]))) {
                            if (!havePrepForIndex(words, i) && i != predIndex) {
                                return i;
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }

    private String getPredGender(String word) {
        List<MorphInfo> pred = MorphUtils
                .parse(morphology.getMorphInfo(word));
        for (MorphInfo pinfo : pred) {
            if (pinfo.checkPartOfSpeech(MorphInfo.G)) {
                return pinfo.getGender();
            }
        }
        return null;
    }

    private int findVerb(String[] words) {
        for (int i = 0; i < words.length; i++) {
            if (canBePredicate(words[i])) {
                return i;
            }
        }
        return -1;
    }

    private int countVerbs(String[] words) {
        int rez = 0;
        for (String s : words) {
            if (canBePredicate(s)) {
                rez++;
            }
        }
        return rez;
    }

    private boolean canBeCounter(MorphInfo info) {
        return info.checkPartOfSpeech(MorphInfo.CHISL);
    }

    private boolean canBeSubject(MorphInfo info) {
        return (info.checkPartOfSpeech(MorphInfo.S)
                || info.checkPartOfSpeech(MorphInfo.MS))
                && info.checkNCase(MorphInfo.IM);
    }

    private boolean canBePredicate(String word) {
        List<MorphInfo> infoList = MorphUtils
                .parse(morphology.getMorphInfo(word));
        for (MorphInfo info : infoList) {
            if (info.checkPartOfSpeech(MorphInfo.G)) {
                return true;
            }
        }
        return false;
    }

    private boolean canBeImage(MorphInfo info) {
        return info.checkPartOfSpeech(MorphInfo.N)
                || info.checkPartOfSpeech(MorphInfo.DEEPRICHASTIE);
    }

    private boolean canBeObject(MorphInfo info) {
        return (info.checkPartOfSpeech(MorphInfo.S)
                || info.checkPartOfSpeech(MorphInfo.MS))
                && info.checkNCase(MorphInfo.VN);
    }

    private boolean canBeFeature(MorphInfo info) {
        return info.checkPartOfSpeech(MorphInfo.P)
                || info.checkPartOfSpeech(MorphInfo.PRICHASTIE)
                || info.checkPartOfSpeech(MorphInfo.KR_PRICHASTIE)
                || info.checkPartOfSpeech(MorphInfo.KR_PRIL)
                || info.checkPartOfSpeech(MorphInfo.CHISL_P)
                || info.checkPartOfSpeech(MorphInfo.MS_P);
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
