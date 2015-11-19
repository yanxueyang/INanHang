package www.nuaa.edu.cn.inanhang.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yxy on 15/9/19.
 */
public class NotificationsDB extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "Notifications.db";
    private final static int DATABASE_VERSION = 2;
    private final static String TABLE_NAME = "notifications_table";
    public final static String Notifications_id = "Notifications_id";
    public final static String Notifications_title = "Notifications_title";
    public final static String Notifications_content = "Notifications_content";
    public final static String Notifications_level = "Notifications_level";
    public final static String Notifications_time = "Notifications_time";
    public final static String Notifications_source = "Notifications_source";
    public final static String Notifications_pic = "Notifications_pic";
    public final static String Notifications_read = "Notifications_read";
    public final static String Notifications_noticeAreaCode = "Notifications_noticeAreaCode";

    public NotificationsDB(Context context) {
// TODO Auto-generated constructor stub
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //创建table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + Notifications_id
                + " INTEGER primary key autoincrement, " + Notifications_title + " text, " + Notifications_content + " text, "
                + Notifications_level + " text, " + Notifications_time + " text, "
                + Notifications_source + " text, " + Notifications_pic + " text, " + Notifications_read + " text, " + Notifications_noticeAreaCode + " text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public Cursor select() {
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db
//                .query(TABLE_NAME, null, null, null, null, null, null);
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " order by " + Notifications_time + " desc", null);
        return cursor;
    }

    //增加操作
    public long insert(String title, String content, String level, String time, String source, String pic, String read, String noticeAreaCode) {
        SQLiteDatabase db = this.getWritableDatabase();
/* ContentValues */
        ContentValues cv = new ContentValues();
        cv.put(Notifications_title, title);
        cv.put(Notifications_content, content);
        cv.put(Notifications_level, level);
        cv.put(Notifications_time, time);
        cv.put(Notifications_source, source);
        cv.put(Notifications_pic, pic);
        cv.put(Notifications_read, read);
        cv.put(Notifications_noticeAreaCode, noticeAreaCode);
        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }

    //删除操作
    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = Notifications_id + " = ?";
        String[] whereValue = {Integer.toString(id)};
        db.delete(TABLE_NAME, where, whereValue);
    }

    public void deleteall() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    //修改操作
    public void update(int id, String title, String content, String level, String time, String source, String pic, String read, String noticeAreaCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = Notifications_id + " = ?";
        String[] whereValue = {Integer.toString(id)};

        ContentValues cv = new ContentValues();
        cv.put(Notifications_title, title);
        cv.put(Notifications_content, content);
        cv.put(Notifications_level, level);
        cv.put(Notifications_time, time);
        cv.put(Notifications_source, source);
        cv.put(Notifications_pic, pic);
        cv.put(Notifications_read, read);
        cv.put(Notifications_noticeAreaCode, noticeAreaCode);
        db.update(TABLE_NAME, cv, where, whereValue);
    }

}
