/*
    Assignment: Inclass12
    Team members:
    Akhil Madhamshetty:801165622
    Tarun thota:801164383
 */
package com.example.inclass12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email,pass;
    Button btn_login,btn_signup;
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            Log.d("demo", "Current User: "+currentUser.getEmail());
            Intent i1=new Intent(MainActivity.this,HomescreenActivity.class);
            startActivity(i1);
        }else{
            Log.d("demo", "Not found user, login!!");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Login");
        email=findViewById(R.id.et_email);
        pass=findViewById(R.id.et_password);
        btn_login=findViewById(R.id.btn_login);
        btn_signup=findViewById(R.id.btn_signup);
        mAuth=FirebaseAuth.getInstance();
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(i1);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().equals(""))
                    email.setError("Field can't be empty");
                else if(!RegisterActivity.validate(email.getText().toString()))
                    email.setError("Invalid Mail ID");
                else if (pass.getText().toString().equals(""))
                    pass.setError("Field can't be empty");
                else {
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("demo", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Intent i1 = new Intent(MainActivity.this, HomescreenActivity.class);
                                        startActivity(i1);
                                    } else {
                                        Log.w("demo", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Login was not Succesful",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }
}
