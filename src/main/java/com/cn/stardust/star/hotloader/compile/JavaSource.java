package com.cn.stardust.star.hotloader.compile;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * Description: com.cn.stardust.star
 * Created by Oracle on 2019/6/12 19:16
 */
public class JavaSource extends SimpleJavaFileObject {

    private String code;

    private ByteArrayOutputStream outPutStream;

    public JavaSource(String name, String code) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        outPutStream = new ByteArrayOutputStream();
        return outPutStream;
    }

    public byte[] getCompiledBytes() {
        return outPutStream.toByteArray();
    }
}