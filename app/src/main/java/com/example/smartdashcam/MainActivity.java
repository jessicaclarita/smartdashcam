package com.example.smartdashcam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText Username, Password, Confirm;
    Button Signup, Signin;
    DataBaseHelper DataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username = (EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.password);
        Confirm = (EditText) findViewById(R.id.confirm);
        Signup = (Button) findViewById(R.id.btnsignup);
        Signin = (Button) findViewById(R.id.btnsignin);
        DataBase = new DataBaseHelper(this);

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(intent);

            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = Username.getText().toString();
                String password = Password.getText().toString();
                String confirm = Confirm.getText().toString();

                if (user.equals("") || password.equals("") || confirm.equals("")) {
                    Toast.makeText(MainActivity.this, "Please fill in the required information ！", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.equals(confirm)) {
                        Boolean checkuser = DataBase.checkusername(user);
                        if (checkuser == false) {
                            Boolean insert = DataBase.insertData(user, password);
                            if (insert == true) {
                                Toast.makeText(MainActivity.this, "Sign Up Successfully ！", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Sign In Fail ！", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Username already exists ！Please Sign In ！", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, " Password incorrect ！", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });



    }
}