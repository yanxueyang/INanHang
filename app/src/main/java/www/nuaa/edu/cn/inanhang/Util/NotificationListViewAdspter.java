package www.nuaa.edu.cn.inanhang.Util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import www.nuaa.edu.cn.inanhang.R;

/**
 * Created by yxy on 15/9/17.
 */
public class NotificationListViewAdspter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private Cursor cursor;

    public NotificationListViewAdspter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public final class Kongjian {
        ImageView notificationimageView;
        TextView notificationtitle;
        TextView notificationcontent;
        TextView notificationsource;
        TextView notificationtime;
        TextView notificationread;
        ImageView notificationlevel;
        TextView notificationnone;
    }

    public int getCount() {
        return cursor.getCount();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int posititon, View converView, ViewGroup parent) {
        Kongjian kongjian = null;
//        if (converView == null) {
        kongjian = new Kongjian();
        converView = layoutInflater.inflate(R.layout.item_announcement, null);
        kongjian.notificationimageView = (ImageView) converView.findViewById(R.id.anm_image);
        kongjian.notificationtitle = (TextView) converView.findViewById(R.id.anm_title);
        kongjian.notificationcontent = (TextView) converView.findViewById(R.id.anm_content);
        kongjian.notificationsource = (TextView) converView.findViewById(R.id.anm_source);
        kongjian.notificationtime = (TextView) converView.findViewById(R.id.anm_date);
        kongjian.notificationread = (TextView) converView.findViewById(R.id.anm_mark);
        kongjian.notificationlevel = (ImageView) converView.findViewById(R.id.anm_level);
        converView.setTag(kongjian);
//        } else {
//            kongjian = (Kongjian) converView.getTag();
//        }
        cursor.moveToPosition(posititon);
//        kongjian.notificationimageView.setBackgroundResource(R.mipmap.announcement_pic_default);
        kongjian.notificationtitle.setText(cursor.getString(1));
        String content = cursor.getString(2);
        String regxp = "<\\s*img\\s+([^>]*)\\s*>";
        Pattern pattern = Pattern.compile(regxp);
        Matcher matcher = pattern.matcher(content);
        StringBuffer sb = new StringBuffer();
        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        content = sb.toString();
        // 过滤文章内容中的html
        content = content.replaceAll("</?[^<]+>", "");
        // 去除字符串中的空格 回车 换行符 制表符 等
        content = content.replaceAll("\\s*|\t|\r|\n", "");
        // 去除空格
        content = content.replaceAll("&nbsp;", "");
        kongjian.notificationcontent.setText(Html.fromHtml(content));
        kongjian.notificationsource.setText(cursor.getString(5));
        kongjian.notificationread.setText(cursor.getString(7));
        if (cursor.getString(7).equals("已读")) {
            kongjian.notificationread.setTextColor(Color.rgb(209, 209, 209));
            converView.setBackgroundColor(Color.rgb(226, 226, 226));
        } else {
            kongjian.notificationread.setTextColor(Color.rgb(62, 177, 235));
            converView.setBackgroundColor(Color.rgb(255, 255, 255));
        }
        if (cursor.getString(3).equals("3")) {
            kongjian.notificationlevel.setBackgroundResource(R.mipmap.announcement_level_1_icon);
        } else if (cursor.getString(3).equals("2")) {
            kongjian.notificationlevel.setBackgroundResource(R.mipmap.announcement_level_2_icon);
        } else {
            kongjian.notificationlevel.setBackgroundResource(R.mipmap.announcement_level_3_icon);
        }
        //获取系统时间
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currenttime = sDateFormat.format(new Date());
        long days = 0;
        try {
            Date currentdate = sDateFormat.parse(currenttime);
            Date fabudate = sDateFormat.parse(cursor.getString(4).substring(0, 10));
            long diff = currentdate.getTime() - fabudate.getTime();
            days = diff / (1000 * 60 * 60 * 24);
        } catch (Exception e) {
        }
        if (days == 0) {
            kongjian.notificationtime.setText("今天  " + cursor.getString(4).substring(10, 16));
        } else if (days == 1) {
            kongjian.notificationtime.setText("昨天  " + cursor.getString(4).substring(10, 16));
        } else if (days == 2) {
            kongjian.notificationtime.setText("前天  " + cursor.getString(4).substring(10, 16));
        } else {
            kongjian.notificationtime.setText(cursor.getString(4).substring(0, 10));
        }
        if (cursor.getString(6).contains("http")) {
            Picasso.with(context).load(cursor.getString(6))
                    .placeholder(R.mipmap.announcement_pic_default)
                    .error(R.mipmap.announcement_pic_default)
                    .resize(88, 62).into(kongjian.notificationimageView);
        }
        return converView;
    }

}
