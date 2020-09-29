package com.cn.stardust.opencv.number;

/**
 *
 * 复数集
 *
 * 设x 是一个复数，则可表达如下形式。
 * x = a + bi;
 * 其中a是实部，b是虚部
 *
 * @author stardust
 * @date 2020/9/29 14:25
 */
public class ComplexNum {

    /**
     * 实部
     */
    private double relValue;

    /**
     * 虚部
     */
    private double imgValue;

    public ComplexNum() {
    }

    public ComplexNum(double relValue, double imgValue) {
        this.relValue = relValue;
        this.imgValue = imgValue;
    }

    public double getRelValue() {
        return relValue;
    }

    public void setRelValue(double relValue) {
        this.relValue = relValue;
    }

    public double getImgValue() {
        return imgValue;
    }

    public void setImgValue(double imgValue) {
        this.imgValue = imgValue;
    }

    /**
     * 根方值
     * @return
     */
    public double getSquare(){
        return Math.sqrt(relValue*relValue + imgValue*imgValue);
    }

    @Override
    public String toString() {
        if(imgValue <= 0){
            return relValue +
                    " - " + (-imgValue);
        }
        return relValue +
                " + " + imgValue;
    }
}
