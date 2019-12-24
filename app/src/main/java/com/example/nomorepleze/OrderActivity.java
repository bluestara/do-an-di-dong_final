package com.example.nomorepleze;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private void uploadOrder()
    {
        if (itemOrder.size() != 0)
        {
            String nodeID = databaseReference.push().getKey();
            order_DTO order_dto = new order_DTO();
            order_dto.address = mEditTextAddress.getText().toString().trim();
            order_dto.phonneNumber = mEditTextPhone.getText().toString().trim();
            order_dto.total = Double.parseDouble(mTextViewTotal.getText().toString().trim());
            order_dto.isOrderConfirm = false;
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null)
                order_dto.userName = user.getDisplayName();
            else
                order_dto.userName = "Anonymous";
            order_dto.items = itemOrder;
            databaseReference.child(nodeID).setValue(order_dto);
            Toast.makeText(this, "Order Success", Toast.LENGTH_LONG).show();
            finish();
        }
        else
            Toast.makeText(this, "There is nothing to order", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
}
