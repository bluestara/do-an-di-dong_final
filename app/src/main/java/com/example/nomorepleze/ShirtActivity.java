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

public class ShirtActivity extends AppCompatActivity {

    private ArrayList<ListItem> mShirtList;

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
        mShirtList.add(new ListItem(R.drawable.shirtone, "Line 1", "Line 2" ));
        mShirtList.add(new ListItem(R.drawable.aaa, "Line 3", "Line 4" ));
        mShirtList.add(new ListItem(R.drawable.shirtthree, "Line 5", "Line 6" ));
        mShirtList.add(new ListItem(R.drawable.shirtfour, "Line 5", "Line 6" ));
        mShirtList.add(new ListItem(R.drawable.shirtfive, "Line 5", "Line 6" ));
        mShirtList.add(new ListItem(R.drawable.shirtsix, "Line 5", "Line 6" ));
        mShirtList.add(new ListItem(R.drawable.shirtseven, "Line 5", "Line 6" ));
        mShirtList.add(new ListItem(R.drawable.shirteight, "Line 5", "Line 6" ));
        mShirtList.add(new ListItem(R.drawable.shirtnine, "Line 5", "Line 6" ));
        mShirtList.add(new ListItem(R.drawable.shirtten, "Line 5", "Line 6" ));
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
