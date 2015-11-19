package www.nuaa.edu.cn.inanhang.Base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;

import rx.Observable;

public final class NMActivityManager {

//    private static final String TAG = NMActivityManager.class.getSimpleName();

    private static NMActivityManager sManager;

    private ArrayList<BaseActivity> mStack;

    public static synchronized NMActivityManager getManager() {
        if (sManager == null) sManager = new NMActivityManager();
        return sManager;
    }

    private NMActivityManager() {
        mStack = new ArrayList<>();
    }

    public void add(BaseActivity activity) {
        mStack.add(activity);
        // Log.d(TAG, "add activity: " + activity.getClass().getName());
    }

    public void remove(BaseActivity activity) {
        mStack.remove(activity);
        // Log.d(TAG, "remove activity: " + activity.getClass().getName());
    }

    public void finish(Class clz) {
        Observable.from(mStack).filter(act -> act.getClass().equals(clz)).subscribe(Activity::finish);
    }

    public void clearStack() {
        Observable.from(mStack).forEach(Activity::finish);
    }

    public void exit(Application application) {
        clearStack();
        ActivityManager am = (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
        try {
            am.killBackgroundProcesses(application.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(1);
    }
}
