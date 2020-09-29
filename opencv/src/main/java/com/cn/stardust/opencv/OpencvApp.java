package com.cn.stardust.opencv;

import org.opencv.core.Core;
import org.opencv.core.CvType;
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
        Mat mat = Imgcodecs.imread("C:\\Users\\14256\\Desktop\\bunny.jpg");
        Mat outMat = new Mat(mat.rows(),mat.cols(), CvType.CV_8SC3);
        double[] values;
        for(int i = 0 ; i < mat.rows();i++){
            for(int j = 0 ; j < mat.cols() ; j++){
                values = mat.get(i,j);
                /**
                 * B - G - R
                 * 依次对应三通道
                 */
                outMat.put(i,j,0,0,values[1]);
            }
        }
        Imgcodecs.imwrite("C:\\Users\\14256\\Desktop\\bunny_R.jpg",outMat);
        System.out.println("============= finished =============");
    }
}
