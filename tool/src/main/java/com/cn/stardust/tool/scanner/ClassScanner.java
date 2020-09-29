package com.cn.stardust.tool.scanner;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://github.com/KnowNoUnknown
 * <p>
 *
 *
 * 根据.class,迭代扫描该类所在包的所有.class文件
 * 使用树状的数据结构，进行关系依赖引用。
 *
 *
 * 详细请参考{@link Clazz}和
 * {@link Package}
 *
 * @author stardust
 * @version 1.0.0
 */
public class ClassScanner {

    /**
     * 获取指定类的所在路径，开始扫描
     * @param clazz 待扫描包的class
     * @return 返回的Package，建议仅仅对其进行读取即可，如非必要请勿修改。有可能可能导致引用错乱
     * @throws Exception
     */
    public static Package scan(Class<?> clazz)throws Exception{
        String rootPath = getRootPath(clazz);
        File dic = new File(rootPath);
        Package pack = new Package();
        pack.setPackageName(clazz.getPackage().getName());
        scanPack(pack,dic.list(),rootPath);
        return pack;
    }

    /**
     * 指定路径开始扫描.class文件，文件夹
     * @param pack
     * @param fileNames
     * @param path
     */
    private static void scanPack(Package pack, String[] fileNames, String path){
        List<String> javaFileNames = Arrays.stream(fileNames)
                .filter(e -> (e.lastIndexOf('.') != -1 && e.endsWith(".class")))
                .collect(Collectors.toList());
        // 加载.class类
        for(String fileName : javaFileNames){
            try {
            Clazz clazz = new Clazz(pack,Class.forName(pack.getPackageName()
                    +'.'
                    +fileName.substring(0,fileName.lastIndexOf('.'))));
            pack.getClazzes().add(clazz);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        /**
         * 文件夹名称集合
         */
        List<String> dicFileNames = Arrays.stream(fileNames)
                .filter(e ->{
                        File file = new File(path+File.separator+e);
                        return file.exists() && file.isDirectory();
                })
                .collect(Collectors.toList());
        dicFileNames.forEach(e -> {
            // 创建子包
            Package pac = new Package(pack,pack.getPackageName()+'.'+e);
            // 添加打父包引用
            pack.getSubPackages().add(pac);
            scanPack(pac,new File(path+File.separator+e).list(),path+File.separator+e);
        });
    }

    /**
     * 根据class获取所在类路径
     * 文件路径分隔符已经适配不同系统
     * @param clazz
     * @return
     * @throws Exception
     */
    private static String getRootPath(Class<?> clazz)throws Exception{
        String rootPath = clazz.getResource("/").toURI().getPath();
        rootPath = rootPath.replaceAll("\\\\","/");
        rootPath = rootPath + clazz.getPackage().getName().replaceAll("\\.","/");
        rootPath = Arrays.stream(rootPath.split("/")).reduce((a,b)->a+File.separator+b).get();
        return rootPath;
    }

    /**
     * 获取 Package 下所有class
     * @param classes 待返回class 集合的容器，不能为空
     * @param pack 带扫描的包
     * @return
     */
    public static List<Class> getAllClass(List<Class> classes , Package pack){
        if(pack.getClazzes().size() == 0 && pack.getSubPackages().size() == 0){
            // 结束条件
            return classes;
        }
        classes.addAll(pack.getClazzes().stream().map(e->e.getClazz()).collect(Collectors.toList()));
        pack.getSubPackages().forEach(e -> getAllClass(classes,e));
        return classes;
    }
}