package com.example.admin.gpstrackerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;

public class RegisterActivity extends AppCompatActivity {
    EditText e1;
    Button b1;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth=FirebaseAuth.getInstance();
        dialog= new ProgressDialog(this);
        e1= (EditText)findViewById(R.id.editText3);
    }
    public void gotopasswordactivity(View v)
    {

        dialog.setMessage("Checking email");
        dialog.show();
        auth.fetchProvidersForEmail(e1.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<ProviderQueryResult> task) {

                        if(task.isSuccessful())
                        {
                            dialog.dismiss();
                            boolean check= !task.getResult().getProviders().isEmpty();


                            if(!check)
                            {

                                Intent myintent = new Intent(RegisterActivity.this,PasswordActivity.class);
                                myintent.putExtra("email",e1.getText().toString());
                                startActivity(myintent);
                                finish();

                            }
                            else
                            {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(),"This email is already registered",Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });


    }
}
