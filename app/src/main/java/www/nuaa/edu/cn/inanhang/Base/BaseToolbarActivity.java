package www.nuaa.edu.cn.inanhang.Base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import www.nuaa.edu.cn.inanhang.R;

import static www.nuaa.edu.cn.inanhang.R.id.toolbar_operation;
/**
 * 此类主要提供一下功能：<br/>
 * 1）导航栏；
 * 2）基于Annotation的视图创建。
 *
 * @author sun
 * @version 15/8/24
 */
public class BaseToolbarActivity extends BaseActivity {

    private Toolbar mToolbar;
    private CircleImageView mToolbarPortrait;
    private ImageView mToolbarBack;
    private TextView mToolbarTitle;
    private TextView mToolbarOperation;
    private FrameLayout mContentFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base_toolbar);
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
        this.mToolbarPortrait = (CircleImageView) findViewById(R.id.portrait_image);
        this.mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        // 返回
        this.mToolbarBack.setOnClickListener(v -> onBackPressed());
        this.mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        this.mToolbarOperation = (TextView) findViewById(toolbar_operation);
        this.mContentFrame = (FrameLayout) findViewById(R.id.content_frame);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getLayoutInflater().inflate(layoutResID, mContentFrame);
        ButterKnife.bind(this);
        setupToolbar();
    }

    /**
     * 初始化导航栏，默认会在{@link BaseToolbarActivity#setContentView(int)}自动调用
     */
    public void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);

        setImmerseLayout(mToolbar);
    }

    /**
     * 获取导航栏
     *
     * @return toolbar
     */
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * 获取用户头像视图
     */
    public CircleImageView getPortraitView() {
        return mToolbarPortrait;
    }

    /**
     * 显示用户头像
     */
    public void showPortrait() {
        mToolbarPortrait.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏返回按钮
     */
    public void hideToolbarBack() {
        mToolbarBack.setVisibility(View.GONE);
        mToolbarBack.setOnClickListener(null);
    }

    /**
     * 设置导航栏标题
     *
     * @param title 标题
     */
    public void setToolbarTitle(String title) {
        if (title == null) mToolbarTitle.setVisibility(View.GONE);
        else mToolbarTitle.setVisibility(View.VISIBLE);
        mToolbarTitle.setText(title);
    }
    public TextView getToolbarTitle(){
        return mToolbarTitle;
    }

    /**
     * 设置导航栏操作项
     *
     * @param operation 操作项
     * @param listener  操作点击事件处理
     *
     */
    public void setToolbarOperation(String operation, View.OnClickListener listener) {
        if (operation == null) mToolbarOperation.setVisibility(View.GONE);
        else mToolbarOperation.setVisibility(View.VISIBLE);
        mToolbarOperation.setText(operation);
        mToolbarOperation.setOnClickListener(listener);
    }
    public TextView getToolbarOperation() {
        return mToolbarOperation;
    }

    /**
     * 重置导航栏
     */
    public void resetToolbar() {
        setToolbarTitle(null);
        setToolbarOperation(null, null);
    }
}
