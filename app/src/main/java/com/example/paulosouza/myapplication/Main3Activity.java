package com.example.paulosouza.myapplication;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class Main3Activity extends AppCompatActivity {
    Button btnVoltar;
    Button btnInserir;
    Button btnListar;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        btnVoltar =  findViewById(R.id.btnVoltar);
        btnInserir = findViewById(R.id.btnInserir);
        btnListar = findViewById(R.id.btnListar);
        final Intent intent = new Intent(this, MainActivity.class);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
        btnInserir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog = ProgressDialog.show(Main3Activity.this, "Aguarde", "Inserindo");
                new Thread(new Runnable() {
                    Handler hand = new Handler();
                    String erro = "";


                    @Override
                    public void run() {
                        try{
                            hand.post(new Runnable() {
                                @Override
                                public void run() {
                                    ClassBD bd = new ClassBD(getBaseContext());
                                    SQLiteDatabase db = bd.getWritableDatabase();
                                    ContentValues valores = new ContentValues();
                                    valores.put("NOME" , "Pessoa");
                                    valores.put ("CPF", "123456789");
                                    long resultado;
                                    resultado = db.insert("CLIENTE",null,valores);
                                }
                            });
                        } catch (Exception e){
                            // dialog.dismiss();
                            erro = e.toString();
                            hand.post(new Runnable() {
                                public void run() {
                                    //Toast.makeText(produtos.this, "ERRO "+erro.toString(), Toast.LENGTH_SHORT).show();
                                    //mensagem("ERRO", erro);
                                    Log.i("Clientes","ERRO "+erro.toString());
//			        					mprogressDialog.setMessage(erro);
                                }
                            });
                            Log.i("Clientes", "ERRO "+e.toString());
                        }
                        /////
                        dialog.dismiss();
                    }
                }).start();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
