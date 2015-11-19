package www.nuaa.edu.cn.inanhang.Util;

import java.io.Serializable;

/**
 * Created by yxy on 15/11/16.
 */
public class son implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;

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


}