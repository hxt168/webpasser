/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: CommonUtil.java
 *   
 */
package com.hxt.webpasser.utils;

import com.alibaba.fastjson.JSON;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 开发人员: hanxuetong <br>
 * 开发时间: 2015-8-10 <br>
 * 审核人员:  <br>
 * 相关文档:  <br>
 * 修改记录:  <br>
 * 修改日期 修改人员 修改说明  <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class CommonUtil {
	
    public static String toJson(Object obj) {

        return JSON.toJSONString(obj);
    }

	public static byte[] long2ByteArray(long l) {
		byte[] array = new byte[8];
		int i, shift;
		for (i = 0, shift = 56; i < 8; i++, shift -= 8) {
			array[i] = (byte) (0xFF & (l >> shift));
		}
		return array;
	}

	public static byte[] int2ByteArray(int value) {
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			int offset = (3 - i) * 8;
			b[i] = (byte) ((value >>> offset) & 0xFF);
		}
		return b;
	}
	
	public static int byteArray2Int(byte[] b) {
		int value = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (b[i] & 0x000000FF) << shift;
		}
		return value;
	}

	public static long byteArray2Long(byte[] b) {
		int value = 0;
		for (int i = 0; i < 8; i++) {
			int shift = (8 - 1 - i) * 8;
			value += (b[i] & 0x000000FF) << shift;
		}
		return value;
	}
}
