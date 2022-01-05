package vn.edu.stu.tranquocvuong;

import android.content.ContentValues;
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
import android.widget.EditText;

import vn.edu.stu.tranquocvuong.R;

public class EditPhongBan extends AppCompatActivity {

    final String DATABASE_NAME = "qlnv.sqlite";
    String id = "";

    Button btnLuuP, btnThoatP;
    EditText txtTenP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phong_ban);

        addControls();
        hienThi();
        addEvents();
    }

    private void addControls() {
        btnLuuP = findViewById(R.id.btnLuuP);
        btnThoatP = findViewById(R.id.btnThoatP);
        txtTenP = findViewById(R.id.txtTenP);
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
        btnLuuP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateP();
            }
        });
        btnThoatP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelP();
            }
        });
    }

    private void cancelP() {
        Intent i = new Intent(this, QuanLyPhongBan.class);
        startActivity(i);
    }

    private void updateP() {
        String t = txtTenP.getText().toString();
        ContentValues con = new ContentValues();
        con.put("ten_phong", t);
        SQLiteDatabase db = database.initDatabase(
                this,
                "qlnv.sqlite"
        );
        db.update("phong", con, "ma_phong = ?", new String[]{id});
        Intent i = new Intent(this, QuanLyPhongBan.class);
        startActivity(i);
    }

    private void hienThi() {
        Intent i = getIntent();
        id = i.getStringExtra("MAPHONG");
        SQLiteDatabase db = database.initDatabase(this, DATABASE_NAME);
        Cursor cur = db.rawQuery(
                "SELECT * FROM phong where ma_phong like ?",
                new String[]{id}
        );
        cur.moveToFirst();
        String ten = cur.getString(1);
        txtTenP.setText(ten);
    }
}