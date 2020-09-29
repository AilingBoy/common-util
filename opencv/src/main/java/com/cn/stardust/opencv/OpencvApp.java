package com.cn.stardust.opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * @author stardust
 * @date 2020/9/29 11:15
 */
public class OpencvApp {

    //加载OpenCV动态链接库
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        Mat mat = Imgcodecs.imread("C:\\Users\\14256\\Desktop\\sheep.png");
        System.out.println(mat.dump());
    }
}
