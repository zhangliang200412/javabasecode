package com.dragonsoft.concurrent;

/**
 * Created by zhangliang on 2019-09-14.
 */
public class CountDownTest {
//    使用CountDownLatch进行异步转同步操作，每个线程退出前必须调用 countDown
//    方法，线程执行代码注意 catch异常，确保 countDown方法被执行到，避免主线程无法执行
//    至await方法，直到超时才返回结果。
//    说明：注意，子线程抛出异常堆栈，不能在主线程 try-catch到
}