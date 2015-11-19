package www.nuaa.edu.cn.inanhang.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import www.nuaa.edu.cn.inanhang.R;

/**
 * Created by yxy on 15/9/24.
 */
public class WeatherSingelView extends LinearLayout {
    private TextView time, temperature;
    private ImageView weather;

    public WeatherSingelView(Context context) {
        super(context);
    }

    public WeatherSingelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.fragment_weathersingel, this);
        time = (TextView) findViewById(R.id.weathersingel_time);
        temperature = (TextView) findViewById(R.id.weathersingel_temperature);
        weather = (ImageView) findViewById(R.id.weathersingel_weather);
    }

    public void setTime(String time) {
        this.time.setText(time);
    }

    public void setTemperature(String temperature) {
        this.temperature.setText(temperature);
    }

    public void setWeather(int image) {
        this.weather.setBackgroundResource(image);
    }
}
