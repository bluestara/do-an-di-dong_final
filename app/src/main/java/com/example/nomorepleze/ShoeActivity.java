package com.example.nomorepleze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShoeActivity extends AppCompatActivity {

    private ArrayList<item> mShoeList;

    private RecyclerView mRecyclerView_shoe;
    private Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager_shoe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycleview_activity);

        createShoeList();
        buildRecyclerView();

    }

    public void createShoeList(){
        mShoeList = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Data/Shoe");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    item temp  = snapshot.getValue(item.class);
                    String test  = snapshot.child("mImageURL").getValue(String.class);
                    temp.mImageURI = test;
                    mShoeList.add(temp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void buildRecyclerView(){
        mRecyclerView_shoe = findViewById(R.id.recyclerView_item_order);
        mRecyclerView_shoe.setHasFixedSize(true);
        mLayoutManager_shoe = new LinearLayoutManager(this);
        mAdapter = new Adapter(mShoeList);

        mRecyclerView_shoe.setLayoutManager(mLayoutManager_shoe);
        mRecyclerView_shoe.setAdapter(mAdapter);



        mAdapter.setOnItemClickListener(new Adapter.OnItemClickListener(){
            @Override
            public void onItemClick(int position){
                Intent intent = new Intent();
                intent.putExtra("keyShoe", mShoeList.get(position));

                setResult(RESULT_OK, intent);

                finish();
            }
        });


    }
}
