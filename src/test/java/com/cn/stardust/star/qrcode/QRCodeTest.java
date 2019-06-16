package com.cn.stardust.star.qrcode;

import org.junit.Test;

/**
 * https://github.com/oraclexing
 * <p>
 *
 * @author stardust
 * @version 1.0.0
 *
 */

public class QRCodeTest {

    @Test
    public void qrcodeTest() throws Exception {
        // 存放在二维码中的内容
        String text = "生成二维码侧式-123456789";
        // 嵌入二维码的图片路径
        String imgPath = "D:/123.jpg";
        // 生成的二维码的路径及名称
        String destPath = "D:/qrCode.jpg";
        //生成二维码
        QRCodeGen qrCodeGen = new QRCodeGen();
        qrCodeGen.encode(text, imgPath, destPath, true);
        // 解析二维码
        String str = qrCodeGen.decode(destPath);
        // 打印出解析出的内容
        System.out.println(str);
    }
}