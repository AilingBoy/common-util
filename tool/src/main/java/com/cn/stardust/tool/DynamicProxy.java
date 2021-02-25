package com.cn.stardust.tool;

import com.cn.stardust.tool.request.IRequest;
import com.cn.stardust.tool.request.RequestTest;

import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <p>
 *
 * @author stardust
 * @version 1.0.0
 * 2021/2/8 14:30
 *
 * JDK 自身的动态代理
 */
public class DynamicProxy implements InvocationHandler {

    private Object target;

    public DynamicProxy(Object target) {
        super();
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("=========<before>=========");
        Object result = method.invoke(target, args);
        System.out.println("=========<after>=========");
        return result;
    }

    public Object getProxy(){

        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), target.getClass().getInterfaces(), this);
    }

    public static void main(String[] args)throws Exception {
        RequestTest requestTest = new RequestTest();
        DynamicProxy handler = new DynamicProxy(requestTest);
        IRequest proxy = (IRequest) handler.getProxy();
        writeClassToDisk("C:\\Develop\\Project\\common-util\\tool\\target\\classes\\com\\cn\\stardust\\tool\\"+proxy.getClass().getName().substring(proxy.getClass().getName().lastIndexOf('.')+1)+".class",proxy.getClass());
        proxy.request();
        proxy.print();
        proxy.printContent("hello , world");
        proxy.multiply(1234);
    }

    public static void writeClassToDisk(String path,Class clazz)throws Exception{

        byte[] classFile = ProxyGenerator.generateProxyClass("$proxy4", new Class[]{clazz});
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            fos.write(classFile);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(fos != null){
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

