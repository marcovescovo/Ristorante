package com.example.marco.ristorante;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AggiungiOrdinazione extends AppCompatActivity {

    protected static Integer tavolo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi_ordinazione);

        final Spinner sp=(Spinner) findViewById(R.id.spinner_tipo_piatto);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                String s=sp.getSelectedItem().toString();
                updateSpecifiche(s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            { }
        });


    }

    private void updateSpecifiche(String s) {
        final Spinner spn=(Spinner) findViewById(R.id.spinner_specifiche);
        switch (s){
            case "Bevanda":
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.specifiche_bevanda, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn.setAdapter(adapter1);
                break;
            case "Antipasto":
                ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.specifiche_antipasto, android.R.layout.simple_spinner_item);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn.setAdapter(adapter2);
                break;
            case "Primo piatto":
                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.specifiche_primo, android.R.layout.simple_spinner_item);
                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn.setAdapter(adapter3);
                break;
            case "Secondo piatto":
                ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.specifiche_secondo, android.R.layout.simple_spinner_item);
                adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn.setAdapter(adapter4);
                break;
            case "Dolce":
                ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this, R.array.specifiche_dolce, android.R.layout.simple_spinner_item);
                adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn.setAdapter(adapter5);
                break;
            case "Vino":
                ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(this, R.array.specifiche_vino, android.R.layout.simple_spinner_item);
                adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn.setAdapter(adapter6);
                break;
        }
    }

    public void scegliTavolo(View view){
        Intent i=new Intent(this,Tavoli.class);
        startActivity(i);
    }



    public void inviaOrdine(View view) {

        Spinner spinner_tipo_ordine=(Spinner)findViewById(R.id.spinner_tipo_piatto);
        String tipo=spinner_tipo_ordine.getSelectedItem().toString();
        Spinner spinner_specifiche=(Spinner)findViewById(R.id.spinner_specifiche);
        String specifiche=spinner_specifiche.getSelectedItem().toString();

        Date data = new Date();
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(data);   // assigns calendar to given date
        String minuto=((Integer)calendar.get(Calendar.MINUTE)).toString();
        if (minuto.length()<2){minuto="0"+minuto;}
        String ora=calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);
        EditText note=(EditText)findViewById(R.id.edit_note);

        RunnableOrdine ro=new RunnableOrdine(tipo,specifiche,ora,tavolo.toString(),note.getText().toString());
        new Thread(ro).start();

        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }

}
