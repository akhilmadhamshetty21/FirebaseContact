package com.example.inclass12;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    Context ctx;
    ArrayList<Contact> contactArrayList=new ArrayList<>();
    public static InteractWithMain interact;

    public ContactAdapter(Context ctx, ArrayList<Contact> contactArrayList) {
        this.ctx = ctx;
        this.contactArrayList = contactArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout=(LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem,parent,false);
        ViewHolder viewHolder=new ViewHolder(linearLayout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        interact=(InteractWithMain)ctx;
        holder.tv_name.setText(contactArrayList.get(position).getName());
        holder.tv_phone.setText(contactArrayList.get(position).getPhone());
        holder.tv_email.setText(contactArrayList.get(position).getEmail());
        if (contactArrayList.get(position).getImageUrl() == null)
            holder.iv_image.setImageResource(R.drawable.ic_person_black_24dp);
        else
            Picasso.get().load(contactArrayList.get(position).getImageUrl()).into(holder.iv_image);
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                interact.deleteitem(position,contactArrayList.get(position).getDocumentID(),contactArrayList.get(position).getImagepath());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactArrayList.size();
    }
    public interface InteractWithMain{
        public void deleteitem(int position,String documentid,String imagepath);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name,tv_phone,tv_email;
        ImageView iv_image;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_email=itemView.findViewById(R.id.tv_email);
            tv_phone=itemView.findViewById(R.id.tv_phone);
            iv_image=itemView.findViewById(R.id.iv_image);
            cardView=itemView.findViewById(R.id.cardView);
        }
    }
}
