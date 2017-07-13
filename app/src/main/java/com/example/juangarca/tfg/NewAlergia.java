package com.example.juangarca.tfg;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewAlergia extends AppCompatActivity {

    private Button bAñadir;
    private TextView texto;
    private EditText id;
    private EditText name;
    private static final int NOTIF_ALERTA_ID = 1;

    private class LongRunningGetIO extends AsyncTask<Void, Void, String> {
        String s_id = id.getText().toString();
        String s_name = name.getText().toString();
        @Override
        protected String doInBackground(Void... params) {
            String text = null;
            HttpURLConnection urlConnection = null;

            try{
                JSONObject respJSON = new JSONObject("{\"_id\":"+s_id+",\"descripcion\":\""+s_name+"\"}");
                URL urlToRequest = new URL("http://192.168.1.17:8080/alergia/");
                urlConnection = (HttpURLConnection) urlToRequest.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                urlConnection.setFixedLengthStreamingMode(respJSON.toString().getBytes().length);

                urlConnection.connect();
                OutputStream os = new BufferedOutputStream(urlConnection.getOutputStream());
                os.write(respJSON.toString().getBytes());
                os.flush();
                text = respJSON.toString();
            }catch (Exception e){return e.toString();}
            finally { if (urlConnection != null) { urlConnection.disconnect(); }
            }
            return text;
        }

        protected void onPostExecute(String results) {
            /*if (results!=null) {
                texto=(TextView)findViewById(R.id.Mitexto);
                texto.setText(results);
            }*/

            NotificationCompat.Builder notificacion =
                    new NotificationCompat.Builder(NewAlergia.this)
                            .setSmallIcon(R.drawable.ic_stat_name)
                            .setLargeIcon(BitmapFactory.decodeResource
                                    (getResources(), R.drawable.ic_stat_name2))
                            .setContentTitle("InvestigacionApp")
                            .setContentText(results + " se ha añadido correctamente.")
                            .setContentInfo("4")
                            .setTicker("Alerta!");

            Intent notIntent = new Intent(NewAlergia.this, MembersActivity.class);
            PendingIntent contIntent =
                    PendingIntent.getActivity(NewAlergia.this,0, notIntent,
                            0);
            notificacion.setContentIntent(contIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(NOTIF_ALERTA_ID, notificacion.build());

            notificacion.setSound(RingtoneManager.getDefaultUri
                    (RingtoneManager.TYPE_NOTIFICATION));

            notificacion.setAutoCancel (true);

            Toast toast1 = Toast.makeText(getApplicationContext(),
                    "Se ha añadido correctamente.", Toast.LENGTH_SHORT);
            toast1.show();

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alergia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        id = (EditText)findViewById(R.id.id);
        name = (EditText)findViewById(R.id.name);
        bAñadir=(Button)findViewById(R.id.bBuscar);
        bAñadir.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                new LongRunningGetIO().execute(); }
        });

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
                Intent intentHome = new Intent(NewAlergia.this, MainActivity.class);
                startActivity(intentHome);
                return true;
            case R.id.members:
                Intent intentMiembros = new Intent(NewAlergia.this, MembersActivity.class);
                startActivity(intentMiembros);
                return true;
            case R.id.deletegroup:
                Intent intentDeleteGroup = new Intent(NewAlergia.this, DeleteGroupActivity.class);
                startActivity(intentDeleteGroup);
                return true;
            case R.id.dblp:
                Intent intentDBLP = new Intent(NewAlergia.this, DBLPActivity.class);
                startActivity(intentDBLP);
                return true;
            case R.id.newmember:
                Intent intentNewMember = new Intent(NewAlergia.this, NewMemberActivity.class);
                startActivity(intentNewMember);
                return true;
            case R.id.newalergia:
                Intent intentNewAlergia = new Intent(NewAlergia.this, NewAlergia.class);
                startActivity(intentNewAlergia);
                return true;
            case R.id.alergia:
                Intent intentMostrarAlergia = new Intent(NewAlergia.this, MostrarAlergia.class);
                startActivity(intentMostrarAlergia);
                return true;
            case R.id.alergiaPersona:
                Intent intentAlergiaPersona = new Intent(NewAlergia.this, MostrarAlergiaPersona.class);
                startActivity(intentAlergiaPersona);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
