package com.dragonsoft.collection;

import com.sun.deploy.util.StringUtils;
import org.junit.Test;

import java.util.*;

/**
 * Created by zhangliang on 2019-09-14.
 */
public class ListTest {


    @Test
    public void testRemoveList(){
//        不要在foreach循环里进行元素的remove/add操作。remove元素请使用Iterator
//        方式，如果并发操作，需要对 Iterator对象加锁。
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        for (String item : list) {
            if ("2".equals(item)) {
                list.remove(item);
            }
        }
        System.out.println(StringUtils.join(list,","));
    }
    @Test
    public void testRemoveListSuccess(){
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            if ("2".equals(item)) {
                iterator.remove();
            }
        }
        System.out.println(StringUtils.join(list,","));
    }

}