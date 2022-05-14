package com.example.project531;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.project531.Activity.MainActivity;
import com.example.project531.Adapter.FoodListAdapter;
import com.example.project531.Adapter.OrdersAdapter;
import com.example.project531.Domain.FoodItem;
import com.example.project531.Domain.OrderItem;

import java.util.ArrayList;

public class HistoryOrdersActivity extends AppCompatActivity {


    private RecyclerView rcv_order_list;
    private RecyclerView.Adapter adapter;

    DetailOrderDialog detailOrderDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_orders);

        Intent i = new Intent(this, MainActivity.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

        rcv_order_list = findViewById(R.id.rcv_order_list);
        recyclerViewListOrder();
    }



    private void recyclerViewListOrder() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rcv_order_list.setLayoutManager(linearLayoutManager);

        ArrayList<OrderItem> orderlist = new ArrayList<>();
        orderlist.add(new OrderItem(1, "30/11/2000",  13.23, "Dang giao"));
        orderlist.add(new OrderItem(2, "30/12/2000",  13.23, "Dang giao"));
        orderlist.add(new OrderItem(3, "30/12/2000",  13.23, "Hoan tat"));

        adapter = new OrdersAdapter(orderlist, new IClickOrder() {
            @Override
            public void clickItem(OrderItem orderItem) {
                detailOrderDialog = new DetailOrderDialog();
                detailOrderDialog.show(getSupportFragmentManager(),"DIALOGDETAIL");
            }
        });
        rcv_order_list.setAdapter(adapter);
    }

}