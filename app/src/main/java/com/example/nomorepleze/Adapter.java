package com.example.nomorepleze;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ListViewHolder> {

    private ArrayList<item> mList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public static class ListViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageButton;
        public TextView mTextView1;
        public TextView mTextView2;

        public ListViewHolder(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageButton = itemView.findViewById(R.id.imagebtn1);
            mTextView1 = itemView.findViewById(R.id.text1);
            mTextView2 = itemView.findViewById(R.id.text2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }


    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ListViewHolder hvh = new ListViewHolder(v, mListener);
        return hvh;
    }

    public Adapter(ArrayList<item> list){
        mList = list;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        item currentItem = mList.get(position);

        Picasso.get().load(currentItem.mImageURI)
                .fit()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.mImageButton);
        holder.mTextView1.setText(currentItem.Name);
        holder.mTextView2.setText(String.valueOf(currentItem.Price));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
