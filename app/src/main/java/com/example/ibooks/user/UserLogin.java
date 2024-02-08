package com.example.ibooks.user;

import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.ibooks.ForgotPassword;

import com.google.android.material.textfield.TextInputLayout;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.ibooks.R;

public class UserLogin extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextInputLayout email,pass;
    Button Signin ;
    TextView Forgotpassword , signup;

    String emailid,pwd;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        requestQueue = Volley.newRequestQueue(this);
        sharedPreferences=getSharedPreferences("IBooks", Context.MODE_PRIVATE);
//        =  PreferenceManager.getDefaultSharedPreferences(this);
        try{

            email = (TextInputLayout)findViewById(R.id.LogInEmailUser);
            pass = (TextInputLayout)findViewById(R.id.LogInPasswordUser);
            Signin = (Button)findViewById(R.id.userLogInBtn);
            signup = (TextView) findViewById(R.id.CreateUser);
            Forgotpassword = (TextView)findViewById(R.id.forgotPassUser);
            //           Signinphone = (Button)findViewById(R.id.btnphone);

//            Fauth = FirebaseAuth.getInstance();

            Signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    emailid = email.getEditText().getText().toString().trim();
                    pwd = pass.getEditText().getText().toString().trim();

                    if(isValid()){

                        final ProgressDialog mDialog = new ProgressDialog(UserLogin.this);
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        mDialog.setMessage("Sign In Please Wait.......");
                        mDialog.show();

                        String url="https://omar91.pythonanywhere.com/login";
                        JSONObject jsonLogin=new JSONObject();

                        try {
                            jsonLogin.put( "email",emailid);
                            jsonLogin.put("password",pwd);
                        }catch (JSONException e){
                            throw new RuntimeException(e);
                        }

//                        Log.d("login", "clicked on login");
                        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, jsonLogin, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Mobile_No",response.get("Mobile_No").toString());
                                    editor.putString("Fname",response.get("Fname").toString());
                                    editor.putString("Lname",response.get("Lname").toString());
                                    editor.putString("Email",emailid);
                                    editor.putString("Password",pwd);
                                    editor.putBoolean("loged_in",true);
                                    editor.commit();
                                    mDialog.dismiss();
                                    Intent intent=new Intent(UserLogin.this, UserMainHome.class);
//                                    Intent intent=new Intent(CustomerLogin.this, MainHome.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(UserLogin.this, "logged in successfully", Toast.LENGTH_LONG).show();
                                }catch (JSONException e){
                                    Log.d("login", "onResponse: "+response.toString());
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("login", "onErrorResponse: "+error.toString());
                                mDialog.dismiss();
                                Toast.makeText(UserLogin.this, "Error", Toast.LENGTH_LONG).show();
                            }
                        });

                        requestQueue.add(jsonObjectRequest);

//                        Fauth.signInWithEmailAndPassword(emailid,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//
//                                if(task.isSuccessful()){
//                                    mDialog.dismiss();
//
//                                    if(Fauth.getCurrentUser().isEmailVerified()){
//                                        mDialog.dismiss();
//                                        Toast.makeText(Cheflogin.this, "Congratulation! You Have Successfully Logged in", Toast.LENGTH_SHORT).show();
//                                        Intent Z = new Intent(Cheflogin.this,ChefFoodPanel_BottomNavigation.class);
//                                        startActivity(Z);
//                                        finish();
//
//                                    }else{
//                                        Toast.makeText(Cheflogin.this,"Verification Failed\nYou Have Not Verified Your Email",Toast.LENGTH_LONG).show();
//
//                                    }
//                                }else{
//                                    mDialog.dismiss();
//                                    Toast.makeText(Cheflogin.this,"Error"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        });
                    }
                }
            });
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(UserLogin.this, UserRegister.class));
                    finish();
                }
            });
            Forgotpassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(UserLogin.this, ForgotPassword.class));
                    finish();
                }
            });
//            Signinphone.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(CustomerLogin.this,Chefloginphone.class));
//                    finish();
//                }
//            });
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
}