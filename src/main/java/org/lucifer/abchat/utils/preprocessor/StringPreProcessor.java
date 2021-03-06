package org.lucifer.abchat.utils.preprocessor;


import java.util.*;

public class StringPreProcessor {
    private static final String EMPTY_STRING = "";
    private static final String SPACE = " ";
    private static final String NUMBER_PATTERN = "[0-9]";
    private static final String MANY_SPACES = "[ ]+";
    private static final Map<String, String> ALPHABETICAL = new LinkedHashMap<>();
    private static final Map<Character, Character> MISTAKES = new LinkedHashMap<>();
    private static final String smallKyrilic = "абвгдеёжзиёклмнопрстуфхцчшщъыьэюя";

    static {
        ALPHABETICAL.put("[Ww]", "ш");
        ALPHABETICAL.put("[Ww][']", "щ");
        ALPHABETICAL.put("[']", "ь");
        ALPHABETICAL.put("[Yy][Uu]", "ю");
        ALPHABETICAL.put("[Yy][Aa]", "я");
        ALPHABETICAL.put("[Yy][Oo]", "ё");
        ALPHABETICAL.put("[Ii][Jj]", "й");
        ALPHABETICAL.put("[Cc][Hh]", "ч");
        ALPHABETICAL.put("[Ss][Hh]", "ш");

        ALPHABETICAL.put("[Aa]", "а");
        ALPHABETICAL.put("[Bb]", "б");
        ALPHABETICAL.put("[Vv]", "в");
        ALPHABETICAL.put("[Gg]", "г");
        ALPHABETICAL.put("[Dd]", "д");
        ALPHABETICAL.put("[Ee]", "е");
        ALPHABETICAL.put("[Jj]", "ж");
        ALPHABETICAL.put("[Zz]", "з");
        ALPHABETICAL.put("[Ii]", "и");
        ALPHABETICAL.put("[Yy]", "й");
        ALPHABETICAL.put("[Kk]", "к");
        ALPHABETICAL.put("[Ll]", "л");
        ALPHABETICAL.put("[Mm]", "м");
        ALPHABETICAL.put("[Nn]", "н");
        ALPHABETICAL.put("[Oo]", "о");
        ALPHABETICAL.put("[Pp]", "п");
        ALPHABETICAL.put("[Rr]", "р");
        ALPHABETICAL.put("[Qq]", "к");
        ALPHABETICAL.put("[Ss]", "с");
        ALPHABETICAL.put("[Tt]", "т");
        ALPHABETICAL.put("[Uu]", "у");
        ALPHABETICAL.put("[Ff]", "ф");
        ALPHABETICAL.put("[Hh]", "х");
        ALPHABETICAL.put("[Cc]", "ц");
        ALPHABETICAL.put("[Xx]", "х");

        MISTAKES.put('й', 'ц');
        MISTAKES.put('ц', 'й');
        MISTAKES.put('ы', 'ц');
        MISTAKES.put('ф', 'ы');
        MISTAKES.put('в', 'а');
        MISTAKES.put('а', 'п');
        MISTAKES.put('п', 'а');
        MISTAKES.put('а', 'в');
        MISTAKES.put('я', 'ч');
        MISTAKES.put('ч', 'я');
        MISTAKES.put('с', 'м');
        MISTAKES.put('м', 'с');
        MISTAKES.put('к', 'у');
        MISTAKES.put('у', 'к');
        MISTAKES.put('к', 'е');
        MISTAKES.put('е', 'к');
        MISTAKES.put('ё', 'e');
        MISTAKES.put('и', 'м');
        MISTAKES.put('м', 'и');
        MISTAKES.put('т', 'и');
        MISTAKES.put('и', 'т');
        MISTAKES.put('т', 'ь');
        MISTAKES.put('б', 'ь');
        MISTAKES.put('ь', 'б');
        MISTAKES.put('ю', 'б');
        MISTAKES.put('б', 'ю');
        MISTAKES.put('ж', 'э');
        MISTAKES.put('э', 'ж');
        MISTAKES.put('о', 'а');
        MISTAKES.put('а', 'о');
        MISTAKES.put('р', 'п');
        MISTAKES.put('н', 'р');
        MISTAKES.put('е', 'н');
        MISTAKES.put('н', 'г');
        MISTAKES.put('г', 'н');
        MISTAKES.put('ш', 'г');
        MISTAKES.put('з', 'щ');
        MISTAKES.put('щ', 'з');
        MISTAKES.put('з', 'х');
        MISTAKES.put('х', 'з');
        MISTAKES.put('ъ', 'х');
        MISTAKES.put('х', 'ъ');
        MISTAKES.put('е', 'и');
        MISTAKES.put('и', 'е');
    }

    public static String littleKyrilic(String s) {
        if (s == null) return null;
        s = s.replaceAll(NUMBER_PATTERN, EMPTY_STRING);
        s = s.replaceAll(MANY_SPACES, SPACE);
        for (Map.Entry<String, String> entry : ALPHABETICAL.entrySet()) {
            s = s.replaceAll(entry.getKey(), entry.getValue());
        }
        return s.trim();
    }

    public static boolean onlyLittleKyrilic(String s) {
        if (s == null) return false;
        for (char c : s.toCharArray()) {
            boolean contains = false;
            for (char sk : smallKyrilic.toCharArray()) {
                if (sk == c) contains = true;
            }
            if (!contains) return false;
        }
        return true;
    }

    public static boolean canBeReplacedToLK(String s) {
        s = littleKyrilic(s);
        return onlyLittleKyrilic(s);
    }

    public static String[] makeErrors(String[] s, double prob) {
        if (prob < 0 || prob > 1) return null;
        for (int i = 0; i < s.length; i++) {
            if (Math.random() < prob) {
                s[i] = makeError(s[i]);
            }
        }
        return s;
    }

    public static String makeErrors(String s, double prob) {
        String[] res = makeErrors(split(s), prob);
        if (res != null) {
            return join(" ", res);
        }
        return null;
    }

    public static String[] split(String message) {
        return message.toLowerCase().split("( )*([.,!?:;])( )*| ");
    }
	
	private static String join(String del, String[] arr) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i]).append(del);
		}
		return sb.toString().trim();
	}

    private static String makeError(String s) {
        char[] chars = s.toCharArray();
        int selectedIndex = (int) (Math.random() * chars.length);
        char selected = chars[selectedIndex];
        List<Character> canBeUsed = new ArrayList<>();
        for (Map.Entry<Character, Character> entry : MISTAKES.entrySet()) {
            if (entry.getKey().equals(selected)) {
                canBeUsed.add(entry.getValue());
            }
        }
        if (canBeUsed.size() == 0) return new String(chars);
        chars[selectedIndex] = canBeUsed.get((int) (Math.random() * canBeUsed.size()));
        return new String(chars);
    }

    public static void main(String[] args) {
        System.out.println(littleKyrilic("lalka sasai testovi1y 214 zapusk uletaya acheshuenno"));
        System.out.println(Arrays.toString(makeErrors(new String[]{"привет", "как", "дела"}, 0.3)));
    }
}
