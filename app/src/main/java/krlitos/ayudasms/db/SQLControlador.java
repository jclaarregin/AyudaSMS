package krlitos.ayudasms.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SQLControlador {

    private DBhelper dbhelper;
    private Context ourcontext;
    private SQLiteDatabase database;

    public SQLControlador(Context c) {
        ourcontext = c;
    }

    public SQLControlador abrirBaseDeDatos() throws SQLException {
        dbhelper = new DBhelper(ourcontext);
        database = dbhelper.getWritableDatabase();
        return this;
    }

    public void cerrar() {
        dbhelper.close();
    }

    public void insertarDatos(String name, String phone) {
        ContentValues cv = new ContentValues();
        cv.put(DBhelper.CONTACTO_NOMBRE, name.toUpperCase());
        cv.put(DBhelper.CONTACTO_CELULAR, phone);
        database.insert(DBhelper.TABLE_CONTACTO, null, cv);
    }

    public Cursor leerDatos() {
        String[] todasLasColumnas = new String[] {
                DBhelper.CONTACTO_ID,
                DBhelper.CONTACTO_NOMBRE,
                DBhelper.CONTACTO_CELULAR
        };
        Cursor c = database.query(DBhelper.TABLE_CONTACTO, todasLasColumnas, null,
                null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public int modificarDatos(long contactID, String contactName,String contactPhone) {
        ContentValues cvActualizar = new ContentValues();
        //el toUpperCase() convierte en mayusculas lo que se guarde
        cvActualizar.put(DBhelper.CONTACTO_NOMBRE, contactName.toUpperCase());
        cvActualizar.put(DBhelper.CONTACTO_CELULAR, contactPhone);
        int i = database.update(DBhelper.TABLE_CONTACTO, cvActualizar,
                DBhelper.CONTACTO_ID + " = " + contactID, null);
        return i;
    }

    public void eliminarDatos(long contactID) {
        database.delete(DBhelper.TABLE_CONTACTO, DBhelper.CONTACTO_ID + "="
                +contactID, null);
    }
}
