package www.nuaa.edu.cn.inanhang.ViewActivity;

import android.app.Activity;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import www.nuaa.edu.cn.inanhang.Base.BaseActivity;
import www.nuaa.edu.cn.inanhang.MainActivity;
import www.nuaa.edu.cn.inanhang.R;
import www.nuaa.edu.cn.inanhang.Util.UIHelper;

/**
 * 启动页
 *
 * @author sun
 * @version 15/8/24
 */
public class SplashActivity extends BaseActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setImmerseLayout(findViewById(R.id.splash_bg));
        if (!getNMApplication().isLogin()) {
            Observable.timer(1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> {
                        Class<? extends Activity> cls = null;
                        if (getNMApplication().isFirstUse()) {
                            cls = IntroActivity.class;
                            // 标记App已经使用过
                            getPreferences().setUsed();
                        } else {
                            cls = MainActivity.class;
                        }
                        UIHelper.startActivityWithFinish(SplashActivity.this, cls);
                    });
            return;
        }
    }
}
