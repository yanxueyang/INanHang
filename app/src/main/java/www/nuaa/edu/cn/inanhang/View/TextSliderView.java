package www.nuaa.edu.cn.inanhang.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import www.nuaa.edu.cn.inanhang.Base.BaseFragment;
import www.nuaa.edu.cn.inanhang.R;

public class TextSliderView extends BaseSliderView {

    private TextView textView;
    private String text;
    private BaseFragment fragment;
    private String url;
    public TextSliderView(Context context) {
        super(context);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.view_slider_text, null);
        ImageView imageView = (ImageView) v.findViewById(R.id.slider_image);
        textView = (TextView) v.findViewById(R.id.slider_tv_1);
        textView.setText(text);
        bindEventAndShow(v, imageView);
        return v;
    }

    public void setText(String text) {
        this.text = text;
        if (this.textView != null) this.textView.setText(text);
    }
    public void setFragment(BaseFragment fragment) {
        this.fragment = fragment;
    }
    public void setUrl(String url){this.url=url;}
}
