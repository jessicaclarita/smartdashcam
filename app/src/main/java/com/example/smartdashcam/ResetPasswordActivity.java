package com.example.smartdashcam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ResetPasswordActivity extends AppCompatActivity {

    TextView Username;
    EditText Password, Reenter;
    Button Btnok;
    DataBaseHelper DataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Username = (TextView) findViewById(R.id.usernameresettext);
        Password = (EditText) findViewById(R.id.passwordreset);
        Reenter = (EditText) findViewById(R.id.repasswordreset);
        Btnok = (Button) findViewById(R.id.btnok);

        DataBase = new DataBaseHelper(this);

        Intent intent = getIntent();
        Username.setText(intent.getStringExtra("username"));

        Btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = Username.getText().toString();
                String password = Password.getText().toString();
                String repassword = Reenter.getText().toString();

                if (password.equals("") || repassword.equals(""))
                {
                    Toast.makeText(ResetPasswordActivity.this, "Please fill in the required information ！", Toast.LENGTH_SHORT).show();
                }

                else if (password.equals(repassword))
                {
                Boolean checkpasswordupdate = DataBase.updatepassword(user, password);

                if (checkpasswordupdate == true)
                {
                    Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                    startActivity(intent);
                    Toast.makeText(ResetPasswordActivity.this,"Update Password Successfully ！",Toast.LENGTH_SHORT).show();
                }
                else  {
                    Toast.makeText(ResetPasswordActivity.this,"Update Password Failed ！",Toast.LENGTH_SHORT).show();
                }

                }


                else  {
                    Toast.makeText(ResetPasswordActivity.this,"Password Not Matching ！",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}