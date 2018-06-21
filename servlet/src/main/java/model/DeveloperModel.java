package model;

public class DeveloperModel {
    private int id;
    private String name;
    private String site;
    private String avatar = "";

    public DeveloperModel() {
    }

    public DeveloperModel(int id, String name, String site, String avatar) {
        this.id = id;
        this.name = name;
        this.site = site;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "DeveloperModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
