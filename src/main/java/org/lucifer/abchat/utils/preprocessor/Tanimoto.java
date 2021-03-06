package org.lucifer.abchat.utils.preprocessor;

import java.util.ArrayList;
import java.util.List;

public class Tanimoto {
    private static final String EMPTY_STRING = "";

    public static double tanimoto(String s1, String s2) {
        if (s1 == null || s2 == null) return 0;
        if (s1.equals(s2)) return 1.0;
        if (s1.trim().equals(EMPTY_STRING)
                || s2.trim().equals(EMPTY_STRING)) return 0;
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
        double tanimoto = ((double) equal / (words1.length + words2.length - equal));
        return (deepTanimoto(s1, s2) + tanimoto) / 2;
    }

    protected static double deepTanimoto(String s1, String s2) {
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


    public static void main(String[] args) {
        System.out.println(tanimoto("за столом сидели", "за тобой выехали"));
    }
}
