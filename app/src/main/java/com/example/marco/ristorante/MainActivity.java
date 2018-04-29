package com.example.marco.ristorante;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openListaOrdinazioni(View view){
        Intent i=new Intent(this,ListaOrdinazioni.class);
        startActivity(i);
    }

    public void openAggiungiOrdinazione(View view){
        Intent i=new Intent(this,AggiungiOrdinazione.class);
        startActivity(i);
    }

}
