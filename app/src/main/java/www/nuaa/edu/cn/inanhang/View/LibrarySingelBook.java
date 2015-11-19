package www.nuaa.edu.cn.inanhang.View;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import www.nuaa.edu.cn.inanhang.R;

/**
 * Created by yxy on 15/10/27.
 */
public class LibrarySingelBook extends LinearLayout {
    TextView title,time;
    ImageView image;
    public LibrarySingelBook(Context context) {
        super(context);
    }

    public LibrarySingelBook(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.libraryshow, this);
        time = (TextView) findViewById(R.id.libraryshowtime);
        title = (TextView) findViewById(R.id.libraryshowtitle);
        image = (ImageView) findViewById(R.id.libraryshowimage);
    }
    public void set(String time,String title,int image,int type){
        this.time.setText(time);
        this.title.setText(title);
        this.image.setBackgroundResource(image);
        if(type==0)
            this.time.setTextColor(Color.rgb(30,86,166));//蓝色
        else
            this.time.setTextColor(Color.rgb(255,13,17));//浅黑
    }
    public void setTime(String time) {
        this.time.setText(time);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setImage(int image) {
        this.image.setBackgroundResource(image);
    }

    public void hide(){
        title.setVisibility(View.GONE);
        time.setVisibility(View.GONE);
        image.setVisibility(View.GONE);
    }
}
