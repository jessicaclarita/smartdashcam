package com.example.smartdashcam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText Username;
    Button Reset;
    DataBaseHelper DataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


        Username = (EditText) findViewById(R.id.reset);
        Reset = (Button) findViewById(R.id.btnreset);
        DataBase = new DataBaseHelper(this);

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = Username.getText().toString();

                Boolean checkuser = DataBase.checkusername(user);
                if (checkuser == true) {
                    Intent intent = new Intent(getApplicationContext(),ResetPasswordActivity.class);
                    intent.putExtra("username", user);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ForgetPasswordActivity.this,"User does not exists ！" , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}