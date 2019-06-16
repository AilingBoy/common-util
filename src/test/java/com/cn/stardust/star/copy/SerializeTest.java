package com.cn.stardust.star.copy;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;
import sun.misc.Unsafe;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description: com.cn.stardust.star
 * Created by Oracle on 2019/6/12 19:16
 */
public class SerializeTest {

    @Test
    public void serializeRunTest()throws Exception{
        serializeTest();
        objectCopyTest();
        listCopyTest();
        arrayTest();
        setCopyTest();
        unSafeTest();
        reflectTest();
    }


    public void serializeTest()throws Exception{
        ConcurrentHashMap<String,Object> concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.put("date",new Date());
        concurrentHashMap.put("localTime", LocalTime.now());
        concurrentHashMap.put("localDate", LocalDate.now());
        Phone phone = new Phone();
        phone.setColor("蓝色");
        phone.setName("华为");
        phone.setPrice(BigDecimal.valueOf(1399.99));
        phone.setLocalDateTime(LocalDateTime.now());
        phone.setConcurrentHashMap(concurrentHashMap);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(FstUtil.serializer(phone));
        Phone copy = FstUtil.unserializer(outputStream.toByteArray());
        System.out.println(phone.equals(copy));
    }

    public void objectCopyTest(){
        ConcurrentHashMap<String,Object> concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.put("date",new Date());
        concurrentHashMap.put("localTime", LocalTime.now());
        concurrentHashMap.put("localDate", LocalDate.now());
        Phone phone = new Phone();
        phone.setColor("蓝色");
        phone.setName("华为");
        phone.setPrice(BigDecimal.valueOf(1399.99));
        phone.setLocalDateTime(LocalDateTime.now());
        phone.setConcurrentHashMap(concurrentHashMap);
        Phone copy = FstUtil.copyObject(phone);
        System.out.println(phone.equals(copy));
    }

    public void listCopyTest(){
        ConcurrentHashMap<String,Object> concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.put("date",new Date());
        concurrentHashMap.put("localTime", LocalTime.now());
        concurrentHashMap.put("localDate", LocalDate.now());
        Phone phone = new Phone();
        phone.setColor("蓝色");
        phone.setName("华为");
        phone.setPrice(BigDecimal.valueOf(1399.99));
        phone.setLocalDateTime(LocalDateTime.now());
        phone.setConcurrentHashMap(concurrentHashMap);
        List<Phone> phones = Lists.newArrayList(phone,FstUtil.copyObject(phone),FstUtil.copyObject(phone),FstUtil.copyObject(phone));
        List<Phone> copys = FstUtil.copyObject(phones);
        System.out.println(phones.equals(copys));
    }



    public void arrayTest(){
        ConcurrentHashMap<String,Object> concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.put("date",new Date());
        concurrentHashMap.put("localTime", LocalTime.now());
        concurrentHashMap.put("localDate", LocalDate.now());
        Phone phone = new Phone();
        phone.setColor("蓝色");
        phone.setName("华为");
        phone.setPrice(BigDecimal.valueOf(1399.99));
        phone.setLocalDateTime(LocalDateTime.now());
        phone.setConcurrentHashMap(concurrentHashMap);
        Phone[] phones = {phone,FstUtil.copyObject(phone),FstUtil.copyObject(phone),FstUtil.copyObject(phone),FstUtil.copyObject(phone)};
        Phone[] copys = FstUtil.copyObject(phones);
        System.out.println(phones.equals(copys));
    }


    public void setCopyTest(){
        ConcurrentHashMap<String,Object> concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.put("date",new Date());
        concurrentHashMap.put("localTime", LocalTime.now());
        concurrentHashMap.put("localDate", LocalDate.now());
        Phone phone = new Phone();
        phone.setColor("蓝色");
        phone.setName("华为");
        phone.setPrice(BigDecimal.valueOf(1399.99));
        phone.setLocalDateTime(LocalDateTime.now());
        phone.setConcurrentHashMap(concurrentHashMap);
        Set<Phone> phones = Sets.newHashSet(phone, FstUtil.copyObject(phone), FstUtil.copyObject(phone), FstUtil.copyObject(phone));
        Set<Phone> copys = FstUtil.copyObject(phones);
        System.out.println(phones.equals(copys));
    }


    /**
     * 反序列化创建对象本质
     * @throws Exception
     */
    public void unSafeTest()throws Exception{
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe)field.get(null);
        ConcurrentHashMap<String,Object> concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.put("date",new Date());
        concurrentHashMap.put("localTime", LocalTime.now());
        concurrentHashMap.put("localDate", LocalDate.now());
        Phone phone = (Phone) unsafe.allocateInstance(Phone.class);
        phone.setColor("蓝色");
        phone.setName("华为");
        phone.setPrice(BigDecimal.valueOf(1399.99));
        phone.setLocalDateTime(LocalDateTime.now());
        phone.setConcurrentHashMap(concurrentHashMap);
        System.out.println(JSON.toJSONString(phone));
        for (Field f: Phone.class.getDeclaredFields()){
        Long offset = unsafe.objectFieldOffset(f);
        if(f.getGenericType().equals(String.class)){
            unsafe.getAndSetObject(phone,offset,f.getName());
            }
        }
        System.out.println(JSON.toJSONString(phone));
    }

    /**
     * 反射设置属性
     * @throws Exception
     */
    public void reflectTest()throws Exception{

        /**
         * Constructor 无法创建私有的构造方法的对象
         */
        Constructor constructor = Phone.class.getConstructor();
        constructor.setAccessible(true);
        Phone p1 = (Phone) constructor.newInstance();

        /**
         * class for name 无法创建私有化构造方法的对象
          */
        Phone phone = (Phone)Class.forName(Phone.class.getName()).newInstance();

        /**
         * 跳过私有set方法设置属性值,可行
         */
        for (Field f: Phone.class.getDeclaredFields()){
            if(f.getGenericType().equals(String.class)){
                f.setAccessible(true);
                f.set(phone,f.getName());
            }
        }
        System.out.println(JSON.toJSONString(phone));

        /**
         * 测试直接调用私有set方法,可行
         */
        for(Method m: Phone.class.getDeclaredMethods()){
            if(m.getName().startsWith("set") && m.getParameterTypes()[0].equals(String.class)){
                m.setAccessible(true);
                m.invoke(phone,"method==="+m.getName());
            }
        }
        System.out.println(JSON.toJSONString(phone));
    }
}