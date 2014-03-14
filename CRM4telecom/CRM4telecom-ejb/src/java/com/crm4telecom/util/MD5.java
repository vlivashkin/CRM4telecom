package com.crm4telecom.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class MD5 {

    public static String getHash(String password,String salt) {
        MessageDigest md5 ;        
        StringBuffer  hexString = new StringBuffer();
        try {
            md5 = MessageDigest.getInstance("md5");
            Date d = new Date();
            md5.reset();
            String str = salt+ password;
             md5.update(str.getBytes());
            byte messageDigest[] = md5.digest();
                        
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
        } 
        catch (NoSuchAlgorithmException e) {                        
            return e.toString();
        }
        return hexString.toString();
    }
    
}