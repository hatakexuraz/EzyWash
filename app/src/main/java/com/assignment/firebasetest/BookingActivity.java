package com.assignment.firebasetest;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

public class BookingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText txt_location;
    private EditText txt_location2;
    private EditText txt_postal;
    private EditText txt_city;
    private EditText txt_phone_num;

    private Button btn_cnf_booking;
    private DatabaseReference databaseReference;
    private Booking booking;
    private String time2, date;
        protected long maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        txt_location = findViewById(R.id.txt_address1);
        txt_location2 = findViewById(R.id.txt_address2);
        txt_postal = findViewById(R.id.txt_postal);
        txt_city = findViewById(R.id.txt_city);
        txt_phone_num = findViewById(R.id.txt_phone);

        booking = new Booking();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Booking"); //Creates a reference to the database with a child named "User"
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

        ImageView imageView;
        ImageView imageView1;
        imageView1 = findViewById(R.id.imageView3);
        imageView = findViewById(R.id.imageView);
        btn_cnf_booking = findViewById(R.id.btn_cnf_booking);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
        btn_cnf_booking.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Log.d("BookingActivity", "Date: "+date+" and Time: "+time2);

                booking.setUser(Global.email);
                booking.setLocation(txt_location.getText().toString());
                booking.setLocation2(txt_location2.getText().toString());
                booking.setPostal(txt_postal.getText().toString());
                booking.setCity(txt_city.getText().toString());
                booking.setPhone_num(txt_phone_num.getText().toString());
                booking.setWash_class(Global.class_wash);
                booking.setDate(date);
                booking.setTime(time2);

//                databaseReference.child(String.valueOf(maxid+1)).setValue(booking);
                databaseReference.push().setValue(booking);
                Global.booking_number= Math.toIntExact(maxid + 1);
                Global.booking_date = date;
                Global.booking_time=time2;
                Log.d("BookingActivity","Booking id : "+Global.booking_number);
                Log.d("BookingActivity","Booking date : "+Global.booking_date);
                Log.d("BookingActivity","Booking time : "+Global.booking_time);

//                databaseReference.push().setValue(booking);
                Toast.makeText(BookingActivity.this, "Booking successful.", Toast.LENGTH_LONG).show();

                txt_city.setText("");
                txt_location.setText("");
                txt_location2.setText("");
                txt_phone_num.setText("");
                txt_postal.setText("");

                Intent success = new Intent(v.getContext(), PaymentActivity.class);
                startActivity(success);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        this.date = currentDate;
        Toast.makeText(this, currentDate, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time ;
        time = hourOfDay+" : "+minute;
        time2 = time;
        Toast.makeText(this, time, Toast.LENGTH_LONG).show();
    }
}
