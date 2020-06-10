package com.example.myapplication;

import android.util.Log;
import android.content.Context;
import java.util.ArrayList;
import java.util.Properties;

public class WordsArrayList {

    int index = 0;
    int size;
    int wrong1 = 0;
    int wrong2 = 0;
    int wrong3 = 0;
    ArrayList<String> list = new ArrayList<>();

    WordsArrayList(Context context){
        try {
            list = ReadTxt.readExternal(context, "a");
            size = list.size();
            StaticValue.wordsArrayList = this;

        }catch (Exception e){
            size = 0;
        }
    }
    WordsArrayList(Properties prop){
        for(int i=0; i<prop.size(); i++){
            list.add(prop.getProperty(String.valueOf(i+1)));
        }
        size = list.size();

        StaticValue.wordsArrayList = this;

    }

    public String getMore(){
        String back ;
        try {
            back = list.get(index).split("\\*")[2];
        } catch (Exception e){
            back = " ";
        }
        return back;
    }
    public String getWord(){
        return list.get(index).split("\\*")[0];
    }
    public String getZhongWen(){
        return list.get(index).split("\\*")[1];
    }
    public String getOtherZhongWen(){

        if(size<=4){
            //return "单词太少，无法随机！";
            return simpleOtherZhongWen();
        }
        int wrongNum = (int)(Math.random()*size);
        if(wrong1 == index){

            while (wrongNum == index || wrongNum < 0 || wrongNum > size-1){
                wrongNum = (int)(Math.random()*size);
            }
            wrong1 = wrongNum;
            return list.get(wrongNum).split("\\*")[1];

        }else if(wrong2 == index){

            while (wrongNum == wrong1 || wrongNum == index || wrongNum < 0 || wrongNum > size-1){
                wrongNum = (int)(Math.random()*size);
            }
            wrong2 = wrongNum;
            return list.get(wrongNum).split("\\*")[1];

        }else if(wrong3 == index){

            while (wrongNum == wrong1 ||wrongNum == wrong2 || wrongNum == index || wrongNum < 0 || wrongNum > size-1){
                wrongNum = (int)(Math.random()*size);
            }
            wrong3 = wrongNum;
            return list.get(wrongNum).split("\\*")[1];

        }
        //否则出错
        Log.e("error","获取错误翻译出错！返回第0个");
        return list.get(0).split("\\*")[1];
    }

    public String simpleOtherZhongWen(){
        int wrongNum = (int)(Math.random()*size);
        while (wrongNum == index || wrongNum < 0 || wrongNum > size-1){
            wrongNum = (int)(Math.random()*size);
        }
        return list.get(wrongNum).split("\\*")[1];
    }

    public void beforeWord(){
        if(index == 0){
            wrong1 = index;
            wrong2 = index;
            wrong3 = index;
            return;
        }
        index--;
        wrong1 = index;
        wrong2 = index;
        wrong3 = index;
    }
    public void afterWord(){
        if(index == size-1){
            wrong1 = index;
            wrong2 = index;
            wrong3 = index;
            return;
        }
        index++;
        wrong1 = index;
        wrong2 = index;
        wrong3 = index;
    }

    public void jump(int target){
        this.index = target;
        wrong1 = index;
        wrong2 = index;
        wrong3 = index;
    }
}

