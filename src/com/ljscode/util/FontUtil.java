package com.ljscode.util;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class FontUtil {

    /**
     * 字体
     */
    public static Font font;

    // 实体化字体
    static {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/Alibaba-PuHuiTi.ttf"));
            font = font.deriveFont(16L);
        } catch (FontFormatException | IOException e) {
            font = new Font("宋体", Font.BOLD, 16);
            e.printStackTrace();
        }
    }

    public static Font LoadFont(float fontSize) {
        Font font = null;
        try {
            File alibabaPuHUiTi = new File("res/font/Alibaba-PuHuiTi.ttf"); // 打开ttf字体文件(阿里巴巴普惠体)
            font = Font.createFont(Font.TRUETYPE_FONT, alibabaPuHUiTi); // 创建字体对象
            font = font.deriveFont(fontSize); // 设置字体大小
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        if (font == null) { // 如果字体加载出错则使用默认字体
            font = new Font("宋体", Font.PLAIN, (int) fontSize);
        }
        return font;
    }
}
