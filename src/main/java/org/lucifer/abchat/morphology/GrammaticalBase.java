package org.lucifer.abchat.morphology;

public class GrammaticalBase {
    public String noun;
    public String verb;

    public GrammaticalBase(String noun, String verb) {
        this.noun = noun;
        this.verb = verb;
    }

    @Override
    public String toString(){
        return noun + " " + verb;
    }
}
