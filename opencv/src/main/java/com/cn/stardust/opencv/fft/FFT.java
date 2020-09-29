package com.cn.stardust.opencv.fft;

import com.cn.stardust.opencv.number.ComplexNum;
import com.cn.stardust.opencv.pi.CosPi;
import com.cn.stardust.opencv.pi.SinPi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author stardust
 * @date 2020/9/29 18:02
 */
public class FFT {

    public List<ComplexNum> fftCaculate(List<Number> numbers){
        int num = numbers.size();
        CosPi cosPi = new CosPi(num);
        SinPi sinPi = new SinPi(num);
        List<ComplexNum> complexNums = new ArrayList<>();
        for(int i = 0 ; i < num ; i ++){
            complexNums.add(new ComplexNum(cosPi.getData(numbers,i),-sinPi.getData(numbers,i)));
        }
        return complexNums;
    }
}
