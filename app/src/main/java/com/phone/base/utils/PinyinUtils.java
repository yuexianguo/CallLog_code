package com.phone.base.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtils {
    /**
     * 得到 全拼
     * @param
     * @return
     */
//    public static String getPingYin(String str) {
//        char[] t1 = null;
//        t1 = str.toCharArray();
//        String[] t2 = new String[t1.length];
//        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
//        t3.setCaseType(HanyuPinyinCaseType.UPPERCASE);
//        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
//        String t4 = "";
//        int t0 = t1.length;
//        try {
//            for (int i = 0; i < t0; i++) {
////判断是否为汉字字符
//                if (java.lang.Character.toString(t1[i]).matches(
//                        "[\\u4E00-\\u9FA5]+")) {
//                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
//                    t4 += t2[0];
//                } else {
//                    t4 +=  java.lang.Character.toString(t1[i]);
//                }
//            }
//            return t4;
//        } catch (BadHanyuPinyinOutputFormatCombination e1) {
//            e1.printStackTrace();
//        }
//        return t4;
//    }

    public static String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        char[] input = inputString.trim().toCharArray();
        String output = "";

        try {
            for (int i = 0; i < input.length; i++) {
                if (java.lang.Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
                    output += temp[0];
                } else
                    output += java.lang.Character.toString(input[i]);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return output;
    }

}
