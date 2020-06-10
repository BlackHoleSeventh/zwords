package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    private WordsArrayList wordsArrayList;
    private ArrayList<String> list = new ArrayList<>();
    private String correctAnwser = "正确答案";
    private Context context;
    //计时器，7秒后仍没有赋予权限则退出
    private static int timeCount = 0;


    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //获取读权限
        //requestReadExternalPermission();
        /*while(!checkRead()){
            if(timeCount <5){
                try {
                    timeCount++;
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                exit();
            }
        }
        timeCount = 0;*/
        //获取写权限
        //requestWriteExternalPermission();
        /*while(!checkWrite()){
            if(timeCount <5){
                try {
                    timeCount++;
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                exit();
            }
        }
        timeCount = 0;*/



        //如果没有读权限或写权限
        /*if(!checkWrite() || !checkRead()){
            //退出程序
            exit();
        }*/

        //将assets/eng.properties保存为根目录下的zc/zc.txt
        copyPropToZc();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initWord();
        initButton();

        initFiveClick();
    }
    private void init(){
        context = getApplicationContext();
        Properties prop = Way.loadProperties(context);
        wordsArrayList = new WordsArrayList(context);
        if(wordsArrayList.size == 0){
            Log.i("debug","list个数为0，没有读取到/zc/zc.txt");
            Log.i("debug","默认使用properties中的单词表");
            wordsArrayList = new WordsArrayList(prop);
        }
    }

    private void initWord(){
        clearAnswer();
        TextView nowAndSize = (TextView) findViewById(R.id.nowAndSize);
        nowAndSize.setText((wordsArrayList.index+1)+"/"+wordsArrayList.size);

        TextView word = (TextView) findViewById(R.id.word);
        word.setText(wordsArrayList.getWord());

        initABCD(wordsArrayList);
        //Log.i("debug","中文");
        //Log.i("debug",(String)prop.getProperty("1"));
    }
    private void initABCD(final WordsArrayList wordsArrayList){
        list.clear();
        list.add(wordsArrayList.getZhongWen());
        correctAnwser = list.get(0);
        list.add(wordsArrayList.getOtherZhongWen());
        list.add(wordsArrayList.getOtherZhongWen());
        list.add(wordsArrayList.getOtherZhongWen());

        final TextView result = (TextView) findViewById(R.id.result);

        final TextView A = (TextView) findViewById(R.id.A);
        final TextView B = (TextView) findViewById(R.id.B);
        final TextView C = (TextView) findViewById(R.id.C);
        final TextView D = (TextView) findViewById(R.id.D);

        int flag = (int)(Math.random()*4);
        while(flag<0 || flag>3){
            flag = (int)(Math.random()*4);
        }
        A.setText("A."+list.get(flag));
        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(A.getText().toString().contains(correctAnwser)){
                    findViewById(R.id.resultImgRight).setVisibility(View.VISIBLE);
                    findViewById(R.id.resultImgWrong).setVisibility(View.GONE);
                    result.setText(wordsArrayList.getMore());
                }else{
                    findViewById(R.id.resultImgRight).setVisibility(View.GONE);
                    findViewById(R.id.resultImgWrong).setVisibility(View.VISIBLE);
                    result.setText("");
                }
            }
        });
        list.remove(flag);

        flag = (int)(Math.random()*3);
        while(flag<0 || flag>2){
            flag = (int)(Math.random()*3);
        }
        B.setText("B."+list.get(flag));
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(B.getText().toString().contains(correctAnwser)){
                    findViewById(R.id.resultImgRight).setVisibility(View.VISIBLE);
                    findViewById(R.id.resultImgWrong).setVisibility(View.GONE);
                    result.setText(wordsArrayList.getMore());
                }else{
                    findViewById(R.id.resultImgRight).setVisibility(View.GONE);
                    findViewById(R.id.resultImgWrong).setVisibility(View.VISIBLE);
                    result.setText("");
                }
            }
        });
        list.remove(flag);

        flag = (int)(Math.random()*2);
        while(flag<0 || flag>1){
            flag = (int)(Math.random()*2);
        }
        C.setText("C."+list.get(flag));
        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(C.getText().toString().contains(correctAnwser)){
                    findViewById(R.id.resultImgRight).setVisibility(View.VISIBLE);
                    findViewById(R.id.resultImgWrong).setVisibility(View.GONE);
                    result.setText(wordsArrayList.getMore());
                }else{
                    findViewById(R.id.resultImgRight).setVisibility(View.GONE);
                    findViewById(R.id.resultImgWrong).setVisibility(View.VISIBLE);
                    result.setText("");
                }
            }
        });
        list.remove(flag);

        D.setText("D."+list.get(0));
        D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(D.getText().toString().contains(correctAnwser)){
                    findViewById(R.id.resultImgRight).setVisibility(View.VISIBLE);
                    findViewById(R.id.resultImgWrong).setVisibility(View.GONE);
                    result.setText(wordsArrayList.getMore());
                }else{
                    findViewById(R.id.resultImgRight).setVisibility(View.GONE);
                    findViewById(R.id.resultImgWrong).setVisibility(View.VISIBLE);
                    result.setText("");
                }
            }
        });
    }

    private void initButton(){
        Button beforeButton = findViewById(R.id.before);
        Button jumpButton = findViewById(R.id.jump);
        Button afterButton = findViewById(R.id.after);

        beforeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticValue.wordsArrayList.beforeWord();
                initWord();
            }
        });
        afterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticValue.wordsArrayList.afterWord();
                initWord();
            }
        });

        jumpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpDialog();
            }
        });
    }

    private void jumpDialog(){
        View view = LayoutInflater.from(this).inflate(R.layout.jumpdialog,null,false);
        final AlertDialog ad = new AlertDialog.Builder(this).setView(view).create();
        final EditText jumpNum = view.findViewById(R.id.edit_content);
        final Button yesBtn = view.findViewById(R.id.btn_yes);
        final Button noBtn = view.findViewById(R.id.btn_no);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = jumpNum.getText().toString();
                try {
                    int target = Integer.valueOf(str);
                    if(target<1 || target>wordsArrayList.size){
                        //超过目标页数，跳转失败
                    }else{
                        wordsArrayList.jump((target-1));
                        initWord();
                    }
                }catch (Exception e){
                    //输入的不为数字，跳转失败。
                }
                ad.dismiss();
            }
        });
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.dismiss();
            }
        });

        ad.show();
        //int width = context.getResources().getDisplayMetrics().widthPixels/4*3;
        //int height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        //ad.getWindow().setLayout(width,height);
    }

    private void clearAnswer(){
        TextView result = (TextView) findViewById(R.id.result);
        result.setText("");
        ImageView iv1 = (ImageView) findViewById(R.id.resultImgRight);
        iv1.setVisibility(View.GONE);
        ImageView iv2 = (ImageView) findViewById(R.id.resultImgWrong);
        iv2.setVisibility(View.GONE);

    }

    private void initFiveClick(){
        TextView tv = findViewById(R.id.textView2);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                if(counter == 5){
                    showFiveLog();
                }else if(counter == 10){
                    findViewById(R.id.mainTable).setBackground(getResources().getDrawable(R.drawable.white));
                    counter=0;
                }
            }
        });
    }

    private void showFiveLog(){
        View view2 = LayoutInflater.from(this).inflate(R.layout.jumpdialog2,null,false);
        final AlertDialog ad2 = new AlertDialog.Builder(this).setView(view2).create();
        final EditText pass = view2.findViewById(R.id.edit_content);
        final Button yesBtn = view2.findViewById(R.id.btn_yes);
        final Button noBtn = view2.findViewById(R.id.btn_no);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = pass.getText().toString();
                try {
                    int password = Integer.valueOf(str);
                    if(password == 5555){
                        findViewById(R.id.mainTable).setBackground(getResources().getDrawable(R.drawable.p1));
                        findViewById(R.id.mainTable).getBackground().mutate().setAlpha(50);
                    }else if(password == 55555){
                        findViewById(R.id.mainTable).setBackground(getResources().getDrawable(R.drawable.p2));
                        findViewById(R.id.mainTable).getBackground().mutate().setAlpha(50);
                    }else{
                        findViewById(R.id.mainTable).setBackground(getResources().getDrawable(R.drawable.blue));
                    }
                }catch (Exception e){
                    findViewById(R.id.mainTable).setBackground(getResources().getDrawable(R.drawable.blue));
                }
                ad2.dismiss();
            }
        });
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad2.dismiss();
                findViewById(R.id.mainTable).setBackground(getResources().getDrawable(R.drawable.blue));
            }
        });
        ad2.show();
    }




    /////////////////////////////////
    /////////////////////////////////
    /////////////////////////////////
    @SuppressLint("NewApi")
    private void requestReadExternalPermission() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("Debug", "READ permission IS NOT granted...");
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        } else {
            Log.d("Debug", "READ permission is granted...");
        }
    }

    @SuppressLint("NewApi")
    private void requestWriteExternalPermission() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("Debug", "WRITE permission IS NOT granted...");
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            Log.d("Debug", "WRITE permission is granted...");
        }
    }
    /*@SuppressLint("NewApi")
    private void requestReadExternalPermission() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("Debug", "READ permission IS NOT granted...");
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        } else {
            Log.d("Debug", "READ permission is granted...");
        }
    }

    @SuppressLint("NewApi")
    private void requestWriteExternalPermission() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("Debug", "WRITE permission IS NOT granted...");
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            Log.d("Debug", "WRITE permission is granted...");
        }
    }*/

    @SuppressLint("NewApi")
    private void copyPropToZc(){
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            copyStart();
        }
        else{

        }
    }

    @SuppressLint("NewApi")
    private boolean checkRead(){
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }

    @SuppressLint("NewApi")
    private boolean checkWrite(){
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode){
            case 1:
                switch (permissions[0]){
                    case Manifest.permission.READ_EXTERNAL_STORAGE://权限1
                        if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                            //readContacts();
                            readBool = true;
                        }else {
                            System.exit(0);
                            //Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case Manifest.permission.WRITE_EXTERNAL_STORAGE://权限2
                        if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                            //call();
                            writeBool = true;
                        } else {
                            System.exit(0);
                            //Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                }
                break;
            default:
        }
    }*/


    private void copyStart(){
        String systemPath = Environment.getExternalStorageDirectory() + "/";
        String appFolderPath = systemPath + "zc/";
        File folder = new File(appFolderPath);
        if (!folder.exists()) {
            Log.d("Debug","AppClassificationAssets folder does not exist, creating one");
            folder.mkdirs();
        } else {
            Log.w("Debug","INFO: AppClassificationAssets folder already exists.");
        }

        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new InputStreamReader(getAssets().open("eng.properties")));
            String outPath = appFolderPath + "zc.txt";
            File out = new File(outPath);
            if(out.exists()){
                Log.i("Debug", "zc.txt已存在，不进行复制");
                return;
            }else{
                out.createNewFile();
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out)));

                String str = null;
                while ((str = br.readLine()) != null) {
                    bw.write(Way.decodeUnicode(str));
                    bw.write("\n");
                }
            }

            br.close();
            bw.flush();
            bw.close();
        } catch(Exception e) {
            e.printStackTrace();
            Log.e("Debug", "复制eng.properties失败");
        }

    }
    /**
     * 应用退出，结束所有的activity
     */
    public void exit() {
        this.finish();
        //释放内存，退出程序
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
