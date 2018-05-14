package com.example.admin.gpstrackerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class InviteCodeActivity extends AppCompatActivity {

    String name,email,password,date,issharing,code;
    Uri imageuri;
    TextView t1;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;
    ProgressDialog progressdialog;
    String userId;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);
        t1=(TextView)findViewById(R.id.textView5);
        auth=FirebaseAuth.getInstance();
        progressdialog=new ProgressDialog(this);

        reference= FirebaseDatabase.getInstance().getReference().child("users");
        storageReference= FirebaseStorage.getInstance().getReference().child("user_images");

        Intent myintent =getIntent();
        if(myintent!=null)
        {
            name=myintent.getStringExtra("name");
            email=myintent.getStringExtra("email");
            password=myintent.getStringExtra("password");
            code=myintent.getStringExtra("code");
            issharing=myintent.getStringExtra("isSharing");
            imageuri=myintent.getParcelableExtra("imageuri");

        }
        t1.setText(code);
    }


    public void  registeruser(View v)
    {
        progressdialog.setMessage("Please wait while we are creating an account for you");
        progressdialog.show();



        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            //insert value in real time db
                            user=auth.getCurrentUser();

                            CreateUser  createUser = new CreateUser(name,email,password,code,"false","na","na","na",user.getUid());



                            user=auth.getCurrentUser();
                            userId=user.getUid();



                            reference.child(userId).setValue(createUser)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {

                                                //save image to firebase storage


                                               StorageReference sr= storageReference.child(user.getUid() + ".jpg");
                                                sr.putFile(imageuri)
                                                        .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                                if(task.isSuccessful())
                                                                {
                                                                    String download_image_path =task.getResult().getDownloadUrl().toString();
                                                                    reference.child(user.getUid()).child("imageurl").setValue(download_image_path)
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if(task.isSuccessful())
                                                                                    {
                                                                                        progressdialog.dismiss();
                                                                                      //Toast.makeText(getApplicationContext(),"Email Send for verification",Toast.LENGTH_SHORT).show();
                                                                                       sendverificationemail();
                                                                                        Intent myintent = new Intent(InviteCodeActivity.this,MainActivity.class);
                                                                                        startActivity(myintent);
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                        progressdialog.dismiss();
                                                                                        Toast.makeText(getApplicationContext(),"Error occured while creating account",Toast.LENGTH_SHORT).show();

                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        });


                                            }
                                            else
                                            {
                                                progressdialog.dismiss();
                                                Toast.makeText(getApplicationContext(),"Could not insert value in database",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }

                    }
                });

    }


    public void sendverificationemail()
    {
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Email sent for verification",Toast.LENGTH_SHORT).show();
                            finish();
                            auth.signOut();

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"error in sending email",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

}
