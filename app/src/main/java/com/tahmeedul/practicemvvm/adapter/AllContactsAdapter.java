package com.tahmeedul.practicemvvm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.tahmeedul.practicemvvm.R;
import com.tahmeedul.practicemvvm.model.NewContactModel;

import java.util.List;

public class AllContactsAdapter extends RecyclerView.Adapter<AllContactsAdapter.MyViewHolder>{

    List<NewContactModel> list;

    public void getContactList(List<NewContactModel> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.model_contact_list_single_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(holder.imageView.getContext())
                .load(list.get(position).newImage)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_person_24)
                .into(holder.imageView);

        holder.textView1.setText(list.get(position).getNewId());
        holder.textView2.setText(list.get(position).getNewName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircularImageView imageView;
        TextView textView1, textView2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.listDp);
            textView1 = itemView.findViewById(R.id.listId);
            textView2 = itemView.findViewById(R.id.listName);

        }
    }
}
