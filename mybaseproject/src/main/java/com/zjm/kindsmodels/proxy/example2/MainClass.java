package com.zjm.kindsmodels.proxy.example2;


public class MainClass {
   public static void main(String[] args){
   ProxyHandler proxyHandler=new ProxyHandler();
   IPrinter printer=(IPrinter) proxyHandler.newProxyInstance(new Printer());
   printer.print(10);
 }
}

