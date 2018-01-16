package cloud.thecode.android.diaryvault;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.media.Rating;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.support.android.designlibdemo.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

public class AddPost extends AppCompatActivity {

    private ImageButton select_image;
    private ImageButton back_button;
    private ImageView selected_image;
    private boolean image_selected = false;
    private Button add;
    private Uri the_image;

    // All input variables from add post
    private EditText title, description;
    private RatingBar rating;
    private CheckBox favorite;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        select_image = (ImageButton) findViewById(R.id.select_image);
        back_button = (ImageButton) findViewById(R.id.back_button);
        selected_image = (ImageView) findViewById(R.id.selected_image);
        add = (Button) findViewById(R.id.add);

        // Initialising the input variables
        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);
        rating = (RatingBar) findViewById(R.id.rating);
        favorite = (CheckBox) findViewById(R.id.favorite);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPost.this.finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(image_selected) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddPost.this);
                    builder.setMessage("What would you like to do")
                            .setCancelable(false)
                            .setPositiveButton("Select new image", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                    photoPickerIntent.setType("image/*");
                                    startActivityForResult(photoPickerIntent, 1);
                                }
                            })
                            .setNegativeButton("Clear Image", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    selected_image.setImageResource(R.drawable.background);
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, 1);

                    image_selected = true;
                }

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get everything
                String the_title = title.getText().toString();
                String the_description = description.getText().toString();
                int the_rating = (int) rating.getRating();

                int isFavorite = favorite.isSelected()? 1:0;

                if(the_title.length() > 1 && the_description.length() > 1 && the_rating != 0 && the_image != null) {
                    try {
                        DatabaseHandler dbh = new DatabaseHandler(getApplicationContext());
                        dbh.addPost(new Post(the_title, the_description, "", the_image, the_rating, isFavorite));
                        Toast.makeText(AddPost.this, "Post Added", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } catch (Exception ex) {
                        Toast.makeText(AddPost.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddPost.this, "Make sure you have filled all the inputs", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                the_image = imageUri;
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                selected_image.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(AddPost.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(AddPost.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
    

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit without saving?")
                .setCancelable(false)
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddPost.this.finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                })
                .setNegativeButton("Stay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }



}
