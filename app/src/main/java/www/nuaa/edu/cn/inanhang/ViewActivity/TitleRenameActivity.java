package www.nuaa.edu.cn.inanhang.ViewActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.Bind;
import www.nuaa.edu.cn.inanhang.Base.BaseToolbarActivity;
import www.nuaa.edu.cn.inanhang.R;
import www.nuaa.edu.cn.inanhang.Util.UIHelper;

/**
 * Created by yxy on 15/10/28.
 */
public class TitleRenameActivity extends BaseToolbarActivity {
    @Bind(R.id.titlerename_et)MaterialEditText mSignaturEt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titlerename);
        setToolbarTitle("标题设置");
        mSignaturEt.setHint(get());
        setToolbarOperation("完成", v -> {
            confirm();
        });
    }
    public void confirm() {
        if (!mSignaturEt.validate()) return;
        MaterialDialog dialog = UIHelper.showProgressDialog(this, "正在设置，请稍等");
        save();
        dialog.dismiss();
        onBackPressed();
    }
    public void save(){
        SharedPreferences mySharedPreferences = getSharedPreferences("current_title",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("title", mSignaturEt.getText().toString());
        //提交当前数据
        editor.apply();

    }
    public String get(){
        SharedPreferences sharedPreferences = getSharedPreferences("current_title",
                Activity.MODE_PRIVATE);
        String starttime = sharedPreferences.getString("title", "");
        return starttime;
    }
}
