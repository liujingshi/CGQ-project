package com.ljscode.base;

/**
 * 鼠标点击事件接口
 *
 * @param <T> 点击的元素
 */
public interface BaseMouseListener<T> {

    /**
     * 鼠标点击
     *
     * @param that 点击的元素
     */
    void mouseClicked(T that);

}
