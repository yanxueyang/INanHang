package www.nuaa.edu.cn.inanhang.Base;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import net.danlew.android.joda.JodaTimeAndroid;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import www.nuaa.edu.cn.inanhang.Model.Preferences;
import www.nuaa.edu.cn.inanhang.Model.User;

/**
 * nmApplication
 *
 * @author sun
 * @version 15/8/19
 */
public final class NMApplication extends Application {
    //DEBUG
    private static final String TAG = NMApplication.class.getSimpleName();

    //    private RefWatcher mRefWatcher;
    private OkHttpClient mHttpClient;
    private Preferences mPreferences;
    private User mUser;
    private DiskLruCache mDiskLruCache;

    @Override
    public void onCreate() {
        super.onCreate();
        // 用于检测内存泄露
//        mRefWatcher = LeakCanary.install(this);
        // 日志
        Logger.init("NewMan")               // default PRETTYLOGGER or use just init()
                .setMethodCount(0)            // default 2
                .hideThreadInfo()             // default shown
                .setLogLevel(LogLevel.FULL)  // default LogLevel.FULL
                .setMethodOffset(2);
        // 日期
        JodaTimeAndroid.init(this);
        // 初始化缓存
        try {
            mDiskLruCache = DiskLruCache.open(getCacheDir(), 2, 4, Integer.MAX_VALUE);
        } catch (IOException e) {
            Log.e(TAG, "缓存创建失败", e);
        }



        // 加载本地账户缓存信息并初始化账户
        mUser = new User();
        mPreferences = new Preferences(this, mUser);

        // 处理app异常退出
        setExceptionHandler();
    }

//    public RefWatcher getRefWatcher() {
//        return mRefWatcher;5
//    }

    /**
     * 获取OkHttpClient，用于发送http请求
     *
     * @return OkHttpClient
     */
    @NonNull
    public OkHttpClient httpClient() {
        return mHttpClient;
    }

    public DiskLruCache cache() {
        return mDiskLruCache;
    }

    /**
     * 获取当前用户
     *
     * @return User
     */
    @NonNull
    public User getUser() {
        return mUser;
    }

    /**
     * 账户注销
     */
    public void logout() {
        // 重新创建账户信息
        mUser = new User();
        mPreferences = new Preferences(this, mUser);
    }

    @NonNull
    public Preferences getPreferences() {
        return mPreferences;
    }

    /**
     * app是否是初次使用
     *
     * @return true表示初次使用
     */
    public boolean isFirstUse() {
        return !mPreferences.isUsed();
    }

    /**
     * 本地是否缓存账号信息
     *
     * @return true表示本地存在账号信息
     */
    public boolean isLogin() {
        return mUser.getPassword() != null;
    }

    /**
     * 当前账号是否与管理系统绑定
     *
     * @return true表示已绑定
     */
    public boolean isBinding() {
        return mUser.getPersonID() != null;
    }

    /**
     * 执行客户端请求
     *
     * @param request 请求
     * @param onNext  请求执行成功时调用该对象进行处理
     * @param onFail  请求执行失败时调用该对象进行处理
     */
    public void executeRequest(final Request request, Action1<String> onNext, Action1<Throwable> onFail) {
        Observable.create((Subscriber<? super String> subscriber) -> {
//            try {
//                if (BuildConfig.DEBUG) Log.d(TAG, request.urlString());
//                Response response = mHttpClient.newCall(request).execute();
//                String data = response.body().string();
//                if (BuildConfig.DEBUG) Logger.json(data);
//                if (response.isSuccessful()) {
//                    subscriber.onNext(data);
//                    subscriber.onCompleted();
//                } else {
//                    subscriber.onError(new RequestFailureException(response, data));
//                }
//            } catch (Exception e) {
//                Log.e(TAG, "HTTP请求错误", e);
//                subscriber.onError(e);
//            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext::call, onFail::call);
    }

    /**
     * 处理App异常终止
     */
    public void setExceptionHandler() {
        Thread.currentThread().setUncaughtExceptionHandler((thread, ex) -> {
//            try {
//                PrintWriter logPw = FileUtils.generateLogFile();
//                if (logPw != null) {
//                    ex.printStackTrace(logPw);
//                    logPw.flush();
//                    logPw.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            //// 重启App TODO: 15/10/2  可能存在BUG，需要测试
//            Intent intent = new Intent(this, SplashActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            // 退出App
//            NMActivityManager.getManager().exit(NMApplication.this);
        });
    }

}
