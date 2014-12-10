package com.izv.angel.updatesqlite;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Ayudante extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "futbol.sqlite";
    public static final int DATABASE_VERSION = 2;

    public Ayudante (Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql = "create table " + Contrato.TablaPartido.TABLA +
                " (" + Contrato.TablaPartido._ID +
                " integer primary key autoincrement , " +
                Contrato.TablaPartido.IDJUGADOR + " integer, " +
                Contrato.TablaPartido.CONTRINCANTE + " text, " +
                Contrato.TablaPartido.VALORACION + " integer," +
                " unique ("+Contrato.TablaPartido.IDJUGADOR+","+Contrato.TablaPartido.CONTRINCANTE+"))";
        Log.v("sql",sql);
        db.execSQL(sql);

        sql = "create table "+ Contrato.TablaJugadorNew.TABLA +
                " (" + Contrato.TablaJugadorNew._ID +
                " integer primary key autoincrement, " +
                Contrato.TablaJugadorNew.NOMBRE + " text unique, " +
                Contrato.TablaJugadorNew.TELEFONO + " text, " +
                Contrato.TablaJugadorNew.FNAC + " text)";
        Log.v("sql",sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // transformar esquema de la version old a la version new sin se que se produzca ninguna perdida de datos
        // 1º crear tablas de respaldo (idénticas)
        String sql = "create table respaldo"+ Contrato.TablaJugador.TABLA +
                " (" + Contrato.TablaJugador._ID +
                " integer primary key, " +
                Contrato.TablaJugador.NOMBRE + " text, " +
                Contrato.TablaJugador.TELEFONO + " text unique, " +
                Contrato.TablaJugador.VALORACION+ " integer, "+
                Contrato.TablaJugador.FNAC + " text)";
        Log.v("sql",sql);
        db.execSQL(sql);
        // 2º copio los datos que tengo en esas tablas
        sql= "insert into respaldo"+Contrato.TablaJugador.TABLA+" select * from "+Contrato.TablaJugador.TABLA;
        Log.v("sql",sql);
        db.execSQL(sql);
        // 3º Borrar las tablas originales
        sql = "drop table "+Contrato.TablaJugador.TABLA;
        Log.v("sql",sql);
        db.execSQL(sql);
        // 4º crear las tablas nuevas
        onCreate(db);
        // 5º copio los datos de las tablas de respaldo en mis tablas
        sql= "insert into "+Contrato.TablaJugadorNew.TABLA+" select "
                +Contrato.TablaJugador._ID+","+Contrato.TablaJugador.NOMBRE+","+Contrato.TablaJugador.TELEFONO+","+
                Contrato.TablaJugador.FNAC +" from respaldo"+Contrato.TablaJugador.TABLA;
        Log.v("sql",sql);
        db.execSQL(sql);

        sql = "insert into "+Contrato.TablaPartido.TABLA+"("+ Contrato.TablaPartido.IDJUGADOR +","+Contrato.TablaPartido.VALORACION+") select "+Contrato.TablaJugador._ID+","
                +Contrato.TablaJugador.VALORACION+" from respaldo"+Contrato.TablaJugador.TABLA;
        Log.v("sql",sql);
        db.execSQL(sql);
        sql = "update "+Contrato.TablaPartido.TABLA+" set "+Contrato.TablaPartido.CONTRINCANTE+"='inicial'";
        Log.v("sql",sql);
        db.execSQL(sql);
        // 6º borro las tablas de respaldo.
        sql="drop table respaldo"+Contrato.TablaJugador.TABLA;
        Log.v("sql",sql);
        db.execSQL(sql);
    }

}