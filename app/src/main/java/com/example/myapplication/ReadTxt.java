package com.example.myapplication;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReadTxt {
    private final static String FILE_NAME = "zc.txt";
    private final static String PATH_NAME="/storage/emulated/0/zc/";
    ReadTxt(){

    }

    /**
     * 从sd card文件中读取数据
     * https://blog.csdn.net/yoryky/article/details/78675373
     * @param filename 待读取的sd card
     * @return
     * @throws IOException
     */
    public static ArrayList<String> readExternal(Context context, String filename) throws IOException {

        ArrayList<String> list = new ArrayList<>();

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

            String abc = "/storage/emulated/0/Android/data/com.example.myapplication/cache/zc/zc.txt";
            String def = "/storage/emulated/0/zc/zc.txt";
            String fff = context.getExternalCacheDir().getAbsolutePath() +File.separator+ FILE_NAME;

            filename = PATH_NAME + FILE_NAME;

            Log.i("debug","文件名为: "+filename);
            //打开文件输入流
            FileInputStream inputStream = new FileInputStream(filename);

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            try {
                String str;
                while ((str = br.readLine()) != null) {
                    String target = str.split("=")[1];
                    if(!target.isEmpty()) {
                        list.add(target);
                    }
                }
            }catch (Exception e){
                //读取文件失败

            }
            //关闭输入流
            br.close();
            inputStream.close();
        }
        return list;
    }

}
