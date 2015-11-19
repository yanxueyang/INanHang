package www.nuaa.edu.cn.inanhang.ViewFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.google.gson.Gson;

import org.askerov.dynamicgrid.DynamicGridView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.nuaa.edu.cn.inanhang.Base.BaseFragment;
import www.nuaa.edu.cn.inanhang.Base.BaseToolbarActivity;
import www.nuaa.edu.cn.inanhang.R;
import www.nuaa.edu.cn.inanhang.Util.ServiceDynamicAdapter;
import www.nuaa.edu.cn.inanhang.Util.UIHelper;
import www.nuaa.edu.cn.inanhang.View.TextSliderView;
import www.nuaa.edu.cn.inanhang.ViewActivity.DormActivity;
import www.nuaa.edu.cn.inanhang.ViewActivity.EmsQueryActivity;
import www.nuaa.edu.cn.inanhang.ViewActivity.LibraryActivity;
import www.nuaa.edu.cn.inanhang.ViewActivity.MycardActivity;
import www.nuaa.edu.cn.inanhang.ViewActivity.TitleRenameActivity;
import www.nuaa.edu.cn.inanhang.ViewActivity.WeatherActivity;
import www.nuaa.edu.cn.inanhang.ViewActivity.WebViewShow;

/**
 * Created by yxy on 15/10/24.
 */
public class HomeFragment extends BaseFragment{
    BaseToolbarActivity mtoolbaractivity=new BaseToolbarActivity();
    @Bind(R.id.ad_slider) SliderLayout mAdSlider;
    @Bind(R.id.homeGridView)DynamicGridView gridView;
    private ServiceFragment.ServiceDetail servicedetail;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setContentView(R.layout.fragment_home);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    protected void setupToolbar(BaseToolbarActivity toolbar) {
        toolbar.showPortrait();
        mtoolbaractivity=toolbar;
        if(get().equals("")){
            save("i南航");
        }
        toolbar.setToolbarTitle(get());
        toolbar.getToolbarTitle().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("onLongClick", "onLongClick");
                UIHelper.startActivity(getActivity(), TitleRenameActivity.class);
                return false;
            }
        });
    }

    public void save(String text){
        SharedPreferences mySharedPreferences =  getActivity().getSharedPreferences("current_title",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("title", text);
        //提交当前数据
        editor.apply();
    }
    public String get(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("current_title",
                Activity.MODE_PRIVATE);
        String starttime = sharedPreferences.getString("title", "");
        return starttime;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // 初始化广告视图
        mAdSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        mAdSlider.setDuration(4000);


        TextSliderView  mGreetingView = new TextSliderView(getApplication());
        mGreetingView.setFragment(HomeFragment.this);
        mGreetingView.image(R.mipmap.adv_bg);
        mAdSlider.addSlider(mGreetingView);

//        mAdSlider.addSlider(new ImageSliderView(getApplication()).image(R.mipmap.adv_bg));
        getService1();

    }
    @Override
    public void onResume() {
        super.onResume();
        mtoolbaractivity.setToolbarTitle(get());
    }
    @OnClick(R.id.home_service_library)
    public void home_service_library(){
        UIHelper.startActivity(getActivity(), LibraryActivity.class);
    }
    @OnClick(R.id.home_service_card)
    public void home_service_card(){
        UIHelper.startActivity(getActivity(), MycardActivity.class);
    }
//    @OnClick(R.id.home_service_nav)
//    public void home_service_nav(){
//        Intent intent = new Intent(getActivity(), WebViewShow.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("title", "校园导航");
//        bundle.putSerializable("url", "http://map.baidu.com");
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }
    @OnClick(R.id.home_ad)
    public void home_ad(){
        Intent intent = new Intent(getActivity(), WebViewShow.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("title", "活动详情");
        bundle.putSerializable("url", "http://my.nuaa.edu.cn/evaluation/index.php");
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        Log.d("TAG", "onDestroyView ");
        super.onDestroyView();
        ButterKnife.unbind(this);
        getToolbarActivity().resetToolbar();
    }

    private void getService1(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("current_service1",
                Activity.MODE_PRIVATE);
//         使用getString方法获得value，注意第2个参数是value的默认值
        String service = sharedPreferences.getString("service", "");
        if (service.equals("")) {
            service ="{\"servicedatas\": [" +
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
        Log.i("service", service);
        Gson gson = new Gson();
        ServiceFragment.ServiceDetail servicedetailjiexi = gson.fromJson(service, ServiceFragment.ServiceDetail.class);
        servicedetail=servicedetailjiexi;
        Log.i("service", servicedetail.servicedatas.size() + "");
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
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(getContext(), parent.getAdapter().getItem(position).toString(),
                                    Toast.LENGTH_SHORT).show();
                            if (parent.getAdapter().getItem(position).toString().equals("快递查询")) {
                                UIHelper.startActivity(getActivity(), EmsQueryActivity.class);
                            }
                            if (parent.getAdapter().getItem(position).toString().equals("天气预报")) {
                                UIHelper.startActivity(getActivity(), WeatherActivity.class);
                            }
                            if (parent.getAdapter().getItem(position).toString().equals("电量查询")) {
                                UIHelper.startActivity(getActivity(), DormActivity.class);
                            }
                        }
                    });
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
