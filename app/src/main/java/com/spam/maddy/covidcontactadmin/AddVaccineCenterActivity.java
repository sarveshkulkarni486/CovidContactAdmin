package com.spam.maddy.covidcontactadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.spam.maddy.covidcontactadmin.Common.Common;
import com.spam.maddy.covidcontactadmin.Interface.ItemClickListener;
import com.spam.maddy.covidcontactadmin.Model.Vaccine;
import com.spam.maddy.covidcontactadmin.ViewHolder.VaccineViewHolder;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class AddVaccineCenterActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FloatingActionButton fab;

    FirebaseDatabase db;
    DatabaseReference foodList;
    FirebaseStorage storage;
    StorageReference storageReference;

    String categoryId="";

    FirebaseRecyclerAdapter<Vaccine, VaccineViewHolder> adapter;

    EditText edtVaccineCenterName,edtContactNo,edtAddress,edtVaccineName,edtVaccineQty,edtVaccinePrice,edtUpdateVaccineQty;
    Button btnSelect,btnUpload;

    Vaccine newFood;

    Uri saveUri;
    RelativeLayout rootLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vaccine_center);
//        foodList=db.getReference("Data").child("Vaccine").child("VaccineCenter");
        BottomNavigationView bottomNavigationView=(BottomNavigationView)findViewById(R.id.vaccinenv);
        bottomNavigationView.setSelectedItemId(R.id.action_vaccineCenter);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.action_home :
                        startActivity(new Intent(getApplicationContext(),UserListActivity.class));
                        finish();
                        return true;

                    case R.id.action_precautions :
                        startActivity(new Intent(getApplicationContext(),CovidHelpActivity.class));
                        finish();
                        return true;

                    case R.id.action_vaccineCenter :
                        //startActivity(new Intent(getApplicationContext(),AddVaccineCenterActivity.class));
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

        db=FirebaseDatabase.getInstance();
        foodList=db.getReference("Data").child("Vaccine").child("VaccineCenter");
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        recyclerView=(RecyclerView)findViewById(R.id.recycler_vaccine);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        rootLayout=(RelativeLayout)findViewById(R.id.rootLayout);

        fab=(FloatingActionButton)findViewById(R.id.fabVaccine);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAddFoodDailog();
            }
        });
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),UserListActivity.class));
        finish();
    }

    private void showAddFoodDailog() {

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(AddVaccineCenterActivity.this);
        alertDialog.setTitle("Add new Food");
        alertDialog.setMessage("Please fill full information");

        LayoutInflater inflater=this.getLayoutInflater();
        View add_menu_layout=inflater.inflate(R.layout.add_new_vaccine_center,null);
      //  EditText edtVaccineCenterName,edtContactNo,edtAddress,edtVaccineName,edtVaccineQty,edtVaccinePrice,edtUpdateVaccineQty;
        edtVaccineCenterName=add_menu_layout.findViewById(R.id.edtVCName);
        edtContactNo=add_menu_layout.findViewById(R.id.edtVCContactNo);
        edtAddress=add_menu_layout.findViewById(R.id.edtVCAddress);
        edtVaccineName=add_menu_layout.findViewById(R.id.edtVaccineName);
        edtVaccineQty=add_menu_layout.findViewById(R.id.edtVaccineQty);
        edtVaccinePrice=add_menu_layout.findViewById(R.id.edtPriceVaccine);
        edtUpdateVaccineQty=add_menu_layout.findViewById(R.id.edtUpdateVaccineQty);

        btnSelect=add_menu_layout.findViewById(R.id.btnSelect);
        btnUpload=add_menu_layout.findViewById(R.id.btnUpload);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        alertDialog.setView(add_menu_layout);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                if (newFood!=null){
                    foodList.push().setValue(newFood);
                    Snackbar.make(rootLayout,"New Category"+newFood.getVaccineCenterName()+" was added",Snackbar.LENGTH_SHORT).show();

                }
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        alertDialog.show();

    }

    private void uploadImage() {
        if (saveUri!=null){
            final ProgressDialog mDialog=new ProgressDialog(this);
            mDialog.setMessage("Uploading...");
            mDialog.show();

            String imageName= UUID.randomUUID().toString();
            final StorageReference imageFolder=storageReference.child("images/"+imageName);
            imageFolder.putFile(saveUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mDialog.dismiss();
                    Toast.makeText(AddVaccineCenterActivity.this,"Uploaded !!!",Toast.LENGTH_SHORT).show();
                    imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            newFood=new Vaccine();
                            int qty=Integer.parseInt(edtVaccineQty.getText().toString());
                            int uptqty=Integer.parseInt(edtUpdateVaccineQty.getText().toString());
                            int totqty=qty+uptqty;
                            //EditText edtVaccineCenterName,edtContactNo,edtAddress,edtVaccineName,edtVaccineQty,edtVaccinePrice,edtUpdateVaccineQty;
                            newFood.setVaccineCenterName(edtVaccineCenterName.getText().toString());
                            newFood.setContactNo(edtContactNo.getText().toString());
                            newFood.setAddress(edtAddress.getText().toString());
                            newFood.setVaccineName(edtVaccineName.getText().toString());
                            newFood.setVaccineQty(String.valueOf(totqty));
                            newFood.setVaccinePrice(edtVaccinePrice.getText().toString());
                            newFood.setVaccineCenterImageUrl(uri.toString());
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mDialog.dismiss();
                    Toast.makeText(AddVaccineCenterActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress=(100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    mDialog.setMessage("Uploaded "+progress+"%");

                }
            });
        }
    }
    private void chooseImage() {

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), Common.PICK_IMAGE_REQUEST);

    }


    private void loadListFood() {

        //Query listFoodByCategoryId = foodList;
        Query listFoodByCategoryId = foodList;//.orderByChild("menuId").equalTo(categoryId);

        FirebaseRecyclerOptions<Vaccine> options = new FirebaseRecyclerOptions.Builder<Vaccine>()
                .setQuery(listFoodByCategoryId,Vaccine.class)
                .build();

        adapter= new FirebaseRecyclerAdapter<Vaccine, VaccineViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull VaccineViewHolder viewHolder, int position, @NonNull Vaccine model) {
                viewHolder.vaccine_name.setText(model.getVaccineCenterName());
                Picasso.with(AddVaccineCenterActivity.this).load(model.getVaccineCenterImageUrl()).into(viewHolder.vaccine_center_image);
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });
            }

            @NonNull
            @Override
            public VaccineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vaccine_item,parent,false);


                return new VaccineViewHolder(itemView);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Common.PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            saveUri=data.getData();
            btnSelect.setText("Image Selected!");
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(Common.UPDATE)){
            showUpdateFoodDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));

        }else if(item.getTitle().equals(Common.DELETE)){

            deleteFood(adapter.getRef(item.getOrder()).getKey());

        }
        return super.onContextItemSelected(item);
    }

    private void deleteFood(String key) {
        foodList.child(key).removeValue();
        Snackbar.make(rootLayout," Food  was deleted",Snackbar.LENGTH_SHORT).show();
    }

    private void showUpdateFoodDialog(final String key, final Vaccine item) {

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(AddVaccineCenterActivity.this);
        alertDialog.setTitle("Edit Food");
        alertDialog.setMessage("Please fill full information");

        LayoutInflater inflater=this.getLayoutInflater();
        View add_menu_layout=inflater.inflate(R.layout.add_new_vaccine_center,null);

        edtVaccineCenterName=add_menu_layout.findViewById(R.id.edtVCName);
        edtContactNo=add_menu_layout.findViewById(R.id.edtVCContactNo);
        edtAddress=add_menu_layout.findViewById(R.id.edtVCAddress);
        edtVaccineName=add_menu_layout.findViewById(R.id.edtVaccineName);
        edtVaccineQty=add_menu_layout.findViewById(R.id.edtVaccineQty);
        edtVaccinePrice=add_menu_layout.findViewById(R.id.edtPriceVaccine);
        edtUpdateVaccineQty=add_menu_layout.findViewById(R.id.edtUpdateVaccineQty);

        edtVaccineCenterName.setText(item.getVaccineCenterName());
        edtContactNo.setText(item.getContactNo());
        edtAddress.setText(item.getAddress());
        edtVaccineName.setText(item.getVaccineName());
        edtVaccineQty.setText(item.getVaccineQty());
        edtVaccinePrice.setText(item.getVaccinePrice());

        btnSelect=add_menu_layout.findViewById(R.id.btnSelect);
        btnUpload=add_menu_layout.findViewById(R.id.btnUpload);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(item);
            }
        });

        alertDialog.setView(add_menu_layout);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();


                edtVaccineCenterName.setText(item.getVaccineCenterName());
                edtContactNo.setText(item.getContactNo());
                edtAddress.setText(item.getAddress());
                edtVaccineName.setText(item.getVaccineName());
                edtVaccineQty.setText(item.getVaccineQty());
                edtVaccinePrice.setText(item.getVaccinePrice());


                int qty=Integer.parseInt(edtVaccineQty.getText().toString());
                int uptqty=Integer.parseInt(edtUpdateVaccineQty.getText().toString());
                int totqty=qty+uptqty;
                //EditText edtVaccineCenterName,edtContactNo,edtAddress,edtVaccineName,edtVaccineQty,edtVaccinePrice,edtUpdateVaccineQty;
                item.setVaccineCenterName(edtVaccineCenterName.getText().toString());
                item.setContactNo(edtContactNo.getText().toString());
                item.setAddress(edtAddress.getText().toString());
                item.setVaccineName(edtVaccineName.getText().toString());
                item.setVaccineQty(String.valueOf(totqty));
                item.setVaccinePrice(edtVaccinePrice.getText().toString());


                foodList.child(key).setValue(item);
                Snackbar.make(rootLayout," Food "+item.getVaccineCenterName()+" was Edited",Snackbar.LENGTH_SHORT).show();



            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        alertDialog.show();

    }



    private void changeImage(final Vaccine item) {
        if (saveUri!=null){
            final ProgressDialog mDialog=new ProgressDialog(this);
            mDialog.setMessage("Uploading...");
            mDialog.show();

            String imageName= UUID.randomUUID().toString();
            final StorageReference imageFolder=storageReference.child("images/"+imageName);
            imageFolder.putFile(saveUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mDialog.dismiss();
                    Toast.makeText(AddVaccineCenterActivity.this,"Uploaded !!!",Toast.LENGTH_SHORT).show();
                    imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            item.setVaccineCenterImageUrl(uri.toString());

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mDialog.dismiss();
                    Toast.makeText(AddVaccineCenterActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress=(100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    mDialog.setMessage("Uploaded "+progress+"%");

                }
            });
        }
    }


}
