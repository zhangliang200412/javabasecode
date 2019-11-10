package com.dragonsoft.netty.httpserver.http;

import io.netty.handler.codec.http.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangliang on 2019-11-08.
 */
public class Request {

    private FullHttpRequest fullHttpRequest;

    public  Request(FullHttpRequest request ){
        this.fullHttpRequest = request;
    }

    public String getMethod(){
        return fullHttpRequest.getMethod().name();
    }

    public String getPath(){
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(fullHttpRequest.getUri());
        return queryStringDecoder.path();
    }

    public String getURI(){
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(fullHttpRequest.getUri());
        return queryStringDecoder.uri();
    }

    public Map<String,List<String>> getParameters(){
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(fullHttpRequest.getUri());

        return queryStringDecoder.parameters();
    }

    public String getParameter(String key){
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(fullHttpRequest.getUri());

        List<String> strings = queryStringDecoder.parameters().get(key);

        return strings.isEmpty() ? null : strings.get(0);
    }

    public Object getParameterList(String key){
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(fullHttpRequest.getUri());

        List<String> strings = queryStringDecoder.parameters().get(key);
        return strings;
    }
}