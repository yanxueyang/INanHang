package www.nuaa.edu.cn.inanhang.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import www.nuaa.edu.cn.inanhang.R;

public class ImageSliderView extends BaseSliderView {

    public ImageSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.view_slider_image, null);
        bindEventAndShow(v, (ImageView) v);
        return v;
    }
}
