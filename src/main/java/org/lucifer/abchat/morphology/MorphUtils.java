package org.lucifer.abchat.morphology;

import java.util.ArrayList;
import java.util.List;

public class MorphUtils {
    public static MorphInfo parse(String info) {
        MorphInfo result = new MorphInfo();
        result = parsePartOfSpeech(result, info);
        result = parseGrammems(result, info);
        return result;
    }

    public static List<MorphInfo> pack(List<String> list) {
        List <MorphInfo> ready = new ArrayList<MorphInfo>();
        for (String s : list) {
            ready.add(parse(s));
        }
        return ready;
    }

    private static MorphInfo parseGrammems(MorphInfo obj, String info) {
        //TODO grammems
        return obj;
    }

    private static MorphInfo parsePartOfSpeech(MorphInfo obj, String info) {
        obj = PartOfSpeech(obj, info, MorphInfo.S);
        obj = PartOfSpeech(obj, info, MorphInfo.MS);
        obj = PartOfSpeech(obj, info, MorphInfo.G);
        obj = PartOfSpeech(obj, info, MorphInfo.PRICHASTIE);
        obj = PartOfSpeech(obj, info, MorphInfo.DEEPRICHASTIE);
        obj = PartOfSpeech(obj, info, MorphInfo.INFINITIV);
        obj = PartOfSpeech(obj, info, MorphInfo.MS_PREDK);
        obj = PartOfSpeech(obj, info, MorphInfo.MS_P);
        obj = PartOfSpeech(obj, info, MorphInfo.CHISL);
        obj = PartOfSpeech(obj, info, MorphInfo.CHISL_P);
        obj = PartOfSpeech(obj, info, MorphInfo.N);
        obj = PartOfSpeech(obj, info, MorphInfo.PREDK);
        obj = PartOfSpeech(obj, info, MorphInfo.PREDL);
        obj = PartOfSpeech(obj, info, MorphInfo.SOUZ);
        obj = PartOfSpeech(obj, info, MorphInfo.MEJD);
        obj = PartOfSpeech(obj, info, MorphInfo.CHAST);
        obj = PartOfSpeech(obj, info, MorphInfo.VVODN);
        obj = PartOfSpeech(obj, info, MorphInfo.KR_PRIL);
        obj = PartOfSpeech(obj, info, MorphInfo.KR_PRICHASTIE);
        return obj;
    }

    private static MorphInfo PartOfSpeech(MorphInfo obj, String info, String pos) {
        if (info.contains(pos)) {
            obj.setPartOfSpeech(pos);
        }
        return obj;
    }
}
