package com.example.juangarca.tfg;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juangarca.tfg.MainActivity;
import  com.example.juangarca.tfg.R;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class DeleteGroupActivity extends AppCompatActivity {

    private Button bEliminar;
    private TextView texto;
    private EditText eText;

    private class LongRunningGetIO extends AsyncTask<Void, Void, String> {
        String idGrupo = eText.getText().toString();

        @Override
        protected String doInBackground(Void... params) {
            String text = null;
            HttpURLConnection urlConnection = null;
            try {
                URL urlToRequest = new URL("http://192.168.1.17:8080/user/" + idGrupo);
                urlConnection = (HttpURLConnection) urlToRequest.openConnection();
                urlConnection.setRequestMethod("DELETE");

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
               text = new Scanner(in).useDelimiter(
                        "\\A").next();
            } catch (Exception e) {
                return e.toString();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return text;
        }

        protected void onPostExecute(String results) {

            if (results.equals("true")) {
                View view = findViewById(R.id.content_delete_group);
                Snackbar.make(view, "Grupo " + eText.getText().toString() + " eliminado" , Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }else {//Si el grupo que se le pasa no existe
                Toast toast1 = Toast.makeText(getApplicationContext(),
                        "No se ha encontrado el grupo.", Toast.LENGTH_SHORT);
                toast1.show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eText = (EditText)findViewById(R.id.edText);
        bEliminar=(Button)findViewById(R.id.bEliminar);
        bEliminar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                new DeleteGroupActivity.LongRunningGetIO().execute();
            }


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
                Intent intentHome = new Intent(DeleteGroupActivity.this, MainActivity.class);
                startActivity(intentHome);
                return true;
            case R.id.members:
                Intent intentMiembros = new Intent(DeleteGroupActivity.this, MembersActivity.class);
                startActivity(intentMiembros);
                return true;
            case R.id.deletegroup:
                Intent intentDeleteGroup = new Intent(DeleteGroupActivity.this, DeleteGroupActivity.class);
                startActivity(intentDeleteGroup);
                return true;
            case R.id.dblp:
                Intent intentDBLP = new Intent(DeleteGroupActivity.this, DBLPActivity.class);
                startActivity(intentDBLP);
                return true;
            case R.id.newmember:
                Intent intentNewMember = new Intent(DeleteGroupActivity.this, NewMemberActivity.class);
                startActivity(intentNewMember);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
