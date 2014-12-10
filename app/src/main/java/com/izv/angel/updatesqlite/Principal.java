package com.izv.angel.updatesqlite;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class Principal extends Activity {

    private GestorJugador gj;
    private Adaptador ad;
    private final int ACTIVIDADAGREGAR=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        gj=new GestorJugador(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_aniadir) {
            Intent i = new Intent(Principal.this,Agregar.class);
            i.setType("agregar");
            startActivityForResult(i,ACTIVIDADAGREGAR);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gj.open();
        Cursor c = gj.getCursor();
        ad = new Adaptador(this,c);
        final ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(ad);
        lv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Principal.this,Secundaria.class);
                intent.putExtra("id",id);
                startActivity(intent);
                }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) lv.getItemAtPosition(position);
                Jugador j = GestorJugador.getRow(cursor);
                gj.delete(j);
                ad.changeCursor(gj.getCursor());
                return false;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        gj.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Jugador ju;
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case ACTIVIDADAGREGAR:
                    gj.open();
                    ju=(Jugador)data.getExtras().getSerializable("jugador");
                    gj.insert(ju);
                    gj.getCursor().close();
                    ad.changeCursor(gj.getCursor());
                    Toast.makeText(this, R.string.insertado, Toast.LENGTH_LONG).show();
            }
        }
    }
}
