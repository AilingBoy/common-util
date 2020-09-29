package com.cn.stardust.tool.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.net.URL;
import java.util.Hashtable;


/**
 * https://github.com/KnowNoUnknown
 * <p>
 *
 * @author stardust
 * @version 1.0.0
 * <p>
 * 生成二维码，包含嵌入log图
 */

public class QRCodeGen {

    private static final String CHARSET = "utf-8";

    private static final String FORMAT_NAME = "JPG";

    /**
     * 二维码尺寸基础限制
     */
    private final Integer BASIC_QRCODE_SIZE = 100;

    /**
     * logo尺寸 基础限制
     */
    private final Integer BASIC_LOGO_SIZE = 20;

    /**
     * 二维码尺寸（默认300）
     */
    private int qrcodeSize = 300;

    /**
     * 中间logo尺寸（默认60）
     */
    private int logoSize = 60;

    /**
     * 无参构造器, 参数使用默认值
     */
    public QRCodeGen() {
    }

    /**
     * 构造器 两个包装类参数，均可为空，使用默认值
     *
     * @param qrcodeSize 二维码尺寸
     * @param logoSize   中间logo尺寸
     */
    public QRCodeGen(Integer qrcodeSize, Integer logoSize) {
        setQrcodeSize(qrcodeSize);
        setLogoSize(logoSize);
    }

    public void setQrcodeSize(int qrcodeSize) {
        if (qrcodeSize >= BASIC_QRCODE_SIZE) {
            this.qrcodeSize = qrcodeSize;
        }
    }

    public void setLogoSize(int logoSize) {
        if (logoSize >= BASIC_LOGO_SIZE) {
            this.logoSize = logoSize;
        }
    }

    /**
     * 创建 BufferedImage
     *
     * @param content      二维码文本内容
     * @param logo         logo 图片
     * @param needCompress 是否需要压缩
     * @return
     * @throws Exception
     */
    private BufferedImage createImage(String content, Image logo, boolean needCompress) throws Exception {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrcodeSize, qrcodeSize,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (null == logo) {
            return image;
        }
        // 插入logo图片
        this.insertImage(image, logo, needCompress);
        return image;
    }

    /**
     * 插入logo 图
     *
     * @param source       二维码图
     * @param src          log 图
     * @param needCompress 是否需要压缩
     * @throws Exception
     */
    private void insertImage(BufferedImage source, Image src, boolean needCompress) throws Exception {
        if (null == src) {
            System.err.println("logo 图片无法加载！");
            return;
        }
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) {
            // 压缩LOGO
            width = width > logoSize ? logoSize : width;
            height = height > logoSize ? logoSize : height;
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            // 绘制缩小后的图
            g.drawImage(image, 0, 0, null);
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (qrcodeSize - width) / 2;
        int y = (qrcodeSize - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 根据本地logo图创建二维码
     *
     * @param content      内容
     * @param imgPath      logo 图路径
     * @param destPath     输出二维码路径
     * @param needCompress 压缩
     * @throws Exception
     */
    public void encode(String content, String imgPath, String destPath, boolean needCompress) throws Exception {
        Image logo = null;
        try {
            logo = ImageIO.read(new File(imgPath));
        } catch (Exception e) {
        }
        BufferedImage image = this.createImage(content, logo, needCompress);
        mkdirs(destPath);
        ImageIO.write(image, FORMAT_NAME, new File(destPath));
    }

    /**
     * 从网络加载logo 图进行创建
     *
     * @param content      内容
     * @param logoUrl      网络logo图
     * @param needCompress 压缩
     * @return 二维码BufferedImage
     * @throws Exception
     */
    public BufferedImage encode(String content, URL logoUrl, boolean needCompress) throws Exception {
        Image logo = null;
        try {
            logo = ImageIO.read(logoUrl);
        } catch (Exception e) {
        }
        BufferedImage image = this.createImage(content, logo, needCompress);
        return image;
    }

    /**
     * 根据指定生成二维码的路径层级，创建目录
     *
     * @param destPath 目录
     */
    private void mkdirs(String destPath) {
        File file = new File(destPath);
        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 创建二维码
     *
     * @param content      二维码文本内容
     * @param logoImgPath  logo 图片路径
     * @param output       输出流，用于将产生的二维码输出
     * @param needCompress 需要压缩
     * @throws Exception
     */
    public void encode(String content, String logoImgPath, OutputStream output, boolean needCompress)
            throws Exception {
        Image logo = null;
        try {
            logo = ImageIO.read(new File(logoImgPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        BufferedImage image = this.createImage(content, logo, needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    /**
     * 解析本地文件二维码内容
     *
     * @param filePath 二维码文件路径
     * @return
     * @throws Exception
     */
    public String decode(String filePath) throws Exception {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.decode(image);
    }

    /**
     * 解析网络上二维码内容
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String decode(URL url) throws Exception {
        BufferedImage image = null;
        try {
            image = ImageIO.read(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.decode(image);
    }

    /**
     * 解析
     *
     * @param image 待二维码图片
     * @return 二维码内容
     * @throws Exception
     */
    public String decode(BufferedImage image) throws Exception {
        if (image == null) {
            return null;
        }
        Result result;
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Hashtable hints = new Hashtable();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }
}