package com.ljscode.base;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BaseOnlyInputNumber extends KeyAdapter {

    public void keyTyped(KeyEvent e) {
        String key="-.0123456789"+(char)8;
        if(key.indexOf(e.getKeyChar())<0){
            e.consume();//如果不是数字则取消
        }
    }
}
