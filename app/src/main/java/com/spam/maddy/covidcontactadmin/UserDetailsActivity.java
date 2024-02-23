package com.spam.maddy.covidcontactadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.spam.maddy.covidcontactadmin.Common.Common;
import com.spam.maddy.covidcontactadmin.Model.User;

import java.text.SimpleDateFormat;

public class UserDetailsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

   public TextView txtUserPhone,txtUserAddress,txtUserName,txtUserDOB,txtCovidTest,txtUserEmail,covidResult,txtCovidTestDate;
   public Switch covidSwitch;
   String order_id_value;
   Button updateUser;
    FirebaseDatabase db;
    DatabaseReference foodList;
    FirebaseStorage storage;
    StorageReference storageReference;
    String covidTestResult="Negative";
    String testResult;
    String CovidTestingDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        db=FirebaseDatabase.getInstance();
        foodList=db.getReference("User").child("Guest");
        Intent intent = getIntent();
        String str = intent.getStringExtra("message_key");
        txtUserPhone=findViewById(R.id.userDetailMno);
        txtUserName=findViewById(R.id.userDetailName);
        txtUserAddress=findViewById(R.id.userDetailAddress);
        txtUserDOB=findViewById(R.id.userDetailDOB);
        txtUserEmail=findViewById(R.id.userDetailEmail);
        txtCovidTest=findViewById(R.id.userDetailCovidTest);
        updateUser=findViewById(R.id.updateUser);
        covidResult=findViewById(R.id.covidResult);
        txtCovidTestDate=findViewById(R.id.userDetailCovidTestDate);
            //order_id_value=getIntent().getStringExtra("OrderId");


        txtUserPhone.setText(str);
        txtUserName.setText(Common.useruser.getName());
        txtUserEmail.setText(Common.useruser.getEmail());
        txtUserAddress.setText(Common.useruser.getHomeAddress());
        txtUserDOB.setText(Common.useruser.getDob());
        txtCovidTest.setText(Common.useruser.getCovidTest());

        covidSwitch = (Switch) findViewById(R.id.switch1);
        covidSwitch.setOnCheckedChangeListener(UserDetailsActivity.this);

        updateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                User user = new User(
                        txtUserName.getText().toString(),
                        Common.useruser.getPassword(),
                        txtUserDOB.getText().toString(),
                        txtUserEmail.getText().toString(),
                        txtUserAddress.getText().toString(),
                        covidTestResult,
                        txtCovidTestDate.getText().toString()
                );

                foodList.child(txtUserPhone.getText().toString()).setValue(user);
                Toast.makeText(UserDetailsActivity.this,Common.useruser.getName()+"Update Corona Report",Toast.LENGTH_SHORT).show();
            }
        });

        testResult=txtCovidTest.getText().toString();
        if(testResult.equals("Positive")){
            covidSwitch.setChecked(true);
            covidTestResult="Positive";
            covidResult.setText(covidTestResult);
            covidResult.setTextColor(Color.RED);
           // CovidTestingDate= SimpleDateFormat.getDateInstance().format(new Date());
        }else {
            covidSwitch.setChecked(false);
            covidTestResult="Negative";
            covidResult.setText(covidTestResult);
            covidResult.setTextColor(Color.GREEN);

        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            // do something when check is selected
            covidTestResult="Positive";
            covidResult.setText(covidTestResult);
            covidResult.setTextColor(Color.RED);
            txtCovidTestDate.setText(SimpleDateFormat.getDateInstance().format(new Date()));
        } else {
            //do something when unchecked
            covidTestResult="Negative";
            covidResult.setText(covidTestResult);
            covidResult.setTextColor(Color.GREEN);
        }
    }
}