package www.nuaa.edu.cn.inanhang.Model;

/**
 * @author sun
 * @version 15/9/28
 */
public class Advertisement {
    private final String imageUrl;
    private final String contentUrl;
    private final String slogan;
    private final int type;

    public Advertisement(String imageUrl, String contentUrl,String slogan, int type) {
        this.imageUrl = imageUrl;
        this.contentUrl=contentUrl;
        this.slogan = slogan;
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getContentUrl(){return contentUrl;}

    public String getSlogan() {
        return slogan;
    }

    public int getType() {
        return type;
    }
}
