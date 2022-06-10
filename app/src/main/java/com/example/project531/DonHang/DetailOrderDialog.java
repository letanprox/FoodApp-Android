package com.example.project531.DonHang;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.project531.Activity.MainActivity;
import com.example.project531.Adapter.FoodListAdapterOrdered;
import com.example.project531.Domain.FoodItem;
import com.example.project531.Domain.OrderItem;
import com.example.project531.Interface.ImplementJson;
import com.example.project531.API.ParseURL;
import com.example.project531.R;
import com.example.project531.SignupActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DetailOrderDialog extends DialogFragment {

    private RecyclerView rcv_detail_order;
    private RecyclerView.Adapter adapter;

    private RequestQueue mQueue;
    ParseURL parseURL;
    OrderItem orderItem;
    TextView generatePDFbtn;

    int pageHeight = 1120;
    int pagewidth = 792;
    Bitmap bmp, scaledbmp;

    private static final int PERMISSION_REQUEST_CODE = 200;
    int count = 0;
    Bitmap bitmap;
    ImageView image;

    List<String> listurl = new ArrayList<>();
    List<Bitmap> bitmapList = new ArrayList<>();
    List<Bitmap> bitmapscle = new ArrayList<>();

    ArrayList<FoodItem> foodlist;
    ProgressDialog progressDialog;

    public DetailOrderDialog(OrderItem orderItem) {
        this.orderItem = orderItem;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_order, container,false);

        rcv_detail_order = view.findViewById(R.id.rcv_detail_order);
        mQueue = Volley.newRequestQueue(getContext());
        parseURL = new ParseURL(mQueue);
        recyclerViewListFood();

        generatePDFbtn = view.findViewById(R.id.generatePDFbtn);
        generatePDFbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(getContext());
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                new GetImageFromUrl().execute();
            }
        });
        return view;
    }


    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... url) {
            String stringUrl = listurl.get(count);
            bitmap = null;
            InputStream inputStream;
            try {
                inputStream = new java.net.URL(stringUrl).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);
            bitmapList.add(bitmap);
            count = count + 1;
            if(count == listurl.size()){
                for (int i = 0; i < bitmapList.size(); i++){
                    bitmapscle.add(Bitmap.createScaledBitmap(bitmapList.get(i), 140, 140, false));
                }
                generatePDF();
            }else{
                new GetImageFromUrl().execute();
            }
        }
    }

    private void recyclerViewListFood() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        rcv_detail_order.setLayoutManager(linearLayoutManager);
        foodlist = new ArrayList<>();

        parseURL.ParseData(MainActivity.connectURL + "/donhang_sanpham/list?id="+orderItem.getOrderNum(), new ImplementJson() {
            @Override
            public void Done(JSONArray jsonArray) {
                try{
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = null;
                        data = jsonArray.getJSONObject(i);

                        String TEN = data.getString("TEN");
                        String ANH = data.getString("ANH");
                        int SOLUONGBAN = data.getInt("SOLUONG");
                        double GIA = data.getDouble("GIA");

                        foodlist.add(new FoodItem(1,TEN, ANH, "", GIA, SOLUONGBAN, SOLUONGBAN));
                        listurl.add(ANH);
                    }
                    //Log.e("xxx", MainActivity.connectURL + "/donhang_sanpham/list?id="+orderItem.getOrderNum());
                    adapter = new FoodListAdapterOrdered(foodlist);
                    rcv_detail_order.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    private void generatePDF() {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint title = new Paint();
        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);
        Canvas canvas = myPage.getCanvas();
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        //IT START HERE:
        title.setTextSize(30);
        title.setColor(ContextCompat.getColor(getContext(), R.color.black));
        title.setTextAlign(Paint.Align.CENTER);

        canvas.drawText("Đơn hàng: "+orderItem.getOrderNum(), 396, 70,title);
        canvas.drawText("Ngày: " + orderItem.getDate(), 396, 120,title);
        canvas.drawText("Tổng tiền: " + orderItem.getPrice(), 396, 170,title);

        title.setTextAlign(Paint.Align.LEFT);
        for (int i = 0; i < foodlist.size(); i++){
            canvas.drawBitmap(bitmapscle.get(i), 60, 200 + i*180, paint);
            canvas.drawText("Tên: "+foodlist.get(i).getTitle(), 233, 230 + i*180,title);
            canvas.drawText("Giá: "+foodlist.get(i).getFee() + "  x"+foodlist.get(i).getNumberInCart(), 230, 300 + i*180,title);
        }

        //END HERE:
        pdfDocument.finishPage(myPage);
        File file = new File(Environment.getExternalStorageDirectory(), "GFG.pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(getContext(), "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("xxx",e.getMessage());
        }
        progressDialog.dismiss();
        pdfDocument.close();
    }
}