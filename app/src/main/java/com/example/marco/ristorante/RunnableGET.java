package com.example.marco.ristorante;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class RunnableGET implements Runnable {
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void run() {
        try {
            String json=richiestaJSON("http://192.168.1.7:8080/WebApp/webresources/manager/getjsonordini");
            ListaOrdinazioni.lOrdini=getListaOrdini(json);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String richiestaJSON(String urlString) throws ProtocolException, IOException {
        String responsestring="";
        URL url = new URL(urlString);
        HttpURLConnection c = (HttpURLConnection)url.openConnection();
        c.setRequestMethod("GET");
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
            String str;
            while ((str = in.readLine()) != null) {
                responsestring += str+"\n";
            }
        }catch(Exception e){
            System.out.println(e);
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
}
