/*
 * 系统名称: eden 1.0
 * 模块名称: catcher
 * 类 名 称: Sha256.java
 * 软件版权: 浙江榕基信息技术有限公司
 *   
 */
package com.hxt.webpasser.utils;
/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong@rongji.com <br>
 * 开发时间: 2014-12-10 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
    /**
     * 对字符串加密,加密算法使用MD5,SHA-1,SHA-256,默认使用SHA-256
     * 
     * @param strSrc
     *            要加密的字符串
     * @param encName
     *            加密类型
     * @return
     */
    public static String Encrypt(String strSrc, String encName) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {
            if (encName == null || encName.equals("")) {
                encName = "SHA-256";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
    
    public static void main(String args[]){
     String s=Encrypt.Encrypt("aaa3281390", "SHA-1");
     
     String pw=("476701371@qq.com" + s + "1418141234824" + "115ud&52DaRBaew");
     String sss=Encrypt.Encrypt(pw, "md5");
     System.out.println(sss);
    }
}
