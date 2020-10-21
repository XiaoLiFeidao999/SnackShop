package SortFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.snackshop.GoodsAdapter;
import com.example.snackshop.GoodsBean;
import com.example.snackshop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AllFragment extends Fragment {
    private ListView all_listview;
    private GoodsAdapter goodsAdapter;
    private List<GoodsBean> list;
    private getHandler handler=new getHandler();
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntanceState) {
        view = inflater.inflate(R.layout.all_fragment, container, false);
        get();
        return view;
    }
    class getHandler extends Handler {
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    all_listview=(ListView)view.findViewById(R.id.all_listview);
                    list=(List<GoodsBean>)msg.obj;
                    goodsAdapter=new GoodsAdapter(list,getActivity());
                    all_listview.setAdapter(goodsAdapter);
                    break;
                default:
                    break;
            }
        }
    }
    public void get(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] indata=readParse();
                List<GoodsBean> lgb=new ArrayList<GoodsBean>();
                JSONArray array;
                try{
                    array=new JSONArray(new String(indata));
                    for(int i=0;i<array.length();i++){
                        JSONObject item=array.getJSONObject(i);
                        String name=item.getString("name");
                        String price=item.getString("price");
                        String address=item.getString("address");
                        GoodsBean goodsBean=new GoodsBean();
                        goodsBean.setName(name);
                        goodsBean.setPrice(price);
                        goodsBean.setAddress(address);
                        lgb.add(goodsBean);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                Message msg=handler.obtainMessage();
                msg.what=0;
                msg.obj=lgb;
                handler.sendMessage(msg);
            }
        }).start();
    }
    public byte[] readParse(){
        String url="http://192.168.137.1:8080/SnackShop/ReturngoodsServlet";
        ByteArrayOutputStream outStream=new ByteArrayOutputStream();
        int len=0;
        byte[] data=new byte[1024*1024*20];
        try{
            URL byteurl=new URL(url);
            HttpURLConnection conn=(HttpURLConnection) byteurl.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(100000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            InputStream in=conn.getInputStream();
            while ((len=in.read(data))!=-1){
                outStream.write(data,0,len);
            }
            in.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return outStream.toByteArray();
    }
}
