package com.example.staffscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String loggedInName = username.getText().toString();
                if(username.getText().toString().equals("user") && password.getText().toString().equals("user")){
                    Intent in = new Intent(MainActivity.this, Dashboard.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("uname",loggedInName);
                    in.putExtras(bundle);
                    startActivity(in);
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Wrong username/password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}