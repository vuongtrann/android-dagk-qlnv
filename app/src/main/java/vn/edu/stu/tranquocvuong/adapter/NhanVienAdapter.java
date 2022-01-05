package vn.edu.stu.tranquocvuong.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vn.edu.stu.tranquocvuong.EditNhanVien;
import vn.edu.stu.tranquocvuong.R;
import vn.edu.stu.tranquocvuong.database;
import vn.edu.stu.tranquocvuong.model.NhanVien;

public class NhanVienAdapter extends BaseAdapter {

    Activity context;
    ArrayList<NhanVien> list;

    public NhanVienAdapter(Activity context, ArrayList<NhanVien> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_nhan_vien_adapter, null);
        ImageView imgDaiDien = row.findViewById(R.id.imgDaiDien);
        TextView txtID = row.findViewById(R.id.txtID);
        TextView txtTen = row.findViewById(R.id.txtTen);
        TextView txtPhong = row.findViewById(R.id.txtPhong);
        Button btnXoaNV = row.findViewById(R.id.btnXoaNV);
        Button btnSua = row.findViewById(R.id.btnSua);

        NhanVien nv = list.get(position);
        txtID.setText(nv.id + "");
        txtTen.setText(nv.ten);
        txtPhong.setText(nv.maPhong);

        Bitmap bm = BitmapFactory.decodeByteArray(nv.anh, 0, nv.anh.length);
        imgDaiDien.setImageBitmap(bm);

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditNhanVien.class);
                i.putExtra("ID", nv.id);
                context.startActivity(i);
            }
        });
        btnXoaNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle("Xác Nhận Xóa");
                builder.setMessage("Bạn có chắc muốn xóa thằng này không hả?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        xoanv(nv.id);
                    }


                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return row;
    }

    private void xoanv(int idnv) {
        SQLiteDatabase db = database.initDatabase(context, "qlnv.sqlite");
        db.delete("nhanvien", "id_nv = ? ", new String[]{idnv + ""});
        list.clear();
        Cursor cur = db.rawQuery("SELECT * FROM nhanvien", null);
        while (cur.moveToNext()) {
            int id = cur.getInt(0);
            String ten = cur.getString(1);
            String maPhong = cur.getString(2);
            byte[] anh = cur.getBlob(3);
            String loai = cur.getString(4);
            String dc = cur.getString(5);

            list.add(new NhanVien(id, ten, maPhong, anh, loai, dc));

        }
        notifyDataSetChanged();
    }
}