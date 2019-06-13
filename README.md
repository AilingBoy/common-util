# common-util  常用工具集合

#### maven依赖
&ensp;&lt;dependency&gt;<br/>
   &ensp;&ensp;&ensp;&ensp;&lt;groupId&gt;com.github.oraclexing&lt;/groupId&gt;<br/>
   &ensp;&ensp;&ensp;&ensp;&lt;artifactId&gt;common-utils&lt;/artifactId&gt;<br/>
   &ensp;&ensp;&ensp;&ensp;&lt;version&gt;1.0.0&lt;/version&gt;<br/>
&ensp;&lt;/dependency&gt;<br/>

#### 1.对象复制
    . 借助 org.nustaq.serialization.FSTConfiguration类进行序列化与反序列化，实现复杂对象的深度复制
    . FstUtil 类静态方法使用泛型，美化调用放(防止写过多的强制类型转换代码)


#### 2.动态加载
    . HotLoader 静态方法start()为入口函数
    . 入参为String dir ，系统会监听所有该目录下的.java文件变动
        * 文件的创建与修改都会触发动态编译与加载
        * 要求是待监听的类文件需要实现Live接口
        * 当编译加载成功后，会立刻执行run()方法
        
#### 3.待扩展更多功能 ...... &nbsp;&nbsp;&nbsp;&nbsp;;)

#### 目录结构
<pre>
src
├─main
│  ├─java
│  │  └─com
│  │      └─cn
│  │          └─stardust
│  │              └─star
│  │                  │  Common.java  
│  │                  │  
│  │                  ├─copy
│  │                  │      FstUtil.java &ensp 对象复制util
│  │                  │      
│  │                  └─hotloader
│  │                      │  HotLoader.java &ensp 动态加载入口类
│  │                      │  
│  │                      ├─basedo
│  │                      │      Live.java &ensp 需要动态加载类所必须实现的接口
│  │                      │      People.java &ensp 实现Live接口类
│  │                      │      
│  │                      ├─classload
│  │                      │      FileClassLoader.java &ensp 自定义类加载器
│  │                      │      
│  │                      ├─compile
│  │                      │      JCompiler.java &ensp 编译器
│  │                      │      MemoryJavaFileManager.java &ensp 编译结果封装类
│  │                      │      
│  │                      └─listen
│  │                              FileListener.java  &ensp 文件修改监听器
│  │                              
│  └─resources
└─test
    └─java
        └─com
            └─cn
                └─stardust
                    └─star
                        │  CommonTest.java
                        │  
                        ├─copy
                        │      Phone.java
                        │      SerializeTest.java
                        │      
                        └─hotloader
                                HotLoaderTest.java

</pre>        