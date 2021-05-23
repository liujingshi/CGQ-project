package com.ljscode.util;

import java.io.*;
import java.nio.charset.StandardCharsets;

public abstract class FileUtil {

    public static String ReadFile(String path) {
        File file = new File(path);
        StringBuilder text = new StringBuilder();
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        br = new BufferedReader(isr);
        String line = null;
        while (true){
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            text.append(line);
        }
        return text.toString();
    }

    public static void WriteFile(String path, String text) {
        File file = new File(path);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(text.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
