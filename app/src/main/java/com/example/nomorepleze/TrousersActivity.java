package com.example.nomorepleze;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;

public class TrousersActivity extends AppCompatActivity {

    private ArrayList<ListItem> mTrousersList;

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

        mTrousersList.add(new ListItem(R.drawable. pantwo, "Line 1", "Line 2" ));
        mTrousersList.add(new ListItem(R.drawable.pl, "Line 3", "Line 4" ));
        mTrousersList.add(new ListItem(R.drawable.pantthree, "Line 5", "Line 6" ));
        mTrousersList.add(new ListItem(R.drawable.pantfour, "Line 5", "Line 6" ));
        mTrousersList.add(new ListItem(R.drawable.pantfive, "Line 5", "Line 6" ));
        mTrousersList.add(new ListItem(R.drawable.pantsix, "Line 5", "Line 6" ));
        mTrousersList.add(new ListItem(R.drawable.pantseven, "Line 5", "Line 6" ));
        mTrousersList.add(new ListItem(R.drawable.panteight, "Line 5", "Line 6" ));
        mTrousersList.add(new ListItem(R.drawable.pantnine, "Line 5", "Line 6" ));
        mTrousersList.add(new ListItem(R.drawable.pantten, "Line 5", "Line 6" ));
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
