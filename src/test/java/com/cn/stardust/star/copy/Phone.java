package com.cn.stardust.star.copy;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description: com.cn.stardust.star
 * Created by Oracle on 2019/6/12 19:16
 */
public class Phone implements Serializable {

    private String name;

    private String color;

    private BigDecimal price;

    private LocalDateTime localDateTime;

    private ConcurrentHashMap concurrentHashMap ;

    public Phone() {
        System.out.println("无参构造方法!");
    }

    public Phone(String name, String color, BigDecimal price) {
        System.out.println("三参构造方法!");
        this.name = name;
        this.color = color;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public ConcurrentHashMap getConcurrentHashMap() {
        return concurrentHashMap;
    }

    public void setConcurrentHashMap(ConcurrentHashMap concurrentHashMap) {
        this.concurrentHashMap = concurrentHashMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return Objects.equal(name, phone.name) &&
                Objects.equal(color, phone.color) &&
                Objects.equal(price, phone.price) &&
                Objects.equal(localDateTime, phone.localDateTime) &&
                Objects.equal(concurrentHashMap, phone.concurrentHashMap);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, color, price, localDateTime, concurrentHashMap);
    }
}
