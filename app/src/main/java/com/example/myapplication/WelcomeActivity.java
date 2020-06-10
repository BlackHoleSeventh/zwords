package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        initButton();

        //如果有读写权限
        if(checkWrite()){
            //跳转页面
            Intent intent =new Intent(WelcomeActivity.this, MainActivity.class);
            //启动
            startActivity(intent);
            WelcomeActivity.this.finish();

        }

    }

    private void initButton(){
        Button welcomeButton = findViewById(R.id.welcome);
        welcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //如果有读写权限
                if(checkWrite()){
                    //跳转页面
                    Intent intent =new Intent(WelcomeActivity.this, MainActivity.class);
                    //启动
                    startActivity(intent);
                    WelcomeActivity.this.finish();
                }else{
                    //申请权限
                    requestWriteExternalPermission();
                }
            }
        });
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

    /**
     * 当申明了WRITE_EXTERNAL_STORAGE权限时， READ_EXTERNAL_STORAGE权限会自动添加的。
     */

    /////////////////////////////////

    @SuppressLint("NewApi")
    private void requestWriteExternalPermission() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("Debug", "Write permission IS NOT granted...");
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            Log.d("Debug", "Write permission is granted...");
        }
    }

    //参数 requestCode是我们在申请权限的时候使用的唯一的申请码
    //String[] permission则是权限列表，一般用不到
    //int[] grantResults 是用户的操作响应，包含这权限是够请求成功
    //由于在权限申请的时候，我们就申请了一个权限，所以此处的数组的长度都是1
    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            //当然权限多了，建议使用Switch，不必纠结于此
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "权限申请成功",Toast.LENGTH_SHORT).show();
                canJump = true;

            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                //Toast.makeText(this, "权限申请失败，用户拒绝权限", Toast.LENGTH_SHORT).show();
                canJump = false;

            }
        }
    }
*/
}
