package com.example.marco.ristorante;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;

public class ListaOrdinazioni extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ordinazioni);
        try {
            aggiornaOrdinazioni();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void nuovoOrdine(final Ordine o){
        ListView lista_ordinazioni=(ListView) findViewById(R.id.listview_ordini);

        LinearLayout ordinazione=new LinearLayout(this);
        ordinazione.setOrientation(LinearLayout.HORIZONTAL);
        ordinazione.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 60));

        TextView tipo_ordinazione=new TextView(this);
        tipo_ordinazione.setWidth(110);
        tipo_ordinazione.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        tipo_ordinazione.setText(o.getTipo()+":");
        tipo_ordinazione.setTextSize(15);
        tipo_ordinazione.setTextColor(000000);
        tipo_ordinazione.setLeft(5);
        tipo_ordinazione.setTop(23);

        TextView specifiche_ordinazione=new TextView(this);
        specifiche_ordinazione.setWidth(120);
        specifiche_ordinazione.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        specifiche_ordinazione.setText(o.getSpecifiche());
        specifiche_ordinazione.setTextColor(000000);
        specifiche_ordinazione.setTop(23);

        TextView ora_ordinazione=new TextView(this);
        ora_ordinazione.setWidth(50);
        ora_ordinazione.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        ora_ordinazione.setText(o.getOra());
        ora_ordinazione.setTextColor(000000);
        ora_ordinazione.setTypeface(null, Typeface.BOLD);
        ora_ordinazione.setTop(23);
        ora_ordinazione.setLeft(10);

        Button bottone_ordinazione=new Button(this);
        bottone_ordinazione.setWidth(50);
        bottone_ordinazione.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        bottone_ordinazione.setText(o.getTavolo());
        bottone_ordinazione.setTop(23);
        bottone_ordinazione.setLeft(10);
        bottone_ordinazione.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                eliminaOrdinazione(v);
            }
        });

        ordinazione.addView(tipo_ordinazione);
        ordinazione.addView(specifiche_ordinazione);
        ordinazione.addView(ora_ordinazione);
        ordinazione.addView(bottone_ordinazione);
        ordinazione.setOnLongClickListener(new View.OnLongClickListener(){
            public boolean onLongClick(View arg0) {
                creaAlert("NOTE",o.getNote());
                return false;
            }
        });

        lista_ordinazioni.addFooterView(ordinazione);
    }

    public void eliminaOrdinazione(View view){
        LinearLayout ordinazione=(LinearLayout)view.getParent();
        ListView lw=(ListView) findViewById(R.id.listview_ordini);
        lw.removeView(ordinazione);
    }

    public void creaAlert(String titolo, String testo){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(titolo)
                .setMessage(testo)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String richiestaJSON(String urlString) throws ProtocolException, IOException {
        String responsestring="";
        URL url = new URL(urlString);
        HttpURLConnection c = (HttpURLConnection)url.openConnection();
        c.setRequestMethod("GET");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()))) {
            String str;
            while ((str = in.readLine()) != null) {
                responsestring += str+"\n";
            }
        }
        return responsestring;
    }

    public static ArrayList<Ordine> getListaOrdini(String JSON) throws JSONException {

        ArrayList<Ordine> lOrdini=new ArrayList<>();
        JSONArray arr = new JSONArray(JSON);
        for (int i = 0; i < arr.length(); i++){
            Ordine ordine=new Ordine();
            ordine.setId(arr.getJSONObject(i).getInt("id"));
            ordine.setTipo(arr.getJSONObject(i).getString("tipo"));
            ordine.setSpecifiche(arr.getJSONObject(i).getString("specifiche"));
            ordine.setOra(arr.getJSONObject(i).getString("ora"));
            ordine.setTavolo(arr.getJSONObject(i).getString("tavolo"));
            ordine.setNote(arr.getJSONObject(i).getString("note"));
            lOrdini.add(ordine);
        }
        return lOrdini;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void aggiornaOrdinazioni() throws IOException, JSONException {
        String url="http://192.168.1.100:8080/WebApp/webresources/manager/getjsonordini";
        String json=richiestaJSON(url);
        ArrayList<Ordine> lOrdini=getListaOrdini(json);
        for(Ordine ordine:lOrdini){
            nuovoOrdine(ordine);
        }
    }
}
