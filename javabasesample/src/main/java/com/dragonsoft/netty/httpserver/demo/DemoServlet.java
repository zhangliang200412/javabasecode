package com.dragonsoft.netty.httpserver.demo;

import com.dragonsoft.netty.httpserver.annotation.Controller;
import com.dragonsoft.netty.httpserver.annotation.GET;
import com.dragonsoft.netty.httpserver.annotation.POST;
import com.dragonsoft.netty.httpserver.annotation.Param;
import com.dragonsoft.netty.httpserver.http.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/11/10.
 */
@Controller("user")
public class DemoServlet {

    @GET("/test1")
    public String test1(Request request){
        String param1 = request.getParameter("param1");
        return param1 +  "response";
    }

    @POST("/test2")
    public Map<String, String> test1(@Param("param1") String id){
        HashMap<String, String> objectObjectHashMap = new HashMap();
        objectObjectHashMap.put("status","200");
        objectObjectHashMap.put("message","success");
        objectObjectHashMap.put("id",id);
        return objectObjectHashMap;
    }
}
