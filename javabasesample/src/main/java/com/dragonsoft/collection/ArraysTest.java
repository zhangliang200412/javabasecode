package com.dragonsoft.collection;

import com.sun.deploy.util.StringUtils;
import org.junit.Test;

import javax.print.attribute.HashAttributeSet;
import java.util.*;

/**
 * Created by zhangliang on 2019-09-14.
 */
public class ArraysTest {

    @Test
    public void testArraysAsList(){
//        【强制】使用工具类 Arrays.asList()把数组转换成集合时，不能使用其修改集合相关的方
//        法，它的add/remove/clear方法会抛出 UnsupportedOperationException异常。
//        说明： asList的返回对象是一个 Arrays内部类，并没有实现集合的修改方法。 Arrays.asList
//        体现的是适配器模式，只是转换接口，后台的数据仍是数组。
        String[] str = new String[] { "you", "wu" };
        List list = Arrays.asList(str);
        str[0] = "you2";
//        list.get(0)也会随之修改。
        System.out.println(list.get(0));
//        运行时异常。
        list.add("test");
        System.out.println(list.size());
    }

}