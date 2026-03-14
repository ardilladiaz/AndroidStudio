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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    EditText etNombre, etApellido, etEmail, etPassword, etPassword2;
    Button button3, button2;
    
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    String Nombre = "";
    String Apellido = "";
    String Email = "";
    String Password = "";
    String Password2 = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPassword2 = findViewById(R.id.etPassword2);
        button3 = findViewById(R.id.button3);
        button2 = findViewById(R.id.button2);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCanceledOnTouchOutside(false);
        
        //Crear cuenta
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarDatos();
            }
        });

        //Login
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, login.class));
            }
        });
    }

    private void validarDatos() {
        Nombre = etNombre.getText().toString().trim();
        Apellido = etApellido.getText().toString().trim();
        Email = etEmail.getText().toString().trim();
        Password = etPassword.getText().toString().trim();
        Password2 = etPassword2.getText().toString().trim();
        
        if (TextUtils.isEmpty(Nombre)) {
            Toast.makeText(this, "Ingrese su Nombre", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Apellido)) {
            Toast.makeText(this, "Ingrese su Apellido", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            Toast.makeText(this, "Ingrese un email válido", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Password) || Password.length() < 6) {
            Toast.makeText(this, "Ingrese su contraseña de al menos 6 caracteres", Toast.LENGTH_SHORT).show();
        } else if (!Password.equals(Password2)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        } else {
            crearCuenta();
        }
    }

    private void crearCuenta() {
        progressDialog.setMessage("Creando cuenta...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        guardarUsuario();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(SignUp.this, "Ocurrió un problema: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void guardarUsuario() {
        progressDialog.setMessage("Guardando usuario...");
        progressDialog.show();
        
        String uid = firebaseAuth.getUid();
        
        HashMap<String, String> datosUsuario = new HashMap<>();
        datosUsuario.put("uid", uid);
        datosUsuario.put("Nombre_usuario", Nombre);
        datosUsuario.put("Apellido_usuario", Apellido);
        datosUsuario.put("Email_usuario", Email);
        datosUsuario.put("Password_usuario", Password);
        datosUsuario.put("Password2_usuario", Password2);
    
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");
        databaseReference.child(uid).setValue(datosUsuario)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(SignUp.this, "Cuenta creada", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp.this, HomeActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(SignUp.this, "Ocurrió un problema al guardar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
