package www.nuaa.edu.cn.inanhang.ViewActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import www.nuaa.edu.cn.inanhang.Base.BaseToolbarActivity;
import www.nuaa.edu.cn.inanhang.Model.DormEvent;
import www.nuaa.edu.cn.inanhang.R;
import www.nuaa.edu.cn.inanhang.Util.ChooseLeftListAdapter;
import www.nuaa.edu.cn.inanhang.Util.ChooseRightListAdapter;

/**
 * Created by yxy on 15/11/17.
 */
public class ChooseDormActivity extends BaseToolbarActivity {
    @Bind(R.id.choosedormleftlist)ListView choosedormleftlist;
    @Bind(R.id.choosedormrightlist)ListView choosedormrightlist;
    ChooseLeftListAdapter choosedormleftadapter;
    ChooseRightListAdapter choosedormrightadapter;
    String[] xiaoqu = new String[] {"将军路校区", "明故宫校区"};
    String[][] lou=new String[][]{{"惠园1","惠园2","惠园3","惠园4","惠园5",
            "博园6","博园7","博园8","博园9","博园10","博园11","博园12","博园13","博园14","博园15",
            "怡园16","怡园17","怡园18","怡园19","怡园20","怡园21","怡园22",
            "东区23","东区24","东区25","东区26","东区27","东区28","东区29","东区30","东区31","东区32","东区33","东区34",
            "南区35","南区36","南区37"},
            {"b6","b7","b8","b9","b10","b11","b12","b13","b14","b15","b16","b17","b18","b19"}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosedorm);
        setToolbarTitle("楼宇选择");
        choosedormleftadapter=new ChooseLeftListAdapter(this, xiaoqu);
        choosedormleftlist.setAdapter(choosedormleftadapter);
        choosedormleftlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                choosedormleftadapter.setSelectItem(position);
                choosedormleftadapter.notifyDataSetChanged();
                choosedormrightadapter = new ChooseRightListAdapter(getApplicationContext(), lou[position]);
                choosedormrightlist.setAdapter(choosedormrightadapter);
                choosedormrightlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        choosedormrightadapter.setSelectItem(position);
                        choosedormrightadapter.notifyDataSetChanged();
                    }
                });
            }
        });
        setToolbarOperation("确认", v -> {
            if(choosedormleftadapter.getSelectItem()!=-1&&choosedormrightadapter.getSelectItem()!=-1){
                EventBus.getDefault().post(new DormEvent(xiaoqu[choosedormleftadapter.getSelectItem()]+"/"+lou[choosedormleftadapter.getSelectItem()][choosedormrightadapter.getSelectItem()]));
                finish();
            }else {
                Toast.makeText(getApplicationContext(), "校区或楼号未选择",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
