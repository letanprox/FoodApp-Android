package com.example.project531;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.example.project531.Activity.MainActivity;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public long INSERT_USER(int id,String ten, String anh,String sdt, String matkhau, String email){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Userx VALUES("+id+", ?, ?, ?, ?, ?)";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.clearBindings();

        sqLiteStatement.bindString(1, ten);
        sqLiteStatement.bindString(2, anh);
        sqLiteStatement.bindString(3, sdt);
        sqLiteStatement.bindString(4, matkhau);
        sqLiteStatement.bindString(5, email);

        return  sqLiteStatement.executeInsert();
    }



    public long UPDATE_USER(String ten, String anh,String sdt, String email){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE Userx SET " +
                "TEN = ? ," +
                "ANH = ?," +
                "SDT = ? ," +
                "EMAIL = ? " +
                "WHERE ID = " + MainActivity.ID_USER;
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.clearBindings();

        sqLiteStatement.bindString(1, ten);
        sqLiteStatement.bindString(2, anh);
        sqLiteStatement.bindString(3, sdt);
        sqLiteStatement.bindString(4, email);

        return  sqLiteStatement.executeInsert();
    }
//
//
//    public int UPDATE_SANPHAM(int ma,String ten, String donvi,int soluong, String giatien, byte[] hinhanh){
//        SQLiteDatabase database = getWritableDatabase();
//        String sql = "UPDATE SanPham SET " +
//                "TenSanPham = ? ," +
//                "DonVi = ?," +
//                "Soluong = 0 ," +
//                "GiaTien = ? ," +
//                "HinhAnh = ? " +
//                "WHERE MaSanPham = " + ma ;
//        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
//        sqLiteStatement.clearBindings();
//
//        sqLiteStatement.bindString(1, ten);
//        sqLiteStatement.bindString(2, donvi);
//        sqLiteStatement.bindString(3, giatien);
//        sqLiteStatement.bindBlob(4, hinhanh);
//        return  sqLiteStatement.executeUpdateDelete();
//    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
