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
import vn.edu.stu.tranquocvuong.adapter.PhongBanAdapter;
import vn.edu.stu.tranquocvuong.model.PhongBan;

public class QuanLyPhongBan extends AppCompatActivity {

    final String DATABASE_NAME = "qlnv.sqlite";
    SQLiteDatabase db;

    ListView lvPhong;
    ArrayList<PhongBan> listP;
    PhongBanAdapter adapterP;
    Button btnThemP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_phong_ban);

        addControls();
        docDuLieu();
        addEvents();
    }

    private void addControls() {
        lvPhong = findViewById(R.id.lvPhong);
        listP = new ArrayList<>();
        adapterP = new PhongBanAdapter(this, listP);
        lvPhong.setAdapter(adapterP);
        btnThemP = findViewById(R.id.btnThemP);
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
        btnThemP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuanLyPhongBan.this, AddPhongBan.class);
                startActivity(i);
            }
        });
    }

    private void docDuLieu() {
        db = database.initDatabase(this, DATABASE_NAME);
        Cursor cur = db.rawQuery("SELECT * FROM phong", null);
        listP.clear();
        for (int i = 0; i < cur.getCount(); i++) {
            cur.moveToPosition(i);
            String id = cur.getString(0);
            String ten = cur.getString(1);
            listP.add(new PhongBan(id, ten));

        }
        adapterP.notifyDataSetChanged();
    }

}