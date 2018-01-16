package cloud.thecode.android.diaryvault;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Mazen on 1/7/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static String DB = "PostDB";
    private final static int VERSION = 4;

    /* Tables */
    private final static String POST_TABLE = "Post";


    /* Columns */
    private final static String TITLE_COLUMN = "title";
    private final static String ID_COLUMN = "id";
    private final static String DESCRIPTION_COLUMN = "description";
    private final static String DATE_COLUMN = "date";
    private final static String IMAGE_COLUMN = "image";
    private final static String RATING_COLUMN = "rating";
    private final static String FAVORITE_COLUMN = "favorite";



    public DatabaseHandler(Context context) {
        super(context, DB, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Post ("+ID_COLUMN+" integer primary key autoincrement, "+TITLE_COLUMN+" text, "+DESCRIPTION_COLUMN+" text, "+DATE_COLUMN+" text, "+IMAGE_COLUMN+" text, "+RATING_COLUMN+" numeric(8), "+FAVORITE_COLUMN+" numeric(2))");
        db.execSQL("CREATE TABLE User (id integer primary key autoincrement, password numeric(4), name text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + POST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }

    public void setInfo(int password, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("password", password);
        db.insertWithOnConflict("User", null, v, SQLiteDatabase.CONFLICT_ABORT);
        db.close();
    }

    public boolean hasUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM User", null);

        c.moveToFirst();

        db.close();

        if(c.getCount() > 0)
            return true;
        return false;
    }

    public boolean checkPassword(int password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM User WHERE password = " + password, null);

        // Cursor after being filled moves to the end
        // for us to read the data we go back to the beginning
        c.moveToFirst();

        db.close();

        // Check if cursor is empty
        if(c.getCount() < 1)
            return false;

        return true;
    }


    public void addPost(Post s) {

        // Construct the date
        String date = "";

        String dayNames[] = new DateFormatSymbols().getWeekdays();
        String monthNames[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "Novemeber", "December"};

        Calendar calendar = Calendar.getInstance();
        date += dayNames[calendar.get(Calendar.DAY_OF_WEEK)] + " ";
        date += calendar.get(Calendar.DAY_OF_MONTH) + " ";
        date += monthNames[calendar.get(Calendar.MONTH)] + " at ";
        SimpleDateFormat formatDate = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        Date mdate = new Date(System.currentTimeMillis());
        date += formatDate.format(mdate);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(TITLE_COLUMN, s.getTitle());
        v.put(DESCRIPTION_COLUMN, s.getDescription());
        v.put(DATE_COLUMN, date);
        v.put(IMAGE_COLUMN, s.getImage().toString());
        v.put(RATING_COLUMN, s.getRating());
        v.put(FAVORITE_COLUMN, s.isFavorite());
        db.insertWithOnConflict(POST_TABLE, null, v, SQLiteDatabase.CONFLICT_ABORT);
        db.close();
    }


    public boolean updatePost(Post s) {

        // Construct the date
        String date = "";

        String dayNames[] = new DateFormatSymbols().getWeekdays();
        String monthNames[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "Novemeber", "December"};

        Calendar calendar = Calendar.getInstance();
        date += dayNames[calendar.get(Calendar.DAY_OF_WEEK)] + " ";
        date += calendar.get(Calendar.DAY_OF_MONTH) + " ";
        date += monthNames[calendar.get(Calendar.MONTH)] + " at ";
        SimpleDateFormat formatDate = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        Date mdate = new Date(System.currentTimeMillis());
        date += formatDate.format(mdate);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(TITLE_COLUMN, s.getTitle());
        v.put(DESCRIPTION_COLUMN, s.getDescription());
        v.put(DATE_COLUMN, date);
        v.put(IMAGE_COLUMN, s.getImage().toString());
        v.put(RATING_COLUMN, s.getRating());
        v.put(FAVORITE_COLUMN, s.isFavorite());

        int count = db.update(POST_TABLE, v,ID_COLUMN + "=" + s.getId(), null);


        if(count < 1)
            return false;
        return true;
}

    public Post getPost(int id) throws Exception{
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + POST_TABLE + " WHERE " + ID_COLUMN + " = " + id, null);

        // Cursor after being filled moves to the end
        // for us to read the data we go back to the beginning
        c.moveToFirst();

        db.close();

        // Check if cursor is empty
        if(c.getCount() < 1)
            throw new Exception("Record not found");

        Post p = new Post(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), Uri.parse(c.getString(4)), c.getInt(5), c.getInt(6));

        db.close();

        return p;
    }

    /*

    public void deletePost(int id) throws Exception{
        SQLiteDatabase db = this.getWritableDatabase();
        // Just like PDO Prepared Statements
        int count = db.delete(POST_TABLE, STUDENTS_COLOUMN_ID + " = ?", new String[] {Integer.toString(id)});

        db.close();

        if(count == 0)
            throw new Exception("Record Not Found");

    }


    public void updatePost(Post s) throws Exception{
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(STUDENTS_COLOUMN_NAME, s.getName());
        int count = db.update(POST_TABLE, v, STUDENTS_COLOUMN_ID + " = ?", new String[] {Integer.toString(s.getId())});

        db.close();
        if(count < 1) throw new Exception("Record Not Found");
    }
    */
    public ArrayList<Post> getAllPosts() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + POST_TABLE + " order by " + DATE_COLUMN + " desc", null);

        c.moveToFirst();
        ArrayList<Post> posts = new ArrayList<>();

        //OR
        while(!c.isAfterLast()) {
            posts.add(new Post(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), Uri.parse(c.getString(4)), c.getInt(5), c.getInt(6)));
            c.moveToNext();
        }

        return posts;
    }

    public ArrayList<Post> getAllPostsFavorites() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + POST_TABLE, null);

        c.moveToFirst();
        ArrayList<Post> posts = new ArrayList<>();

        //OR
        while(!c.isAfterLast()) {
            posts.add(new Post(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), Uri.parse(c.getString(4)), c.getInt(5), c.getInt(6)));
            c.moveToNext();
        }

        return posts;
    }

    public ArrayList<Post> getAllPostsRating() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + POST_TABLE + " order by " + RATING_COLUMN + " desc", null);

        c.moveToFirst();
        ArrayList<Post> posts = new ArrayList<>();

        //OR
        while(!c.isAfterLast()) {
            posts.add(new Post(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), Uri.parse(c.getString(4)), c.getInt(5), c.getInt(6)));
            c.moveToNext();
        }

        return posts;
    }


}
