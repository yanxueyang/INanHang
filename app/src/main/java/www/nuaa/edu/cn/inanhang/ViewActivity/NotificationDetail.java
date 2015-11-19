package www.nuaa.edu.cn.inanhang.ViewActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import www.nuaa.edu.cn.inanhang.Base.BaseToolbarActivity;
import www.nuaa.edu.cn.inanhang.R;
import www.nuaa.edu.cn.inanhang.Util.NotificationsDB;

/**
 * Created by yxy on 15/9/20.
 */
public class NotificationDetail extends BaseToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notificationdetail);
        setToolbarTitle("公告详情");
        Intent intent = this.getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String pic = intent.getStringExtra("pic");
        String level = intent.getStringExtra("level");
        String source = intent.getStringExtra("source");
        String time = intent.getStringExtra("time");
        TextView notificationdetailtitle = (TextView) findViewById(R.id.notificationdetailtitle);
        notificationdetailtitle.setText(title);
        WebView notificationdetailcontent = (WebView) findViewById(R.id.notificationdetailcontent);
        content = content.replace("<img", "<img    width=\"100%\"");

        notificationdetailcontent.getSettings().setJavaScriptEnabled(true);
        notificationdetailcontent.getSettings().setDefaultTextEncodingName("utf -8");
        // 加载HTML字符串进行显示
        content = "<body bgcolor=\"#f1f1f1\">  <font size=\"4px\" style=\"line-height:150%;\">" + content;
        Log.d("yxy", content);
        notificationdetailcontent.loadData(content, "text/html; charset=UTF-8", null);// 这种写法可以正确解码
        TextView notificationdetailsource = (TextView) findViewById(R.id.notificationdetailsource);
        notificationdetailsource.setText(source);
        TextView notificationdetailtime = (TextView) findViewById(R.id.notificationdetailtime);
        //获取系统时间
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currenttime = sDateFormat.format(new Date());
        long days = 0;
        try {
            Date currentdate = sDateFormat.parse(currenttime);
            Date fabudate = sDateFormat.parse(time.substring(0, 10));
            long diff = currentdate.getTime() - fabudate.getTime();
            days = diff / (1000 * 60 * 60 * 24);
        } catch (Exception e) {
        }
        if (days == 0) {
            notificationdetailtime.setText("今天  " + time.substring(10, 16));
        } else if (days == 1) {
            notificationdetailtime.setText("昨天  " + time.substring(10, 16));
        } else if (days == 2) {
            notificationdetailtime.setText("前天  " + time.substring(10, 16));
        } else {
            notificationdetailtime.setText(time.substring(0, 10) + "  " + time.substring(10, 16));
        }
        Log.d("pic",pic+"");
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        ImageView notificationdetailpic = (ImageView) findViewById(R.id.notificationdetailpic);
        if (pic.contains("http")) {
            Picasso.with(getApplicationContext()).load(pic)
                    .placeholder(R.mipmap.adv_bg)
                    .error(R.mipmap.adv_bg)
                    .resize(metric.widthPixels, metric.widthPixels / 1080 * 480).into(notificationdetailpic);
        }
        ImageView notificationdetaillevel = (ImageView) findViewById(R.id.notificationdetaillevel);
        if (level.equals("3")) {
            notificationdetaillevel.setBackgroundResource(R.mipmap.announcement_level_1_icon);
        } else if (level.equals("2")) {
            notificationdetaillevel.setBackgroundResource(R.mipmap.announcement_level_2_icon);
        } else {
            notificationdetaillevel.setBackgroundResource(R.mipmap.announcement_level_3_icon);
        }
        NotificationsDB notificationsDB = new NotificationsDB(getApplication().getApplicationContext());
        Cursor cursor = notificationsDB.select();
        int weizhi = 0;
        for (weizhi = 0; weizhi < cursor.getCount(); weizhi++) {
            cursor.moveToPosition(weizhi);
            if (cursor.getString(4).equals(time)) {
                Log.d("yxy1+weizhi", weizhi + "");
                break;
            }
        }
        cursor.moveToPosition(weizhi);
        notificationsDB.update(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6), "已读", cursor.getString(8));
        cursor.requery();
    }

}
