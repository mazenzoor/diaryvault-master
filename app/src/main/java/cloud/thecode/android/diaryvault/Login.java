package cloud.thecode.android.diaryvault;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.support.android.designlibdemo.R;

public class Login extends AppCompatActivity {

    TextView password, header;
    Button login;
    int tries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login = (Button) findViewById(R.id.login);
        password = (TextView) findViewById(R.id.password);
        header = (TextView) findViewById(R.id.ptv);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.login));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int the_password = Integer.parseInt(password.getText().toString());


                    DatabaseHandler dbh = new DatabaseHandler(getApplicationContext());

                    if (tries < 2) {
                        if (dbh.checkPassword(the_password)) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            tries++;
                            password.setText("");
                            header.setText("You have " + (3 - tries) + " more attempts");
                        }
                    } else {
                        finish();
                    }
                } catch (Exception ex) {
                    Toast.makeText(Login.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
