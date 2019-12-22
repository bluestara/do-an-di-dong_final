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

public class BagActivity extends AppCompatActivity {

    private ArrayList<item> mBagList;

    private RecyclerView mRecyclerView_bag;
    private Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager_bag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycleview_activity);

        createBagList();
        buildRecyclerView();

    }

    public void createBagList(){
        mBagList = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Data/Bag");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    item temp  = snapshot.getValue(item.class);
                    mBagList.add(temp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void buildRecyclerView(){
        mRecyclerView_bag = findViewById(R.id.recyclerView);
        mRecyclerView_bag.setHasFixedSize(true);
        mLayoutManager_bag = new LinearLayoutManager(this);
        mAdapter = new Adapter(mBagList);

        mRecyclerView_bag.setLayoutManager(mLayoutManager_bag);
        mRecyclerView_bag.setAdapter(mAdapter);



        mAdapter.setOnItemClickListener(new Adapter.OnItemClickListener(){
            @Override
            public void onItemClick(int position){
                Intent intent = new Intent();
                intent.putExtra("keyBag", mBagList.get(position));

                setResult(RESULT_OK, intent);

                finish();
            }
        });


    }
}
