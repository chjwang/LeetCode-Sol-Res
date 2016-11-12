import java.util.HashMap;
import java.util.Map;

/**
 *
 * sample input: sevenabsixde sample output: aaaaaaabdddddde 数字单词只可能是zero~nine
 */
public class CountAndSayWord {
    static Map<String, Integer> digits = new HashMap<String, Integer>()
    {{
        put("zero", 0);
        put("one", 1);
        put("two", 2);
        put("three", 3);
        put("four", 4);
        put("five", 5);
        put("six", 6);
        put("seven", 7);
        put("eight", 8);
        put("nine", 9);
    }};

    public static String countAndSay(String s) {
        // string shorter than 3
        if (s.length() <= 3)
            return s;

        int i = 0;
        StringBuilder sb = new StringBuilder();
        while (i < s.length()) {
            String subStr3 = null;
            if (s.length() >= i+3) subStr3 = s.substring(i, i + 3);
            String subStr4 = null;
            if (s.length() >= i+4) subStr4 = s.substring(i, i + 4);
            String subStr5 = null;
            if (s.length() >= i+5) subStr5 = s.substring(i, i + 5);

            Integer val3 = null;
            if (subStr3 != null) val3 = digits.get(subStr3);
            Integer val4 = null;
            if (subStr4 != null) val4 = digits.get(subStr4);
            Integer val5 = null;
            if (subStr5 != null) val5 = digits.get(subStr5);

            if (val3 != null) {
                for (int j = 0; j < val3; j++) {
                    sb.append(s.charAt(i + 3));
                }
                i += 4;
            } else if (val4 != null) {
                for (int j = 0; j < val4; j++) {
                    sb.append(s.charAt(i + 4));
                }
                i += 5;
            } else if (val5 != null) {
                for (int j = 0; j < val5; j++) {
                    sb.append(s.charAt(i + 5));
                }
                i += 6;
            } else {
                sb.append(s.charAt(i));
                i++;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(countAndSay("sevenabsixde"));
        System.out.println(countAndSay("zeroaonebsixdzeroe"));
    }
}
