package SortFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.snackshop.GetServerData;
import com.example.snackshop.GoodsAdapter;
import com.example.snackshop.GoodsBean;
import com.example.snackshop.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BreadFragment extends Fragment {
    private ListView bread_listview;
    private GoodsAdapter goodsAdapter;
    private List<GoodsBean> list;
    private GetServerData getServerData;
    private GetHandler handler=new GetHandler();
    private View view;
    private String sort="面包";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntanceState) {
        view = inflater.inflate(R.layout.bread_fragment, container, false);
        get();
        return view;
    }
    class GetHandler extends Handler {
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    bread_listview=(ListView)view.findViewById(R.id.bread_listview);
                    list=(List<GoodsBean>)msg.obj;
                    goodsAdapter=new GoodsAdapter(list,getActivity());
                    bread_listview.setAdapter(goodsAdapter);
                    break;
                default:
                    break;
            }
        }
    }
    public void get(){
        getServerData=new GetServerData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] indata=getServerData.readParse(sort);
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
                }catch(Exception e){
                    e.printStackTrace();
                }
                Message msg=handler.obtainMessage();
                msg.what=0;
                msg.obj=lgb;
                handler.sendMessage(msg);
            }
        }).start();
    }
}
