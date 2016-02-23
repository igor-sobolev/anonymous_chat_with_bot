package org.lucifer.abchat.morphology;

public class MorphInfo {
    public static final String S = "С";
    public static final String MS = "МС";
    public static final String G = "Г";
    public static final String PRICHASTIE = "ПРИЧАСТИЕ";
    public static final String DEEPRICHASTIE = "ДЕЕПРИЧАСТИЕ";
    public static final String INFINITIV = "ИНФИНИТИВ";
    public static final String MS_PREDK = "МС-ПРЕДК";
    public static final String MS_P = "МС-П";
    public static final String CHISL = "ЧИСЛ";
    public static final String CHISL_P = "ЧИСЛ-П";
    public static final String N = "Н";
    public static final String PREDK = "ПРЕДК";
    public static final String PREDL = "ПРЕДЛ";
    public static final String SOUZ = "СОЮЗ";
    public static final String MEJD = "МЕЖД";
    public static final String CHAST = "ЧАСТ";
    public static final String VVODN = "ВВОДН";
    public static final String KR_PRIL = "КР_ПРИЛ";
    public static final String KR_PRICHASTIE = "КР_ПРИЧАСТИЕ";

    //noun
    public static final String MR = "мр";
    public static final String JR = "жр";
    public static final String SR = "ср";
    public static final String OD = "од";
    public static final String NO = "но";
    public static final String ED = "ед";
    public static final String MN = "мн";
    public static final String IM = "им";
    public static final String RD = "рд";
    public static final String DT = "дт";
    public static final String VN = "вн";
    public static final String TV = "тв";
    public static final String PR = "пр";
    public static final String ZV = "зв";
    public static final String TWO = "2";

    //verb
    public static final String SV = "св";
    public static final String NS = "нс";
    public static final String PE = "пе";
    public static final String NP = "нп";
    public static final String NST = "нст";
    public static final String PRSH = "прш";
    public static final String BUD = "буд";
    public static final String PVL = "пвл";
    public static final String FL = "1л";
    public static final String SL = "2л";
    public static final String TL = "3л";
    public static final String BEZL = "безл";

    public static final String DST = "дст";
    public static final String STR = "стр";

    public static final String ZERO = "0";

    public static final String KR = "кр";
    public static final String SRAVN = "сравн";
    public static final String KACH = "кач";

    public static final String IMYA = "имя";
    public static final String FAM = "фам";
    public static final String OTCH = "отч";

    public static final String LOK = "лок";
    public static final String ORG = "орг";

    public static final String VOPR = "вопр";
    public static final String OTNOS = "относ";

    public static final String DFST = "дфст";

    public static final String JARG = "жарг";
    public static final String ARH = "арх";
    public static final String PROF = "проф";

    public static final String ABBR = "аббр";

    private String partOfSpeech;

    //существительное
    private String gender;                                                      //род
    private String count;                                                       //число
    private String animate;                                                     //одушевленность
    private String ncase;                                                       //падеж
    private boolean second;                                                     //2 родительный или предложный падеж

    //глагол
    private String perf;                                                        //совершенность
    private String transit;                                                     //переходность
    private String time;                                                        //время
    private boolean command;                                                    //повелительная форма (пвл)
    private String face;                                                        //лицо
    private boolean impersonal;                                                 //безличный глагол

    //деепричастие
    private String voice;                                                       //залог

    private boolean unchangable;                                                //неизменяемое 0

    private boolean cut;                                                        //краткость (прил и прич)

    private boolean compare;                                                    //сравнительнаяформа (прил)
    private boolean quality;                                                    //качественное прилагательное

    private boolean name;                                                       //имя
    private boolean surname;                                                    //фамилия
    private boolean patronymic;                                                 //отчество

    private boolean locativity;                                                 //локативность организация
    private boolean organization;

    private String qwRel;                                                       //вопросительность относительность

    private boolean dfst;                                                       //не имеет множественного числа

    private boolean mistake;                                                    //опечатка

    private String slang;                                                       //жарг, арх, проф

    private boolean abbr;                                                       //аббревиатура

    public MorphInfo() {

    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAnimate() {
        return animate;
    }

    public void setAnimate(String animate) {
        this.animate = animate;
    }

    public String getNcase() {
        return ncase;
    }

    public void setNcase(String ncase) {
        this.ncase = ncase;
    }

    public boolean isSecond() {
        return second;
    }

    public void setSecond(boolean second) {
        this.second = second;
    }

    public String getPerf() {
        return perf;
    }

    public void setPerf(String perf) {
        this.perf = perf;
    }

    public String getTransit() {
        return transit;
    }

    public void setTransit(String transit) {
        this.transit = transit;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isCommand() {
        return command;
    }

    public void setCommand(boolean command) {
        this.command = command;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public boolean isImpersonal() {
        return impersonal;
    }

    public void setImpersonal(boolean impersonal) {
        this.impersonal = impersonal;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public boolean isUnchangable() {
        return unchangable;
    }

    public void setUnchangable(boolean unchangable) {
        this.unchangable = unchangable;
    }

    public boolean isCut() {
        return cut;
    }

    public void setCut(boolean cut) {
        this.cut = cut;
    }

    public boolean isCompare() {
        return compare;
    }

    public void setCompare(boolean compare) {
        this.compare = compare;
    }

    public boolean isQuality() {
        return quality;
    }

    public void setQuality(boolean quality) {
        this.quality = quality;
    }

    public boolean isName() {
        return name;
    }

    public void setName(boolean name) {
        this.name = name;
    }

    public boolean isSurname() {
        return surname;
    }

    public void setSurname(boolean surname) {
        this.surname = surname;
    }

    public boolean isPatronymic() {
        return patronymic;
    }

    public void setPatronymic(boolean patronymic) {
        this.patronymic = patronymic;
    }

    public boolean isLocativity() {
        return locativity;
    }

    public void setLocativity(boolean locativity) {
        this.locativity = locativity;
    }

    public boolean isOrganization() {
        return organization;
    }

    public void setOrganization(boolean organization) {
        this.organization = organization;
    }

    public String getQwRel() {
        return qwRel;
    }

    public void setQwRel(String qwRel) {
        this.qwRel = qwRel;
    }

    public boolean isDfst() {
        return dfst;
    }

    public void setDfst(boolean dfst) {
        this.dfst = dfst;
    }

    public boolean isMistake() {
        return mistake;
    }

    public void setMistake(boolean mistake) {
        this.mistake = mistake;
    }

    public String getSlang() {
        return slang;
    }

    public void setSlang(String slang) {
        this.slang = slang;
    }

    public boolean isAbbr() {
        return abbr;
    }

    public void setAbbr(boolean abbr) {
        this.abbr = abbr;
    }
}
