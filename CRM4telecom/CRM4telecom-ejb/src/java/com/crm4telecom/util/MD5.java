package com.crm4telecom.util;

import com.crm4telecom.mail.MailManager;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.LoggerFactory;

public class MD5 {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MD5.class);

    public static String getHash(String password, String salt) {
        MessageDigest md5;
        StringBuilder hexString = new StringBuilder();
        try {
            md5 = MessageDigest.getInstance("md5");
            Date d = new Date();
            md5.reset();
            String str = salt + password;
            md5.update(str.getBytes());
            byte messageDigest[] = md5.digest();

            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
        } catch (NoSuchAlgorithmException e) {
                logger.error("Can't find md5 algoritm , so ", e);
        }
        return hexString.toString();
    }

    public static List<String> getSaltPassword(String password, String login) throws NoSuchAlgorithmException {

        MessageDigest md5;
        StringBuilder hexString = new StringBuilder();

        List<String> l = new ArrayList();
        md5 = MessageDigest.getInstance("md5");
        Date d = new Date();

        md5.reset();
        String salt = Long.toString(d.getTime()) + "!SD@#$D@XC" + password;
        StringBuilder salthash = new StringBuilder();
        md5.update(salt.getBytes());
        byte saltDigest[] = md5.digest();

        for (int i = 0; i < saltDigest.length; i++) {
            salthash.append(Integer.toHexString(0xFF & saltDigest[i]));
        }
        salt = salthash.toString();
        l.add(salthash.toString());
        String ss = salt + password;
        md5.reset();

        md5.update(ss.getBytes());

        byte messageDigest[] = md5.digest();

        for (int i = 0; i < messageDigest.length; i++) {
            hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
        }
        l.add(hexString.toString());

        return l;
    }

}
