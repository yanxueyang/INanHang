package www.nuaa.edu.cn.inanhang.ViewActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import www.nuaa.edu.cn.inanhang.Base.BaseToolbarActivity;
import www.nuaa.edu.cn.inanhang.Model.ColumnChartItem;
import www.nuaa.edu.cn.inanhang.R;
import www.nuaa.edu.cn.inanhang.View.ColumnChartView;

/**
 * Created by yxy on 15/11/10.
 */
public class ElectricActivity extends BaseToolbarActivity {
    @Bind(R.id.powercost)TextView powercost;
    @Bind(R.id.powerdosage)TextView powerdosage;
    @Bind(R.id.horizontalScrollView)HorizontalScrollView horizontalScrollView;
    @Bind(R.id.ll_scrollview)LinearLayout ll_scrollview;
    @Bind(R.id.electrictext)TextView electrictext;
    ColumnChartView tv;
    ArrayList<ColumnChartItem> list = new ArrayList<ColumnChartItem>();
    String louyu,sushe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electric);
        setToolbarTitle("电量查询");
        louyu = getIntent().getStringExtra("louyu");
        sushe = getIntent().getStringExtra("sushe");
        //获取系统时间
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String endtime = sDateFormat.format(new Date());
        electrictext.setText("以上为"+louyu+"幢"+sushe+"宿舍\n截至止"+endtime+"的查询结果");
//        SpannableString styledText = new SpannableString("本月已用电量\n210.21度");
//        styledText.setSpan(new AbsoluteSizeSpan(50), 6,styledText.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        powercost.setText(styledText, TextView.BufferType.SPANNABLE);
//        styledText = new SpannableString("本月已用电费\n115.62元");
//        styledText.setSpan(new AbsoluteSizeSpan(50), 6,styledText.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        powerdosage.setText(styledText, TextView.BufferType.SPANNABLE);
        tv=new ColumnChartView(getApplicationContext());
//        tv.setMinimumWidth(5000);
//        ll_scrollview.addView(tv);
        tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("获取屏幕点击点坐标",event.getX()+""+event.getY());
                for(int i=0;i<tv.xyItems.size();i++){
                    if(event.getX()>(tv.xyItems.get(i).getX()-50)&&event.getX()<(tv.xyItems.get(i).getX()+50)){
                        if(event.getY()>(tv.xyItems.get(i).getY()-50)&&event.getY()<(tv.xyItems.get(i).getY()+50)){
                            Log.i("获取屏幕点击点坐标",i+"选定");
                            powercost.setText(list.get(i).getX()+"已用电费\n"+list.get(i).getY()+"元");
                            powerdosage.setText(list.get(i).getX()+"已用电量\n"+list.get(i).getY()*2+"度");
                        }
                    }
                }
                return false;
            }
        });
        loadChart();

    }
    private void loadChart() {
        list.add(new ColumnChartItem("本月", 105.5f));
//        list.add(new ColumnChartItem("11月份", 213.5f));
        list.add(new ColumnChartItem("10月份", 205.5f));
        list.add(new ColumnChartItem("9月份", 113.5f));
        list.add(new ColumnChartItem("8月份", 224.7f));
        list.add(new ColumnChartItem("7月份", 0.0f));
        list.add(new ColumnChartItem("6月份", 131.5f));
        list.add(new ColumnChartItem("5月份", 105.5f));
        list.add(new ColumnChartItem("4月份", 213.5f));
        list.add(new ColumnChartItem("3月份", 124.7f));
        list.add(new ColumnChartItem("2月份", 0.0f));
        list.add(new ColumnChartItem("1月份", 111.5f));

        tv.setMinimumWidth((list.size()) * 180);
        ll_scrollview.addView(tv);
        tv.SetTuView(list, "单位: 元");
    }
    @OnClick(R.id.gotorecharge)
    public void gotorecharge(){
        Toast.makeText(this, "暂未提供此类服务", Toast.LENGTH_LONG).show();
    }
}