package com.event.joe.myapplication.com.event.joe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.event.joe.myapplication.R;

import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    MySQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etFirstName = (EditText)findViewById(R.id.su_firstname);
        etLastName = (EditText) findViewById(R.id.su_lastname);
        etUsername = (EditText) findViewById(R.id.su_username);
        etPassword = (EditText) findViewById(R.id.su_password);
        etConfirmPassword = (EditText) findViewById(R.id.su_confirmpassword);
        Button btnCreateAccount = (Button) findViewById(R.id.btn_create);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String passwordConfirm = etConfirmPassword.getText().toString();

                sqLiteHelper = new MySQLiteHelper(SignUpActivity.this);

                //Username check logic and user add
                List<String> usernames = sqLiteHelper.getAllUsernames();
                boolean userNameFound = false;

                for (int i = 0; i < usernames.size(); i++) {
                    if (usernames.get(i).equals(username)) {
                        userNameFound = true;
                        break;
                    }
                }

                if (username.length() < 1 || password.length() < 1 || passwordConfirm.length() < 1 || firstName.length() < 1 || lastName.length() < 1) {
                    Toast.makeText(SignUpActivity.this, "Not all fields have been completed", Toast.LENGTH_SHORT).show();
                } else {

                       if (!passwordConfirm.equals(password)) {
                           Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                       } else {
                          if (password.length() < 8 ){
                              Toast.makeText(SignUpActivity.this, "Passwords must be at least 8 characters long", Toast.LENGTH_SHORT).show();
                          }else {
                              if (userNameFound) {
                                  Toast.makeText(SignUpActivity.this, "Username Already Exists", Toast.LENGTH_SHORT).show();
                                  etUsername.getText().clear();
                              }else{
                                  sqLiteHelper.addUser(username, password, firstName,lastName);
                                  Toast.makeText(SignUpActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                                  finish();
                              }
                          }
                   }
                }
            }
        });

       TextView alreadyAccount = (TextView)findViewById(R.id.link_login);
        alreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
