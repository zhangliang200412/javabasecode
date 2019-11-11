package com.dragonsoft.netty.httpserver;

import com.dragonsoft.netty.httpserver.annotation.Controller;
import com.dragonsoft.netty.httpserver.annotation.GET;
import com.dragonsoft.netty.httpserver.annotation.POST;
import com.dragonsoft.netty.httpserver.support.ClassScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/11/10.
 */
public class ClassMappingLoader {

    private static Map<String, InvokerHolder> mapping = new HashMap();

    public static void loader() {
        List<Class> scan = ClassScanner.scan("com.dragonsoft.netty.httpserver.demo");
        for(Class clazz : scan){
            Controller annotation = (Controller)clazz.getAnnotation(Controller.class);
            if(annotation != null){
                String rootPath = annotation.value();

                Method[] methods = clazz.getMethods();
                if(methods == null || methods.length == 0) continue;

                for(Method method : methods){
                    GET getAnno = method.getAnnotation(GET.class);
                    if(getAnno != null){
                        mapping.put(appendPath(rootPath , getAnno.value() ), new InvokerHolder(method, clazz));
                    }else {
                        POST postAnno = method.getAnnotation(POST.class);
                        if(postAnno != null){
                            mapping.put(appendPath(rootPath , postAnno.value() ), new InvokerHolder(method, clazz));
                        }
                    }
                }
            }
        }
    }

    public static InvokerHolder getMethod(String path){
        return mapping.get(path);
    }

    public static String appendPath(String ... values){
        StringBuffer sb = new StringBuffer();
        for(String value : values){
            if(value.startsWith("/")){
                sb.append(value);
            }else{
                sb.append("/" + value);
            }
        }
        return sb.toString();
    }


    public static class InvokerHolder{
        public Method method;
        public Class clazz;

        public InvokerHolder(Method method, Class clazz) {
            this.method = method;
            this.clazz = clazz;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public Class getClazz() {
            return clazz;
        }

        public void setClazz(Class clazz) {
            this.clazz = clazz;
        }
    }
}
