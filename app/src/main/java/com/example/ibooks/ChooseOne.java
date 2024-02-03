package com.example.ibooks;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;

public class ChooseOne extends Activity {
    Button Owner,Customer,DeliveryPerson;
    Intent intent;
    String type;
    ConstraintLayout bgimage;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_one);
        AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img),3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img_2),3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img_1),3000);
        animationDrawable.setOneShot(false);
        animationDrawable.setEnterFadeDuration(850);
        animationDrawable.setExitFadeDuration(1600);

        bgimage = findViewById(R.id.back3);
        bgimage.setBackgroundDrawable(animationDrawable);
        animationDrawable.start();

        intent = getIntent();
        type = intent.getStringExtra("Home").toString().trim();

        Owner = (Button)findViewById(R.id.btnConnectBookOwner);
        DeliveryPerson = (Button)findViewById(R.id.btnDelivery);
        Customer = (Button)findViewById(R.id.btnCustomer);


        Owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("Email")){
                    Intent loginemail  = new Intent(ChooseOne.this, BookOwnerLogin.class);
                    startActivity(loginemail);
                    finish();
                }

                if(type.equals("SignUp")){
                    Intent Register  = new Intent(ChooseOne.this, BookOwnerRegistration.class);
                    startActivity(Register);
                }
            }
        });
//
//        Customer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(type.equals("Email")){
//                    Intent loginemailcust  = new Intent(ChooseOne.this,Login.class);
//                    startActivity(loginemailcust);
//                    finish();
//                }
//                if(type.equals("SignUp")){
//                    Intent Registercust  = new Intent(ChooseOne.this,Registration.class);
//                    startActivity(Registercust);
//                }
//
//            }
//        });
//
        DeliveryPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(type.equals("Email")){
                    Intent loginEmail = new Intent(ChooseOne.this,DeliveryLogin.class);
                    startActivity(loginEmail);
                    finish();
                }
                if(type.equals("SignUp")){
                    Intent RegisterDelivery  = new Intent(ChooseOne.this,DeliveryRegistration.class);
                    startActivity(RegisterDelivery);
                }

            }
        });

    }
}
