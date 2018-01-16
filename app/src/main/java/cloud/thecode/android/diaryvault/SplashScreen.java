package cloud.thecode.android.diaryvault;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.support.android.designlibdemo.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.white));

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();

                // Check if the user exists

                DatabaseHandler dbh = new DatabaseHandler(getApplicationContext());

                if(dbh.hasUser())
                    startActivity(new Intent(getApplicationContext(), Login.class));
                else
                    startActivity(new Intent(getApplicationContext(), signup.class));

            }
        }, 1800);

    }
}
