package www.nuaa.edu.cn.inanhang.ViewActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cocosw.bottomsheet.BottomSheet;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import www.nuaa.edu.cn.inanhang.Base.BaseToolbarActivity;
import www.nuaa.edu.cn.inanhang.R;
import www.nuaa.edu.cn.inanhang.Util.EmsListViewAdspter;

import static www.nuaa.edu.cn.inanhang.Util.UIHelper.showProgressDialog;

/**
 * Created by yxy on 15/10/27.
 */
public class EmsQueryActivity extends BaseToolbarActivity {
    private ListView emsshow = null;
    EmsDetail emsdetail = null;
    BottomSheet bottomSheet = null;
    MaterialDialog dialog = null;
    List<Map<String, Object>> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ems_query);
        setToolbarTitle("快递查询");

        emsshow = (ListView)findViewById(R.id.emsframentlistview);
        emsshow.setClickable(false);
        Button getems = (Button) findViewById(R.id.emsframentbutton);
        MaterialEditText emsnumber = (MaterialEditText) findViewById(R.id.emsframentnumber);
        TextView emscompany = (TextView) findViewById(R.id.emsframentcompany);
        getems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emscompany.getText().equals("请选择配送商")!=true&&emsnumber.getText().equals("")!=true) {
                    dialog = showProgressDialog(EmsQueryActivity.this, "正在加载");
                    getAsync("http://a.apix.cn/apixlife/express/delivery?id=" + emsnumber.getText() + "&logistics=" + emscompany.getText());
                }  else {
                    Toast.makeText(getApplication().getApplicationContext(), "单号或配送商不能为空",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        emscompany.setClickable(true);
        emscompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Map<String, Object>> list = null;
                bottomSheet = new BottomSheet.Builder(EmsQueryActivity.this)
                        .title("请选配送商").sheet(0, "申通快递").sheet(1, "EMS").sheet(2, "顺丰速运")
                        .sheet(3, "韵达快递").sheet(4, "中通快递")
                        .sheet(5, "圆通速递").listener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("yxy", bottomSheet.getMenu().getItem(which).getTitle().toString());
                                emscompany.setText(bottomSheet.getMenu().getItem(which).getTitle().toString());
                            }
                        }).show();
            }
        });
    }

    private void getAsync(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("apix-key", "4f6632e8b45c44aa611e92203e36100d")
                .build();
        client.setConnectTimeout(2, TimeUnit.SECONDS);
        client.setWriteTimeout(2, TimeUnit.SECONDS);
        client.setReadTimeout(2, TimeUnit.SECONDS);
        Response response = null;
        Gson gson = new Gson();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("chaoshi", "超时");
                handler.sendEmptyMessage(400);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                dialog.dismiss();
                String result = response.body().string();
                //不能操作ui，回调依然在子线程
                Log.d("TAG", result);
                EmsDetail emsdetailjiexi = gson.fromJson(result, EmsDetail.class);
                emsdetail = emsdetailjiexi;
                Log.d("yxy", emsdetail.error_code.toString());
                if (emsdetailjiexi.error_code.equals("0")) {
                    handler.sendEmptyMessage(200);
                } else {
                    handler.sendEmptyMessage(300);
                }
            }

        });
    }

    public List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < emsdetail.data.result.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            if (i == 0) {
                map.put("image", R.mipmap.ems_running_icon);
                map.put("context", emsdetail.data.result[i].context);
                map.put("time", emsdetail.data.result[i].time);
            } else {
                map.put("image", R.mipmap.ems_company_icon);
                map.put("context", emsdetail.data.result[i].context);
                map.put("time", emsdetail.data.result[i].time);
            }
            list.add(map);
        }
        return list;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    list = getData();
                    emsshow.setAdapter(new EmsListViewAdspter(getApplication().getApplicationContext(), list));
                    break;
                case 300:
                    if (list != null) {
                        list.clear();
                        emsshow.setAdapter(new EmsListViewAdspter(getApplication().getApplicationContext(), list));
                    }
                    Log.d("错误", emsdetail.message);
                    if (emsdetail.error_code.equals("-2")) {
                        Toast.makeText(getApplication().getApplicationContext(), "单号或配送商不能为空",
                                Toast.LENGTH_SHORT).show();
                    } else if(emsdetail.error_code.equals("-5")){
                        Toast.makeText(getApplication().getApplicationContext(), "参数错误",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplication().getApplicationContext(),emsdetail.message,
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 400:
                    if (list != null) {
                        list.clear();
                        emsshow.setAdapter(new EmsListViewAdspter(getApplication().getApplicationContext(), list));
                    }
                    Log.d("chaoshi", "超时");
                    dialog.dismiss();
                    Toast.makeText(EmsQueryActivity.this, "连接超时，请重新查询",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public class EmsDetail {
        String error_code;
        String message;
        Data data;

        public class Data {
            String updatetime;
            Result result[];
        }

        public class Result {
            String ftime;
            String location;
            String context;
            String time;
        }
    }
}
