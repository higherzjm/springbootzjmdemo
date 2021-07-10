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
@Api(tags = "�ӽ���")
public class EncryptionAndDecryptionController {
    @GetMapping("/testEncryption/{plainValue}/{securityKey}")
    @ApiOperation(value = "���Լ���", notes = "���Լ���")
    public String StringtestEncryption(@ApiParam(name = "plainValue", value = "����") @PathVariable("plainValue") String plainValue,
                                    @ApiParam(name = "securityKey", value = "��Կ") @PathVariable("securityKey") String securityKey) {
        byte[] result = null;
        DESKeySpec desKeySpec = null; //ʵ����DES��Կ����
        try {
            desKeySpec = new DESKeySpec(Hex.decodeHex(securityKey));
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES"); //ʵ������Կ����
            Key convertSecretKey = factory.generateSecret(desKeySpec); //������Կ
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            // ����
            cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
            result = cipher.doFinal(plainValue.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Hex.encodeHexString(result);
    }

    @GetMapping("/testDecryption/{securityValue}/{securityKey}")
    @ApiOperation(value = "���Խ���", notes = "���Խ���")
    public String StringtestDecryption(@ApiParam(name = "securityValue", value = "����") @PathVariable("securityValue") String securityValue,
                                    @ApiParam(name = "securityKey", value = "��Կ") @PathVariable("securityKey") String securityKey) {
        String desStr = "";
        try {
            DESKeySpec desKeySpec = new DESKeySpec(Hex.decodeHex(securityKey)); //ʵ����DES��Կ����
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES"); //ʵ������Կ����
            Key convertSecretKey = factory.generateSecret(desKeySpec); //������Կ
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            // ����
            cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
            byte[] desResult = cipher.doFinal(Hex.decodeHex(securityValue));
            desStr = new String(desResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return desStr;
    }

    @GetMapping("/generateSecurtykey")
    @ApiOperation(value = "����������Կ", notes = "����������Կ")
    public String generateSecurtykey() {
        // ����key//��������ָ���㷨��Կ��KeyGenerator����
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyGenerator.init(56);//��ʼ������Կ������,ʹ�����ȷ������Կ��С
        SecretKey secretKey = keyGenerator.generateKey();//����һ����Կ
        byte[] bs = secretKey.getEncoded();
        String encodeHexString = Hex.encodeHexString(bs);
        log.info("��ԿΪ:"+encodeHexString);
        return encodeHexString;
    }


}
