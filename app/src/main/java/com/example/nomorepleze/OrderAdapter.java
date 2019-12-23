package com.example.nomorepleze;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderItemViewHolder> {
    private ArrayList<item> mList;
    public OrderAdapter(ArrayList<item> list) {mList = list;}

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client,parent,false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        item temp = mList.get(position);
        holder.mTextViewName.setText(temp.Name);
        holder.mTextViewPrice.setText(String.valueOf(temp.Price));

        Picasso.get().load(temp.mImageURI)
                    .fit()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.mImageButton);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class OrderItemViewHolder extends RecyclerView.ViewHolder
    {
        public ImageButton mImageButton;
        public TextView mTextViewName;
        public TextView mTextViewPrice;
        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageButton =itemView.findViewById(R.id.imagebtn1);
            mTextViewName = itemView.findViewById(R.id.text1);
            mTextViewPrice = itemView.findViewById(R.id.text2);
        }
    }

}
