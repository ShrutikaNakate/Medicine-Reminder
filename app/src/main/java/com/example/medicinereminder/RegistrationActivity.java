package com.example.medicinereminder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    SQLiteDatabase db;
    EditText eu,ee,ep,ecp,epn,ed,ea;
    RadioButton rm,rf,ro;
    Button sb;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);

        eu=findViewById(R.id.usernm);
        ee=findViewById(R.id.email);
        ep=findViewById(R.id.password);
        ecp=findViewById(R.id.confirm_pass);
        epn=findViewById(R.id.phone);
        ed=findViewById(R.id.date_Birth);
        ea=findViewById(R.id.address);

        rm=findViewById(R.id.male);
        rf=findViewById(R.id.female);
        ro=findViewById(R.id.other);

        sb=findViewById(R.id.submit_btn);

        db=openOrCreateDatabase("medicare", Context.MODE_PRIVATE,null);
        db.execSQL("create table if not exists registration(Username varchar(50),Email varchar(90) primary key,Password varchar(20),Confirm_Password varchar(20),Phone_Number varchar(20),DOB varchar(10),Address varchar(100),Gender varchar(7))");

        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = eu.getText().toString();
                String email= ee.getText().toString();
                String password = ep.getText().toString();
                String confirm_password = ecp.getText().toString();
                String phone = epn.getText().toString();
                String dob = ed.getText().toString();
                String address = ea.getText().toString();
                String gender=" ";
                if(rm.isChecked())
                {
                    gender="Male";
                }
                else if(rf.isChecked()){
                    gender="Female";
                }
                if(ro.isChecked()){
                    gender="Other";
                }



                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirm_password.isEmpty() || phone.isEmpty() || dob.isEmpty() || address.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } 
                else if (!password.equals(confirm_password)) {
                    Toast.makeText(RegistrationActivity.this, "Password not match", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{

                        String insertQuery = "INSERT INTO registration VALUES('" + username + "','" + email + "','" + password + "','" + confirm_password + "','" + phone + "','" + dob + "','" + address + "','" + gender + "')";
                        db.execSQL(insertQuery);

                        eu.setText("");
                        ee.setText("");
                        ep.setText("");
                        ecp.setText("");
                        epn.setText("");
                        ed.setText("");
                        ea.setText("");
                        rm.setChecked(false);
                        rf.setChecked(false);
                        ro.setChecked(false);

                        Intent iHome=new Intent(RegistrationActivity.this,HomeScreen.class);
                        startActivity(iHome);
                    }
                    catch (Exception e) {
                        Toast.makeText(RegistrationActivity.this, "Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}