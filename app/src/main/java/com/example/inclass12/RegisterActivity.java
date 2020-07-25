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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText fname,lname,email,pass,cpass;
    Button btn_signup,btn_cancel;
    private FirebaseAuth mAuth;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle("Sign Up");
        fname=findViewById(R.id.et_fname);
        lname=findViewById(R.id.et_lname);
        email=findViewById(R.id.et_smail);
        pass=findViewById(R.id.et_cpass);
        cpass=findViewById(R.id.et_rpass);
        btn_signup=findViewById(R.id.btn_ssignup);
        btn_cancel=findViewById(R.id.btn_scancel);
        mAuth = FirebaseAuth.getInstance();
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fname.getText().toString().equals("")){
                    fname.setError("First name can't be empty");
                }
                else if(lname.getText().toString().equals("")){
                    lname.setError("Last name can't be empty");
                }
                else if(email.getText().toString().equals("")){
                    email.setError("Email can't be empty");
                }
                else if (!validate(email.getText().toString()))
                    email.setError("Invalid Mail ID Format");
                else if(pass.getText().toString().equals("")){
                    pass.setError("Password can't be empty");
                }
                else if(cpass.getText().toString().equals("")){
                    cpass.setError("Confirm password can't be empty");
                }
                else if(!cpass.getText().toString().equals(pass.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Password and confirm password should be same", Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("User creation", "success");
                                        Toast.makeText(getApplicationContext(), "Authentication success", Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Intent i1=new Intent(RegisterActivity.this,HomescreenActivity.class);
                                        startActivity(i1);
                                    } else {
                                        Log.w("demo", "createUserWithEmail:failure", task.getException());
//                                        try {
//                                            throw task.getException();
//                                        } catch(FirebaseAuthWeakPasswordException e) {
//                                            Toast.makeText(getApplicationContext(), "Weak password", Toast.LENGTH_SHORT).show();
//                                        } catch(FirebaseAuthInvalidCredentialsException e) {
//                                            Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
//                                        } catch(FirebaseAuthUserCollisionException e) {
//                                            Toast.makeText(getApplicationContext(), "Already exists", Toast.LENGTH_SHORT).show();
//                                        } catch(Exception e) {
//                                            Log.e("demo", e.getMessage());
//                                            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                                        }
                                        Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }
}
