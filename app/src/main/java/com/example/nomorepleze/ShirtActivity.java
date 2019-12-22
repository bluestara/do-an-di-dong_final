package com.example.nomorepleze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShirtActivity extends AppCompatActivity {

    private ArrayList<item> mShirtList;

    private RecyclerView mRecyclerView_shirt;
    private Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager_shirt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycleview_activity);

        createShirtList();
        buildRecyclerView();

    }

    public void createShirtList(){
        mShirtList = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Data/Shirt");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    // Quick Fix lỗi chỉ lấy được 1 phần dữ liệu
                    // Cụ thể là chỉ lấy được Name và Price còn mImageURI không lấy được bằng snapshot.getValue(item.class)
                    // Buộc phải lấy bằng cách thủ công
                    //
                    //Note: Chưa tìm ra nguyên nhân xảy ra lỗi
                    item temp  = snapshot.getValue(item.class);
                    String test  = snapshot.child("mImageURL").getValue(String.class);
                    temp.mImageURI = test;
                    mShirtList.add(temp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ShirtActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



    public void buildRecyclerView(){
        mRecyclerView_shirt = findViewById(R.id.recyclerView);
        mRecyclerView_shirt.setHasFixedSize(true);
        mLayoutManager_shirt = new LinearLayoutManager(this);
        mAdapter = new Adapter(mShirtList);

        mRecyclerView_shirt.setLayoutManager(mLayoutManager_shirt);
        mRecyclerView_shirt.setAdapter(mAdapter);



        mAdapter.setOnItemClickListener(new Adapter.OnItemClickListener(){
            @Override
            public void onItemClick(int position){
                Intent intent = new Intent();
                intent.putExtra("keyShirt", mShirtList.get(position));

                setResult(RESULT_OK, intent);

                finish();
            }
        });


    }
}
