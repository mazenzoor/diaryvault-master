package cloud.thecode.android.diaryvault;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.support.android.designlibdemo.R;

public class signup extends AppCompatActivity {

    EditText password, name;
    Button signup;
    TextView header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button signup = (Button) findViewById(R.id.signup);
        password = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        header = (TextView) findViewById(R.id.ptv);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler dbh = new DatabaseHandler(getApplicationContext());
                String the_password = password.getText().toString();
                String the_name = name.getText().toString();
                if(the_password.length() > 3 && the_name.length() > 1) {
                    dbh.setInfo(Integer.parseInt(the_password), the_name);

                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));


                } else {
                    header.setText("Make sure your password is greater than 3 characters, and you have filled your name");
                }

            }
        });

    }
}
