package com.dragonsoft.netty.httpserver;

import com.dragonsoft.netty.httpserver.http.Request;
import com.dragonsoft.netty.httpserver.http.Response;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2019/11/10.
 */
public class DispatherServlet {

    public void doDispatcher(Request request, Response response) throws IllegalAccessException, InvocationTargetException, InstantiationException {

        ClassMappingLoader.InvokerHolder method = findMethod(request);

        Object object = invokeMethod(request,method);

        response.write(object);
    }

    private Object invokeMethod(Request request,ClassMappingLoader.InvokerHolder method) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object o = method.getClazz().newInstance();
        Object invoke = method.getMethod().invoke(o, request);
        return invoke;
    }

    private ClassMappingLoader.InvokerHolder findMethod(Request request) {
        ClassMappingLoader.InvokerHolder invokerHolder = ClassMappingLoader.getMethod(request.getPath());
        return invokerHolder;
    }

}
