package www.nuaa.edu.cn.inanhang.Model;

import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.List;

/**
 * 房屋
 *
 * @author sun
 * @version 15/9/15
 */
public class House implements Serializable {
    private final String area;
    private final int hasHousePermission;
    private final int ownership;
    private final String houseStartTime;
    private final String houseEndTime;
    private final List<Lock> locks;

    private boolean selected;

    public House(String area, int hasHousePermission, int ownership, String houseStartTime,
                 String houseEndTime, List<Lock> locks) {
        this.area = area;
        this.hasHousePermission = hasHousePermission;
        this.ownership = ownership;
        this.houseStartTime = houseStartTime;
        this.houseEndTime = houseEndTime;
        this.locks = locks;
    }

    public String getArea() {
        return area;
    }

    public boolean isHasHousePermission() {
        LocalDate sd = LocalDate.parse(houseStartTime.split(" ")[0]);
        LocalDate ed = LocalDate.parse(houseEndTime.split(" ")[0]);
        return ed.isAfter(sd);
    }

    public int getOwnership() {
        return ownership;
    }

    public String getHouseStartTime() {
        return houseStartTime;
    }

    public String getHouseEndTime() {
        return houseEndTime;
    }

    public List<Lock> getLocks() {
        return locks;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "{" +
                "area='" + area + '\'' +
                ", hasHousePermission=" + hasHousePermission +
                ", ownership=" + ownership +
                ", houseStartTime='" + houseStartTime + '\'' +
                ", houseEndTime='" + houseEndTime + '\'' +
                ", locks=" + locks +
                '}';
    }
}
