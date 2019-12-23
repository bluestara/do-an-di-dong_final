package com.example.nomorepleze;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    private RecyclerView mRecycleViewItem;
    private EditText mEditTextPhone;
    private EditText mEditTextAddress;
    private TextView mTextViewTotal;
    private Button mButtonOrder;
    private OrderAdapter mAdapter;
    ArrayList<item> itemOrder;

    private DatabaseReference databaseReference;
    private UploadTask mUploadTask;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        mEditTextAddress = findViewById(R.id.edit_text_order_address);
        mEditTextPhone = findViewById(R.id.edit_text_order_phone);
        mTextViewTotal = findViewById(R.id.text_view_total_money);
        mRecycleViewItem = findViewById(R.id.recyclerView_item_order);
        mRecycleViewItem.setHasFixedSize(true);
        mRecycleViewItem.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
        mButtonOrder = findViewById(R.id.button_order);
        itemOrder = new ArrayList<>();

        //Get order
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) itemOrder = bundle.getParcelableArrayList("ITEM_ORDER");

        //Load order's item into Recycle View
        mAdapter = new OrderAdapter(itemOrder);
        mRecycleViewItem.setAdapter(mAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Order");
        mButtonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadOrder();
            }
        });

        double total = 0;
        for (int i = 0; i < itemOrder.size(); i++)
            total += itemOrder.get(i).Price;
        mTextViewTotal.setText(String.valueOf(total));
    }
    private void uploadOrder()
    {

    }
}
