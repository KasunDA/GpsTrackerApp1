package com.example.admin.gpstrackerapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class NameActivity extends AppCompatActivity {
    EditText e5_name;
    String email,password;
    CircleImageView circleImageView;
    Uri resultUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        e5_name=(EditText)findViewById(R.id.editText5);
        circleImageView =(CircleImageView)findViewById(R.id.circleimageview);
        Intent myintent =getIntent();
        if(myintent!=null)
        {
            email=myintent.getStringExtra("email");
            password=myintent.getStringExtra("password");
        }
    }



    public void generatecode(  View v)
    {
        Date mydate = new Date();
        SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());
        String date = format1.format(mydate);
        Random r =new Random();
        int n=100000 + r.nextInt(900000);
        String code = String.valueOf(n);


        if(resultUri!=null)
        {

            Intent myintent= new Intent(NameActivity.this,InviteCodeActivity.class);
            myintent.putExtra("name",e5_name.getText().toString());
            myintent.putExtra("email",email);
            myintent.putExtra("password",password);
            myintent.putExtra("date",date);
            myintent.putExtra("isSharing","false");
            myintent.putExtra("code",code);
            myintent.putExtra("imageuri",resultUri);
            startActivity(myintent);
            finish();


        }
        else
        {
            Toast.makeText(getApplicationContext(),"please choose an image",Toast.LENGTH_SHORT).show();
        }
    }


    public  void selectimage(View v)
    {

        Intent i= new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i,12);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==12 && resultCode==RESULT_OK && data!=null)
        {

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                 resultUri = result.getUri();
                circleImageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
