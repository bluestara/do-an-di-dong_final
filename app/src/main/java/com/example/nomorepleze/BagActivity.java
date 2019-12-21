package com.example.nomorepleze;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class BagActivity extends AppCompatActivity {

    private ArrayList<ListItem> mBagList;

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

        mBagList.add(new ListItem(R.drawable.bagone, "Line 1", "Line 2" ));
        mBagList.add(new ListItem(R.drawable.bagtwo, "Line 3", "Line 4" ));
        mBagList.add(new ListItem(R.drawable.bagthree, "Line 5", "Line 6" ));
        mBagList.add(new ListItem(R.drawable.bagfour, "Line 5", "Line 6" ));
        mBagList.add(new ListItem(R.drawable.bagfive, "Line 5", "Line 6" ));
        mBagList.add(new ListItem(R.drawable.bagsix, "Line 5", "Line 6" ));
        mBagList.add(new ListItem(R.drawable.bagseven, "Line 5", "Line 6" ));
        mBagList.add(new ListItem(R.drawable.bageight, "Line 5", "Line 6" ));
        mBagList.add(new ListItem(R.drawable.bagnine, "Line 5", "Line 6" ));
        mBagList.add(new ListItem(R.drawable.bagten, "Line 5", "Line 6" ));
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
