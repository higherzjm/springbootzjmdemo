package com.zjm.my_hash;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author zhujianming
 */
@Slf4j
public class MyHash {



    @Test
    public void test2() {
        log.info("toHexString:"+Integer.toHexString(11));
    }
    /**
     * 获取字符串的hash值
     */
    @Test
    public void test1() {
        String hashValue=this.strToHashKey("1");
        log.info("hshValue:"+hashValue);
    }

    String strToHashKey(String k) {
        String tmpKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(k.getBytes());
            tmpKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            tmpKey = String.valueOf(k.hashCode());
        }
        return tmpKey;
    }

    String bytesToHexString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(0xFF & b[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();

    }
}
