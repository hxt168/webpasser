/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: ArchiveUtils.java
 *   
 */
package com.hxt.webpasser.utils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class ArchiveUtils {

	
	
    public static String formatBytesForDisplay(long amount) {
    	long displayAmount =  amount;
        int unitPowerOf1024 = 0; 

        if(amount <= 0){
            return "0 B";
        }
        
        while(displayAmount>=1024 && unitPowerOf1024 < 4) {
            displayAmount = displayAmount / 1024;
            unitPowerOf1024++;
        }
        
        final String[] units = { " B", " KiB", " MiB", " GiB", " TiB" };
        
        // ensure at least 2 significant digits (#.#) for small displayValues
        int fractionDigits = (displayAmount < 10) ? 1 : 0; 
        return doubleToString(displayAmount, fractionDigits, fractionDigits) 
                   + units[unitPowerOf1024];
    }
    
    
    public static String doubleToString(double val, int maxFractionDigits){
        return doubleToString(val, maxFractionDigits, 0);
    }

    private static String doubleToString(double val, int maxFractionDigits, int minFractionDigits) {
        NumberFormat f = NumberFormat.getNumberInstance(Locale.US); 
        f.setMaximumFractionDigits(maxFractionDigits);
        f.setMinimumFractionDigits(minFractionDigits);
        return f.format(val); 
    }
	
}
