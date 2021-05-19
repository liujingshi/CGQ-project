package com.ljscode.base;

/**
 * 鼠标点击事件接口
 * @param <T> 点击的元素
 */
public interface BaseMouseListener<T> {

    void mouseClicked(T that);

}
