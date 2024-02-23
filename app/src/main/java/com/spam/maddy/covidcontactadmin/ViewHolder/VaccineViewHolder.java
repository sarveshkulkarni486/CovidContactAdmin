package com.spam.maddy.covidcontactadmin.ViewHolder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.spam.maddy.covidcontactadmin.Common.Common;
import com.spam.maddy.covidcontactadmin.Interface.ItemClickListener;
import com.spam.maddy.covidcontactadmin.R;

public class VaccineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

    public TextView vaccine_name;
    public ImageView vaccine_center_image;

    private ItemClickListener itemClickListener;

    public VaccineViewHolder(@NonNull View itemView) {
        super(itemView);
        vaccine_name=(TextView)itemView.findViewById(R.id.vaccine_name);
        vaccine_center_image=(ImageView)itemView.findViewById(R.id.vaccine_center_image);

        itemView.setOnCreateContextMenuListener(this);

        itemView.setOnClickListener(this);


    }

    public  void  setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;

    }


    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Select the action");
        contextMenu.add(0,0,getAdapterPosition(), Common.UPDATE);
        contextMenu.add(0,1,getAdapterPosition(), Common.DELETE);


    }
}