package com.cn.stardust.opencv;

import com.cn.stardust.opencv.fft.FFT;
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
        Mat sourceMat = Imgcodecs.imread("C:\\Users\\14256\\Desktop\\small_bunny.jpg");
        Mat mat = new Mat(Core.getOptimalDFTSize(sourceMat.rows()),Core.getOptimalDFTSize(sourceMat.cols()), CvType.CV_8UC1);
        sourceMat.copyTo(mat);
        FFT fft = new FFT(mat);
        Mat out =  fft.fftCalculate(0);
        System.out.println(out.dump());
        Imgcodecs.imwrite("C:\\Users\\14256\\Desktop\\bunny_F0.jpg",out);
        System.out.println("============= finished =============");
    }

    public void splitChannle(){
        Mat sourceMat = Imgcodecs.imread("C:\\Users\\14256\\Desktop\\bunny.jpg");
        Mat mat = new Mat(Core.getOptimalDFTSize(sourceMat.rows()),Core.getOptimalDFTSize(sourceMat.cols()), CvType.CV_8SC3);
        sourceMat.copyTo(mat);
        Mat outMat = new Mat(Core.getOptimalDFTSize(mat.rows()),Core.getOptimalDFTSize(mat.cols()), CvType.CV_8SC3);
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
