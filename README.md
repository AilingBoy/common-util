# common-util  常用工具集合

#### maven依赖
    <dependency>
             <groupId>com.github.oraclexing</groupId>
             <artifactId>common-utils</artifactId>
             <version>1.0.0-SNAPSHOT</version>
    </dependency>
 
##### 请检查maven是否启用snapshot
    maven 的安装目录 修改settings.xml文件，<profiles>节点中添加以下配置
      <profile>
          <id>唯一id</id>
          <activation>
            <activeByDefault>true</activeByDefault>
          </activation>
    	  <repositories>
    		   <repository>
    			 <id>snapshots-repo</id>
    			 <url>https://oss.sonatype.org/content/repositories/snapshots</url>
    			 <releases><enabled>true</enabled></releases>
    			 <snapshots><enabled>true</enabled></snapshots>
    		   </repository>
    	   </repositories>
        </profile>

#### 1.对象复制
    . 借助 org.nustaq.serialization.FSTConfiguration类进行序列化与反序列化，实现复杂对象的深度复制
    . FstUtil 类静态方法使用泛型，美化调用方(防止写过多的强制类型转换代码)


#### 2.动态加载
    . HotLoader 静态方法start()为入口函数
    . 入参为String dir ，系统会监听所有该目录下的.java文件变动
        * 文件的创建与修改都会触发动态编译与加载
        * 要求是待监听的类文件需要实现Live接口
        * 当文件被修改，且编译加载成功后，会立刻执行run()方法

#### 3.二维码生成与解析
    . QRCodeGen 类
    . 支持嵌入中间logo图(本地/网络来源)
    . 解析二维码，得到字符串内容
    
#### 4.AES加解密
    . AESUtil 类

#### 5.指定class的所在包扫描
    . ClassScanner 类 ,使用方式请参考test目录下的ClassScannerTest.java 文件
               
#### 6.Spring 代码生成器，
    . 包含Model,Mapper,Service,ServiceImpl 四种结构生成
    . 详细内容请参看codegen包，使用方式请参考com.cn.stardust.star.codegen.CodeGenerateTest。

#### 7.Json对象复制，
    . 基于alibaba fastjson 的 JSONObject/JSONArray 对象复制,确保待复制模板的格式正确即可.
    . 详细内容请参看com.cn.stardust.star.copy.JsonUtil。

