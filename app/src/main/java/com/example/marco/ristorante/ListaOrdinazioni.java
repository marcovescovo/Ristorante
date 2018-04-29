package com.example.marco.ristorante;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
import java.util.ArrayList;
import java.util.HashMap;

public class ListaOrdinazioni extends AppCompatActivity {

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

    public void eliminaOrdinazione(View view){
        LinearLayout ordinazione=(LinearLayout)view.getParent().getParent();
        ViewGroup ordinazioni = (ViewGroup) ordinazione.getParent();
        ordinazioni.removeView(ordinazione);
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

    public void creaOrdinazione(String text_tipo_ordinazione, String text_specifiche_ordinazione, String text_ora_ordinazione, int text_tavolo_ordinazione, final String note_ordinazione){
        LinearLayout lista_ordinazioni=(LinearLayout) findViewById(R.id.lista_ordinazioni);

        LinearLayout ordinazione=new LinearLayout(this);
        ordinazione.setOrientation(LinearLayout.HORIZONTAL);
        ordinazione.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 60));

        TextView tipo_ordinazione=new TextView(this);
        tipo_ordinazione.setWidth(110);
        tipo_ordinazione.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        tipo_ordinazione.setText(text_tipo_ordinazione);
        tipo_ordinazione.setTextSize(15);
        tipo_ordinazione.setTextColor(000000);
        tipo_ordinazione.setLeft(5);
        tipo_ordinazione.setTop(23);

        TextView specifiche_ordinazione=new TextView(this);
        specifiche_ordinazione.setWidth(120);
        specifiche_ordinazione.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        specifiche_ordinazione.setText(text_specifiche_ordinazione);
        specifiche_ordinazione.setTextColor(000000);
        specifiche_ordinazione.setTop(23);

        TextView ora_ordinazione=new TextView(this);
        ora_ordinazione.setWidth(50);
        ora_ordinazione.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        ora_ordinazione.setText(text_ora_ordinazione);
        ora_ordinazione.setTextColor(000000);
        ora_ordinazione.setTypeface(null, Typeface.BOLD);
        ora_ordinazione.setTop(23);
        ora_ordinazione.setLeft(10);

        Button bottone_ordinazione=new Button(this);
        bottone_ordinazione.setWidth(50);
        bottone_ordinazione.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        bottone_ordinazione.setText(text_tavolo_ordinazione);
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
                creaAlert("NOTE",note_ordinazione);
                return false;
            }
        });

        lista_ordinazioni.addView(ordinazione);
    }

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
            //closing stream
        }
        return responsestring;
    }

    public static ArrayList<HashMap<String,String>> listaMapOrdini(String JSON) throws JSONException {
        ArrayList<HashMap<String,String>> lMap=new ArrayList<>();
        JSONObject obj = new JSONObject(JSON);
        JSONObject ordini = obj.getJSONObject("ordini");
        JSONArray arr = ordini.getJSONArray("ordine");
        for (int i = 0; i < arr.length(); i++){
            HashMap<String,String> map=new HashMap<>();
            String tipo = arr.getJSONObject(i).getString("tipo");
            String specifiche = arr.getJSONObject(i).getString("specifiche");
            String ora = arr.getJSONObject(i).getString("ora");
            String tavolo = arr.getJSONObject(i).getString("tavolo");
            String note = arr.getJSONObject(i).getString("note");
            map.put("tipo", tipo);
            map.put("specifiche", specifiche);
            map.put("ora", ora);
            map.put("tavolo", tavolo);
            map.put("note", note);
            lMap.add(map);
        }
        return lMap;
    }

    public void aggiornaOrdinazioni() throws IOException, JSONException {
        String url="http://192.168.1.100:8080/WebServer/webresources/controller/getordini";
        String json=richiestaJSON(url);
        ArrayList<HashMap<String,String>> lMap=listaMapOrdini(json);
        for(HashMap<String,String> map:lMap){
            creaOrdinazione(map.get("tipo"),map.get("specifiche"),map.get("ora"),Integer.getInteger(map.get("tavolo")),map.get("note"));
        }
    }
}
