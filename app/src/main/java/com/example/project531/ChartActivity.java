package com.example.project531;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.project531.API.ParseURL;
import com.example.project531.Activity.MainActivity;
import com.example.project531.DonHang.HistoryOrdersActivity;
import com.example.project531.Interface.ImplementJson;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ChartActivity extends AppCompatActivity {
    LineChart lineChart;

    private RequestQueue mQueue;
    ParseURL parseURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        mQueue = Volley.newRequestQueue(this);
        parseURL = new ParseURL(mQueue);

        ArrayList<Entry> datax = new ArrayList<Entry>();
        ArrayList<String> xAxisLabel = new ArrayList<>();

        Intent i = new Intent(this, HistoryOrdersActivity.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

        //GET DATA USER DON HANG:
        parseURL.ParseData(MainActivity.connectURL + "/api/user/donhang/chartdata", new ImplementJson() {
            @Override
            public void Done(JSONArray jsonArray) {
                try{
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = null;
                        data = jsonArray.getJSONObject(i);
                        datax.add(new Entry(i,data.getInt("SOLUONG")));
                        xAxisLabel.add(data.getString("NGAYDAT").replace("T00:00:00.000Z",""));
                        lineChart = findViewById(R.id.line_chart_phieunhap);

                        LineDataSet lineDataSet = new LineDataSet(datax, "Số đơn trên ngày");
                        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                        dataSets.add(lineDataSet);

                        LineData dataxx = new LineData(dataSets);
                        lineChart.setData(dataxx);
                        lineChart.invalidate();
                        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
                        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                        lineDataSet.setLineWidth(5);
                        lineDataSet.setCircleColors(Color.BLACK);
                        lineDataSet.setColor(Color.RED);
                    }
                    if(jsonArray.length() == 0){
                        Toast.makeText(getApplicationContext(), "Fail to Connect Database!!", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
//        lineChart = findViewById(R.id.line_chart_phieunhap);
//
//        LineDataSet lineDataSet = new LineDataSet(dataValuePhieuNhap(), "Số phiếu trên nhập ngày");
//        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//        dataSets.add(lineDataSet);
//
//        LineData data = new LineData(dataSets);
//        lineChart.setData(data);
//        lineChart.invalidate();
//
//        ArrayList<String> xAxisLabel = new ArrayList<>();
//        Cursor cursor = MainActivity.database.GetData("SELECT NgayTaoPhieu, COUNT(NgayTaoPhieu) AS tong FROM PhieuNhap GROUP BY NgayTaoPhieu ");
//        while (cursor.moveToNext()){
//            xAxisLabel.add(cursor.getString(0));
//        }
//        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
//
//        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//        lineDataSet.setLineWidth(5);
//        lineDataSet.setCircleColors(Color.BLACK);
//        lineDataSet.setColor(Color.RED);

    }
}