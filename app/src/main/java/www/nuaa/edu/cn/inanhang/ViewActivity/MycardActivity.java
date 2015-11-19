package www.nuaa.edu.cn.inanhang.ViewActivity;

import android.os.Bundle;

import www.nuaa.edu.cn.inanhang.Base.BaseToolbarActivity;
import www.nuaa.edu.cn.inanhang.R;

/**
 * Created by yxy on 15/10/26.
 */
public class MycardActivity extends BaseToolbarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        setToolbarTitle("校园卡信息");
    }
}
