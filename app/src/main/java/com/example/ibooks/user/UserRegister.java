package com.example.ibooks.user;

//import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//import android.app.AlertDialog;
import android.app.ProgressDialog;
//import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
import com.example.ibooks.R;
import com.google.android.material.textfield.TextInputLayout;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
//import java.util.HashMap;


public class UserRegister extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    RequestQueue requestQueue;
    String[] Cities = {"Ramallah","Birzeit","Nablus"};
    String[] Areas = {"Albireh","Jefna","Elbalad"};

    TextInputLayout Fname,Lname,Email,Pass,cpass,mobileno,houseno,area,pincode;
    Spinner Statespin,Cityspin;
    Button signup, Emaill, Phone;
    CountryCodePicker Cpp;
    //    FirebaseAuth FAuth;
//    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String fname,lname,emailid,password,confpassword,mobile,house,Area,Pincode,statee,cityy;
    String role="User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        sharedPreferences=  PreferenceManager.getDefaultSharedPreferences(this);
        requestQueue = Volley.newRequestQueue(this);

        Fname = (TextInputLayout)findViewById(R.id.FirstnameUser);
        Lname = (TextInputLayout)findViewById(R.id.LastnameUser);
        Email = (TextInputLayout)findViewById(R.id.EmailUser);
        Pass = (TextInputLayout)findViewById(R.id.PwdUser);
        cpass = (TextInputLayout)findViewById(R.id.ConfPassUser);
        mobileno = (TextInputLayout)findViewById(R.id.MobileNumUser);
        houseno = (TextInputLayout)findViewById(R.id.houseNumUser);
        pincode = (TextInputLayout)findViewById(R.id.PinCodeUser);
        Statespin = (Spinner) findViewById(R.id.StateUser);
        Cityspin = (Spinner) findViewById(R.id.CitySpinUser);
        area = (TextInputLayout)findViewById(R.id.AreaUser);

        signup = (Button)findViewById(R.id.SignupBtnUser);
        Emaill = (Button)findViewById(R.id.emailSignUpUser);

        Cpp = (CountryCodePicker)findViewById(R.id.CountryCodeUser);

        Statespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Object value = parent.getItemAtPosition(position);

                    ArrayList<String> list = new ArrayList<>();
                    for (String cities : Cities){
                        list.add(cities);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(UserRegister.this,android.R.layout.simple_spinner_item,list);
                    Cityspin.setAdapter(arrayAdapter);
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Cityspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                cityy = value.toString().trim();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        databaseReference = FirebaseDatabase.getInstance().getReference("Chef");
//        FAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fname = Fname.getEditText().getText().toString().trim();
                lname = Lname.getEditText().getText().toString().trim();
                emailid = Email.getEditText().getText().toString().trim();
                mobile = mobileno.getEditText().getText().toString().trim();
                password = Pass.getEditText().getText().toString().trim();
                confpassword = cpass.getEditText().getText().toString().trim();
                Area = area.getEditText().getText().toString().trim();
                house = houseno.getEditText().getText().toString().trim();
                Pincode = pincode.getEditText().getText().toString().trim();

                if (isValid())
                {
                    final ProgressDialog mDialog = new ProgressDialog(UserRegister.this);
                    mDialog.setCancelable(false);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setMessage("Registration in progress please wait......");
                    mDialog.show();

                    String url="https://omar91.pythonanywhere.com/register";
                    JSONObject jsonRegister=new JSONObject();


                    try {
                        jsonRegister.put( "First_Name",fname);
                        jsonRegister.put("Last_Name",lname);
                        jsonRegister.put( "EmailId",emailid);
                        jsonRegister.put("City",cityy);
                        jsonRegister.put("Area",Area);
                        jsonRegister.put("Password",password);
                        jsonRegister.put("Pincode",Pincode);
                        jsonRegister.put("State",statee);
                        jsonRegister.put("House",house);
                        jsonRegister.put("Mobile_No",mobile);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    Log.d("mmnnn", "ononononononono: ");

                    JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,url,jsonRegister, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("mmnnn", "onResponse: "+response.toString());
                            try {
                                if (response.get("message").equals("slecet other emai")){
                                    Email.setErrorEnabled(true);
                                    Email.setError("Email not available");
                                    mDialog.cancel();
                                    Toast.makeText(UserRegister.this, "Email not available! select other email", Toast.LENGTH_LONG).show();
                                }else {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("email",emailid);
                                    Toast.makeText(UserRegister.this, "successed", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            mDialog.cancel();
                        }
                    },new Response.ErrorListener(){

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("mmnnn", "onErrorResponse: "+error.networkResponse.statusCode+" - "+error.toString());
                            int statusCode = error.networkResponse.statusCode;
                            mDialog.cancel();
                            Toast.makeText(UserRegister.this, "Connetion Error", Toast.LENGTH_LONG).show();
                        }
                    });
                    requestQueue.add(jsonObjectRequest);
//                    FAuth.createUserWithEmailAndPassword(emailid,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//
//                            if (task.isSuccessful()){
//                                String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                                databaseReference = FirebaseDatabase.getInstance().getReference("User").child(useridd);
//                                final HashMap<String , String> hashMap = new HashMap<>();
//                                hashMap.put("Role",role);
//                                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//
//                                        HashMap<String , String> hashMap1 = new HashMap<>();
//                                        hashMap1.put("Mobile No",mobile);
//                                        hashMap1.put("First Name",fname);
//                                        hashMap1.put("Last Name",lname);
//                                        hashMap1.put("EmailId",emailid);
//                                        hashMap1.put("City",cityy);
//                                        hashMap1.put("Area",Area);
//                                        hashMap1.put("Password",password);
//                                        hashMap1.put("Pincode",Pincode);
//                                        hashMap1.put("State",statee);
//                                        hashMap1.put("Confirm Password",confpassword);
//                                        hashMap1.put("House",house);
//
//                                        firebaseDatabase.getInstance().getReference("Chef")
//                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                                .setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<Void> task) {
//                                                        mDialog.dismiss();
//
//                                                        FAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                            @Override
//                                                            public void onComplete(@NonNull Task<Void> task) {
//
//                                                                if(task.isSuccessful()){
//                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(ChefRegistration.this);
//                                                                    builder.setMessage("You Have Registered! Make Sure To Verify Your Email");
//                                                                    builder.setCancelable(false);
//                                                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                                                        @Override
//                                                                        public void onClick(DialogInterface dialog, int which) {
//
//                                                                            dialog.dismiss();
//
//                                                                        }
//                                                                    });
//                                                                    AlertDialog Alert = builder.create();
//                                                                    Alert.show();
//                                                                }else{
//                                                                    mDialog.dismiss();
//                                                                    Toast.makeText(ChefRegistration.this, "Error"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                                                                }
//                                                            }
//                                                        });
//
//                                                    }
//                                                });
//
//                                    }
//                                });
//                            }
//                        }
//                    });
                }

            }
        });

    }

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public boolean isValid(){
        Email.setErrorEnabled(false);
        Email.setError("");
        Fname.setErrorEnabled(false);
        Fname.setError("");
        Lname.setErrorEnabled(false);
        Lname.setError("");
        Pass.setErrorEnabled(false);
        Pass.setError("");
        mobileno.setErrorEnabled(false);
        mobileno.setError("");
        cpass.setErrorEnabled(false);
        cpass.setError("");
        area.setErrorEnabled(false);
        area.setError("");
        houseno.setErrorEnabled(false);
        houseno.setError("");
        pincode.setErrorEnabled(false);
        pincode.setError("");

        boolean isValid=false,isValidhouseno=false,isValidlname=false,isValidname=false,isValidemail=false,isValidpassword=false,isValidconfpassword=false,isValidmobilenum=false,isValidarea=false,isValidpincode=false;
        if(TextUtils.isEmpty(fname)){
            Fname.setErrorEnabled(true);
            Fname.setError("Enter First Name");
        }else{
            isValidname = true;
        }
        if(TextUtils.isEmpty(lname)){
            Lname.setErrorEnabled(true);
            Lname.setError("Enter Last Name");
        }else{
            isValidlname = true;
        }
        if(TextUtils.isEmpty(emailid)){
            Email.setErrorEnabled(true);
            Email.setError("Email Is Required");
        }else{
            if(emailid.matches(emailpattern)){
                isValidemail = true;
            }else{
                Email.setErrorEnabled(true);
                Email.setError("Enter a Valid Email Id");
            }
        }
        if(TextUtils.isEmpty(password)){
            Pass.setErrorEnabled(true);
            Pass.setError("Enter Password");
        }else{
            if(password.length()<8){
                Pass.setErrorEnabled(true);
                Pass.setError("Password is Weak");
            }else{
                isValidpassword = true;
            }
        }
        if(TextUtils.isEmpty(confpassword)){
            cpass.setErrorEnabled(true);
            cpass.setError("Enter Password Again");
        }else{
            if(!password.equals(confpassword)){
                cpass.setErrorEnabled(true);
                cpass.setError("Password Dosen't Match");
            }else{
                isValidconfpassword = true;
            }
        }
        if(TextUtils.isEmpty(mobile)){
            mobileno.setErrorEnabled(true);
            mobileno.setError("Mobile Number Is Required");
        }else{
            if(mobile.length()<10){
                mobileno.setErrorEnabled(true);
                mobileno.setError("Invalid Mobile Number");
            }else{
                isValidmobilenum = true;
            }
        }
        if(TextUtils.isEmpty(Area)){
            area.setErrorEnabled(true);
            area.setError("Area Is Required");
        }else{
            isValidarea = true;
        }
        if(TextUtils.isEmpty(Pincode)){
            pincode.setErrorEnabled(true);
            pincode.setError("Please Enter Pincode");
        }else{
            isValidpincode = true;
        }
        if(TextUtils.isEmpty(house)){
            houseno.setErrorEnabled(true);
            houseno.setError("Fields Can't Be Empty");
        }else{
            isValidhouseno = true;
        }

        isValid = (isValidarea && isValidconfpassword && isValidpassword && isValidpincode && isValidemail && isValidmobilenum && isValidname && isValidhouseno && isValidlname) ? true : false;
        return isValid;


    }
}