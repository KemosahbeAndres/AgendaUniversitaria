package cl.stomas.agendauniversitaria.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import cl.stomas.agendauniversitaria.modelos.Carrera;

public class DAOCarrera {
    private DBConnectionManager manager;

    public DAOCarrera(Context context) {
        this.manager = DBConnectionManager.getInstance(context);
    }

    public ArrayList<Carrera> getAll(){
        SQLiteDatabase db = manager.getReadableDatabase();
        ArrayList<Carrera> carreras = new ArrayList<>();
        Cursor rows = db.rawQuery("SELECT * FROM "+ DBContract.TABLA_CARRERAS.NOMBRE, null);
        while(rows.moveToNext()){
            try{
                int indexID = rows.getColumnIndexOrThrow(DBContract.TABLA_CARRERAS.COL_ID);
                int indexNAME = rows.getColumnIndexOrThrow(DBContract.TABLA_CARRERAS.COL_NOMBRE);
                int indexANIO = rows.getColumnIndexOrThrow(DBContract.TABLA_CARRERAS.COL_ANIO);
                Carrera c = new Carrera(
                    rows.getInt(indexID),
                    rows.getString(indexNAME),
                    rows.getInt(indexANIO)
                );
                carreras.add(c);

            }catch (Exception e){}
        }
        rows.close();
        return carreras;
    }

    public Carrera get(int id){
        SQLiteDatabase db = manager.getReadableDatabase();
        Carrera carrera = null;
        Cursor rows = db.rawQuery("SELECT * FROM "+ DBContract.TABLA_CARRERAS.NOMBRE + " WHERE id=" + String.valueOf(id), null);
        while(rows.moveToNext()){
            try{
                int indexID = rows.getColumnIndexOrThrow(DBContract.TABLA_CARRERAS.COL_ID);
                int indexNAME = rows.getColumnIndexOrThrow(DBContract.TABLA_CARRERAS.COL_NOMBRE);
                int indexANIO = rows.getColumnIndexOrThrow(DBContract.TABLA_CARRERAS.COL_ANIO);
                carrera = new Carrera(
                        rows.getInt(indexID),
                        rows.getString(indexNAME),
                        rows.getInt(indexANIO)
                );
            }catch (Exception e){}
        }
        rows.close();
        return carrera;
    }

    public boolean insert(Carrera carrera){
        try{
            SQLiteDatabase db = manager.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBContract.TABLA_CARRERAS.COL_NOMBRE, carrera.getNombre());
            values.put(DBContract.TABLA_CARRERAS.COL_ANIO, carrera.getAnio());
            db.insert(DBContract.TABLA_CARRERAS.NOMBRE, null, values);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean update(Carrera carrera){
        try{
            SQLiteDatabase db = manager.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBContract.TABLA_CARRERAS.COL_NOMBRE, carrera.getNombre());
            values.put(DBContract.TABLA_CARRERAS.COL_ANIO, carrera.getAnio());
            db.update(DBContract.TABLA_CARRERAS.NOMBRE, values, "id="+carrera.getId(), null);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public void delete(Carrera carrera){
        SQLiteDatabase db = manager.getWritableDatabase();
        db.delete(DBContract.TABLA_CARRERAS.NOMBRE, "id="+carrera.getId(), null);
    }

}
