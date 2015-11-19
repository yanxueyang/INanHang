package www.nuaa.edu.cn.inanhang.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import www.nuaa.edu.cn.inanhang.R;

/**
 * Created by yxy on 15/9/1.
 */
public class EmsListViewAdspter extends BaseAdapter {
    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public EmsListViewAdspter(Context context, List<Map<String, Object>> data) {
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public final class Kongjian {
        public ImageView imageView;
        public TextView context;
        public TextView time;
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int posititon, View converView, ViewGroup parent) {
        Kongjian kongjian = null;
        if (converView == null) {
            kongjian = new Kongjian();
            converView = layoutInflater.inflate(R.layout.emsdetail, null);
            kongjian.imageView = (ImageView) converView.findViewById(R.id.emsdetailimageview);
            kongjian.context = (TextView) converView.findViewById(R.id.emsdetailcontext);
            kongjian.time = (TextView) converView.findViewById(R.id.emsdetailtime);
            converView.setTag(kongjian);
        } else {
            kongjian = (Kongjian) converView.getTag();
        }
        kongjian.imageView.setBackgroundResource((Integer) data.get(posititon).get("image"));
        kongjian.context.setText((String) data.get(posititon).get("context"));
        kongjian.time.setText((String) data.get(posititon).get("time"));
        return converView;
    }

}
