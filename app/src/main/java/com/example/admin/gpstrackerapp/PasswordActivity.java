package com.example.admin.gpstrackerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {
    String email;
    EditText e3_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        e3_password=(EditText)findViewById(R.id.editText4);
        Intent myintent =getIntent();
        if(myintent!=null)
        {
            email=myintent.getStringExtra("email");
        }
    }


    public void gotoNamePicActivity(View v)
    {

        if(e3_password.getText().toString().length()>6)
        {
            Intent myintent = new Intent(PasswordActivity.this,NameActivity.class);
            myintent.putExtra("email",email);
            myintent.putExtra("password",e3_password.getText().toString());
            startActivity(myintent);
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Password length should be greater than 6",Toast.LENGTH_SHORT).show();
        }
    }
}
