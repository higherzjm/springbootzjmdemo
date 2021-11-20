package com.zjm.util;

import com.alibaba.fastjson.JSONObject;
import com.zjm.base.StudentInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhujianming
 * 对称加解密util
 */
@Slf4j
public class SymmetricEncDecUtil {
     //全局变量，用以存放秘钥和cipher工具对象
    private ThreadLocal<Map<String, Object>> local = new ThreadLocal<>();

    /**
     * 加解密对象
     */
    @Test
    public void test1() throws Exception {
        String secKey = generateSeckey();
        initCipher(secKey);
        JSONObject data = new JSONObject();
        data.put("studentInfoList", Arrays.asList(new StudentInfo("张三", 10), new StudentInfo("李四", 20)));
        data.put("studentInfoList2", Arrays.asList(new StudentInfo("小三", 99), new StudentInfo("小四", 100)));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("secKey", secKey);
        jsonObject.put("data", dataEnc(data.toJSONString()));
        log.info(jsonObject.toJSONString());

        String dataDecryptionStr = dataDec(jsonObject.get("data").toString());
        log.info("dataDecryptionStr:" + dataDecryptionStr);
        JSONObject dataDecryptionJson = JSONObject.parseObject(dataDecryptionStr);

        log.info("studentInfoList2:" + dataDecryptionJson.get("studentInfoList2"));
        log.info("studentInfoList:" + dataDecryptionJson.get("studentInfoList"));

    }

    /**
     * 简单使用
     */
    @Test
    public void test2() throws Exception {
        String secKey = generateSeckey();
        initCipher(secKey);
        String msg = "我是中国人";
        String secMsg = dataEnc(msg);
        log.info("加密结果:{}", secKey);
        String decMsg = dataDec(secMsg);
        log.info("解密结果：{}", decMsg);
    }

    /**
     * @Description: 随机生成秘钥
     **/
    private static String generateSeckey() {
        // 生成key//返回生成指定算法密钥的KeyGenerator对象
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("DES");
        } catch (Exception e) {
            e.printStackTrace();
        }
        keyGenerator.init(56);//初始化此密钥生成器,使其具有确定的密钥大小
        SecretKey secretKey = keyGenerator.generateKey();//生成一个密钥
        byte[] bs = secretKey.getEncoded();
        String encodeHexString = Hex.encodeHexString(bs);
        return encodeHexString;
    }

    /**
     * 加密
     * @param plainValue 明文
     * @return
     * @throws Exception
     */
    private String dataEnc(String plainValue) throws Exception {
        Map<String, Object> map = local.get();
        Key key = (Key) map.get("key");
        Cipher cipher = (Cipher) map.get("cipher");
        // 加密
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(plainValue.getBytes());
        return Hex.encodeHexString(result);
    }

    private String dataDec(String securityValue) throws Exception {
        Map<String, Object> map = local.get();
        Key key = (Key) map.get("key");
        Cipher cipher = (Cipher) map.get("cipher");
        // 解密
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] desResult = cipher.doFinal(Hex.decodeHex(securityValue));
        return new String(desResult);
    }

    /**
     * 初始化秘钥工厂
     */
    private void initCipher(String securityKey) {
        try {
            DESKeySpec desKeySpec = new DESKeySpec(Hex.decodeHex(securityKey)); //实例化DES密钥规则
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES"); //实例化密钥工厂
            Key convertSecretKey = factory.generateSecret(desKeySpec); //生成密钥
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            Map<String, Object> map = new HashMap<>();
            map.put("key", convertSecretKey);
            map.put("cipher", cipher);
            local.set(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
