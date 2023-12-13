package com.jmbp.prueba2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Pago_Activity extends AppCompatActivity {

    private TextView txtCancelar;
    private Button btnVolver;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago);

        txtCancelar = findViewById(R.id.txtCancelar);
        btnVolver = findViewById(R.id.btnVolver);

        Intent intent = getIntent();
        if (intent != null) {
            String cedula = intent.getStringExtra("cedula");
            String nombre = intent.getStringExtra("nombre");
            String placa = intent.getStringExtra("placa");
            String fbVehiculo = intent.getStringExtra("fbVehiculo");
            String marca = intent.getStringExtra("marca");
            String color = intent.getStringExtra("color");
            String tipo = intent.getStringExtra("tipo");
            String valor = intent.getStringExtra("valor");
            String multas = intent.getStringExtra("multas");

            double sueldoBasico = 435;
            double importePlacas = calcularImportePlacas(cedula, placa);
            double multaContaminacion = calcularMultaContaminacion(fbVehiculo);
            double valorMatriculacion = calcularValorMatriculacion(marca, tipo, valor, sueldoBasico, multas);

            double totalPagar = importePlacas + multaContaminacion + valorMatriculacion;

            String resultado = "Importe por renovación de placas: $" + importePlacas + "\n" +
                    "Multa por contaminación: $" + multaContaminacion + "\n" +
                    "Valor de matriculación: $" + valorMatriculacion + "\n" +
                    "Total a pagar: $" + totalPagar;

            txtCancelar.setText(resultado);

            btnVolver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }


            });
        }
    }
            private double calcularImportePlacas(String cedula, String placa) {

                if (cedula.startsWith("1") && placa.startsWith("I")) {
                    return 0.05 * 435;
                } else {
                    return 0;
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            private double calcularMultaContaminacion(String fbVehiculo) {

                int anioActual = Calendar.getInstance().get(Calendar.YEAR);
                int anioFabricacion = Integer.parseInt(fbVehiculo);
                int aniosContaminacion = anioActual - anioFabricacion;

                if (aniosContaminacion > 0 && anioFabricacion < 2010) {
                    return 0.02 * aniosContaminacion;
                } else {
                    return 0;
                }
            }

            private double calcularValorMatriculacion(String marca, String tipo, String valor, double sueldoBasico, String multas) {

                double valorVehiculo = Double.parseDouble(valor);
                double valorMatriculacion = 0;

                if (marca.equalsIgnoreCase("Toyota")) {
                    if (tipo.equalsIgnoreCase("Jeep")) {
                        valorMatriculacion = valorVehiculo * 0.08;
                    } else if (tipo.equalsIgnoreCase("Camioneta")) {
                        valorMatriculacion = valorVehiculo * 0.12;
                    }
                } else if (marca.equalsIgnoreCase("Suzuki")) {
                    if (tipo.equalsIgnoreCase("Vitara")) {
                        valorMatriculacion = valorVehiculo * 0.10;
                    } else if (tipo.equalsIgnoreCase("Automóvil")) {
                        valorMatriculacion = valorVehiculo * 0.09;
                    }
                }

                if (multas.equalsIgnoreCase("si")) {
                    return valorMatriculacion * 0.25;
                } else {
                    return valorMatriculacion;
                }
            }

        }

