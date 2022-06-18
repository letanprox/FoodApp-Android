package com.example.project531.DonHang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.project531.Activity.MainActivity;
import com.example.project531.Adapter.OrdersAdapter;
import com.example.project531.ChartActivity;
import com.example.project531.Domain.OrderItem;
import com.example.project531.Interface.IClickOrder;
import com.example.project531.Interface.ImplementJson;
import com.example.project531.API.ParseURL;
import com.example.project531.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryOrdersActivity extends AppCompatActivity {

    private RecyclerView rcv_order_list;
    private RecyclerView.Adapter adapter;
    DetailOrderDialog detailOrderDialog;
    private RequestQueue mQueue;
    ParseURL parseURL;
    LinearLayout chart_button;

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

        mQueue = Volley.newRequestQueue(this);
        parseURL = new ParseURL(mQueue);

        Intent ixx = new Intent(this, ChartActivity.class);
        chart_button = findViewById(R.id.chart_button);
        chart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ixx);
            }
        });
        rcv_order_list = findViewById(R.id.rcv_order_list);
        recyclerViewListOrder();
    }



    ///API GET LIST DON HANG:
    private void recyclerViewListOrder() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcv_order_list.setLayoutManager(linearLayoutManager);
        ArrayList<OrderItem> orderlist = new ArrayList<>();

        parseURL.ParseData(MainActivity.connectURL+"/api/user/donhang/list", new ImplementJson() {
            @Override
            public void Done(JSONArray jsonArray) {
                try{
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = null;
                        data = jsonArray.getJSONObject(i);

                        int ID = data.getInt("ID");
                        String NGAYDAT = data.getString("NGAYDAT");
                        NGAYDAT = NGAYDAT.replace("T00:00:00.000Z","");
                        Double GIA = data.getDouble("GIA");
                        String TRANGTHAI = data.getString("TRANGTHAI");

                        if(TRANGTHAI.equals("N")){
                            TRANGTHAI = "Đang Giao";
                        }else{
                            TRANGTHAI = "Hoàn Tất";
                        }
                        orderlist.add(new OrderItem(ID, NGAYDAT,  GIA, TRANGTHAI));
                    }
                    adapter = new OrdersAdapter(orderlist, new IClickOrder() {
                        @Override
                        public void clickItem(OrderItem orderItem) {
                            detailOrderDialog = new DetailOrderDialog(orderItem);
                            detailOrderDialog.show(getSupportFragmentManager(),"DIALOGDETAIL");
                        }
                    });
                    rcv_order_list.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}