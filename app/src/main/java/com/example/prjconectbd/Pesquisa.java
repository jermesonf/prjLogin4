package com.example.prjconectbd;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Pesquisa extends AppCompatActivity {

    //Spinner so lê vetores, entao devemos adaptar um vetor
    ArrayAdapter<String>ListaAgenda;
    String[] AgendaNome;
    TextView txtName,txtAddress,txtPhone;
    SQLiteDatabase BancoSQLite;
    Spinner sn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);

        BancoSQLite = openOrCreateDatabase("AulaDB", Context.MODE_PRIVATE,null);

        sn = findViewById(R.id.spNome);

        txtName = findViewById(R.id.txtName);
        txtAddress = findViewById(R.id.txtAddress);
        txtPhone = findViewById(R.id.txtPhone);
        preencher();

        //EVENTO CLICK DO SPINNER
        sn.setOnItemSelectedListener
                (new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected
                            (AdapterView parent, View view, int pos, long id) {

                        String nomeL;
                        nomeL = parent.getItemAtPosition(pos).toString();
                        Cursor c2 = BancoSQLite.rawQuery("SELECT * FROM Aula where nome = '"+nomeL+"'",null);
                        if(c2.moveToNext())
                        {
                            txtName.setText(c2.getString(1).toString());
                            txtAddress.setText(c2.getString(2).toString());
                            txtPhone.setText(c2.getString(3).toString());
                        }

                    }

                    public void onNothingSelected(AdapterView parent){}
                });
    }

    public void preencher()
    {

        Cursor c = BancoSQLite.rawQuery("SELECT * FROM Aula order by nome", null);
        //Definindo o tamanho do vetor, e dizendo para preecher de acordo com o tamanho do banco. obs: evita linhas em branco
        AgendaNome = new String[c.getCount()];
        int i = 0;

        while (c.moveToNext())
        {
            AgendaNome[i]=c.getString(1).toString();
            i++;
        }

        ListaAgenda = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, AgendaNome);
        sn.setAdapter(ListaAgenda);
    }


}