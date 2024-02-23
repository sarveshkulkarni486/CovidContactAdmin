package com.spam.maddy.covidcontactadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.spam.maddy.covidcontactadmin.Common.Common;
import com.spam.maddy.covidcontactadmin.Interface.ItemClickListener;
import com.spam.maddy.covidcontactadmin.Model.User;
import com.spam.maddy.covidcontactadmin.Model.Vaccine;
import com.spam.maddy.covidcontactadmin.ViewHolder.UserViewHolder;
import com.spam.maddy.covidcontactadmin.ViewHolder.VaccineViewHolder;
import com.squareup.picasso.Picasso;

public class UserListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FloatingActionButton fab;

    FirebaseDatabase db;
    DatabaseReference foodList;
    FirebaseStorage storage;
    StorageReference storageReference;

    String categoryId="";

    FirebaseRecyclerAdapter<User, UserViewHolder> adapter;


    User newFood;

    Uri saveUri;
    RelativeLayout rootLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        db=FirebaseDatabase.getInstance();
        foodList=db.getReference("User").child("Guest");
        BottomNavigationView bottomNavigationView=(BottomNavigationView)findViewById(R.id.userListnv);
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.action_home :
                        /*startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        finish();*/
                        return true;

                    case R.id.action_precautions :
                        startActivity(new Intent(getApplicationContext(),CovidHelpActivity.class));
                       // finish();
                        return true;

                    case R.id.action_vaccineCenter :
                        startActivity(new Intent(getApplicationContext(),AddVaccineCenterActivity.class));
                        // finish();
                        return true;

                    case R.id.action_logout :
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                        return true;
                }
                return true;
            }
        });


        recyclerView=(RecyclerView)findViewById(R.id.recycler_user);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        rootLayout=(RelativeLayout)findViewById(R.id.rootLayout);

        if(getIntent()!=null)
            categoryId= "mm";
        if(!categoryId.isEmpty())
            loadListFood();

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.startListening();
    }

    private void loadListFood() {

        //Query listFoodByCategoryId = foodList;
        Query listFoodByCategoryId = foodList;//.orderByChild("menuId").equalTo(categoryId);

        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(listFoodByCategoryId,User.class)
                .build();

        adapter= new FirebaseRecyclerAdapter<User, UserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder viewHolder, int position, @NonNull User model) {
            //    viewHolder.txtUserId.setText(adapter.getRef(position).getKey());
                viewHolder.txtUserName.setText(model.getName());
                viewHolder.txtUserAddress.setText(model.getHomeAddress());
                viewHolder.txtUserDOB.setText(model.getDob());
                viewHolder.txtUserPhone.setText(adapter.getRef(position).getKey());
                String covidResult=model.getCovidTest();
                viewHolder.txtUserCovidTest.setText(model.getCovidTest());

                if (covidResult.equals("Negative")){
                    viewHolder.txtUserName.setTextColor(Color.GREEN);
                    viewHolder.txtUserCovidTest.setTextColor(Color.GREEN);

                }else{
                    viewHolder.txtUserName.setTextColor(Color.RED);
                    viewHolder.txtUserCovidTest.setTextColor(Color.RED);

                }
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent orderDetail=new Intent(UserListActivity.this,UserDetailsActivity.class);
                        Common.useruser=model;
                        orderDetail.putExtra("message_key",adapter.getRef(position).getKey());
                        startActivity(orderDetail);

                    }
                });
            }

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.user_item,parent,false);


                return new UserViewHolder(itemView);
            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}