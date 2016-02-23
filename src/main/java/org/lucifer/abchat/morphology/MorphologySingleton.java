package org.lucifer.abchat.morphology;

import org.apache.lucene.morphology.Morphology;
import org.apache.lucene.morphology.russian.RussianMorphology;

import java.io.IOException;

public class MorphologySingleton {
    private static RussianMorphology morphology;

    public static  RussianMorphology getInstance() {
        if (morphology == null) {
            try {
                morphology = new RussianMorphology();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return morphology;
    }

    public static void main(String[] args) {
        Morphology m = getInstance();
        System.out.println(m.getMorphInfo("чаю"));
    }
}
