package com.assignment.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PaymentReceiptActivity extends AppCompatActivity {

    private TextView txt_booking_date;
    private TextView txt_booking_time;
    private TextView txt_class_wash;
    private Button btn_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_receipt);

        txt_booking_date = findViewById(R.id.lbl_booking_date);
        txt_booking_time = findViewById(R.id.lbl_booking_time);
        txt_class_wash = findViewById(R.id.lbl_class_wash);
        btn_close = findViewById(R.id.btn_goto_dashboard);

        txt_booking_date.setText("Date: "+Global.booking_date);
        txt_booking_time.setText("Time: "+Global.booking_time);
        txt_class_wash.setText(Global.class_wash+" SERVICE");

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dashboard2.class);
                startActivity(intent);
            }
        });
    }
}
