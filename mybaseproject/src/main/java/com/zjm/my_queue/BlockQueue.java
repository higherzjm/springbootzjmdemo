package com.zjm.my_queue;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 阻塞队列，例子以发邮件为例，队列中有邮件就发邮件，没有就不要发
 */
@Slf4j
public class BlockQueue {
  //阻塞队列
  private BlockingDeque<String> blockingDeque = new LinkedBlockingDeque<>(10);
  public static void main(String[] args){
      BlockQueue myQueue=new BlockQueue();
      myQueue.start();
      myQueue.queueTake();

  }
  public void start(){
      Thread thread=new Thread(new MyThread());
      thread.start();
  }
  public void  queueTake(){
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
              //每次并发向队列中添加5个元素
              IntStream.rangeClosed(1,5).mapToObj(i2-> blockingDeque.add("name:"+i2)).collect(Collectors.toList());
              try {
                  Thread.sleep(2000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
      }
  }

}
