package Model;


import java.sql.Blob;

public class PaperProvider {

    private int provider_id;
    private String title;
    private String provider_name;
    private String description;
    private byte[] image;

    public PaperProvider(int provider_id, String title, String provider_name, String description, byte[] image) {
        this.provider_id = provider_id;
        this.title = title;
        this.provider_name = provider_name;
        this.description = description;
        this.image = image;
    }

    public int getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(int provider_id) {
        this.provider_id = provider_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
