package com.zjm.my_queue;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 队列
 */
@Slf4j
public class MyQueue {
  //阻塞队列
  private BlockingDeque<String> blockingDeque = new LinkedBlockingDeque<>();
  public static void main(String[] args){
      MyQueue myQueue=new MyQueue();
      myQueue.start();
      myQueue.quequeTake();

  }
  public void start(){
      Thread thread=new Thread(new MyThread());
      thread.start();
  }
  public void  quequeTake(){
      while (true){
          String str="";
          try {
               str=blockingDeque.take();//阻塞方式获取队列信息，假如没有数据就会停留在这边，直到有数据才往下执行
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
          log.info("str:"+str);
      }
  }


  class MyThread implements  Runnable{

      @Override
      public void run() {
          for (int i=0;i<10;i++){
              blockingDeque.add("name:"+i);
              try {
                  Thread.sleep(2000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
      }
  }

}
