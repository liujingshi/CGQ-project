package com.ljscode.util;

public abstract class ParseSystemUtil {
    /**
     * 获得倒序二进制数据
     * @param hexString
     * @return
     */
    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0)
            return null;
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    public static double bin2DecXiao(String binXStr){
        double decX = 0.0;
        //位数
        int k =0;
        for(int i=0;i<binXStr.length();i++){
            int exp = binXStr.charAt(i)-'0';
            exp = -(i+1)*exp;
            if(exp!=0) {
                decX += Math.pow(2, exp);
            }
        }
        return decX;
    }
}
