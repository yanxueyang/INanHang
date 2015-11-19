package www.nuaa.edu.cn.inanhang.ViewFragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.askerov.dynamicgrid.DynamicGridView;

import java.util.List;

import butterknife.ButterKnife;
import www.nuaa.edu.cn.inanhang.Base.BaseFragment;
import www.nuaa.edu.cn.inanhang.Base.BaseToolbarActivity;
import www.nuaa.edu.cn.inanhang.Model.ServiceData;
import www.nuaa.edu.cn.inanhang.R;
import www.nuaa.edu.cn.inanhang.Util.ServiceDynamicAdapter;
import www.nuaa.edu.cn.inanhang.Util.UIHelper;
import www.nuaa.edu.cn.inanhang.ViewActivity.DormActivity;
import www.nuaa.edu.cn.inanhang.ViewActivity.EmsQueryActivity;
import www.nuaa.edu.cn.inanhang.ViewActivity.WeatherActivity;

/**
 * Created by yxy on 15/10/24.
 */
public class ServiceFragment extends BaseFragment {
    private static final String TAG = ServiceFragment.class.getName();
    private  ServiceDetail servicedetail;
    private DynamicGridView gridView;
    private TextView toolbarOperation;
    private int i=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, null);
        gridView = (DynamicGridView) view.findViewById(R.id.dynamic_grid);
        getService1();
        return view;
    }
    @Override
    protected void setupToolbar(BaseToolbarActivity toolbar) {
        toolbar.setToolbarTitle("服务");
        toolbarOperation=toolbar.getToolbarOperation();
        toolbarOperation.setVisibility(View.VISIBLE);
        toolbarOperation.setText("排序");
        toolbarOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick","onClick"+i);
                if(i%2==0){
                    toolbarOperation.setText("完成");
                    gridView.startEditMode();
                }
                else {
                    toolbarOperation.setText("排序");
                    if (gridView.isEditMode()) {
                        gridView.stopEditMode();
                    }
                }
                i++;
            }
        });
    }

    private void getService1(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("current_service1",
                Activity.MODE_PRIVATE);
//         使用getString方法获得value，注意第2个参数是value的默认值
        String service = sharedPreferences.getString("service", "");
        if (service.equals("")) {
            service = "{\"servicedatas\": [" +
                    "{\"name\":\"天气预报\",\"pic\":\"service_weather_icon\",\"activity\":\"WeatherActivity.class\"}," +
                    "{\"name\":\"充值缴费\",\"pic\":\"service_money_icon\",\"activity\":\"null\"}," +
                    "{\"name\":\"快递查询\",\"pic\":\"service_ems_icon\",\"activity\":\"EmsQueryActivity.class\"}," +
                    "{\"name\":\"校园导航\",\"pic\":\"service_nav_icon\",\"activity\":\"null\"}," +
                    "{\"name\":\"门禁服务\",\"pic\":\"service_door_icon\",\"activity\":\"null\"}," +
                    "{\"name\":\"最新服务\",\"pic\":\"service_action_icon\",\"activity\":\"null\"}," +
                    "{\"name\":\"社团参与\",\"pic\":\"service_team_icon\",\"activity\":\"null\"}," +
                    "{\"name\":\"教室查询\",\"pic\":\"service_classroom_icon\",\"activity\":\"null\"}," +
                    "{\"name\":\"电量查询\",\"pic\":\"service_power_icon\",\"activity\":\"null\"}" +
                    "]}";
        }
        Gson gson = new Gson();
        ServiceDetail servicedetailjiexi = gson.fromJson(service, ServiceDetail.class);
        servicedetail=servicedetailjiexi;
        Log.i("service", servicedetail.servicedatas.size()+"");
        handler.sendEmptyMessage(200);
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    ServiceDynamicAdapter serviceDynamicAdapter=new ServiceDynamicAdapter(getContext(),servicedetail.servicedatas,4);
                    serviceDynamicAdapter.setActivity(getActivity());
                    gridView.setAdapter(serviceDynamicAdapter);
                    gridView.setOnDragListener(new DynamicGridView.OnDragListener() {
                        @Override
                        public void onDragStarted(int position) {
                            Log.d(TAG, "drag started at position " + position);
                        }

                        @Override
                        public void onDragPositionsChanged(int oldPosition, int newPosition) {
                            Log.d(TAG, String.format("drag item position changed from %d to %d", oldPosition, newPosition));
                            ServiceData s = servicedetail.servicedatas.get(oldPosition);
                            if (oldPosition < newPosition) {
                                servicedetail.servicedatas.add(newPosition + 1, s);
                                servicedetail.servicedatas.remove(oldPosition);
                            } else {
                                servicedetail.servicedatas.add(newPosition, s);
                                servicedetail.servicedatas.remove(oldPosition + 1);
                            }
                            Log.i("service", servicedetail.servicedatas+"");
                        }
                    });

                    gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            toolbarOperation.setText("完成");
                            i++;
                            gridView.startEditMode(position);
                            return true;
                        }
                    });
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(getContext(), parent.getAdapter().getItem(position).toString(),
                                    Toast.LENGTH_SHORT).show();
                            if(parent.getAdapter().getItem(position).toString().equals("快递查询")){
                                UIHelper.startActivity(getActivity(), EmsQueryActivity.class);
                            }
                            if(parent.getAdapter().getItem(position).toString().equals("天气预报")){
                                UIHelper.startActivity(getActivity(), WeatherActivity.class);
                            }
                            if (parent.getAdapter().getItem(position).toString().equals("电量查询")) {
                                UIHelper.startActivity(getActivity(), DormActivity.class);
                            }
                        }
                    });
            }
            super.handleMessage(msg);
        }
    };
    public class ServiceDetail {
        List<ServiceData> servicedatas;
    }
    @Override
    public void onDestroyView() {
         Log.d("TAG", "onDestroyView ");
        super.onDestroyView();
        ButterKnife.unbind(this);
        getToolbarActivity().resetToolbar();
        //javabean转换成json字符串
        Gson gson = new Gson();
        String jsonStr=gson.toJson(servicedetail.servicedatas);
        jsonStr="{\"servicedatas\": "+jsonStr+"}";
        SharedPreferences mySharedPreferences = getActivity().getSharedPreferences("current_service1",
                Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putString("service", jsonStr);
        //提交当前数据
        editor.commit();
    }

}
