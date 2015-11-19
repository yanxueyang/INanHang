package www.nuaa.edu.cn.inanhang.Base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.okhttp.Request;

import butterknife.ButterKnife;
import rx.functions.Action1;
import www.nuaa.edu.cn.inanhang.Model.Preferences;
import www.nuaa.edu.cn.inanhang.Model.User;

/**
 * 本项目所有的Fragment必须继承此类，提供了以下功能：<br/>
 * 1）设置导航栏；
 * 2）基于Annotation的视图创建；
 * 3）检测内存泄露。
 *
 * @author sun
 * @version 15/8/24
 */
public class BaseFragment extends Fragment {

//    private static final String TAG = BaseFragment.class.getSimpleName();

    private int mLayoutId;

    protected void setContentView(@LayoutRes int layout) {
        this.mLayoutId = layout;
    }

    @Override
    public void onAttach(Context context) {
        // Log.d(TAG, "onAttach ");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // Log.d(TAG, "onCreate ");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Log.d(TAG, "onCreateView ");
        View view = inflater.inflate(mLayoutId, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Log.d(TAG, "onViewCreated ");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // Log.d(TAG, "onActivityCreated ");
        super.onActivityCreated(savedInstanceState);
        setupToolbar(getToolbarActivity());
    }

    /**
     * 需要设置导航栏的子类必须在此处对导航栏进行设置
     *
     * @param toolbar 导航栏
     */
    protected void setupToolbar(BaseToolbarActivity toolbar) {

    }

    public final BaseToolbarActivity getToolbarActivity() {
        return (BaseToolbarActivity) getActivity();
    }

    public final NMApplication getApplication() {
        return getToolbarActivity().getNMApplication();
    }

    public final Preferences getPreferences() {
        return getApplication().getPreferences();
    }

    public User getUser() {
        return getApplication().getUser();
    }

    public void executeRequest(Request request, Action1<String> onNext, Action1<Throwable> onFail) {
        getApplication().executeRequest(request, onNext, onFail);
    }

    @Override
    public void onDestroyView() {
        // Log.d(TAG, "onDestroyView ");
        super.onDestroyView();
        ButterKnife.unbind(this);
        getToolbarActivity().resetToolbar();
    }

    @Override
    public void onDestroy() {
        // Log.d(TAG, "onDestroy ");
        super.onDestroy();
//        getApplication().getRefWatcher().watch(this);
    }

    @Override
    public void onDetach() {
        // Log.d(TAG, "onDetach ");
        super.onDetach();
    }
}
