package www.nuaa.edu.cn.inanhang.ViewFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.OnClick;
import www.nuaa.edu.cn.inanhang.Base.BaseFragment;
import www.nuaa.edu.cn.inanhang.Base.BaseToolbarActivity;
import www.nuaa.edu.cn.inanhang.R;
import www.nuaa.edu.cn.inanhang.Util.UIHelper;
import www.nuaa.edu.cn.inanhang.ViewActivity.LibraryActivity;
import www.nuaa.edu.cn.inanhang.ViewActivity.MycardActivity;

/**
 * Created by yxy on 15/10/24.
 */
public class PersonalFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setContentView(R.layout.fragment_personal);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void setupToolbar(BaseToolbarActivity toolbar) {
        toolbar.setToolbarTitle("个人中心");
    }
    @OnClick(R.id.wodexiaoyuanka)
    public void wodexiaoyuanka() {
        UIHelper.startActivity(getActivity(), MycardActivity.class);
    }
    @OnClick(R.id.wodetushuguan)
    public void wodetushuguan() {
        UIHelper.startActivity(getActivity(), LibraryActivity.class);
    }
}
