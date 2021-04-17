package com.zjm.variousAlgorithms.consistentHashing.HashAlgorithm.impl;


import com.zjm.variousAlgorithms.consistentHashing.HashAlgorithm.HashService;

/**
 * @author cartoon
 * @date 2021/01/17
 */
public class SimpleHashServiceImpl implements HashService {

    @Override
    public int getHash(String originData) {
        int res = 0;
        for(char tempChar : originData.toCharArray()){
            if(tempChar >= '0' && tempChar <= '9'){
                res += tempChar;
            } else{
                res++;
            }
        }
        return res;
    }
}
