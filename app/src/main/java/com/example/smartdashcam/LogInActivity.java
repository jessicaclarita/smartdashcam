package com.example.smartdashcam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {

    EditText Username, Password;
    Button Signin;
    TextView Forget;
    DataBaseHelper DataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Username = (EditText) findViewById(R.id.username1);
        Password = (EditText)  findViewById(R.id.password1);
        Signin = (Button) findViewById(R.id.btnsignin1);
        Forget = (TextView) findViewById(R.id.btnforget);
        DataBase = new DataBaseHelper(this);


        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = Username.getText().toString();
                String password = Password.getText().toString();

                if (user.equals("") || password.equals("")) {
                    Toast.makeText(LogInActivity.this," Please fill in the required information！",Toast.LENGTH_SHORT).show();
                }

                else {
                    Boolean checkuserpassword = DataBase.checkusernamepassword(user, password);
                    if (checkuserpassword == true ) {
                        Toast.makeText(LogInActivity.this,"Sign In Successfull ！",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("username",user);
                        startActivity(intent);
                    }

                    else {
                       Toast.makeText(LogInActivity.this,"Invalid ！Please try again ！",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        Forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

    }
}