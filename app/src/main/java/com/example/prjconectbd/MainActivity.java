package com.example.prjconectbd;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtNome, txtEndereco, txtTelefone;
    Button btnSalvar,btnConsultar;
    SQLiteDatabase BancoSQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNome = findViewById(R.id.txtNome);
        txtEndereco = findViewById(R.id.txtEndereco);
        txtTelefone = findViewById(R.id.txtTelefone);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnConsultar = findViewById(R.id.btnConsultar);

        //Definindo o titulo do APP
        setTitle("Cadastro");

        //Criando o Banco de dados
        BancoSQLite = openOrCreateDatabase("AulaDB", Context.MODE_PRIVATE,null);

        //Executando BD E CRIAÇÃO DE TABELA
        //### CASO DE PROBLEMA NA INCIALIZAÇÃO O PROBLEMA PODE SER AQUI
        BancoSQLite.execSQL
                ("CREATE TABLE IF NOT EXISTS Aula" +
                        "(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "Nome VARCHAR," +
                        "Endereco VARCHAR," +
                        "Telefone VARCHAR);");

    }//FIM DO CONSTRUTOR

    //Métodos

    public void mensagem(String str)
    {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void Clicar(View v)
    {
        if(v.getId() == R.id.btnSalvar)
        {
            if (txtNome.getText().toString().isEmpty() || txtEndereco.getText().toString().isEmpty() || txtTelefone.getText().toString().isEmpty())
            {
                mensagem("Preencha os Campos!");
            }//Fim if encadeado
            else
            {
                //CASO O CRASHE AO USAR O BOTÃO O PROBLEMA PODE SER AQUI
                BancoSQLite.execSQL("INSERT INTO Aula(Nome, Endereco, Telefone) VALUES ('"+txtNome.getText()+"', '"+txtEndereco.getText()+"', '"+txtTelefone.getText()+"');");

                mensagem("Dados Salvos");

                txtNome.setText("");
                txtEndereco.setText("");
                txtTelefone.setText("");
                txtNome.requestFocus();

            }//Fim else encadeado

        }//Fim if
        else if (v.getId() == R.id.btnConsultar)
        {

            Cursor c = BancoSQLite.rawQuery("SELECT * FROM Aula", null);

            if(c.getCount() == 0)
            {

                mensagem("Arquivo não encontrado.");

            }//FIM IF ENCADEADO
            else
            {

                StringBuffer buffer = new StringBuffer();
                while (c.moveToNext())
                {
                    buffer.append("Nome: " + c.getString(1) + "\n");
                    buffer.append("Telefone: " + c.getString(2) + "\n");
                    buffer.append("Telefone: " + c.getString(3) +
                            "\n\n--------------------------------------------------\n");
                }

                mensagem(buffer.toString());

            }//FIM ELSE ENCADEADO

        }//Fim else
    }

    public void pesquisa(View v)
    {
        Intent troca = new Intent(MainActivity.this, Pesquisa.class);
        startActivity(troca);
        finish();
    }
}