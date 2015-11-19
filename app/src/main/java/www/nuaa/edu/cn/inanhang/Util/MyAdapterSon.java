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

import java.util.ArrayList;
import java.util.List;

import www.nuaa.edu.cn.inanhang.R;

/**
 * Created by yxy on 15/11/16.
 */
public class MyAdapterSon extends BaseAdapter {
    private List<son> l;
    private Context context;
    private int selectItem = -1;

    public MyAdapterSon(Context context, List<son> l) {
        this.context = context;
        setData(l);
    }

    @Override
    public int getCount() {
        return l.size();
    }

    @Override
    public son getItem(int position) {
        return l.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    // ����adapter
    public void updataAdapter( List<son> l) {
        setData(l);
        this.notifyDataSetChanged();
    }

    public void setData( List<son> l) {
        if (l != null)
            this.l = l;
        else
            this.l = new ArrayList<son>();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item1,
                    null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        if (l.size()>0) {
            holder.name.setText(l.get(position).getName());

            if (position == selectItem) {
                convertView.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.img.setVisibility(View.VISIBLE);
            } else {
                convertView.setBackgroundColor(Color.parseColor("#F5F5F5"));
                holder.img.setVisibility(View.INVISIBLE);
            }
        }
        return convertView;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    class Holder {
        TextView name;
        ImageView img;
    }
    private void show(String str) {
        Toast.makeText(context, str, 0).show();
    }
}
