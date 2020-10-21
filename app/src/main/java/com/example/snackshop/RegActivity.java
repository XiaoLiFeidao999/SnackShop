package com.example.snackshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView btn_back;
    EditText username,password;
    Button btn_reg;
    TextView tip_u,tip_p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ExitApplication.getInstance().addActivity(this);
        //沉浸式状态栏,状态栏背景色跟随主题背景色（activity_main）
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(getResources().getColor(R.color.red));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色
        }
        btn_back=(ImageView) findViewById(R.id.btn_back);
        btn_reg=(Button)findViewById(R.id.btn_reg);
        username=(EditText)findViewById(R.id.username_reg);
        password=(EditText)findViewById(R.id.password_reg);
        tip_u=(TextView)findViewById(R.id.tip_u);
        tip_p=(TextView)findViewById(R.id.tip_p);
        btn_back.setOnClickListener(this);
        btn_reg.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_back:
                this.finish();
                break;
            case R.id.btn_reg:
                String name=username.getText().toString();
                String psw=password.getText().toString();
                if(name.length()==0&&psw.length()!=0){
                    tip_u.setVisibility(View.VISIBLE);
                }
                else if(name.length()!=0&&psw.length()==0){
                    tip_p.setVisibility(View.VISIBLE);
                }
                else if(name.length()==0&&psw.length()==0){
                    tip_u.setVisibility(View.VISIBLE);
                    tip_p.setVisibility(View.VISIBLE);
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
                    URL url=new URL("http://169.254.61.231:8080/SnackShop/RegServlet");
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
    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if("OK".equals(response)){
                    Toast.makeText(RegActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                }
                if("NO".equals(response)){
                    Toast.makeText(RegActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
