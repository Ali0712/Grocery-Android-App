package com.example.yourmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    TextView btn, showpss;
    Button login;
    EditText email, pwd;
    FirebaseAuth auth;



//    public void ShowHidePass(View view){
//
//        if(view.getId()==R.id.show_pass_btn){
//
//            if(pwd.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
////                ((TextView(view)).setImageResource(R.drawable.show_pass);
//
//                //Show Password
//                pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//            }
//            else{
////                ((ImageView)(view)).setImageResource(R.drawable.show_password);
//
//                //Hide Password
//                pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
//
//            }
//        }
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        btn = findViewById(R.id.view_Signup);
        login = findViewById(R.id.login_btn);
        email = findViewById(R.id.login_email);
        pwd = findViewById(R.id.login_pwd);
        showpss =findViewById(R.id.show_pwd);
        auth = FirebaseAuth.getInstance();
        showpss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pwd.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
//                ((TextView(view)).setImageResource(R.drawable.show_pass);

                //Show Password
                pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
//                ((ImageView)(view)).setImageResource(R.drawable.show_password);

                //Hide Password
                pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }




            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();

            }

            private void loginUser() {
                String Useremail = email.getText().toString();
                String Userpwd = pwd.getText().toString();
                if (TextUtils.isEmpty(Useremail)){
                    Toast.makeText(LoginActivity.this, "Enter your Email",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(Userpwd)){
                    Toast.makeText(LoginActivity.this, "Enter your Password",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Userpwd.length() < 6){
                    Toast.makeText(LoginActivity.this, "Password too short, must be greater than 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                    }
                //login User
                auth.signInWithEmailAndPassword(Useremail, Userpwd)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(LoginActivity.this, "Error "+task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}