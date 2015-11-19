package www.nuaa.edu.cn.inanhang.Util;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yxy on 15/11/16.
 */
public class father implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private int image;
    private List<son> sonList;



    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<son> getSonList() {
        return sonList;
    }
    public void setSonList(List<son> sonList) {
        this.sonList = sonList;
    }
    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }


}
