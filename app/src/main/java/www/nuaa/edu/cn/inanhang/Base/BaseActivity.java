package www.nuaa.edu.cn.inanhang.Base;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.squareup.okhttp.Request;

import java.util.List;

import rx.functions.Action1;
import www.nuaa.edu.cn.inanhang.Model.Preferences;
import www.nuaa.edu.cn.inanhang.Model.User;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;

/**
 * 本项目所有的Activity都需要继承此类，提供了以下基本功能：<br/>
 * 1）创建沉浸式状态栏；
 * 2）检测内存泄露。
 *
 * @author sun
 * @version 15/8/24
 */
public class BaseActivity extends AppCompatActivity {

    private boolean mAlive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NMActivityManager.getManager().add(this);
    }

    /**
     * 用于实现沉浸式状态栏，支持Android 4.4+
     *
     * @param view 用于覆盖状态栏的视图
     */
    protected void setImmerseLayout(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            int sbh = resourceId > 0 ? getResources().getDimensionPixelSize(resourceId) : 0;
            view.setPadding(0, sbh, 0, 0);
        }
    }

    public NMApplication getNMApplication() {
        return (NMApplication) getApplication();
    }

    public Preferences getPreferences() {
        return getNMApplication().getPreferences();
    }

    public User getUser() {
        return getNMApplication().getUser();
    }

    public void executeRequest(Request request, Action1<String> onNext, Action1<Throwable> onFail) {
        getNMApplication().executeRequest(request, onNext, onFail);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mAlive) {
            restoreFromBackground();
            mAlive = true;
        }
    }

    /**
     * 从后台进入前台时调用
     */
    protected void restoreFromBackground() {

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isAppOnForeground()) mAlive = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NMActivityManager.getManager().remove(this);
//        RefWatcher refWatcher = getNMApplication().getRefWatcher();
//        // 检测内存泄露
//        refWatcher.watch(this);
    }

    private boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) return false;

        String packageName = getPackageName();
        for (RunningAppProcessInfo ap : appProcesses) {
            // The name of the process that this object is associated with.
            if (ap.processName.equals(packageName) && ap.importance == IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
}
