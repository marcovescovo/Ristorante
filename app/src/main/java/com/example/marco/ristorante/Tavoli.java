package com.example.marco.ristorante;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Tavoli extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tavoli);
    }

    public void setTavolo1(View view){
        AggiungiOrdinazione.tavolo=1;
        Intent i=new Intent(this,AggiungiOrdinazione.class);
        startActivity(i);
    }

    public void setTavolo2(View view){
        AggiungiOrdinazione.tavolo=2;
        Intent i=new Intent(this,AggiungiOrdinazione.class);
        startActivity(i);
    }

    public void setTavolo3(View view){
        AggiungiOrdinazione.tavolo=3;
        Intent i=new Intent(this,AggiungiOrdinazione.class);
        startActivity(i);
    }

    public void setTavolo4(View view){
        AggiungiOrdinazione.tavolo=4;
        Intent i=new Intent(this,AggiungiOrdinazione.class);
        startActivity(i);
    }

    public void setTavolo5(View view){
        AggiungiOrdinazione.tavolo=5;
        Intent i=new Intent(this,AggiungiOrdinazione.class);
        startActivity(i);
    }

}
