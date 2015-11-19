package www.nuaa.edu.cn.inanhang.ViewFragment;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import www.nuaa.edu.cn.inanhang.Base.BaseFragment;
import www.nuaa.edu.cn.inanhang.Base.BaseToolbarActivity;
import www.nuaa.edu.cn.inanhang.R;
import www.nuaa.edu.cn.inanhang.Util.NotificationListViewAdspter;
import www.nuaa.edu.cn.inanhang.Util.NotificationsDB;
import www.nuaa.edu.cn.inanhang.ViewActivity.NotificationDetail;

/**
 * Created by yxy on 15/10/24.
 */
public class AnnouncementFragment extends BaseFragment {
    @Bind(R.id.announcement_list)
    PullToRefreshListView mAnnouncementList;
    @Bind(R.id.announcement_text)
    TextView mAnnouncementText;
    private NotificationsDB notificationsDB;
    private Cursor cursor;
    NotificationsDetail notificationsdetail = null;
    NotificationCompat.Builder mBuilder;
    NotificationListViewAdspter notificationListViewAdspter;
    int notifyId = 100;
    /**
     * Notification管理
     */
    public NotificationManager mNotificationManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setContentView(R.layout.fragment_announcement);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void setupToolbar(BaseToolbarActivity toolbar) {
        toolbar.setToolbarTitle("公告");
    }

    @Override
    public void onResume() {
        super.onResume();
        cursor.requery();
        notificationListViewAdspter.notifyDataSetChanged();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        notificationsDB = new NotificationsDB(getApplication().getApplicationContext());
        cursor = notificationsDB.select();
        getNotifications();
        notificationListViewAdspter = new NotificationListViewAdspter(getApplication(), cursor);
        mAnnouncementList.setAdapter(notificationListViewAdspter);
        mAnnouncementList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(getActivity(), NotificationDetail.class);
                Bundle bundle = new Bundle();
                cursor.moveToPosition(arg2-1);
                notificationsDB.update(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6), "已读", cursor.getString(8));
                cursor.requery();
                notificationListViewAdspter.notifyDataSetChanged();
                mAnnouncementList.onRefreshComplete();
                cursor.moveToPosition(arg2-1);
                bundle.putSerializable("title", cursor.getString(1));
                bundle.putSerializable("content", cursor.getString(2));
                bundle.putSerializable("pic", cursor.getString(6));
                bundle.putSerializable("level", cursor.getString(3));
                bundle.putSerializable("source", cursor.getString(5));
                bundle.putSerializable("time", cursor.getString(4));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mAnnouncementList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                cursor.requery();
                notificationListViewAdspter.notifyDataSetChanged();
                new GetDataTask().execute();
            }
        });
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {
        @Override
        protected String[] doInBackground(Void... params) {
            getNotifications();
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            mAnnouncementList.onRefreshComplete();

            super.onPostExecute(result);
        }
    }

    private void getNotifications() {
        //获取系统时间
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String endtime = sDateFormat.format(new Date());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("current_time",
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String starttime = sharedPreferences.getString("time", "");
        if (starttime.equals("")) {
            starttime = "2015-07-01 12:00:00";
        }
        if(cursor.getCount() == 0){
            mAnnouncementText.setText("没有数据");
            mAnnouncementList.setVisibility(View.GONE);
            getAsync("http://115.159.40.226:9000/restserver/api/v1/announcements?area=杭州&startTime="+starttime+"&endTime="+endtime);
        }else {
            mAnnouncementText.setVisibility(View.GONE);
            getAsync("http://115.159.40.226:9000/restserver/api/v1/announcements?area=杭州&startTime="+starttime+"&endTime="+endtime);

        }
    }

    private void getAsync(String url) {
        Log.d("log1",url);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("userName", "18013870796")
                .header("hashedPassword", "123456")
                .build();
        client.setConnectTimeout(10, TimeUnit.SECONDS);
        client.setWriteTimeout(10, TimeUnit.SECONDS);
        client.setReadTimeout(30, TimeUnit.SECONDS);

        Gson gson = new Gson();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }
            @Override
            public void onResponse(Response response) throws IOException {
                String result = response.body().string();
                Log.i("result",result);
                if (response.code() == 200) {
                    NotificationsDetail notificationsdetailjiexi = gson.fromJson(result, NotificationsDetail.class);
                    notificationsdetail = notificationsdetailjiexi;
                    handler.sendEmptyMessage(200);
                } else {
                    HttprGetError httprgeterror = gson.fromJson(result, HttprGetError.class);
                    handler.sendEmptyMessage(300);
                }
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    if (notificationsdetail.announcements.size() != 0) {
                        int i = 0;
                        for (; i < notificationsdetail.announcements.size(); i++) {
                            notificationsDB.insert(notificationsdetail.announcements.get(i).title,
                                    notificationsdetail.announcements.get(i).content, notificationsdetail.announcements.get(i).level,
                                    notificationsdetail.announcements.get(i).time, notificationsdetail.announcements.get(i).source,
                                    notificationsdetail.announcements.get(i).pic, "未读", notificationsdetail.announcements.get(i).noticeareacode);
                            cursor.requery();
                            mAnnouncementList.setVisibility(View.VISIBLE);
                            mAnnouncementText.setVisibility(View.GONE);
                        }
                    }
                    cursor.requery();
                    cursor.moveToFirst();
                    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String endtime = null;
                    try {
                        Date date = fmt.parse(cursor.getString(4));
                        GregorianCalendar cal = new GregorianCalendar();
                        cal.setTime(date);
                        cal.add(13, 1);
                        date = cal.getTime();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        endtime = formatter.format(date);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SharedPreferences mySharedPreferences = getActivity().getSharedPreferences("current_time",
                            Activity.MODE_PRIVATE);
                    //实例化SharedPreferences.Editor对象（第二步）
                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                    //用putString的方法保存数据
                    Log.i("endtime",endtime);
                    editor.putString("time", endtime);
                    //提交当前数据
                    editor.commit();
                    notificationListViewAdspter.notifyDataSetChanged();
                    break;
                case 300:
                    Toast.makeText(getApplication().getApplicationContext(), "出错",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public class NotificationsDetail {
        List<Announcement> announcements;

        public class Announcement {
            String content;
            String time;
            String title;
            String level;
            String source;
            String pic;
            String noticeareacode;
        }
    }

    public class HttprGetError {
        Error error;

        public class Error {
            String errorCode;
            String data;
            String errorCodeMsg;
        }
    }

}
