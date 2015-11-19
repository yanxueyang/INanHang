package www.nuaa.edu.cn.inanhang;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.widget.ImageView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import www.nuaa.edu.cn.inanhang.Base.BaseToolbarActivity;
import www.nuaa.edu.cn.inanhang.Base.NMActivityManager;
import www.nuaa.edu.cn.inanhang.Util.UIHelper;
import www.nuaa.edu.cn.inanhang.ViewFragment.AnnouncementFragment;
import www.nuaa.edu.cn.inanhang.ViewFragment.HomeFragment;
import www.nuaa.edu.cn.inanhang.ViewFragment.PersonalFragment;
import www.nuaa.edu.cn.inanhang.ViewFragment.ServiceFragment;

public class MainActivity extends BaseToolbarActivity {

    private static final String[] sTabTags = {"home", "notification", "service","personal"};
    private static final int sTabIcons[] = {
            R.drawable.sel_nav_main,
            R.drawable.sel_nav_notice,
            R.drawable.sel_nav_service,
            R.drawable.sel_nav_me
    };
    private static final Class sFragments[] = {
            HomeFragment.class,
            AnnouncementFragment.class,
            ServiceFragment.class,
            PersonalFragment.class
    };

    @Bind(R.id.tab_host)
    FragmentTabHost mTabHost;

    private int mKeyClickedCount;
    private FragmentStatus mFragmentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideToolbarBack();
        mTabHost.setup(getApplication(), getSupportFragmentManager(), R.id.tab_content);
        for (int i = 0; i < sFragments.length; i++) {
            ImageView tabIcon = (ImageView) getLayoutInflater().inflate(R.layout.item_tab, null);
            tabIcon.setImageResource(sTabIcons[i]);
            mTabHost.addTab(mTabHost.newTabSpec(sTabTags[i]).setIndicator(tabIcon), sFragments[i], null);
        }
        // Fragment销毁时，记录当前的状态
        mFragmentStatus = new FragmentStatus();
    }
    /**
     * 切换Tab界面
     *
     * @param tab 界面索引
     */
    public void switchTab(int tab) {
        mTabHost.setCurrentTab(tab);
    }

    public FragmentStatus getFragmentStatus() {
        return mFragmentStatus;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 双击退出程序
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mKeyClickedCount++;
            if (mKeyClickedCount == 2) {
                NMActivityManager.getManager().exit(getNMApplication());
            } else {
                UIHelper.showToast(getApplication(), "再次点击退出程序");
                Observable.timer(3, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aLong -> {
                            mKeyClickedCount = 0;
                        });
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public static class FragmentStatus {
        public int mHomeCurrentLock;
    }
}
