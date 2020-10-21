package com.example.snackshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LogActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_reg;
    EditText username,password;
    Button btn_login;
    ImageView btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExitApplication.getInstance().addActivity(this);
        //沉浸式状态栏,状态栏背景色跟随主题背景色（activity_main）
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(getResources().getColor(R.color.red));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色
        }
        btn_reg=(Button)findViewById(R.id.btn_reg);
        btn_login=(Button)findViewById(R.id.btn_login);
        username=(EditText)findViewById(R.id.username_login);
        password=(EditText)findViewById(R.id.password_login);
        btn_back=(ImageView)findViewById(R.id.btn_back);
        btn_reg.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_back:
                Intent intent1=new Intent(this,HomeActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_reg:
                Intent intent=new Intent(LogActivity.this,RegActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                String name=username.getText().toString();
                String psw=password.getText().toString();
                if((name.length()==0&&psw.length()!=0)||(name.length()!=0&&psw.length()==0)||(name.length()==0&&psw.length()==0)){
                    Toast.makeText(LogActivity.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
                }
                if(name.length()!=0&&psw.length()!=0){
                    sendRequestWithHttpURLConnection(name,psw);
                }
                break;
            default:
                break;
        }
    }
    private void sendRequestWithHttpURLConnection(final String u,final String p){
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try{
                    URL url=new URL("http://169.254.61.231:8080/SnackShop/LogServlet");
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);
                    String data="name="+u+"&password="+p;
                    OutputStream out=connection.getOutputStream();
                    out.write(data.getBytes());
                    out.flush();
                    out.close();
                    InputStream in=connection.getInputStream();
                    //下面对获取到的输入流进行读取
                    reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    showResponse(response.toString());
                }catch(Exception e){
                    e.printStackTrace();
                }finally{
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if ("OK".equals(response)) {
                    Toast.makeText(LogActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                }
                if ("NO".equals(response)) {
                    Toast.makeText(LogActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
