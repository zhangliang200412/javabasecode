package com.dragonsoft.netty.httpserver;

import com.dragonsoft.netty.httpserver.annotation.Param;
import com.dragonsoft.netty.httpserver.http.Request;
import com.dragonsoft.netty.httpserver.http.Response;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by Administrator on 2019/11/10.
 */
public class DispatherServlet {

    public void doDispatcher(Request request, Response response) throws IllegalAccessException, InvocationTargetException, InstantiationException {

        ClassMappingLoader.InvokerHolder method = findMethod(request);

        if(method == null){
            response.write("server error,no handler find", 500);
            return;
        }

        Object object = invokeMethod(request,method);

        response.write(object);
    }

    private Object invokeMethod(Request request,ClassMappingLoader.InvokerHolder method) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object o = method.getClazz().newInstance();

        Parameter[] parameters = method.getMethod().getParameters();
        Object invoke = null;

        if(parameters == null || parameters.length == 0){
            invoke = method.getMethod().invoke(o);
        }else{
            Object[] args = new Object[parameters.length];
            int i = 0;
            for(Parameter parameter : parameters){
                if(Request.class.getName().equals(parameter.getParameterizedType().getTypeName())){
                    args[i] = request;
                }else{
                    Param annotation = parameter.getAnnotation(Param.class);
                    if (annotation != null){
                        args[i] = request.getParameter(annotation.value());
                    }else{
                        throw new RuntimeException("unkown parameter type");
                    }
                }
                i++;
            }
            invoke = method.getMethod().invoke(o, args);
        }

        return invoke;
    }

    private ClassMappingLoader.InvokerHolder findMethod(Request request) {
        ClassMappingLoader.InvokerHolder invokerHolder = ClassMappingLoader.getMethod(request.getPath());
        return invokerHolder;
    }

}
