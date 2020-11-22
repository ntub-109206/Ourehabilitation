package com.example.myrehabilitaion;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt
{

    /**
     * 傳入文字內容，返回 SHA-256 串
     *
     * @param strText
     * @return
     */
    public String SHA256(final String strText)
    {
        return SHA(strText, "SHA-256");
    }

    /**
     * 傳入文字內容，返回 SHA-512 串
     *
     * @param strText
     * @return
     */
    public static String SHA512(final String strText)
    {
        return SHA(strText, "SHA-512");
    }

    /**
     * 字串 SHA 加密
     *
     * @param
     * @return
     */
    private static String SHA(final String strText, final String strType)
    {
        // 返回值
        String strResult = null;

        // 是否是有效字串
        if (strText != null && strText.length() > 0)
        {
            try
            {
                // SHA 加密開始
                // 建立加密物件 並傳入加密型別
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                // 傳入要加密的字串
                messageDigest.update(strText.getBytes());
                // 得到 byte 型別結果
                byte byteBuffer[] = messageDigest.digest();

                // 將 byte 轉換為 string
                StringBuffer strHexString = new StringBuffer();
                // 遍歷 byte buffer
                for (int i = 0; i < byteBuffer.length; i++)
                {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1)
                    {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回結果
                strResult = strHexString.toString();
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
        }

        return strResult;
    }
}