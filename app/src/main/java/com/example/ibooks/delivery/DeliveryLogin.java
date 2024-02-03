package com.example.ibooks.delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibooks.ForgotPassword;
import com.example.ibooks.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeliveryLogin extends AppCompatActivity {

    TextInputLayout email,pass;
    Button Signin;
    TextView Forgotpassword , signup;
    FirebaseAuth Fauth;
    String emailid,pwd;
    boolean isDelivery =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_login);

        try{

            email = (TextInputLayout)findViewById(R.id.Lemail);
            pass = (TextInputLayout)findViewById(R.id.Lpassword);
            Signin = (Button)findViewById(R.id.button4);
            signup = (TextView) findViewById(R.id.textView3);
            Forgotpassword = (TextView)findViewById(R.id.forgotpass);


            Fauth = FirebaseAuth.getInstance();

            Signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    emailid = email.getEditText().getText().toString().trim();
                    pwd = pass.getEditText().getText().toString().trim();

                    if(isValid()){

                        final ProgressDialog progressDialog = new ProgressDialog(DeliveryLogin.this);
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage("Sign In Please Wait.......");
                        progressDialog.show();

                        Fauth.signInWithEmailAndPassword(emailid,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    progressDialog.dismiss();

                                    if(Fauth.getCurrentUser().isEmailVerified()){

                                        progressDialog.dismiss();
                                        Toast.makeText(DeliveryLogin.this, "Congratulation! You Have Successfully Logged in", Toast.LENGTH_SHORT).show();
                                        //TODO:: fix mainHome so it would go to DeliveryMain
                                        Intent Z = new Intent(DeliveryLogin.this, DeliveryMain.class);
                                        isDeliveryCheck();
                                        if (isDelivery){
                                            Toast.makeText(DeliveryLogin.this,"welcome Driver",Toast.LENGTH_LONG).show();
                                            System.out.println(isDelivery +" isDelivery");
                                        }
                                        startActivity(Z);

                                        finish();


                                    }else{
                                        Toast.makeText(DeliveryLogin.this,"Verification Failed\nYou Have Not Verified Your Email",Toast.LENGTH_LONG).show();

                                    }
                                }else{
                                    progressDialog.dismiss();
                                    Toast.makeText(DeliveryLogin.this,"Error"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
            });
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DeliveryLogin.this, DeliveryRegistration.class));
                    finish();
                }
            });
            Forgotpassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DeliveryLogin.this, ForgotPassword.class));
                    finish();
                }
            });


        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    String emailpattern  = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid(){

        email.setErrorEnabled(false);
        email.setError("");
        pass.setErrorEnabled(false);
        pass.setError("");

        boolean isvalid=false,isvalidemail=false,isvalidpassword=false;
        if(TextUtils.isEmpty(emailid)){
            email.setErrorEnabled(true);
            email.setError("Email is required");
        }else{
            if(emailid.matches(emailpattern)){
                isvalidemail=true;
            }else{
                email.setErrorEnabled(true);
                email.setError("Invalid Email Address");
            }
        }
        if(TextUtils.isEmpty(pwd)){

            pass.setErrorEnabled(true);
            pass.setError("Password is Required");
        }else{
            isvalidpassword=true;
        }
        isvalid=(isvalidemail && isvalidpassword)?true:false;
        return isvalid;
    }
    void isDeliveryCheck(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String currentUserId = auth.getCurrentUser().getUid();

        DatabaseReference roleRef = FirebaseDatabase.getInstance().getReference("Role").child(currentUserId);

        roleRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String userRole = dataSnapshot.getValue(String.class);

                    if ("Delivery".equals(userRole)) {
                        isDelivery =true;
                    }else{

                        Toast.makeText(DeliveryLogin.this,"The signed-in user has a different role",Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Role information not found for the signed-in user
                    Toast.makeText(DeliveryLogin.this,"Role information not found for the signed-in user",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });

    }
}