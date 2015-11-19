package www.nuaa.edu.cn.inanhang.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import www.nuaa.edu.cn.inanhang.Model.ColumnChartItem;
import www.nuaa.edu.cn.inanhang.Model.XYItem;

/**
 * Created by yxy on 15/11/12.
 */
public class ColumnChartView extends View {
    private ArrayList<ColumnChartItem> items;
    private String unit;
    private String yFormat = "0.#";
    public ArrayList<XYItem> xyItems;
    private Context context;

    public void SetTuView(ArrayList<ColumnChartItem> list, String unitInfo) {
        this.items = list;
        this.unit = unitInfo;
    }

    public ColumnChartView(Context ct) {
        super(ct);
        this.context = ct;
    }

    public ColumnChartView(Context ct, AttributeSet attrs) {
        super(ct, attrs);
        this.context = ct;
    }

    public ColumnChartView(Context ct, AttributeSet attrs, int defStyle) {
        super(ct, attrs, defStyle);
        this.context = ct;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (items == null) {
            return;
        }

        int height = getHeight();
        int width = getWidth();

        int split = dip2px(context, 8);
        int marginl = width / 12;
        int margint = dip2px(context, 60);
        int margint2 = dip2px(context, 25);
        int bheight = height - margint - 2 * split;
        Log.i("点:  ", (split) + "," + (marginl) + "," + (margint) + "," + (margint2)+ "," + (bheight));
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#a5a9aa"));
        paint.setStrokeWidth(4);
        paint.setStyle(Style.STROKE);
//        canvas.drawLine(split, split, width - split, split, paint);
        Log.i("直线:  ", (split) + "," + (split) + "," + (width - split) + "," + (split));
        canvas.drawLine(split, height - margint2, width - split, height - margint2,
                paint);
        Log.i("直线:  ", (split) + "," + (height - margint2) + "," + (width - split) + "," + (height - margint2));
        // 画单位
        Paint p = new Paint();
        p.setAlpha(0x0000ff);
        p.setTextSize(sp2px(context, 14));
        p.setColor(Color.parseColor("#5b5b5b"));
        canvas.drawText(unit, split, margint2  , p);
        Log.i("单位Text:  ", unit);
        Log.i("单位XY:  ", (split) + "," + (margint2  ));
        // 画X坐标
        ArrayList<Integer> xlist = new ArrayList<Integer>();
        paint.setColor(Color.GRAY);
        for (int i = 0; i < items.size(); i++) {


            int span = (width - split*4*2) /(items.size()-1) ;
//            int x = marginl + span / 2 + span * i;
            int x =   split*4+span * i;
            xlist.add(x);

            Log.i("分割", (span)+",   "+(width)+",   "+( items.size())+",   "+(split));
            drawText(items.get(i).getX(), x, height - split, canvas);
            Log.i("X坐标Text:  ", items.get(i).getX());
            Log.i("X坐标XY:  ",(span)+","+ (x) + "," +  (height-split ));
        }

        float max = Float.MIN_VALUE;
        float min = Float.MAX_VALUE;
        for (int i = 0; i < items.size(); i++) {
            float y = items.get(i).getY();
            if (y > max) {
                max = y;
            }
            if (y < min) {
                min = y;
            }
        }

        float span = max - min;
        if (span == 0) {
            span = 6.0f;
        }
        max = max + span / 6.0f;
        min = min - span / 6.0f;

        // 获取点集合
        Point[] mPoints = getPoints(xlist, max, min, bheight, margint);

        // 画线
        paint.setColor(Color.parseColor("#a5a9aa"));
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(8);
        drawLine(mPoints, canvas, paint);

        xyItems=new ArrayList<XYItem>();
        // 画点
        paint.setColor(Color.parseColor("#68c6f9"));
        paint.setStyle(Style.FILL);
        for (int i = 0; i < mPoints.length; i++) {
            canvas.drawCircle(mPoints[i].x, mPoints[i].y, 12, paint);
            String yText = new java.text.DecimalFormat(yFormat).format(items
                    .get(i).getY());
            drawText(yText, mPoints[i].x,
                    mPoints[i].y - dip2px(context, 12), canvas);
            xyItems.add(new XYItem(mPoints[i].x, mPoints[i].y - dip2px(context, 12)));
        }
    }

    private Point[] getPoints(ArrayList<Integer> xlist, float max, float min,
                              int h, int top) {
        Point[] points = new Point[items.size()];
        for (int i = 0; i < items.size(); i++) {
            int ph = top + h
                    - (int) (h * ((items.get(i).getY() - min) / (max - min)));
            points[i] = new Point(xlist.get(i), ph);
        }
        return points;
    }

    private void drawLine(Point[] ps, Canvas canvas, Paint paint) {
        Point startp = new Point();
        Point endp = new Point();
        for (int i = 0; i < ps.length - 1; i++) {
            startp = ps[i];
            endp = ps[i + 1];
            canvas.drawLine(startp.x, startp.y, endp.x, endp.y, paint);
        }
    }

    private void drawText(String text, int x, int y, Canvas canvas) {
        Paint p = new Paint();
        p.setAlpha(0x0000ff);
        p.setTextSize(sp2px(context, 14));
        p.setTextAlign(Paint.Align.CENTER);
        p.setColor(Color.parseColor("#5b5b5b"));
        canvas.drawText(text, x, y, p);
    }

    public ArrayList<ColumnChartItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<ColumnChartItem> items) {
        this.items = items;
    }

    public String getyFormat() {
        return yFormat;
    }

    public void setyFormat(String yFormat) {
        this.yFormat = yFormat;
    }

    public int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
