package com.example.ernesto.primero;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;


public class ScanActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resultText,tvdatos;
    Button btnScan,btnScanAdd,btnScanExit,btnScanMostrar;
    SqlOpenHelper mDbHelper;
    SQLiteDatabase db;
    Context mContext;
    private static final String TABLE_NAME = "carrito";
    //aca declaro un string OK

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);

        //Si el escaner devuelve un resultado
        if (resultCode == -1) {

            String contents = intent.getStringExtra("SCAN_RESULT");

            Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE cod_barra like '%"+contents+"%';", null);
            // devolveme * de * donde el codigo de barra sea igual al ultimo ingresado
            // el cursor es una clase q te recupera tabla de BD

            if(c.moveToFirst()){
                Toast.makeText(ScanActivity.this, "El codigo ya existe.", Toast.LENGTH_SHORT).show();
            }else{
                //db.execSQL("INSERT INTO " + TABLE_NAME + " (cod_barra) VALUES ('" + contents + "');");
                //Toast.makeText(ScanActivity.this, "Se guardo exitosamente", Toast.LENGTH_SHORT).show();
                //SACAR COMENTARIO ARRIBA
                resultText.setText(contents);
            }

            btnScanAdd.setVisibility(View.VISIBLE);
            btnScanExit.setVisibility(View.VISIBLE);
            btnScan.setVisibility(View.GONE);



        }
        //Si el escaner no encuentra nada (presionar back)
        else
        if (resultCode == 0) {
            // Handle cancel
            resultText.setText("Ud no encontro un carajo");

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        mContext = this;
        mDbHelper = new SqlOpenHelper(mContext);
        db = mDbHelper.getWritableDatabase();

        resultText = (TextView) findViewById(R.id.textViewScanResult);
        tvdatos = (TextView) findViewById(R.id.tvdatos);
        btnScan = ((Button) this.findViewById(R.id.btnScan2));
        btnScanAdd = ((Button)this.findViewById(R.id.btnScanAdd));
        btnScanExit = ((Button) this.findViewById(R.id.btnScanExit));
        btnScanMostrar = ((Button) this.findViewById(R.id.btnScanMostrar));

        btnScan.setOnClickListener(this);
        btnScanAdd.setOnClickListener(this);
        btnScanExit.setOnClickListener(this);
        btnScanMostrar.setOnClickListener(this);
        btnScanAdd.setVisibility(View.GONE);
        btnScanExit.setVisibility(View.GONE);
    }


        public void onClick(View v) {

            Intent intent = null;

            switch (v.getId()) {
                case R.id.btnScan2:
                    IntentIntegrator integrator = new IntentIntegrator(ScanActivity.this);
                    integrator.initiateScan();
                    break;
                //de aca para abajo hacer revisar codigo
                case R.id.btnScanAdd:
                    String codigo= resultText.getText().toString();

                    try {
                        SqlOpenHelper sqlo=new SqlOpenHelper(this);
                        sqlo.abrir();
                        long resultText=sqlo.registrar(codigo);
                        sqlo.cerrar();
                        if (resultText>0){
                            Toast t=Toast.makeText(this, "valor insertado", Toast.LENGTH_LONG);
                            t.show();
                        }

                    } catch (Exception e) {
                        Toast t=Toast.makeText(this, e.toString(), Toast.LENGTH_LONG);
                        t.show();
                        //e.printStackTrace();
                    }




                    //aca habria q guardar los productos en las BD del carrito
                    break;
                case R.id.btnScanMostrar://cambio de uso de boton, momentaneamente muestra la BD
                    try {
                        SqlOpenHelper sqlo=new SqlOpenHelper(this);
                        sqlo.abrir();
                        String resultText=sqlo.consultar();
                        sqlo.cerrar();
                       tvdatos.setText(resultText);
                    } catch (Exception e) {
                        Toast t=Toast.makeText(this, e.toString(), Toast.LENGTH_LONG);
                        t.show();

                        //ver como poner un IF para que si el codigo ya fue ingresado en la BD, salga un toast q diga codigo ya guardado

                    }

                    break;
                case R.id.btnScanExit:
                    //aca vuelve al menu principal
                    break;

            }

        }




    }

