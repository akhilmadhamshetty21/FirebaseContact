package com.example.inclass12;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.UUID;

public class CreateContactActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1 ;
    EditText et_name,et_email,et_phone;
    ImageView iv_profile;
    Button btn_submit;
    FirebaseStorage storage;
    StorageReference storageRef;
    Bitmap bitmap;
    private boolean isTakenPhoto=false;
    private FirebaseAuth mauth;
    FirebaseUser currentUser;
    String imageurl;
    public static String imagepath;
    private FirebaseFirestore db;
    Contact contact;

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mauth.getCurrentUser();
        if(currentUser!=null){
            Log.d("demo", "Current User: "+currentUser.getEmail());
        }else{
            Log.d("demo", "Not found user, login!!");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        setTitle("Create New Contact");
        et_name=findViewById(R.id.et_cname);
        et_email=findViewById(R.id.et_cmail);
        et_phone=findViewById(R.id.et_cphone);
        iv_profile=findViewById(R.id.iv_photo);
        btn_submit=findViewById(R.id.btn_submit);
        mauth=FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        db = FirebaseFirestore.getInstance();
        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_name.getText().toString().equals("")){
                    et_name.setError("Name can't be empty");
                }
                else if(et_email.getText().toString().equals("")){
                    et_email.setError("Email can't be empty");
                }
                else if(!RegisterActivity.validate(et_email.getText().toString()))
                    et_email.setError("Enter Vaild Mail ID");
                else if(et_phone.getText().toString().equals("")){
                    et_phone.setError("Phone can't be empty");
                }
                else{

                    Contact contact=new Contact(et_name.getText().toString(),et_email.getText().toString(),et_phone.getText().toString(),imageurl,imagepath);
                    HashMap<String,Object> toSave=contact.toHashMap();
                    db.collection("Users").document(currentUser.getUid()).collection("Contacts")
                            .add(toSave)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("demo", "OnSuccess:Succesful");
                                    Intent i1=new Intent(CreateContactActivity.this,HomescreenActivity.class);
                                    startActivity(i1);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("demo", "onFailure");
                                }
                            });
                }


            }
        });

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras=data.getExtras();
            Bitmap imageBitmap =(Bitmap) extras.get("data");
            bitmap=imageBitmap;
            iv_profile.setImageBitmap(imageBitmap);
            uploadImage(imageBitmap);
            isTakenPhoto = true;
        }
    }

    private void uploadImage(Bitmap imageBitmap) {
        StorageReference storageReference = storage.getReference();

        final StorageReference imageRepo = storageReference.child("images/" +
                UUID.randomUUID() +
                ".png");
        imagepath=imageRepo.toString();
        imagepath=imagepath.substring(33,imagepath.length());
        Log.d("imagepath",imagepath);


//        Converting the Bitmap into a bytearrayOutputstream....
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRepo.putBytes(data);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                return imageRepo.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Log.d("demo", "Image Download URL" + task.getResult());
                    String imageURL = task.getResult().toString();
                    imageurl = imageURL;
                }
            }
        });
    }
}
