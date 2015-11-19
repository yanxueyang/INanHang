package www.nuaa.edu.cn.inanhang.Util;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import www.nuaa.edu.cn.inanhang.R;

/**
 * Created by yxy on 15/11/18.
 */
public class ChooseLeftListAdapter extends BaseAdapter {
    private String[] l;
    private Context context;
    private int selectItem = -1;
    public ChooseLeftListAdapter(Context context, String[] l) {
        this.context = context;
        this.l = l;
    }
    @Override
    public int getCount() {
        return l.length;
    }

    @Override
    public Object getItem(int position) {
        return l[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.chooseleftlistitem,
                    null);
            holder.name = (TextView) convertView.findViewById(R.id.chooseleftlistitemtext);
            holder.img = (ImageView) convertView.findViewById(R.id.chooseleftlistitemimage);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.name.setText(l[position]);
        holder.img.setVisibility(View.GONE);
        if (position == selectItem) {
            convertView.setBackgroundColor(Color.parseColor("#f7f7f7"));
            holder.name.setTextColor(Color.parseColor("#5890ff"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.name.setTextColor(Color.parseColor("#9b9ea3"));
        }

        return convertView;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    public int getSelectItem() {
        return selectItem;
    }

    class Holder {
        TextView name;
        ImageView img;
    }
    private void show(String str) {
        Toast.makeText(context, str, 0).show();
    }
}
