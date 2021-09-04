package com.zjm.variousAlgorithms.consistentHashing.ConsistentHash;

import com.zjm.variousAlgorithms.consistentHashing.HashAlgorithm.HashService;

import java.util.List;

/**
 * consistentHashing interface
 * @author cartoon
 * @since  10/01/2021
 * @version 1.1
 */
public interface ConsistentHashing {

    /**
     * put data to my_hash loop
     * @param data data list
     * @return put result
     */
    boolean putData(List<String> data);

    /**
     * put data to my_hash loop
     * @param data data
     * @return put result
     */
    boolean putData(String data);

    /**
     * remove node from my_hash loop
     * @param node removing node
     * @return remove result
     */
    boolean removeNode(String node);

    /**
     * add node to my_hash loop
     * @param node adding node
     * @return add result
     */
    boolean addNode(String node);

    /**
     * inject my_hash method to my_hash loop
     * @param hashService my_hash method
     * @throws UnsupportedOperationException if loop already has node
     */
    void setHashMethod(HashService hashService) throws UnsupportedOperationException;

    /**
     * print all data in loop according ascending my_hash value with nodes
     */
    void printAllData();

}
