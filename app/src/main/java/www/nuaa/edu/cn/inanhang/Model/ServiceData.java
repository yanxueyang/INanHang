package www.nuaa.edu.cn.inanhang.Model;

import java.io.Serializable;

/**
 * Created by yxy on 15/11/6.
 */
public class ServiceData implements Serializable{
    private static final long serialVersionUID = -6465237897027410019L;
    public Integer id;
    private String name;
    private String pic;
    private String activity;


    public ServiceData(int id,String nameServiceData, String pic,String activity) {
        this.id = Integer.valueOf(id);
        this.name = name;
        this.pic=pic;
        this.activity = activity;
    }

    public String getName() {
        return name;
    }

    public String getPic(){return pic;}

    public String getActivity() {
        return activity;
    }

    public String toString() {
        return  this.name;
    }
}
