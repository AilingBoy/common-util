package com.cn.stardust.opencv.fft;

import com.cn.stardust.opencv.number.ComplexNum;
import com.cn.stardust.opencv.pi.CosPi;
import com.cn.stardust.opencv.pi.SinPi;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author stardust
 * @date 2020/9/29 18:02
 *
 * 傅里叶变换
 */
public class FFT {

    private CosPi xCosPi;
    private SinPi xSinPi;
    private CosPi yCosPi;
    private SinPi ySinPi;

    private int total;
    /**
     * 缓存2个余玄函数乘积结果
     */
    private Map<String,Double> ccMap = new HashMap<>();
    /**
     * 缓存1个余玄值与1个正玄函数乘积结果
     * key : a-b
     * sin(a + b) 的结果（a < b）
     */
    private Map<String,Double> csMap = new HashMap<>();
    /**
     * 缓存2个正玄函数乘积
     */
    private Map<String,Double> ssMap = new HashMap<>();

    private Mat mat;

    public FFT(Mat mat) {
        this.mat = mat;
        xCosPi = new CosPi(mat.cols());
        xSinPi = new SinPi(mat.cols());
        ySinPi = new SinPi(mat.rows());
        yCosPi = new CosPi(mat.rows());
        total = mat.cols()*mat.rows();
    }

    public List<ComplexNum> fftCalculate(List<Number> numbers){
        int num = numbers.size();
        CosPi cosPi = new CosPi(num);
        SinPi sinPi = new SinPi(num);
        List<ComplexNum> complexNums = new ArrayList<>();
        for(int i = 0 ; i < num ; i ++){
            complexNums.add(new ComplexNum(cosPi.getData(numbers,i),-sinPi.getData(numbers,i)));
        }
        return complexNums;
    }

    /**
     * 傅里叶变换
     * @param channel,获取通道下标
     * @return
     */
    public Mat fftCalculate(int channel){
       if(mat.channels()<= channel){
           throw new IndexOutOfBoundsException();
       }
       Mat out = new Mat(mat.rows(), mat.cols(), CvType.CV_8UC1);
       for(int i = 0 ; i < mat.cols() ; i ++){
           System.out.println(i+"---<");
            for(int j = 0 ; j < mat.rows() ; j ++){
                // caculate (i,j)点的傅里叶变换值
                calculateComplex(i,j,out,channel);
            }
       }
       return out;
    }

    /**
     *
     * @param x
     * @param y
     * @param mat
     * @return
     */
    public strictfp void calculateComplex(int x, int y ,Mat mat,int channel){
        double real = 0.0;
        double img = 0.0;
        for(int i = 0 ; i < mat.cols() ; i ++){
            for(int j = 0 ; j < mat.rows() ; j ++){
                int xIndex = (x*i)%mat.cols();
                int yIndex = (y*j)%mat.rows();
                String key = xIndex > yIndex ? yIndex+"-"+xIndex : xIndex+"-"+yIndex;
                double ccResult;
                if(null == ccMap.get(key)){
                    ccResult =  xCosPi.getData(xIndex) * yCosPi.getData(yIndex);
                    ccMap.put(key,ccResult);
                }else{
                    ccResult = ccMap.get(key);
                }
                double ssResult;
                if(null == ssMap.get(key)){
                    ssResult =  xSinPi.getData(xIndex) * ySinPi.getData(yIndex);
                    ssMap.put(key,ssResult);
                }else{
                    ssResult = ssMap.get(key);
                }
                double result;
                if(null == csMap.get(key)){
                    result = xCosPi.getData(xIndex)*ySinPi.getData(yIndex)
                            + xSinPi.getData(xIndex)* yCosPi.getData(yIndex);
                    csMap.put(key,result);
                }else{
                    result = csMap.get(key);
                }
                real += (ccResult - ssResult);
                img += result;
            }
        }

        double[] doubles = mat.get(y,x);
        real = doubles[channel] * real/total;
        img = doubles[channel] * img/total;
        double result = Math.sqrt(real*real+img*img);
        mat.put(y,x,result);
    }
}
