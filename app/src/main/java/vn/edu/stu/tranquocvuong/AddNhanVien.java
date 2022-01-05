package vn.edu.stu.tranquocvuong;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import vn.edu.stu.tranquocvuong.R;

public class AddNhanVien extends AppCompatActivity {

    Button btnChup, btnChonHinh, btnLuu, btnThoat;
    EditText txtTen, txtPhong, txtLoai, txtAdress;
    ImageView imgHinhAnh;
    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nhan_vien);

        addControls();
        addEvents();

    }

    private void addControls() {
        btnChup = findViewById(R.id.btnChup);
        btnChonHinh = findViewById(R.id.btnChonHinh);
        btnThoat = findViewById(R.id.btnThoat);
        btnLuu = findViewById(R.id.btnLuu);
        txtTen = findViewById(R.id.txtTen);
        txtPhong = findViewById(R.id.txtPhong);
        txtLoai = findViewById(R.id.txtLoai);
        txtAdress = findViewById(R.id.txtAdress);
        imgHinhAnh = findViewById(R.id.imgHinhAnh);
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
        btnChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePic();
            }
        });
        btnChup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePic();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themnv();
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thoat();
            }
        });
    }

    private void themnv() {
        String t = txtTen.getText().toString();
        String p = txtPhong.getText().toString();

        byte[] anh = getByte(imgHinhAnh);
        String l = txtLoai.getText().toString();
        String dc = txtAdress.getText().toString();

        ContentValues con = new ContentValues();
        con.put("ten_nv", t);
        con.put("ma_phong", p);
        con.put("img", anh);
        con.put("loai", l);
        con.put("dia_chi", dc);

        SQLiteDatabase db = database.initDatabase(
                this,
                "qlnv.sqlite");
        db.insert("nhanvien", null, con);
        Intent i = new Intent(this, QuanLyNhanVien.class);
        startActivity(i);
    }

    private void thoat() {
        Intent i = new Intent(this, QuanLyNhanVien.class);
        startActivity(i);
    }

    public byte[] getByte(ImageView imgv) {
        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bm = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void takePic() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, REQUEST_TAKE_PHOTO);
    }

    private void choosePic() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, REQUEST_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_PHOTO) {
                try {
                    Uri imgUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imgUri);
                    Bitmap bm = BitmapFactory.decodeStream(is);
                    imgHinhAnh.setImageBitmap(bm);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_TAKE_PHOTO) {
                Bitmap bm = (Bitmap) data.getExtras().get("data");
                imgHinhAnh.setImageBitmap(bm);
            }
        }
    }
}