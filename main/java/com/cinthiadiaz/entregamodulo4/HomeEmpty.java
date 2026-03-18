package com.cinthiadiaz.entregamodulo4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class HomeEmpty extends AppCompatActivity {

    Button button4, button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_empty);

        button4 =findViewById(R.id.button4);
        button5 =findViewById(R.id.button5);

        //Enviar Dinero
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeEmpty.this, enviar_dinero.class));
            }
        });

        //Ingresar Dinero
        button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeEmpty.this, IngresarDinero.class));
            }
        });
    }
}