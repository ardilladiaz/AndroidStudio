package com.cinthiadiaz.entregamodulo4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button button3, button4;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    String Email = "";
    String Password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(login.this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCanceledOnTouchOutside(false);

        //Login
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarDatos();
            }
        });

        //Registrarse
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aquí deberías navegar a tu actividad de Registro
                // startActivity(new Intent(login.this, SignUp.class));
            }
        });
    }

    private void validarDatos() {
        Email = etEmail.getText().toString().trim();
        Password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(this, "Ingrese su Email", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            Toast.makeText(this, "Ingrese un email válido", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Password) || Password.length() < 6) {
            Toast.makeText(this, "La contraseña es incorrecta", Toast.LENGTH_SHORT).show();
        } else {
            loginUser();
        }
    }

    private void loginUser() {
        progressDialog.setMessage("Iniciando sesión...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(Email, Password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        progressDialog.dismiss();
                        startActivity(new Intent(login.this, HomeActivity.class));
                       finish();
                    }
                });

    }
}
