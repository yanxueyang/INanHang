package www.nuaa.edu.cn.inanhang.ViewActivity;

import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro2;

import www.nuaa.edu.cn.inanhang.Base.BaseActivity;
import www.nuaa.edu.cn.inanhang.Base.NMApplication;
import www.nuaa.edu.cn.inanhang.MainActivity;
import www.nuaa.edu.cn.inanhang.R;
import www.nuaa.edu.cn.inanhang.Util.UIHelper;
import www.nuaa.edu.cn.inanhang.ViewFragment.IntroFragment;

/**
 * 引导页
 *
 * @author sun
 * @version 15/8/25
 */
public class IntroActivity extends AppIntro2 {

    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(IntroFragment.newInstance(R.mipmap.adv_1_page));
        addSlide(IntroFragment.newInstance(R.mipmap.adv_2_page));
        addSlide(IntroFragment.newInstance(R.mipmap.adv_3_page, true));

        showDoneButton(false);
        setDepthAnimation();
    }

    @Override
    public void onDonePressed() {
        if (getIntent().getBooleanExtra("init_intro", false)) {
            finish();
            return;
        }

        Class<? extends BaseActivity> cls = ((NMApplication) getApplication()).isLogin() ?
                MainActivity.class : AccountOperationActivity.class;
        UIHelper.startActivityWithFinish(this, cls);
    }
}

