package com.assignment.firebasetest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PaymentActivity extends AppCompatActivity {

    private TextView txt_class_wash;
    private TextView txt_price;

    private EditText txt_card_type;
    private EditText txt_card_number;
    private EditText txt_card_name;
    private EditText txt_card_expiry;
    private EditText txt_card_cvv;
    private EditText txt_bill_name;
    private EditText txt_bill_postal;
    private EditText txt_bill_city;
    private EditText txt_bill_phone;
    private Button btn_pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        txt_class_wash = findViewById(R.id.txt_class_wash);
        txt_price = findViewById(R.id.txt_price);

        txt_card_type = findViewById(R.id.txt_card_type);
        txt_card_number = findViewById(R.id.txt_card_num);
        txt_card_name = findViewById(R.id.txt_card_name);
        txt_card_expiry = findViewById(R.id.txt_expiry_date);
        txt_card_cvv = findViewById(R.id.txt_cvv);
        txt_bill_name = findViewById(R.id.txt_full_name);
        txt_bill_postal = findViewById(R.id.txt_postal2);
        txt_bill_city = findViewById(R.id.txt_city2);
        txt_bill_phone = findViewById(R.id.txt_phone);
        btn_pay = findViewById(R.id.btn_pay_now);

        txt_class_wash.setText(Global.class_wash);
        txt_price.setText(setPrice());

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {txt_card_type.requestFocus();
                txt_card_type.setError("Please fill this field");
                //check if fields are filled
                checkEmpty();

                long card_num = Long.parseLong(txt_card_number.getText().toString());

                try {
                    boolean valid = validateDate(txt_card_expiry.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                boolean valid = ValidateCard.validitychk(card_num);
                if (valid){
                    Toast.makeText(getApplicationContext(), "The card is valid.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "The card is not valid.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private String setPrice(){
        String price = "$";
        if(Global.class_wash.equals("CLASS A WASH")){
            price = "$290";
        }
        else if (Global.class_wash.equals("CLASS B WASH")){
            price = "$150";
        }
        else if (Global.class_wash.equals("CLASS C WASH")){
            price = "$100";
        }
        return price;
    }

    private void checkEmpty(){
        Log.d("PaymentActivity", "Inside checkEmpty");
        if (txt_card_type.getText().toString().equals("")){
            txt_card_type.requestFocus();
            txt_card_type.setError("Please fill this field");
        }
        else if (txt_card_number.getText().toString().equals("")){
            txt_card_number.requestFocus();
            txt_card_number.setError("Please fill this field");
        }
        else if (txt_card_name.getText().toString().equals("")){
            txt_card_name.requestFocus();
            txt_card_name.setError("Please fill this field");
        }
        else if (txt_card_expiry.getText().toString().equals("")){
            txt_card_expiry.requestFocus();
            txt_card_expiry.setError("Please fill this field");
        }
        else if (txt_card_cvv.getText().toString().equals("")){
            txt_card_cvv.requestFocus();
            txt_card_cvv.setError("Please fill this field");
        }
        else if (txt_bill_name.getText().toString().equals("")){
            txt_bill_name.requestFocus();
            txt_bill_name.setError("Please fill this field");
        }
        else if (txt_bill_postal.getText().toString().equals("")){
            txt_bill_postal.requestFocus();
            txt_bill_postal.setError("Please fill this field");
        }
        else if (txt_bill_city.getText().toString().equals("")){
            txt_bill_city.requestFocus();
            txt_bill_city.setError("Please fill this field");
        }
        else if (txt_bill_phone.getText().toString().equals("")){
            txt_bill_phone.requestFocus();
            txt_bill_phone.setError("Please fill this field");
        }
        else {
            Log.d("PaymentActivity", "Everything is fine");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean validateDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
        Date date1 = sdf.parse(date);
        Log.d("PaymentActivity", " Date is " +date1.toString());

        //getting current date month and year
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/yy");
        LocalDateTime now = LocalDateTime.now();
        String currentDate = dtf.format(now);
        Date currentDate2 = sdf.parse(currentDate);
        Log.d("PaymentActivity", "blabla: " +currentDate2.toString());

        if (date1.before(currentDate2)){
            txt_card_expiry.requestFocus();
            txt_card_expiry.setError("Card is expired");
            return false;
        }
        else{
            return true;
        }
    }
}
