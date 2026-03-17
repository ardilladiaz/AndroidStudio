package com.cinthiadiaz.entregamodulo4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class IngresarDinero extends AppCompatActivity {

    EditText etMonto;
    Button button3, button6;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ingresar_dinero);

        etMonto = findViewById(R.id.etMonto);
        button3 = findViewById(R.id.button3);
        button6 = findViewById(R.id.button6); // Agregado para que no de error

        etMonto.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        progressDialog = new ProgressDialog(IngresarDinero.this);
        progressDialog.setMessage("Ingresando...");
        progressDialog.setCanceledOnTouchOutside(false);

        // Ingresar dinero
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                validarEIngresar(); // Ahora sí tiene su propio método
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

    private void validarEIngresar() {
        String montoStr = etMonto.getText().toString();

        if (montoStr.isEmpty()) {
            etMonto.setError("Por favor, ingrese un monto");
            return;
        }

        try {
            double monto = Double.parseDouble(montoStr);

            if (monto <= 0) {
                etMonto.setError("El monto debe ser mayor a 0");
                return;
            }

            progressDialog.show();
            startActivity(new Intent(IngresarDinero.this, HomeActivity.class));
            finish();

        } catch (NumberFormatException e) {
            etMonto.setError("Formato de número inválido");
        }
    }
}