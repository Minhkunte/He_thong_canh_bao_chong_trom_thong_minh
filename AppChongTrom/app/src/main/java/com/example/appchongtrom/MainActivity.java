package com.example.appchongtrom;

import android.app.Notification;
import android.content.DialogInterface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity {

    Button bat_he_thong, tat_he_thong, tat_coi, thoat_btn;
    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Ánh xạ các nút bấm tới các biến
        bat_he_thong = findViewById(R.id.btnbathethong);
        tat_he_thong = findViewById(R.id.btntathethong);
        tat_coi = findViewById(R.id.btnTatcoi);
        thoat_btn = findViewById(R.id.btnThoat);
        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("He thong chong trom");

        ref.child("Còi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String value = snapshot.getValue(String.class);
                    // Xử lý giá trị nhận được
                    ImageView warning = findViewById(R.id.ImgCanhbao);
                    TextView text_warning = findViewById(R.id.textCanhbao);
                    if(value.equals("ON")) {
                        warning.setVisibility(View.VISIBLE);
                        text_warning.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "ON", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        warning.setVisibility(View.GONE);
                        text_warning.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "OFF", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Xử lý khi xảy ra lỗi
            }
        });

        bat_he_thong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child("HeThong").setValue("ON");
                Toast.makeText(MainActivity.this, "ON", Toast.LENGTH_SHORT).show();
            }
        });

        tat_he_thong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child("HeThong").setValue("OFF");
                Toast.makeText(MainActivity.this, "OFF", Toast.LENGTH_SHORT).show();
            }
        });

        tat_coi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child("Còi").setValue("OFF");
            }
        });

        thoat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
                builder.setTitle("Bạn có chắc muốn trở về trang đăng nhập?");
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
    }
}

