package com.zjm.commonalgorithms.maintest;

import com.zjm.commonalgorithms.ConsistentHash.ConsistentHashing;
import com.zjm.commonalgorithms.ConsistentHash.ConsistentHashingImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @Description: 不含虚拟节点的一致性哈希测试
 * @date 2021/4/12 19:17
 * @return
 */
@Slf4j
public class ConsistentHashingWithoutVirtualNodeTest {

    private ConsistentHashing consistentHashing;

    private String[] servers;

    private String[] data;

    @Before
    public void before() {
        servers = new String[]{"S000", "S111", "S222", "S333", "S555"};
        consistentHashing = new ConsistentHashingImpl(servers);
        data = new String[]{"D000", "D111", "D222", "D333", "D555"};
    }

    @Test
    public void testConsistentHashing() {
        for (String str : data) {
            Assert.assertTrue(consistentHashing.putData(str));
        }
        log.info("------------------------removeNode------------------");
        consistentHashing.removeNode("S333");
        log.info("-------------------addNode-----------------------");
        consistentHashing.addNode("S444");
        log.info("--------------------putData----------------------");
        consistentHashing.putData("D444");
        log.info("------------------------nodes_datas------------------");
        consistentHashing.printAllData();
    }
}
