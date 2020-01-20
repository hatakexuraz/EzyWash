package com.assignment.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity {

    private EditText txt_first_name;
    private EditText txt_last_name;
    private EditText txt_phone;
    private EditText txt_email;
    private EditText txt_password;
    private EditText txt_date_of_birth;
    private EditText txt_gender;
    private Button btn_signup;
    private String gender;
    private DatabaseReference databaseReference;
    private User user;

    private long id=0;

    private Spinner spinner;
    private static final String[] items = {"Male", "Female", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);

        txt_first_name = findViewById(R.id.txt_fname);
        txt_last_name = findViewById(R.id.txt_lname);
        txt_phone = findViewById(R.id.txt_phone);
        txt_email = findViewById(R.id.txt_login_email);
        txt_password = findViewById(R.id.txt_password);
        spinner = findViewById(R.id.select_gender);
        txt_date_of_birth = findViewById(R.id.txt_bdate);
        btn_signup = findViewById(R.id.btn_sign_up);
        txt_gender = findViewById(R.id.txt_gender);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterUser.this,
                android.R.layout.simple_spinner_item,items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        user = new User();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");

        //Get the last user count to later increase the uid
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    id = (dataSnapshot.getChildrenCount());
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onSpinnerItemSelected();

                Log.d("TAG", "Gender: "+gender);

                user.setFirst_name(txt_first_name.getText().toString());
                user.setLast_name(txt_last_name.getText().toString());
                user.setPhone_num(Long.parseLong(txt_phone.getText().toString().trim()));
                user.setEmail(txt_email.getText().toString());
                user.setPassword(txt_password.getText().toString());
                user.setDob(txt_date_of_birth.getText().toString());
                user.setGender(txt_gender.getText().toString());
                //databaseReference.child(String.valueOf(id+1)).setValue(user); //create a auto increment id for the user

                databaseReference.push().setValue(user);
                Toast.makeText(RegisterUser.this, "Data inserted successfully.", Toast.LENGTH_LONG);

                txt_first_name.setText("");
                txt_last_name.setText("");
                txt_email.setText("");
                txt_password.setText("");
                txt_date_of_birth.setText("");
                txt_gender.setText("");
                txt_phone.setText("");

                Intent success = new Intent(v.getContext(), ActivitySuccessfully.class);
                startActivity(success);

            }
        });
    }

    public void goToSignIn(View view) {
        Intent intent = new Intent();
        startActivity(intent);
    }

//    private void onSpinnerItemSelected(){
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                gender = (String) parent.getItemAtPosition(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }
}
