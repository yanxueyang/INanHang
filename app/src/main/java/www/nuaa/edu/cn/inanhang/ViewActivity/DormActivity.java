package www.nuaa.edu.cn.inanhang.ViewActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import www.nuaa.edu.cn.inanhang.Base.BaseToolbarActivity;
import www.nuaa.edu.cn.inanhang.Model.DormEvent;
import www.nuaa.edu.cn.inanhang.R;
import www.nuaa.edu.cn.inanhang.Util.UIHelper;

/**
 * Created by yxy on 15/11/13.
 */
public class DormActivity extends BaseToolbarActivity {
    @Bind(R.id.activity_dormlouyutext)TextView activity_dormlouyutext;
    @Bind(R.id.activity_dormlouyuedit)MaterialEditText activity_dormlouyuedit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dorm);
        setToolbarTitle("宿舍选择");
        EventBus.getDefault().register(this);
    }
    @OnClick(R.id.activity_dormlouyutext)
    public void activity_dormlouyu(){
        UIHelper.startActivity(this, ChooseDormActivity.class);
    }
    @OnClick(R.id.activity_dormquire)
    public void activity_dormquire(){
        if(activity_dormlouyuedit.getText().toString().equals("")!=true&&activity_dormlouyutext.getText().toString().equals("请选择楼宇")!=true) {
            finish();
//            UIHelper.startActivity(this, ElectricActivity.class);
            Intent intent = new Intent(this, ElectricActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("louyu", activity_dormlouyutext.getText().toString());
            bundle.putSerializable("sushe", activity_dormlouyuedit.getText().toString());
            intent.putExtras(bundle);
            startActivity(intent);
        }else {
            Toast.makeText(this, "楼宇号或宿舍号不能为空", Toast.LENGTH_LONG).show();
        }
    }
    public void onEventMainThread(DormEvent event) {
        String msg = event.getMsg();
        activity_dormlouyutext.setText(msg);
    }
}
