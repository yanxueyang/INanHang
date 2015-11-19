package www.nuaa.edu.cn.inanhang.ViewActivity;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import www.nuaa.edu.cn.inanhang.Base.BaseActivity;
import www.nuaa.edu.cn.inanhang.MainActivity;
import www.nuaa.edu.cn.inanhang.R;
import www.nuaa.edu.cn.inanhang.Util.UIHelper;


/**
 * @author sun
 * @version 15/8/25
 */
public class AccountOperationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_operation);
        setImmerseLayout(findViewById(R.id.background));
        ButterKnife.bind(this);
    }

//    @OnClick(R.id.login_btn)
//    public void login() {
//        UIHelper.ActivityLauncher.create(this).target(HostActivity.class)
//                .putExtra(HostActivity.KEY_HOST_TYPE, HostActivity.HOST_TYPE_LOGIN).start();
//    }
//
    @OnClick(R.id.suibianguanguan)
    public void createAccount() {
        UIHelper.startActivityWithFinish(AccountOperationActivity.this, MainActivity.class);
    }
}
