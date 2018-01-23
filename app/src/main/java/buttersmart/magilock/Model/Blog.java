package buttersmart.magilock.Model;

/**
 * Created by Xaiver on 19/12/2017.
 */

public class Blog {

    private String Desc;
    private String Image;
    private String Title;

    public Blog()
    {

    }

    public Blog(String desc, String image, String title) {
        Desc = desc;
        Image = image;
        Title = title;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}