package com.zjm.util;

import com.alibaba.fastjson.JSONObject;
import com.zjm.redis.base.Students;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 */
@Slf4j
public class DesCryptUtil {

    @Test
    public void test1() throws Exception {
        JSONObject jsonObject = new JSONObject();
        List<Students> studentsList = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            studentsList.add(new Students("张三" + i, 20 + i));
        }

        jsonObject.put("datas", studentsList);
        String KEY = "salaryReCheckKey";
        String enStr = encodeMsg(jsonObject.toJSONString(), KEY);
        log.info("enStr:" + enStr);
        String deStr = decodeMsg(enStr, KEY);
        log.info("deStr:" + deStr);
    }

    /**
     * DES加密
     */
    private String encodeMsg(String plainMsg, String securityKeyText) {
        try {
            SecureRandom random = new SecureRandom();
            SecretKey secureKey = initSecretKeyFactory(securityKeyText);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secureKey, random);
            //现在，获取数据并加密
            byte[] temp = Base64.encodeBase64(cipher.doFinal(plainMsg.getBytes()));
            return IOUtils.toString(temp, "UTF-8");
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * DES解密
     */
    private String decodeMsg(String securityMsg, String securityKeyText) throws Exception {
        SecretKey secureKey = initSecretKeyFactory(securityKeyText);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, secureKey, random);
        // 真正开始解密操作
        return IOUtils.toString(cipher.doFinal(Base64.decodeBase64(securityMsg)), "UTF-8");
    }

    private SecretKey initSecretKeyFactory(String securityKeyText) throws Exception {

        // 创建一个DESKeySpec对象
        String CODE_TYPE = "UTF-8";
        DESKeySpec desKey = new DESKeySpec(securityKeyText.getBytes(CODE_TYPE));
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey secureKey = keyFactory.generateSecret(desKey);
        return secureKey;
    }
}
