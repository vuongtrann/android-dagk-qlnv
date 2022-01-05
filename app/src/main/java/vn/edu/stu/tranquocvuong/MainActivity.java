package vn.edu.stu.tranquocvuong;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import vn.edu.stu.tranquocvuong.R;

public class MainActivity extends AppCompatActivity {

    EditText txtUser, txtPass;
    Button btnDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
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

    private void addControls() {
        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
        btnDangNhap = findViewById(R.id.btnDangNhap);
    }

    private void addEvents() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userN = "admin";
                String passW = "123456";

                String user = txtUser.getText().toString();
                String pass = txtPass.getText().toString();

                if (user.equals(userN) && pass.equals(passW)) {

                    Intent intent = new Intent(
                            MainActivity.this,
                            QuanLyNhanVien.class
                    );
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Sai Mật Khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}