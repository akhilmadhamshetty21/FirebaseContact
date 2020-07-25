package com.example.inclass12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class HomescreenActivity extends AppCompatActivity implements ContactAdapter.InteractWithMain{
    Button btn_create;
    ImageView iv_logout;
    private FirebaseAuth mauth;
    RecyclerView recyclerView;
    ArrayList<Contact> contacts=new ArrayList<>();
    RecyclerView.LayoutManager rv_layoutManager;
    RecyclerView.Adapter adapter;
    private FirebaseFirestore db;
    FirebaseUser currentUser;
    String userid;
    FirebaseStorage storage;
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
        setContentView(R.layout.activity_homescreen);

        setTitle("Contacts");
        btn_create=findViewById(R.id.btn_createnew);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        rv_layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rv_layoutManager);
        db = FirebaseFirestore.getInstance();
        iv_logout=findViewById(R.id.iv_logout);
        mauth=FirebaseAuth.getInstance();
        currentUser = mauth.getCurrentUser();
        userid=currentUser.getUid();
        storage = FirebaseStorage.getInstance();

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(HomescreenActivity.this,CreateContactActivity.class);
                startActivity(i1);
            }
        });
        iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mauth.signOut();
                Intent i1=new Intent(HomescreenActivity.this,MainActivity.class);
                startActivity(i1);
            }
        });
        db.collection("Users").document(userid).collection("Contacts")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                            Contact contact=documentSnapshot.toObject(Contact.class);
//                            Log.d("contact:",contact.getImageUrl());
                            contact.setDocumentID(documentSnapshot.getId());
                            Log.d("contacts",contact.toString());
                            contacts.add(contact);
                        }
                        adapter=new ContactAdapter(HomescreenActivity.this,contacts);
                        recyclerView.setAdapter(adapter);
                    }
                });
    }

    @Override
    public void deleteitem(final int position, final String documentid, final String imagepath) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete contact?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.collection("Users").document(userid).collection("Contacts").document(documentid)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("success","Deleted");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                        Log.d("failue","Not Deleted");
                            }
                        });
                        if (imagepath!=null) {
                            StorageReference storageRef = storage.getReference();
                            StorageReference imageref = storageRef.child(imagepath);
                            imageref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Delete Succesful", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Delete Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        contacts.remove(position);
                        adapter.notifyDataSetChanged();

                    }
                }).show();



    }
}
