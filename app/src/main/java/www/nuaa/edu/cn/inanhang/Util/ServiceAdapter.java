package www.nuaa.edu.cn.inanhang.Util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import www.nuaa.edu.cn.inanhang.Model.ServiceData;
import www.nuaa.edu.cn.inanhang.R;

/**
 * Created by yxy on 15/11/9.
 */
public class ServiceAdapter extends BaseAdapter {
    private Activity activity;
    private List<ServiceData> list;
    private LayoutInflater layoutInflater;

    public ServiceAdapter(Context context,
                          List<ServiceData> list) {

        this.list = list;
        layoutInflater = LayoutInflater.from(context);

    }

    /**
     * 数据总数
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * 获取当前数据
     */
    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (layoutInflater != null) {
            view = layoutInflater
                    .inflate(R.layout.item_grid, null);
            TextView textView = (TextView) view.findViewById(R.id.item_title);
            Log.i("position", position+ "position");
            ServiceData s=(ServiceData)getItem(position);
            textView.setText(s.getName());
            int pic = activity.getResources().getIdentifier(s.getPic(), "mipmap", activity.getPackageName());
            textView.setCompoundDrawablesWithIntrinsicBounds(0, pic, 0, 0);
        }
        return view;
    }


    public void setActivity(Activity activity){
        this.activity=activity;
    }
}