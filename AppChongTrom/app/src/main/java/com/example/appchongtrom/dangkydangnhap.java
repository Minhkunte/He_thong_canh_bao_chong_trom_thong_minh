package com.example.appchongtrom;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.service.controls.templates.ControlButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class dangkydangnhap extends AppCompatActivity {
    EditText edtUser, edtPassword;
    Button btndangky, btndangnhap, btnthoat;
    String ten, mk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangkydangnhap);
        Anhxa();
        ControlButton();
    }
    private void ControlButton(){
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(dangkydangnhap.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
                builder.setTitle("Bạn có chắc muốn thoát khỏi app?");
                builder.setIcon(android.R.drawable.ic_lock_idle_alarm);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(dangkydangnhap.this);
                dialog.setTitle("Hộp thoại xử lý");
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.customdialog);
                EditText edttk = (EditText) dialog.findViewById(R.id.edttk);
                EditText edtmk = (EditText) dialog.findViewById(R.id.edtmk);
                Button btnhuy = (Button)  dialog.findViewById(R.id.btnhuy);
                Button btndongy = (Button) dialog.findViewById(R.id.btndongy);
                btndongy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ten = edttk.getText().toString().trim();
                        mk = edtmk.getText().toString().trim();

                        edtUser.setText(ten);
                        edtPassword.setText(mk);

                        dialog.cancel();
                    }
                });
                btnhuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtUser.getText().length() != 0 && edtPassword.getText().length() != 0){
                    if(edtUser.getText().toString().equals(ten) && edtPassword.getText().toString().equals(mk)) {
                        Toast.makeText(dangkydangnhap.this,"Bạn đã đăng nhập thành công",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(dangkydangnhap.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else if(edtUser.getText().toString().equals("Minhkute0809") && edtPassword.getText().toString().equals("12345")){
                        Toast.makeText(dangkydangnhap.this,"Bạn đã đăng nhập thành công",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(dangkydangnhap.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(dangkydangnhap.this,"Bạn đã đăng nhập thất bại",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(dangkydangnhap.this,"Mời bạn nhập đủ thông tin",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void Anhxa(){
        edtUser = (EditText) findViewById(R.id.Tendangnhap);
        edtPassword = (EditText) findViewById(R.id.matkhau);
        btndangky = (Button) findViewById(R.id.btndangky);
        btndangnhap = (Button) findViewById(R.id.btndangnhap);
        btnthoat = (Button) findViewById(R.id.btnthoat);
    }
}