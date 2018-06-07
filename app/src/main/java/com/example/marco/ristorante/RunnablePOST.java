package com.example.marco.ristorante;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RunnablePOST implements Runnable {

    private String tipo;
    private String specifiche;
    private String ora;
    private String tavolo;
    private String note;

    public RunnablePOST(String tipo,String specifiche,String ora,String tavolo,String note){
        this.note=note;
        this.ora=ora;
        this.specifiche=specifiche;
        this.tavolo=tavolo;
        this.tipo=tipo;
    }


    private static void sendPost(String tipo,String specifiche,String ora,String tavolo,String note) throws Exception {

        String USER_AGENT = "Mozilla/5.0";
        String url = "http://192.168.1.7:8080/WebApp/webresources/manager/post";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "tipo="+tipo+"&specifiche="+specifiche+"&ora="+ora+"&tavolo="+tavolo+"&note="+note;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());

    }

    @Override
    public void run() {
        try {
            sendPost(tipo,specifiche,ora,tavolo,note);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
