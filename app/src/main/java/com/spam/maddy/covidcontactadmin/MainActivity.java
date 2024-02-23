package com.spam.maddy.covidcontactadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spam.maddy.covidcontactadmin.Common.Common;
import com.spam.maddy.covidcontactadmin.Model.Admin;
import com.spam.maddy.covidcontactadmin.Model.User;

public class MainActivity extends AppCompatActivity {

    Button loginBtn;
    EditText edtPhone,edtPassword;
    FirebaseDatabase database ;
    DatabaseReference table_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database =FirebaseDatabase.getInstance();
        table_user=database.getReference("User").child("Admin");


        loginBtn=findViewById(R.id.loginBtn);
        edtPhone=(EditText)findViewById(R.id.edtPhone1);
        edtPassword=(EditText)findViewById(R.id.edtPassword);

        loginBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (Common.isConnectedToInternet(getBaseContext())) {

                    final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
                    mDialog.setMessage("Please Waiting.....");
                    mDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String strno=edtPhone.getText().toString();
                            if (dataSnapshot.child(strno).exists()) {
                                mDialog.dismiss();
                                Admin user = dataSnapshot.child(strno).getValue(Admin.class);

                                user.setPhone(edtPhone.getText().toString());


                                if (user.getPassword().equals(edtPassword.getText().toString())) {
                                    {
                                       Intent homeIntent = new Intent(MainActivity.this, UserListActivity.class);
                                        Common.currentUser = user;
                                        startActivity(homeIntent);
                                        finish();
                                        //verridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                        /*   table_user.removeEventListener(this);*/
                                        Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "Something is wrong", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(MainActivity.this, "User not exits in database", Toast.LENGTH_LONG).show();


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(MainActivity.this, "Something is wrong: "+databaseError, Toast.LENGTH_LONG).show();


                        }
                    });
                }else {
                    Toast.makeText(MainActivity.this,"please check your connection !!",Toast.LENGTH_SHORT).show();

                    return;
                }
            }
        });
    }


}