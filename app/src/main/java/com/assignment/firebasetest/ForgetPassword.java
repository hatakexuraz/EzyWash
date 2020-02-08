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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ForgetPassword extends AppCompatActivity {

    Button btn_next;
    EditText txt_email;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private TextView txt_messsage;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        firebaseAuth = FirebaseAuth.getInstance();

        btn_next = findViewById(R.id.btn_change);
        txt_email = findViewById(R.id.txt_login_email2);
        progressBar = findViewById(R.id.progressBar);
        txt_messsage = findViewById(R.id.please_wait);

        progressBar.setVisibility(View.GONE);
        txt_messsage.setVisibility(View.GONE);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = txt_email.getText().toString();
                if (!email.isEmpty()){
                    progressBar.setVisibility(View.VISIBLE);
                    txt_messsage.setVisibility(View.VISIBLE);

                    Log.d("ForgetPassword","Email: "+email);

                    databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
                    final Query query = databaseReference.orderByChild("email").equalTo(email);   //checks email in the firebase
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {
                                Global.uid = databaseReference.push().getKey();
                                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            progressBar.setVisibility(View.GONE);
                                            txt_messsage.setVisibility(View.GONE);
                                            Toast.makeText(ForgetPassword.this, "Link has been sent to your account. " +
                                                    "\n Please check your email to reset your password.", Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            Toast.makeText(ForgetPassword.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                            else {
                                txt_email.setText("");
                                txt_email.setError("Email does not exist");
                                txt_email.requestFocus();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            throw databaseError.toException();
                        }
                    });
                }
                else {
                    Log.d("ForgetPassword","Email: "+email);
                    txt_email.setError("Please fill up this field");
                    txt_email.requestFocus();
                }

            }
        });
    }
}
