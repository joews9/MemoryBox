package com.event.joe.myapplication.com.event.joe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.event.joe.myapplication.R;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;

public class LogInActivity extends AppCompatActivity {
    MySQLiteHelper sqLiteHelper;
    HashMap<String, String> currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();

            if (pref.getString("username", null) != null) {
                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                String name = pref.getString("name", "None Found");
                bundle.putString("name", name);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
        }else {
                Button button = (Button) findViewById(R.id.btn_login);
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
                            String beforeEncryptedPassword = password;
                            String passwordKey = "password";
                            String encryptedPassword = "";
                            try {
                                encryptedPassword = AESCrypt.encrypt(passwordKey, beforeEncryptedPassword);
                            } catch (GeneralSecurityException e) {
                                e.printStackTrace();
                            }
                            if (passwordCurrent.equals(encryptedPassword)) {
                                currentUser = sqLiteHelper.getUserDetails(username);
                                String firstName = currentUser.get("firstName");
                                String lastName = currentUser.get("lastName");
                                addToSharedPreferences(username, firstName, lastName);
                                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("name", firstName + " " + lastName);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(LogInActivity.this, "Username or Password Incorrect", Toast.LENGTH_SHORT).show();
                                etUsername.getText().clear();
                                etPassword.getText().clear();
                            }
                        } else {
                            Toast.makeText(LogInActivity.this, "Username or Password Incorrect", Toast.LENGTH_SHORT).show();
                            etUsername.getText().clear();
                            etPassword.getText().clear();
                        }

                    }

                }
            });

            TextView login = (TextView) findViewById(R.id.link_signup);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    public void addToSharedPreferences(String username, String firstName, String lastName){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        sqLiteHelper = new MySQLiteHelper(LogInActivity.this);
        editor.putString("username", username);
        editor.putString("userID", currentUser.get("userID"));
        Toast.makeText(LogInActivity.this, firstName + lastName, Toast.LENGTH_SHORT).show();
        editor.putString("name", firstName + " " + lastName);
        editor.commit();
    }
}
