package vn.edu.stu.tranquocvuong;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import vn.edu.stu.tranquocvuong.R;
import vn.edu.stu.tranquocvuong.adapter.NhanVienAdapter;
import vn.edu.stu.tranquocvuong.model.NhanVien;

public class QuanLyNhanVien extends AppCompatActivity {


    final String DATABASE_NAME = "qlnv.sqlite";
    SQLiteDatabase db;
    ListView lView;
    ArrayList<NhanVien> list;
    NhanVienAdapter adapternv;
    Button btnThem, btnPhong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_nhan_vien);

        addControls();
        docDuLieu();
        addEvents();
    }

    private void addControls() {
        lView = findViewById(R.id.lView);
        list = new ArrayList<>();
        adapternv = new NhanVienAdapter(this, list);
        lView.setAdapter(adapternv);
        btnThem = findViewById(R.id.btnThem);
        btnPhong = findViewById(R.id.btnPhong);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itAbout:
                Intent i = new Intent(this, About.class);
                startActivity(i);
                return true;
            case R.id.itExit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void addEvents() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(QuanLyNhanVien.this, AddNhanVien.class);
                startActivity(i);
            }
        });

        btnPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuanLyNhanVien.this, QuanLyPhongBan.class);
                startActivity(i);
            }
        });
    }


    private void docDuLieu() {
        db = database.initDatabase(this, DATABASE_NAME);
        Cursor cur = db.rawQuery("SELECT * FROM nhanvien", null);
        list.clear();
        for (int i = 0; i < cur.getCount(); i++) {
            cur.moveToPosition(i);
            int id = cur.getInt(0);
            String ten = cur.getString(1);
            String phong = cur.getString(2);
            byte[] anh = cur.getBlob(3);
            String loai = cur.getString(4);
            String dc = cur.getString(5);
            list.add(new NhanVien(id, ten, phong, anh, loai, dc));
        }
        adapternv.notifyDataSetChanged();

    }
}