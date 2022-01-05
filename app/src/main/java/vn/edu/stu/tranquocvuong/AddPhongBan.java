package vn.edu.stu.tranquocvuong;

import android.content.ContentValues;
import android.content.Intent;
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

public class AddPhongBan extends AppCompatActivity {

    final String DATABASE_NAME = "qlnv.sqlite";

    Button btnLuuP, btnThoatP;
    EditText txtMa, txtTen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phong_ban);

        addControls();
        addEvents();
    }

    private void addControls() {
        btnLuuP = findViewById(R.id.btnLuuP);
        btnThoatP = findViewById(R.id.btnThoatP);
        txtMa = findViewById(R.id.txtMa);
        txtTen = findViewById(R.id.txtTen);
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
                them();
            }
        });
        btnThoatP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thoat();
            }
        });
    }

    private void them() {
        String ma = txtMa.getText().toString();
        String ten = txtTen.getText().toString();

        ContentValues con = new ContentValues();
        con.put("ma_phong", ma);
        con.put("ten_phong", ten);

        SQLiteDatabase db = database.initDatabase(
                this,
                "qlnv.sqlite"
        );
        db.insert("phong", null, con);
        Intent i = new Intent(this, QuanLyPhongBan.class);
        startActivity(i);
    }

    private void thoat() {
        Intent i = new Intent(this, QuanLyPhongBan.class);
        startActivity(i);
    }
}