package com.example.snackshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    //登录
    ImageView btn_person;
    TextView tv_homapage,tv_cart;
    FrameLayout body;
    //碎片
    HomepageFragment hf;
    CartFragment cf;
    //碎片管理器
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ExitApplication.getInstance().addActivity(this);
        //沉浸式状态栏,状态栏背景色跟随主题背景色（activity_main）
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(getResources().getColor(R.color.red));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色
        }
        //获取碎片管理器
        manager=getFragmentManager();
        bindView();
        tv_homapage.performClick();
    }
    public void bindView(){
        btn_person=(ImageView)findViewById(R.id.btn_person);
        tv_homapage=(TextView)findViewById(R.id.tv_homepage);
        tv_cart=(TextView)findViewById(R.id.tv_cart);
        body=(FrameLayout)findViewById(R.id.body);
        btn_person.setOnClickListener(this);
        tv_homapage.setOnClickListener(this);
        tv_cart.setOnClickListener(this);
    }
    //选中状态
    private void setSelected(){
        tv_cart.setSelected(false);
        tv_homapage.setSelected(false);
    }
    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(hf!=null) fragmentTransaction.hide(hf);
        if(cf!=null) fragmentTransaction.hide(cf);
    }
    @Override
    public void onClick(View v){
        FragmentTransaction fTransction=manager.beginTransaction();
        hideAllFragment(fTransction);
        switch (v.getId()){
            case R.id.btn_person:
                Intent intent=new Intent(this,LogActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_homepage:
                setSelected();
                tv_homapage.setSelected(true);
                if(hf==null) {
                    hf = new HomepageFragment();
                    fTransction.add(R.id.body, hf);
                }else{
                    fTransction.show(hf);
                }
                break;
            case R.id.tv_cart:
                setSelected();
                tv_cart.setSelected(true);
                if(cf==null){
                    cf=new CartFragment();
                    fTransction.add(R.id.body,cf);
                }else{
                    fTransction.show(cf);
                }
                break;
            default:
                break;
        }
        fTransction.commit();
    }
}
