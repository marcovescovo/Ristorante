package com.example.marco.ristorante;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;

public class ListaOrdinazioni extends AppCompatActivity {

    protected static ArrayList<Ordine> lOrdini=new ArrayList<>();
    protected static String[] tipi;
    protected static String[] ore;
    protected static String[] specifiche;
    protected static String[] tavoli;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ordinazioni);

        try {
            aggiornaOrdinazioni();
        } catch (Exception e) {
            e.printStackTrace();
        }

        riempiArray();
        ListView listView=(ListView)findViewById(R.id.listviewordini);
        try{
            CustomAdapter customAdapter=new CustomAdapter();
            listView.setAdapter(customAdapter);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void riempiArray(){
        tipi=new String[lOrdini.size()];
        specifiche=new String[lOrdini.size()];
        ore=new String[lOrdini.size()];
        tavoli=new String[lOrdini.size()];
        int i=0;
        for(Ordine o:lOrdini){
            tipi[i]=o.getTipo();
            ore[i]=o.getOra();
            specifiche[i]=o.getSpecifiche();
            tavoli[i]=o.getTavolo();
            i++;
        }
    }

    /*public void eliminaOrdinazione(View view){
        //LinearLayout ordinazione=(LinearLayout)view.getParent();
        ListView lw=(ListView) findViewById(R.id.listviewordini);
        lw.removeView(view);
    }*/

    /*public void creaAlert(String titolo, String testo){
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
    }*/

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void aggiornaOrdinazioni() throws IOException, JSONException {
        RunnableGET rg=new RunnableGET();
        new Thread(rg).start();
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return lOrdini.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=getLayoutInflater().inflate(R.layout.customlayout,null);

            TextView text_tipo=(TextView)convertView.findViewById(R.id.textView_tipo);
            TextView text_specifiche=(TextView)convertView.findViewById(R.id.textView_specifiche);
            TextView text_ora=(TextView)convertView.findViewById(R.id.textView_ora);
            Button button_bottone=(Button)convertView.findViewById(R.id.button_delete);

            text_tipo.setText(tipi[position]);
            text_specifiche.setText(specifiche[position]);
            text_ora.setText(ore[position]);
            button_bottone.setText(tavoli[position]);

            return null;
        }
    }
}
