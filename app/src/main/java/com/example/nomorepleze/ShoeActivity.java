package com.example.nomorepleze;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class ShoeActivity extends AppCompatActivity {

    private ArrayList<ListItem> mShoeList;

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

        mShoeList.add(new ListItem(R.drawable.shoeone, "Line 1", "Line 2" ));
        mShoeList.add(new ListItem(R.drawable.shoestwo, "Line 3", "Line 4" ));
        mShoeList.add(new ListItem(R.drawable.shoesthree, "Line 5", "Line 6" ));
        mShoeList.add(new ListItem(R.drawable.shoesfour, "Line 5", "Line 6" ));
        mShoeList.add(new ListItem(R.drawable.shoesfive, "Line 5", "Line 6" ));
        mShoeList.add(new ListItem(R.drawable.shoessix, "Line 5", "Line 6" ));
        mShoeList.add(new ListItem(R.drawable.shoesseven, "Line 5", "Line 6" ));
        mShoeList.add(new ListItem(R.drawable.shoeseight, "Line 5", "Line 6" ));
        mShoeList.add(new ListItem(R.drawable.shoesnine, "Line 5", "Line 6" ));
        mShoeList.add(new ListItem(R.drawable.shoesten, "Line 5", "Line 6" ));
    }



    public void buildRecyclerView(){
        mRecyclerView_shoe = findViewById(R.id.recyclerView);
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
