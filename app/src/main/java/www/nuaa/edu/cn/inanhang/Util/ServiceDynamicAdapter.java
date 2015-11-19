package www.nuaa.edu.cn.inanhang.Util;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.askerov.dynamicgrid.BaseDynamicGridAdapter;

import java.util.List;

import www.nuaa.edu.cn.inanhang.Model.ServiceData;
import www.nuaa.edu.cn.inanhang.R;

/**
 * Created by yxy on 15/11/9.
 */
public class ServiceDynamicAdapter extends BaseDynamicGridAdapter {
    private Activity activity;
    public ServiceDynamicAdapter(Context context, List<ServiceData> items, int columnCount) {
        super(context, items, columnCount);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CheeseViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_grid, null);
            holder = new CheeseViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CheeseViewHolder) convertView.getTag();
        }
        ServiceData s=(ServiceData)getItem(position);
        holder.build(s.getName(),s.getPic());
        return convertView;
    }

    private class CheeseViewHolder {
        private TextView titleText;
//        private ImageView image;

        private CheeseViewHolder(View view) {
            titleText = (TextView) view.findViewById(R.id.item_title);
//            image = (ImageView) view.findViewById(R.id.item_img);
        }

        void build(String title,String img) {
            titleText.setText(title);
            int pic = activity.getResources().getIdentifier(img, "mipmap", activity.getPackageName());
            titleText.setCompoundDrawablesWithIntrinsicBounds(0,pic,0,0);
//            image.setImageResource(pic);
        }
    }
    public void setActivity(Activity activity){
        this.activity=activity;
    }
}