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

public class TrousersActivity extends AppCompatActivity {

    private ArrayList<item> mTrousersList;

    private RecyclerView mRecyclerView_trousers;
    private Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager_trousers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycleview_activity);

        createTrousersList();
        buildRecyclerView();

    }

    public void createTrousersList(){
        mTrousersList = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Data/Pant");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    item temp  = snapshot.getValue(item.class);
                    mTrousersList.add(temp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void buildRecyclerView(){
        mRecyclerView_trousers = findViewById(R.id.recyclerView);
        mRecyclerView_trousers.setHasFixedSize(true);
        mLayoutManager_trousers = new LinearLayoutManager(this);
        mAdapter = new Adapter(mTrousersList);

        mRecyclerView_trousers.setLayoutManager(mLayoutManager_trousers);
        mRecyclerView_trousers.setAdapter(mAdapter);



        mAdapter.setOnItemClickListener(new Adapter.OnItemClickListener(){
            @Override
            public void onItemClick(int position){
                Intent intent = new Intent();
                intent.putExtra("keyTrousers", mTrousersList.get(position));

                setResult(RESULT_OK, intent);

                finish();
            }
        });


    }
}
