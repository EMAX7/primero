package com.example.ernesto.primero;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;

import com.example.ernesto.primero.ScanActivity;


public class SqlOpenHelper extends SQLiteOpenHelper {

    private static final String BD_NAME = "Scanner.db";
    private static final int SCHEME_VERSION = 1;
    //private SQLiteDatabase db;//esto no va comentado
    Context ctx;//esto es nuevo


    public SqlOpenHelper(Context context) {
        super(context, BD_NAME, null, SCHEME_VERSION);
        ctx = context;//esto es nuevo
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE carrito (id_prod INTEGER PRIMARY KEY AUTOINCREMENT, cod_barra TEXT NOT NULL)");

    }
    //agregar la tabla Productos mediante algun import de un archivo externo  ((agregar detalle TEXT en el producto))


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS carrito");
        //eliminar carrito si ya existe y crea un carrito nuevok
        //onCreate(db);
    }


    //esto es nuevo para abajo tod o
    SqlOpenHelper ayuda;//esto es nuevo
    SQLiteDatabase db;//esto es nuevo

    public void abrir(){
        ayuda= new SqlOpenHelper(ctx);
        db = ayuda.getWritableDatabase();

    }
    public void cerrar() {
        db.close();

    }

    public long registrar(String codigo) throws Exception{
        ContentValues valores=new ContentValues();
        valores.put("cod_barra",codigo);
        return db.insert("carrito", null, valores);
    }

    public String consultar () throws Exception {
        String datos = "";
        String[] columnas = new String[]{"cod_barra"};
        Cursor c = db.query("carrito", columnas, null, null, null, null, null);
        for (c.moveToFirst();!c.isAfterLast(); c.moveToNext()) {
            datos+=c.getString(c.getColumnIndex("cod_barra"))+"\n";
        }
        return datos;
    }


}
