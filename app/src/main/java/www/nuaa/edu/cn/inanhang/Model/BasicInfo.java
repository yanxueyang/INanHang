package www.nuaa.edu.cn.inanhang.Model;

/**
 * 个人基本信息
 *
 * @author sun
 * @version 15/9/16
 */
public class BasicInfo {
    private final String name;
    private final int age;
    private final int sex;
    private final String birth;
    private final String nativePlace;

    public BasicInfo(String name, int age, int sex, String birth, String nativePlace) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.birth = birth;
        this.nativePlace = nativePlace;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex == 0 ? "女" : sex == 1 ? "男" : "未知";
    }

    public String getBirth() {
        return birth;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", birth='" + birth + '\'' +
                ", nativePlace='" + nativePlace + '\'' +
                '}';
    }
}
