package com.event.joe.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

public class LogInActivity extends AppCompatActivity {
    MySQLiteHelper sqLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        final Button button =  (Button)findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sign in logic
                EditText etUsername = (EditText) findViewById(R.id.input_username);
                EditText etPassword = (EditText) findViewById(R.id.input_password);

                String password = etPassword.getText().toString();
                String username = etUsername.getText().toString();

                sqLiteHelper = new MySQLiteHelper(LogInActivity.this);
                List<String> usernames = sqLiteHelper.getAllUsernames();
                boolean userNameFound = false;

                for (int i = 0; i < usernames.size(); i++) {
                    if (usernames.get(i).equals(username)) {
                        userNameFound = true;
                        break;
                    }
                }

                if (username.length() < 1 || password.length() < 1) {
                    Toast.makeText(LogInActivity.this, "Not all fields have been filled in", Toast.LENGTH_SHORT).show();
                } else {
                    if (userNameFound) {
                        String passwordCurrent = sqLiteHelper.getPassword(username);
                        if (passwordCurrent.equals(password)) {
                                sqLiteHelper.deactivateSessions();
                                sqLiteHelper.setActive(username);
                                HashMap<String, String> currentUser = sqLiteHelper.getUserDetails(username);
                                String firstName = currentUser.get("firstName");
                                String lastName = currentUser.get("lastName");
                                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("name" , firstName + " " + lastName);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();

                        } else {
                            Toast.makeText(LogInActivity.this, "Username or Password Incorrect", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LogInActivity.this, "Username or Password Incorrect", Toast.LENGTH_SHORT).show();
                        etUsername.getText().clear();
                        etPassword.getText().clear();
                    }

                }


            }
        });

        TextView login = (TextView)findViewById(R.id.link_signup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
}
