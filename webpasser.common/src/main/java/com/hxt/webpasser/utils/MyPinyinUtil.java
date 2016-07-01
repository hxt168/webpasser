package com.hxt.webpasser.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class MyPinyinUtil {
	
	/**
	 * 如果拼音很长，取前10个中文的拼音
	 * @param zhongwen
	 * @return
	 */
	public static String[] getLengthPinyin(String zhongwen)
	{
		if(zhongwen.length()>10)
		{
			return getPinYin(zhongwen.substring(0, 10));
		}
		return getPinYin(zhongwen);
	}

	/**
	 * 返回拼音和拼音首字母
	 * @param zhongwen
	 * @return
	 */
	public static String[] getPinYin(String zhongwen)    {   

		StringBuilder shortPinYin = new StringBuilder();  
        StringBuilder zhongWenPinYin = new StringBuilder();   
        char[] chars = zhongwen.toCharArray();   
  
        for (int i = 0; i < chars.length; i++) {   
            String[] pinYin=null;
			try {
				pinYin = PinyinHelper.toHanyuPinyinStringArray(chars[i], getDefaultOutputFormat());
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
            // 当转换不是中文字符时,返回null   
            if (pinYin != null) {   
            	zhongWenPinYin.append(pinYin[0]);
            	shortPinYin.append(getFirstChar(pinYin[0]));
            } else {  
            	if(chars[i]>='1'&&chars[i]<='9')
            		zhongWenPinYin.append(chars[i]);  
                
            }   
        }   
        String[] repinyin=new String[2];
        repinyin[0]=zhongWenPinYin.toString();
        repinyin[1]=shortPinYin.toString();
   
        return repinyin;   
    }   
  
    /**  
     * Default Format 默认输出格式  
     *   
     * @return  
     */  
    private static HanyuPinyinOutputFormat getDefaultOutputFormat() {   
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();   
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写   
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 没有音调数字   
        format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);// u显示   
  
        return format;   
    }   
  
    /**  
     * Capitalize 首字母大写  
     *   
     * @param s  
     * @return  
     */  
   private static String capitalize(String s) {   
        char ch[];   
        ch = s.toCharArray();   
        if (ch[0] >= 'a' && ch[0] <= 'z') {   
            ch[0] = (char) (ch[0] - 32);   
        }   
        String newString = new String(ch);   
        return newString;   
    }   
  
   /**
    * 
    */
   private static char getFirstChar(String s)
   {
	   return s.charAt(0);
   }
	
}
