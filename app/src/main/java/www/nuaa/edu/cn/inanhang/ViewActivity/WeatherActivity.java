package www.nuaa.edu.cn.inanhang.ViewActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import www.nuaa.edu.cn.inanhang.Base.BaseToolbarActivity;
import www.nuaa.edu.cn.inanhang.R;
import www.nuaa.edu.cn.inanhang.View.WeatherSingelView;

/**
 * Created by yxy on 15/9/24.
 */
public class WeatherActivity extends BaseToolbarActivity {
    ImageView todayweathertype;
    TextView todayweathertime, todayweatherweek, todayweathertemp, todayweathertips;
    WeatherSingelView yesterdayweather, tomorrowweather, dayaftertomorrowweather;
    WeathersDetail weathersdetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_weather);
        setToolbarTitle("天气预报");
        init();
        getAsync("http://apis.baidu.com/apistore/weatherservice/recentweathers?cityname=南京&cityid=101190101");
    }

    public void init() {
        todayweathertype = (ImageView) findViewById(R.id.todayweathertype);
        todayweathertime = (TextView) findViewById(R.id.todayweathertime);
        todayweatherweek = (TextView) findViewById(R.id.todayweatherweek);
        todayweathertemp = (TextView) findViewById(R.id.todayweathertemp);
        todayweathertips = (TextView) findViewById(R.id.todayweathertips);
        yesterdayweather = (WeatherSingelView) findViewById(R.id.yesterdayweather);
        tomorrowweather = (WeatherSingelView) findViewById(R.id.tomorrowweather);
        dayaftertomorrowweather = (WeatherSingelView) findViewById(R.id.dayaftertomorrowweather);
    }

    public void GetWeather() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("今天的日期：" + df.format(date));
        System.out.println("一天前的日期：" + df.format(new Date(date.getTime() - 1 * 24 * 60 * 60 * 1000)));
        System.out.println("二天后的日期：" + df.format(new Date(date.getTime() + 2 * 24 * 60 * 60 * 1000)));
        String startDate = df.format(new Date(date.getTime() - 1 * 24 * 60 * 60 * 1000));
        String endDate = df.format(new Date(date.getTime() + 2 * 24 * 60 * 60 * 1000));
//        getAsync("http://115.159.40.226:9000/restserver/api/v1/weather?area=杭州&startDate=" + startDate + "&endDate=" + endDate);
    }

    private void getAsync(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header("apikey", "d92435b3fa6e932125540f3909a27312")
                .url(url)
                .build();
        client.setConnectTimeout(10, TimeUnit.SECONDS);
        client.setWriteTimeout(10, TimeUnit.SECONDS);
        client.setReadTimeout(30, TimeUnit.SECONDS);

        Gson gson = new Gson();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String result = response.body().string();
                Log.d("TagFragment", url);
                Log.d("TagFragment", result.toString());
                result = decodeUnicode(result);
                Log.d("TagFragment", result.toString());
                if (response.code() == 200) {
                    WeathersDetail weathersdetailjiexi = gson.fromJson(result, WeathersDetail.class);
                    weathersdetail = weathersdetailjiexi;
                    Log.d("TagFragment", weathersdetail.errNum);
                    Log.d("TagFragment", weathersdetail.retData.city);
                    if (weathersdetail.errNum.equals("0")) {
                        handler.sendEmptyMessage(200);
                    } else {
                        handler.sendEmptyMessage(300);
                    }
                } else {
                    handler.sendEmptyMessage(400);
                }
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    ShowWeather();
                    break;
                case 300:
                    Toast.makeText(getApplication().getApplicationContext(), weathersdetail.errMsg,
                            Toast.LENGTH_SHORT).show();
                    break;
                case 400:
                    Toast.makeText(getApplication().getApplicationContext(), "网络异常",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    public void ShowWeather() {
        todayweathertime.setText(weathersdetail.retData.today.date);
        todayweatherweek.setText(weathersdetail.retData.today.week);
        todayweathertemp.setText(weathersdetail.retData.today.type + "\n" + weathersdetail.retData.today.curTemp);
        SpannableStringBuilder builder = new SpannableStringBuilder("Tips:\n" + weathersdetail.retData.today.index.get(2).details);
        builder.setSpan(new ForegroundColorSpan(Color.rgb(79, 172, 231)), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        todayweathertips.setText(builder);
        todayweathertype.setBackgroundResource(bigtypechoose(weathersdetail.retData.today.type));
        yesterdayweather.setTime(weathersdetail.retData.history.get(6).date + "  " + weathersdetail.retData.history.get(6).week);
        yesterdayweather.setTemperature(weathersdetail.retData.history.get(6).hightemp);
        yesterdayweather.setWeather(littletypechoose(weathersdetail.retData.history.get(6).type));
        tomorrowweather.setTime(weathersdetail.retData.forecast.get(0).date + "  " + weathersdetail.retData.forecast.get(0).week);
        tomorrowweather.setTemperature(weathersdetail.retData.forecast.get(0).hightemp);
        tomorrowweather.setWeather(littletypechoose(weathersdetail.retData.forecast.get(0).type));
        dayaftertomorrowweather.setTime(weathersdetail.retData.forecast.get(1).date + "  " + weathersdetail.retData.forecast.get(1).week);
        dayaftertomorrowweather.setTemperature(weathersdetail.retData.forecast.get(1).hightemp);
        dayaftertomorrowweather.setWeather(littletypechoose(weathersdetail.retData.forecast.get(1).type));
    }

    public int bigtypechoose(String type) {
        int image = R.mipmap.weather_qingtian_big_icon;
        if (type.equals("晴")) {
            image = R.mipmap.weather_qingtian_big_icon;
        } else if (type.equals("多云")) {
            image = R.mipmap.weather_duoyun_big_icon;
        } else if (type.equals("阴")) {
            image = R.mipmap.weather_yintian_big_icon;
        } else if (type.equals("阵雨") || type.equals("雷阵雨") || type.equals("雷阵雨伴有冰雹") || type.equals("雨夹雪") || type.equals("小雨")
                || type.equals("中雨") || type.equals("大雨") || type.equals("暴雨") || type.equals("大暴雨") || type.equals("特大暴雨")
                || type.equals("冻雨") || type.equals("小到中雨") || type.equals("中到大雨") || type.equals("大到暴雨") || type.equals("暴雨到大暴雨")
                || type.equals("大暴雨到特大暴雨")) {
            image = R.mipmap.weather_dayu_big_icon;
        } else if (type.equals("阵雪") || type.equals("小雪") || type.equals("中雪") || type.equals("大雪") || type.equals("暴雪") || type.equals("小到中雪")
                || type.equals("中到大雪") || type.equals("大到暴雪")) {
            image = R.mipmap.weather_xiaxue_big_icon;
        } else if (type.equals("雾") || type.equals("沙尘暴") || type.equals("浮尘") || type.equals("扬沙") || type.equals("强沙尘暴") || type.equals("霾")) {
            image = R.mipmap.weather_wumai_big_icon;
        }
        return image;
    }

    public int littletypechoose(String type) {
        int image = R.mipmap.weather_qingtian_little_icon;
        if (type.equals("晴")) {
            image = R.mipmap.weather_qingtian_little_icon;
        } else if (type.equals("多云")) {
            image = R.mipmap.weather_duoyun_little_icon;
        } else if (type.equals("阴")) {
            image = R.mipmap.weather_yintian_little_icon;
        } else if (type.equals("阵雨") || type.equals("雷阵雨") || type.equals("雷阵雨伴有冰雹") || type.equals("雨夹雪") || type.equals("小雨")
                || type.equals("中雨") || type.equals("大雨") || type.equals("暴雨") || type.equals("大暴雨") || type.equals("特大暴雨")
                || type.equals("冻雨") || type.equals("小到中雨") || type.equals("中到大雨") || type.equals("大到暴雨") || type.equals("暴雨到大暴雨")
                || type.equals("大暴雨到特大暴雨")) {
            image = R.mipmap.weather_dayu_little_icon;
        } else if (type.equals("阵雪") || type.equals("小雪") || type.equals("中雪") || type.equals("大雪") || type.equals("暴雪") || type.equals("小到中雪")
                || type.equals("中到大雪") || type.equals("大到暴雪")) {
            image = R.mipmap.weather_xiaxue_little_icon;
        } else if (type.equals("雾") || type.equals("沙尘暴") || type.equals("浮尘") || type.equals("扬沙") || type.equals("强沙尘暴") || type.equals("霾")) {
            image = R.mipmap.weather_wumai_little_icon;
        }
        return image;
    }

    public class WeathersDetail {
        String errNum;
        String errMsg;
        RetData retData;
    }

    public class RetData {
        String city;
        String cityid;
        Today today;
        List<Forecast> forecast;
        List<History> history;
    }

    public class Today {
        String date;
        String week;
        String curTemp;
        String aqi;
        String fengxiang;
        String fengli;
        String highttemp;
        String lowtemp;
        String type;
        List<Index> index;
    }

    public class Index {
        String name;
        String code;
        String index;
        String details;
        String otherName;
    }

    public class Forecast {
        String date;
        String week;
        String fengxiang;
        String fengli;
        String hightemp;
        String lowtemp;
        String type;
    }

    public class History {
        String date;
        String week;
        String aqi;
        String fengxiang;
        String fengli;
        String hightemp;
        String lowtemp;
        String type;
    }

    //unicode转中文
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();

    }
}