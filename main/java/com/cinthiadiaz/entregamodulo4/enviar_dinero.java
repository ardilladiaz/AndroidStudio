package com.cinthiadiaz.entregamodulo4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType; // Necesario para el teclado numérico
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class enviar_dinero extends AppCompatActivity {

    EditText etMonto;
    Button button3, button6;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enviar_dinero);

        etMonto = findViewById(R.id.etMonto);
        button3 = findViewById(R.id.button3);
        button6 = findViewById(R.id.button6);

        etMonto.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        progressDialog = new ProgressDialog(enviar_dinero.this);
        progressDialog.setMessage("Enviando...");
        progressDialog.setCanceledOnTouchOutside(false);

        // Enviar dinero
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                validarYEnviar();
            }
        });

        // Ir atrás
        button6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void validarYEnviar() {
        String montoStr = etMonto.getText().toString();

        if (montoStr.isEmpty()) {
            etMonto.setError("Ingrese el monto a enviar");
            return;
        }

        try {
            double monto = Double.parseDouble(montoStr);

            if (monto <= 0) {
                etMonto.setError("El monto debe ser mayor a 0");
                return;
            }

            progressDialog.show();

            // Ir atrás
            Intent intent = new Intent(enviar_dinero.this, HomeActivity.class);
            startActivity(intent);
            finish();

        } catch (NumberFormatException e) {
            etMonto.setError("Número inválido");
        }
    }
}