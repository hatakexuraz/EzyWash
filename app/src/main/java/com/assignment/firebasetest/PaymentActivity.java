package com.assignment.firebasetest;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    private BookingPayment payment;
    private DatabaseReference databaseReference;

    protected long maxid=0;

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

        payment = new BookingPayment();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Payment"); //Creates a reference to the database with a child named "User"
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    maxid=(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                //check if fields are filled
                checkEmpty();

                long card_num = Long.parseLong(txt_card_number.getText().toString());

                boolean valid2=false;
                try {
                    valid2 = validateDate(txt_card_expiry.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                boolean valid = ValidateCard.validitychk(card_num);
                if (valid==true){
                    Toast.makeText(getApplicationContext(), "The card is valid.", Toast.LENGTH_LONG).show();
                    if (valid2 == true){
                        Toast.makeText(getApplicationContext(), "The card date is valid.", Toast.LENGTH_LONG).show();

                        Log.d("PaymentActivity","Booking id : "+Global.booking_number);
                        payment.setBooking_id(Global.booking_number);
                        payment.setBill_user(Global.email);
                        payment.setDetail(Global.class_wash);
                        payment.setPrice(Global.price);

//                        databaseReference.child(String.valueOf(maxid+1)).setValue(payment);
                        databaseReference.push().setValue(payment);
                        Log.d("PaymentActivity", "Paid successfully");

                        txt_class_wash.setText("");
                        txt_price.setText("");
                        txt_card_type.setText("");
                        txt_card_number.setText("");
                        txt_card_name.setText("");
                        txt_card_expiry.setText("");
                        txt_card_cvv.setText("");
                        txt_bill_name.setText("");
                        txt_bill_postal.setText("");
                        txt_bill_city.setText("");
                        txt_bill_phone.setText("");
                        btn_pay.setText("");

                        new SendMail().execute("");
                        Log.d("PaymentActivity","Mail should be sent");

                        Intent intent = new Intent(getApplicationContext(), PaymentReceiptActivity.class);
                        startActivity(intent);
                    }
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
            Global.price = price;
        }
        else if (Global.class_wash.equals("CLASS B WASH")){
            price = "$150";
            Global.price = price;
        }
        else if (Global.class_wash.equals("CLASS C WASH")){
            price = "$100";
            Global.price = price;
        }
        return price;
    }

    private void checkEmpty(){
        Log.d("PaymentActivity", "Inside checkEmpty");
        if (txt_card_type.getText().toString().equals("")){
            txt_card_type.requestFocus();
            txt_card_type.setError("Please fill this field");
            Log.d("PaymentActivity", "Why is this happening??");
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
        Log.d("PaymentActivity", "Current Date: " +currentDate2.toString());

        if (date1.before(currentDate2)){
            txt_card_expiry.requestFocus();
            txt_card_expiry.setError("Card is expired");
            return false;
        }
        else{
            return true;
        }
    }


    private class SendMail extends AsyncTask<String, Integer, Void> {

//        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = ProgressDialog.show(MainActivity.this, "Please wait", "Sending mail", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            progressDialog.dismiss();
        }

        protected Void doInBackground(String... params) {
            Mail m = new Mail(Global.main_email, Global.main_password);

            String[] toArr = {Global.email, Global.main_email};
            m.setTo(toArr);
            m.setFrom(Global.main_email);
            m.setSubject("Car Wash Booking");
            m.setBody("EzyWash car wash booking service, \n\nYou just made a booking, following are the details on your booking.\n" +
                    "Booking id: "+Global.booking_number+"\nBooking Class: "+Global.class_wash+ "\nDate: "+Global.booking_date+
                    "\nTime: "+Global.booking_time+"\nPrice: "+Global.price+"\n\nBe sure to be available at the given location with your vehicle. " +
                    "You might also get a call when the staff arrives at the location. Thank you for choosing our service and please give us your " +
                    "feedback so that we can provide you better experience.");

            try {
                if(m.send()) {
                    Toast.makeText(PaymentActivity.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(PaymentActivity.this, "Email was not sent.", Toast.LENGTH_LONG).show();
                }
            } catch(Exception e) {
                Log.e("MailApp", "Could not send email", e);
            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
