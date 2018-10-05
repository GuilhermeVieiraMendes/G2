package com.example.paulosouza.myapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    String s;
    Button btnPesq;
    Button btnVoltar;
    ListView listaclientes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        listaclientes = findViewById(R.id.listadecliente);
        btnPesq =  findViewById(R.id.btnPesquisa);
        btnVoltar =  findViewById(R.id.btnVoltar);
        s = getIntent().getExtras().getString("Primeiro");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnPesq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DownloadAPIJSON()
                        .execute("http://nli.univale.br/apicliente/api/cliente/retornaclientes?tipo=json");
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    class DownloadAPIJSON extends AsyncTask<String, Void,List<Pessoa>> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(Main2Activity.this, "Calma",
                    "Está carregando...");

        }

        //Acessa o serviço do JSON e retorna a lista de pessoas
        @Override
        protected List<Pessoa> doInBackground(String... params) {
            String urlString = params[0];
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(urlString);
            try {
                HttpResponse response = httpclient.execute(httpget);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    String json = getStringFromInputStream(instream);
                    instream.close();
                    List<Pessoa> pessoas = getPessoas(json);
                    return pessoas;
                }
            } catch (Exception e) {
                Log.e("Erro", "Falha ao acessar Web service", e);
            }
            return null;

        }

        @Override
        protected void onPostExecute(List<Pessoa> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if (result.size() > 0) {


                ArrayAdapter<Pessoa> adapter = new ArrayAdapter<Pessoa>(
                        Main2Activity.this,
                        android.R.layout.simple_list_item_1, result);
                listaclientes.setAdapter(adapter);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        Main2Activity.this)
                        .setTitle("Erro")
                        .setMessage("Não foi possível acessar as informações!!")
                        .setPositiveButton("OK", null);
                builder.create().show();
            }
        }

        //Retorna uma lista de pessoas com as informações retornadas do JSON
        private List<Pessoa> getPessoas(String jsonString) {
            List<Pessoa> pessoas = new ArrayList<Pessoa>();
            try {
                JSONArray pessoasJson = new JSONArray(jsonString);
                JSONObject pessoa;

                for (int i = 0; i < pessoasJson.length(); i++) {
                    pessoa = new JSONObject(pessoasJson.getString(i));
                    Log.i("PESSOA ENCONTRADA: ",
                            "nome=" + pessoa.getString("nome"));

                    Pessoa objetoPessoa = new Pessoa();
                    objetoPessoa.setNome(pessoa.getString("nome"));
                    objetoPessoa.setCpf(pessoa.getString("cpf"));
                    pessoas.add(objetoPessoa);
                }

            } catch (JSONException e) {
                Log.e("Erro", "Erro no parsing do JSON", e);
            }
            return pessoas;
        }

        //Converte objeto InputStream para String
        private String getStringFromInputStream(InputStream is) {

            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();

            String line;
            try {

                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return sb.toString();

        }
    }


}