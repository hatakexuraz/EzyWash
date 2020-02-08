package com.assignment.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FeedbackActivity extends AppCompatActivity {

    Button btn_done;
    EditText txt_feedback;
    Feedback feedback;
    DatabaseReference databaseReference;
    long id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedback = new Feedback();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Feedback");

        btn_done = findViewById(R.id.button_done);
        txt_feedback = findViewById(R.id.editText2);

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

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txt_feedback.getText().toString().equals("")){
                    txt_feedback.requestFocus();
                    txt_feedback.setError("Do not leave the field empty");
                }
                else {
                    feedback.setUser(Global.email);
                    feedback.setFeedback(txt_feedback.getText().toString());

                    databaseReference.push().setValue(feedback);
                    Toast.makeText(FeedbackActivity.this, "Your response have been saved.", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(), Dashboard2.class);
                    startActivity(intent);
                }
            }
        });
    }
}
