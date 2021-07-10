package com.zjm.base.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * @author zhujianming
 */
@RequestMapping("/base/encryptionAndDecryption")
@RestController
@Slf4j
@Api(tags = "加解密")
public class EncryptionAndDecryptionController {
    @GetMapping("/testEncryption/{plainValue}/{securityKey}")
    @ApiOperation(value = "测试加密", notes = "测试加密")
    public String StringtestEncryption(@ApiParam(name = "plainValue", value = "明文") @PathVariable("plainValue") String plainValue,
                                    @ApiParam(name = "securityKey", value = "秘钥") @PathVariable("securityKey") String securityKey) {
        byte[] result = null;
        DESKeySpec desKeySpec = null; //实例化DES密钥规则
        try {
            desKeySpec = new DESKeySpec(Hex.decodeHex(securityKey));
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES"); //实例化密钥工厂
            Key convertSecretKey = factory.generateSecret(desKeySpec); //生成密钥
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            // 加密
            cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
            result = cipher.doFinal(plainValue.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Hex.encodeHexString(result);
    }

    @GetMapping("/testDecryption/{securityValue}/{securityKey}")
    @ApiOperation(value = "测试解密", notes = "测试解密")
    public String StringtestDecryption(@ApiParam(name = "securityValue", value = "密文") @PathVariable("securityValue") String securityValue,
                                    @ApiParam(name = "securityKey", value = "秘钥") @PathVariable("securityKey") String securityKey) {
        String desStr = "";
        try {
            DESKeySpec desKeySpec = new DESKeySpec(Hex.decodeHex(securityKey)); //实例化DES密钥规则
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES"); //实例化密钥工厂
            Key convertSecretKey = factory.generateSecret(desKeySpec); //生成密钥
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
            byte[] desResult = cipher.doFinal(Hex.decodeHex(securityValue));
            desStr = new String(desResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return desStr;
    }

    @GetMapping("/generateSecurtykey")
    @ApiOperation(value = "测试生成秘钥", notes = "测试生成秘钥")
    public String generateSecurtykey() {
        // 生成key//返回生成指定算法密钥的KeyGenerator对象
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyGenerator.init(56);//初始化此密钥生成器,使其具有确定的密钥大小
        SecretKey secretKey = keyGenerator.generateKey();//生成一个密钥
        byte[] bs = secretKey.getEncoded();
        String encodeHexString = Hex.encodeHexString(bs);
        log.info("秘钥为:"+encodeHexString);
        return encodeHexString;
    }


}
