package com.example.snackshop;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import SortFragment.AllFragment;
import SortFragment.BiscuitsFragment;
import SortFragment.BreadFragment;
import SortFragment.DrinksFragment;
import SortFragment.JunkfoodFragment;

public class HomepageFragment extends Fragment implements View.OnClickListener {
    private View view;
    //碎片切换
    private TextView tv_all,tv_sort1,tv_sort2,tv_sort3,tv_sort4;
    //碎片
    private AllFragment af;
    private BiscuitsFragment bf;
    private BreadFragment brf;
    private DrinksFragment df;
    private JunkfoodFragment jf;
    //碎片内容
    private FrameLayout sort_body;
    //碎片管理器
    private FragmentManager fragmentManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntanceState) {
        view = inflater.inflate(R.layout.homepage_fragment, container, false);
        fragmentManager=getFragmentManager();
        bindView();
        tv_all.performClick();
        return view;
    }
    public void bindView(){
        tv_all=(TextView)view.findViewById(R.id.tv_all);
        tv_sort1=(TextView)view.findViewById(R.id.tv_sort1);
        tv_sort2=(TextView)view.findViewById(R.id.tv_sort2);
        tv_sort3=(TextView)view.findViewById(R.id.tv_sort3);
        tv_sort4=(TextView)view.findViewById(R.id.tv_sort4);
        sort_body=(FrameLayout)view.findViewById(R.id.sort_body);
        tv_all.setOnClickListener(this);
        tv_sort1.setOnClickListener(this);
        tv_sort2.setOnClickListener(this);
        tv_sort3.setOnClickListener(this);
        tv_sort4.setOnClickListener(this);
    }
    //设置选中状态
    public void setSelect(){
        tv_all.setSelected(false);
        tv_sort1.setSelected(false);
        tv_sort2.setSelected(false);
        tv_sort3.setSelected(false);
        tv_sort4.setSelected(false);
    }
    //隐藏所有的fragment
    public void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(af!=null) fragmentTransaction.hide(af);
        if(bf!=null) fragmentTransaction.hide(bf);
        if(brf!=null) fragmentTransaction.hide(brf);
        if(df!=null) fragmentTransaction.hide(df);
        if(jf!=null) fragmentTransaction.hide(jf);
    }
    @Override
    public void onClick(View v){
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        hideAllFragment(transaction);
        switch (v.getId()){
            case R.id.tv_all:
                setSelect();
                tv_all.setSelected(true);
                if(af==null){
                    af=new AllFragment();
                    transaction.add(R.id.sort_body,af);
                }else{
                    transaction.show(af);
                }
                break;
            case R.id.tv_sort1:
                setSelect();
                tv_sort1.setSelected(true);
                if(jf==null){
                    jf=new JunkfoodFragment();
                    transaction.add(R.id.sort_body,jf);
                }else{
                    transaction.show(jf);
                }
                break;
            case R.id.tv_sort2:
                setSelect();
                tv_sort2.setSelected(true);
                if(df==null){
                    df=new DrinksFragment();
                    transaction.add(R.id.sort_body,df);
                }else{
                    transaction.show(df);
                }
                break;
            case R.id.tv_sort3:
                setSelect();
                tv_sort3.setSelected(true);
                if(bf==null){
                    bf=new BiscuitsFragment();
                    transaction.add(R.id.sort_body,bf);
                }else{
                    transaction.show(bf);
                }
                break;
            case R.id.tv_sort4:
                setSelect();
                tv_sort4.setSelected(true);
                if(brf==null){
                    brf=new BreadFragment();
                    transaction.add(R.id.sort_body,brf);
                }else{
                    transaction.show(brf);
                }
                break;
        }
        transaction.commit();
    }
}
