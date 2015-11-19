package www.nuaa.edu.cn.inanhang.Model;

/**
 * Created by yxy on 15/11/12.
 */
public class ColumnChartItem {
    private String x;
    private float y;

    public ColumnChartItem(String vx, float vy) {
        this.x = vx;
        this.y = vy;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
