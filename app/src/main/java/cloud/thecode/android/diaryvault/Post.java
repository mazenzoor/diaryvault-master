package cloud.thecode.android.diaryvault;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.Date;

/**
 * Created by Mazen on 1/7/2018.
 */

public class Post {
    private int Id;
    private String Title;
    private String Description;
    private String Date;
    private Uri Image;
    private Integer Rating;
    private boolean Favorite;

    public Post(String title, String description, String date, Uri image, Integer rating, int favorite) {
        Title = title;
        Description = description;
        Date = date;
        Image = image;
        Rating = rating;
    }

    public Post(int id, String title, String description, String date, Uri image, Integer rating, int favorite) {
        Id = id;
        Title = title;
        Description = description;
        Date = date;
        Image = image;
        Rating = rating;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Uri getImage() {
        return Image;
    }

    public void setImage(Uri image) {
        Image = image;
    }

    public Integer getRating() {
        return Rating;
    }

    public void setRating(Integer rating) {
        Rating = rating;
    }

    public boolean isFavorite() {
        return Favorite;
    }

    public void setFavorite(boolean favorite) {
        Favorite = favorite;
    }
}
