package com.example.juangarca.tfg;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private TextView texto;

    private class LongRunningGetIO extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String text = null;
            HttpURLConnection urlConnection = null;
            try{
                URL urlToRequest = new URL("http://192.168.1.17:8080/user");
                urlConnection = (HttpURLConnection) urlToRequest.openConnection();
                InputStream in =
                        new BufferedInputStream(urlConnection.getInputStream());
                text=new Scanner(in).useDelimiter(
                        "\\A").next();
            }catch (Exception e){return e.toString();}
            finally { if (urlConnection != null) { urlConnection.disconnect(); }
            }
            return text;
        }

        protected void onPostExecute(String results) {
            if (results!=null) {
                texto=(TextView)findViewById(R.id.Mitexto);
                texto.setText(results);}
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new MainActivity.LongRunningGetIO().execute();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        final TextView myTextView = (TextView) findViewById(R.id.Mitexto);
        switch (item.getItemId()) {
            case R.id.home:
                Intent intentHome = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intentHome);
                return true;
            case R.id.members:
                Intent intentMiembros = new Intent(MainActivity.this, MembersActivity.class);
                startActivity(intentMiembros);
                return true;
            case R.id.deletegroup:
                Intent intentDeleteGroup = new Intent(MainActivity.this, DeleteGroupActivity.class);
                startActivity(intentDeleteGroup);
                return true;
            case R.id.dblp:
                Intent intentDBLP = new Intent(MainActivity.this, DBLPActivity.class);
                startActivity(intentDBLP);
                return true;
            case R.id.newmember:
                Intent intentNewMember = new Intent(MainActivity.this, NewMemberActivity.class);
                startActivity(intentNewMember);
                return true;
            case R.id.newalergia:
                Intent intentNewAlergia = new Intent(MainActivity.this, NewAlergia.class);
                startActivity(intentNewAlergia);
                return true;
            case R.id.alergia:
                Intent intentMostrarAlergia = new Intent(MainActivity.this, MostrarAlergia.class);
                startActivity(intentMostrarAlergia);
                return true;
            case R.id.alergiaPersona:
                Intent intentAlergiaPersona = new Intent(MainActivity.this, MostrarAlergiaPersona.class);
                startActivity(intentAlergiaPersona);
                return true;
            case R.id.anadirAlergiaPersona:
                Intent intentañadirAlergiaPersona = new Intent(MainActivity.this, AnadirAlergiaPersona.class);
                startActivity(intentañadirAlergiaPersona);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
