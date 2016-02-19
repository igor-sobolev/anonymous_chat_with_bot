package org.lucifer.abchat.service;

import org.lucifer.abchat.dao.MessageLinkDao;
import org.lucifer.abchat.domain.*;
import org.lucifer.abchat.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by PiCy on 2/16/2016.
 */
@Service
@Transactional
public class BotServiceImpl extends BaseServiceImpl<Bot> implements BotService {
    public static final String OOPS = "Oops";
    private static final double FIFTEEN_PERCENT = 0.15;
    private static final double FIFTY_PERCENT = 0.5;

    @Autowired
    private MessageLinkDao messageLinkDao;

    @Autowired
    private ChatService chatService;

    public void remember(String stimulus, String message) {
        MessageLink ml = new MessageLink(stimulus, message);
        messageLinkDao.save(ml);
    }

    public Message message(MessageDTO msg) {
        if (Math.random() < FIFTEEN_PERCENT) return null;                       //keep silence

        Chat chat = chatService.findById(msg.getChatId());
        Cospeaker source = getSource(msg);
        Cospeaker target = getTarget(msg);
        Message m = new Message(chat, null, source, target);

        if (messageLinkDao.count() == 0) {
            m.setMessage(OOPS);
        } else {
            String message = findClosest(msg);
            m.setMessage(message);
            if (message == null) m = null;
        }
        return m;
    }

    protected double tanimoto(String s1, String s2) {
        String[] words1 = s1.split("( )*([.,!?:;])( )*| ");
        String[] words2 = s2.split("( )*([.,!?:;])( )*| ");
        List<Integer> userdPositions = new ArrayList<Integer>();
        int equal = 0;
        for (int i = 0; i < words1.length; i++) {
            boolean found = false;
            for (int j = 0; j < words2.length; j++) {
                if (!found && words1[i].toLowerCase().equals(words2[j].toLowerCase()) && !userdPositions.contains(j)) {
                    found = true;
                    equal++;
                    userdPositions.add(j);
                }
            }
        }
        final double correction = 0.28;
        double tanimoto = ((double) equal / (words1.length + words2.length - equal));
        return (deepTanimoto(s1, s2) + tanimoto) / 2;
    }

    protected double deepTanimoto(String s1, String s2) {
        char[] chars1 = s1.toLowerCase().toCharArray();
        char[] chars2 = s2.toLowerCase().toCharArray();
        List<Integer> userdPositions = new ArrayList<Integer>();
        int equal = 0;
        for (int i = 0; i < chars1.length; i++) {
            boolean found = false;
            for (int j = 0; j < chars2.length; j++) {
                if (!found && chars1[i] == chars2[j]) {
                    found = true;
                    equal++;
                    userdPositions.add(j);
                }
            }
        }
        return (double) equal / (chars1.length + chars2.length - equal);
    }

    private String findClosest(MessageDTO msg) {
        final long numberOfPages = Math.round((double) messageLinkDao.count() / 10);
        final double threshold = 0.2;
        String stimulus = msg.getMessage();
        List<MessageLink> probableMessages = new ArrayList<MessageLink>();
        for (long i = 0; i < numberOfPages; i++) {
            List<MessageLink> list = messageLinkDao.getPage(i);
            for (MessageLink ml : list) {
                double concordance = tanimoto(stimulus, ml.getStimulus());
                if (concordance > threshold) {
                    probableMessages.add(ml);
                }
            }
        }
        return chooseMessage(probableMessages, stimulus);
    }

    private String chooseMessage(List<MessageLink> probableMessages, String stimulus) {
        if (Math.random() < FIFTY_PERCENT) {
            return probableMessages.get((int) (Math.random() * probableMessages.size())).getMessage();
        } else {
            return bestFit(probableMessages, stimulus);
        }
    }

    private String bestFit(List<MessageLink> probableMessages, String stimulus) {
        double bestConcordance = 0;
        String message = null;
        for (MessageLink ml : probableMessages) {
            double concordance = tanimoto(stimulus, ml.getStimulus());
            if (concordance > bestConcordance) {
                bestConcordance = concordance;
                message = ml.getMessage();
            }
        }
        return message;
    }

    private Cospeaker getSource(MessageDTO msg) {
        Chat chat = chatService.findById(msg.getChatId());
        Set<Cospeaker> cospeakers = chat.getCospeakers();
        for (Cospeaker csp : cospeakers) {
            if (csp.getBot() != null) {
                return csp;
            }
        }
        return null;
    }

    private Cospeaker getTarget(MessageDTO msg) {
        Chat chat = chatService.findById(msg.getChatId());
        Set<Cospeaker> cospeakers = chat.getCospeakers();
        for (Cospeaker csp : cospeakers) {
            if (csp.getBot() == null) {
                return csp;
            }
        }
        return null;
    }
}
