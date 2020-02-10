package com.assignment.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterUser extends AppCompatActivity {

    private EditText txt_first_name;
    private EditText txt_last_name;
    private EditText txt_phone;
    private EditText txt_email;
    private EditText txt_password;
    private EditText txt_date_of_birth;
    private EditText txt_gender;
    private Button btn_signup;
    private Button btn_validate;
    private DatabaseReference databaseReference;
    private User user;
    private ProgressBar progressBar;
    private TextView txt_messsage;

    protected long id=0;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);

        firebaseAuth = FirebaseAuth.getInstance();

        txt_first_name = findViewById(R.id.txt_fname);
        txt_last_name = findViewById(R.id.txt_lname);
        txt_phone = findViewById(R.id.txt_phone);
        txt_email = findViewById(R.id.txt_login_email);
        txt_password = findViewById(R.id.txt_password);
        txt_date_of_birth = findViewById(R.id.txt_bdate);
        btn_signup = findViewById(R.id.btn_sign_up);
        txt_gender = findViewById(R.id.txt_gender);
        btn_validate = findViewById(R.id.btn_next_validate);
        progressBar = findViewById(R.id.progressBar);
        txt_messsage = findViewById(R.id.please_wait);

        progressBar.setVisibility(View.GONE);
        txt_messsage.setVisibility(View.GONE);

        setVisibleGone();

        user = new User();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("User"); //Creates a reference to the database with a child named "User"

        //Get the last user count to later increase the uid
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    id = (dataSnapshot.getChildrenCount());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                txt_messsage.setVisibility(View.VISIBLE);

                if (verifyEmail(txt_email.getText().toString())){                   //first email's pattern is checked and user is created in database
                    firebaseAuth.createUserWithEmailAndPassword(txt_email.getText().toString(), txt_password.getText().toString()).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){           //checks is email is valid by sending verification link in to gmail account
                                        setVisible();               //make visible all the fields to store user's extra information

                                        btn_signup.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                progressBar.setVisibility(View.GONE);
                                                txt_messsage.setVisibility(View.GONE);

                                                user.setFirst_name(txt_first_name.getText().toString());
                                                user.setLast_name(txt_last_name.getText().toString());
                                                user.setPhone_num(Long.parseLong(txt_phone.getText().toString().trim()));
                                                user.setEmail(txt_email.getText().toString());
                                                user.setDob(txt_date_of_birth.getText().toString());
                                                user.setGender(txt_gender.getText().toString());
                                                //databaseReference.child(String.valueOf(id+1)).setValue(user); //create a auto increment id for the user

                                                firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()){//after the link is sent then user's extra information is stored in the database

                                                        }
                                                        else {
                                                            Toast.makeText(RegisterUser.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });

                                                databaseReference.push().setValue(user);
                                                Toast.makeText(RegisterUser.this, "Data inserted successfully.", Toast.LENGTH_LONG).show();

                                                Intent success = new Intent(v.getContext(), ActivitySuccessfully.class);
                                                startActivity(success);

                                                progressBar.setVisibility(View.GONE);
                                                txt_messsage.setVisibility(View.GONE);

                                                txt_first_name.setText("");
                                                txt_last_name.setText("");
                                                txt_email.setText("");
                                                txt_password.setText("");
                                                txt_date_of_birth.setText("");
                                                txt_gender.setText("");
                                                txt_phone.setText("");
                                            }
                                        });
//                                        Toast.makeText(RegisterUser.this, "User created successfully.", Toast.LENGTH_LONG).show();
//                                        Toast.makeText(RegisterUser.this, "Please check your email for verification.", Toast.LENGTH_LONG).show();

                                    }
                                    else {
                                        progressBar.setVisibility(View.GONE);
                                        txt_messsage.setVisibility(View.GONE);
                                        Toast.makeText(RegisterUser.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                else{
                    progressBar.setVisibility(View.GONE);
                    txt_messsage.setVisibility(View.GONE);
                    txt_email.requestFocus();
                    txt_email.setError("Please enter valid email address.");
                }
            }
        });
    }

    public void goToSignIn(View view) {
        Intent intent = new Intent(RegisterUser.this, LoginActivity.class);
        startActivity(intent);
    }

    private boolean verifyEmail(CharSequence target){
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private boolean verifyPass(CharSequence target){
        if (target == null) {
            return false;
        } else {
            return true;
        }
    }

    private void setVisibleGone(){
        txt_date_of_birth.setVisibility(View.GONE);
        txt_first_name.setVisibility(View.GONE);
        txt_gender.setVisibility(View.GONE);
        txt_last_name.setVisibility(View.GONE);
        txt_phone.setVisibility(View.GONE);
        btn_signup.setVisibility(View.GONE);

    }

    private void setVisible(){
        txt_email.setVisibility(View.GONE);
        txt_password.setVisibility(View.GONE);
        txt_date_of_birth.setVisibility(View.VISIBLE);
        txt_first_name.setVisibility(View.VISIBLE);
        txt_gender.setVisibility(View.VISIBLE);
        txt_last_name.setVisibility(View.VISIBLE);
        txt_phone.setVisibility(View.VISIBLE);
        btn_signup.setVisibility(View.VISIBLE);
        btn_validate.setVisibility(View.GONE);

        progressBar.setVisibility(View.GONE);
        txt_messsage.setVisibility(View.GONE);
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
