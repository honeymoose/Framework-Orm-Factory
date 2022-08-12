package com.ukg.framework.common.tasks;

import org.apache.commons.lang.WordUtils;

public class Task2 {

    public static String capitalizeFirstLetters(String s) {
        /*
         * Please implement this method to capitalize all first letters of the
         * words in the given String. All other symbols shall remain intact. If
         * a word does not start with a letter, it shall remain intact also.
         * Assume that the parameter String can only contain spaces and
         * alphanumeric characters.
         *
         * NOTE: please keep in mind that the words can be divided by single or
         * multiple spaces. The spaces also can be found at the beginning or the
         * end of the parameter string, and you need to preserve them.
         *
         * Please add a main method which evaluates and displays one or more
         * test cases.
         */

        char[] delimiters = null;

        if (s == null || s.length() == 0) {
            return s;
        }
        int strLen = s.length();
        StringBuffer buffer = new StringBuffer(strLen);
        boolean capitalizeNext = true;
        for (int i = 0; i < strLen; i++) {
            char ch = s.charAt(i);

            if (isDelimiter(ch, delimiters)) {
                buffer.append(ch);
                capitalizeNext = true;
            } else if (capitalizeNext) {
                buffer.append(Character.toTitleCase(ch));
                capitalizeNext = false;
            } else {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }

    private static boolean isDelimiter(char ch, char[] delimiters) {
        if (delimiters == null) {
            return Character.isWhitespace(ch);
        }
        for (int i = 0, isize = delimiters.length; i < isize; i++) {
            if (ch == delimiters[i]) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String in = "   2 drer drse   23 wer   1 ";
        System.out.println(capitalizeFirstLetters(in));

        // FYI: Apache WordUtils has capitalize function to do this.
        // System.out.println(WordUtils.capitalize(in));
    }


}
