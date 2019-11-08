package com.dragonsoft.collection;

import com.sun.deploy.util.StringUtils;
import org.junit.Test;

import java.util.*;

/**
 * Created by zhangliang on 2019-09-14.
 */
public class MapTest {

    @Test
    public void testMapInitialCapacity(){
        Map<Object, Object> map = new HashMap(11);
        System.out.println(map);
    }
}