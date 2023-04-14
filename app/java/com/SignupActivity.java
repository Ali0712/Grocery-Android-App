package com.example.yourmart;

import static android.widget.Toast.makeText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourmart.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class SignupActivity extends AppCompatActivity {
    TextView btn2;
    Button signUp;
    EditText email, password, name;
    FirebaseAuth auth;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);
        auth= FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        btn2 = findViewById(R.id.backtologin_tv);
        signUp = findViewById(R.id.signup_btn);
        email = findViewById(R.id.reg_email);
        password = findViewById(R.id.reg_pwd);
        name = findViewById(R.id.reg_name);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();

            }

            private void createUser() {
                String Username = name.getText().toString();
                String Useremail = email.getText().toString();
                String Userpwd = password.getText().toString();
                if (TextUtils.isEmpty(Username)){
               Toast.makeText(SignupActivity.this, "Enter your Name",Toast.LENGTH_SHORT).show();
               return;
                }
                if (TextUtils.isEmpty(Useremail)){
               Toast.makeText(SignupActivity.this, "Enter your Email",Toast.LENGTH_SHORT).show();
                return;
                }

                if (TextUtils.isEmpty(Userpwd)){
               Toast.makeText(SignupActivity.this, "Enter your Password",Toast.LENGTH_SHORT).show();
                return;
                }

                if (Userpwd.length() < 4){
                    Toast.makeText(SignupActivity.this, "Password too short, must be greater than 4 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                //create User

                auth.createUserWithEmailAndPassword(Useremail, Userpwd)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    UserModel userModel = new UserModel(Username,Useremail, Userpwd);
                                    String id = task.getResult().getUser().getUid();
                                    database.getReference().child("Users").child(id).setValue(userModel);


                                    Toast.makeText(SignupActivity.this,"Your account has been created", Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    Toast.makeText(SignupActivity.this, "Error "+ task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}