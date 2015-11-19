package www.nuaa.edu.cn.inanhang.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.Serializable;

import www.nuaa.edu.cn.inanhang.Base.BaseFragment;
import www.nuaa.edu.cn.inanhang.R;

/**
 * @author sun
 * @version 15/8/27
 */
public final class UIHelper {
    /**
     * 显示Toast
     */
    public static void showToast(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }

    public static void showNetError(Context context) {
        showToast(context, "请求失败，请检查网络连接");
    }

    public static void startActivity(Activity currentActivity, Class<? extends Activity> target) {
        Intent intent = new Intent(currentActivity, target);
        currentActivity.startActivity(intent);
    }

    public static void startActivityWithFinish(Activity currentActivity, Class<? extends Activity> target) {
        Intent intent = new Intent(currentActivity, target);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }

//    public static void authen(Activity current) {
//        ActivityLauncher.create(current)
//                .target(HostActivity.class)
//                .putExtra(HostActivity.KEY_HOST_TYPE, HostActivity.HOST_TYPE_AUTHEN)
//                .start();
//    }

    public static void replaceFragment(FragmentManager manager, BaseFragment fragment) {
        manager.beginTransaction()
                .addToBackStack(fragment.getClass().getSimpleName())
                .setCustomAnimations(R.anim.push_left_in,
                        R.anim.push_left_out,
                        R.anim.push_right_in,
                        R.anim.push_right_out)
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    /**
     * 显示Progress Dialog
     */
    public static MaterialDialog showProgressDialog(Context context, String msg) {
        return new MaterialDialog.Builder(context)
                .content(msg)
                .progress(true, 0)
//                .cancelable(false)
                .show();
    }

    /**
     * 获取屏幕尺寸
     */
    public static Point getScreenSize(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size;
    }

    public static class ActivityLauncher {
        private final Activity current;
        private final Intent intent;
        private Class<? extends Activity> target;

        private ActivityLauncher(Activity current) {
            this.current = current;
            this.intent = new Intent();
        }

        public static ActivityLauncher create(Activity current) {
            return new ActivityLauncher(current);
        }

        public ActivityLauncher target(Class<? extends Activity> target) {
            this.target = target;
            return this;
        }

        public ActivityLauncher putExtra(String key, String value) {
            this.intent.putExtra(key, value);
            return this;
        }

        public ActivityLauncher putExtra(String key, Integer value) {
            this.intent.putExtra(key, value);
            return this;
        }

        public ActivityLauncher putExtra(String key, Serializable value) {
            intent.putExtra(key, value);
            return this;
        }

        public void start() {
            intent.setClass(current, target);
            current.startActivity(this.intent);
        }

        public void startForResult(int requestCode) {
            intent.setClass(current, target);
            current.startActivityForResult(intent, requestCode);
        }
    }
}
