package cl.stomas.agendauniversitaria.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import cl.stomas.agendauniversitaria.modelos.Asignatura;
import cl.stomas.agendauniversitaria.modelos.Bloque;
import cl.stomas.agendauniversitaria.modelos.Semestre;

public class DAOAsignatura {

    private DBConnectionManager manager;

    public DAOAsignatura(Context context) {
        manager = DBConnectionManager.getInstance(context);
    }

    public ArrayList<Asignatura> getAll(){
        SQLiteDatabase db = manager.getReadableDatabase();
        ArrayList<Asignatura> asignaturas = new ArrayList<>();
        Cursor rows = db.rawQuery("SELECT * FROM "+ DBContract.TABLA_ASIGNATURAS.NOMBRE, null);
        while(rows.moveToNext()){
            try{
                int indexID = rows.getColumnIndexOrThrow(DBContract.TABLA_ASIGNATURAS.COL_ID);
                int indexNAME = rows.getColumnIndexOrThrow(DBContract.TABLA_ASIGNATURAS.COL_NOMBRE);
                int indexDESC = rows.getColumnIndexOrThrow(DBContract.TABLA_ASIGNATURAS.COL_DESCRIPCION);
                int indexCOLOR = rows.getColumnIndexOrThrow(DBContract.TABLA_ASIGNATURAS.COL_COLOR);
                int indexTEACHER = rows.getColumnIndexOrThrow(DBContract.TABLA_ASIGNATURAS.COL_DOCENTE);
                Asignatura c = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    c = new Asignatura(
                            rows.getInt(indexID),
                            rows.getString(indexNAME),
                            rows.getString(indexDESC),
                            rows.getString(indexCOLOR),
                            rows.getString(indexTEACHER)
                    );
                }else {
                    throw new Exception("SDK antiguo!");
                }
                asignaturas.add(c);

            }catch (Exception e){}
        }
        rows.close();
        return asignaturas;
    }
    public ArrayList<Asignatura> getFrom(Semestre semestre){
        SQLiteDatabase db = manager.getReadableDatabase();
        ArrayList<Asignatura> asignaturas = new ArrayList<>();
        Cursor rows = db.rawQuery(
                "SELECT * FROM "+ DBContract.TABLA_ASIGNATURAS.NOMBRE+" WHERE ?=?",
                new String[]{
                        DBContract.TABLA_ASIGNATURAS.COL_ID_SEMESTRE,
                        String.valueOf(semestre.getId())
                }
                );
        while(rows.moveToNext()){
            try{
                int indexID = rows.getColumnIndexOrThrow(DBContract.TABLA_ASIGNATURAS.COL_ID);
                int indexNAME = rows.getColumnIndexOrThrow(DBContract.TABLA_ASIGNATURAS.COL_NOMBRE);
                int indexDESC = rows.getColumnIndexOrThrow(DBContract.TABLA_ASIGNATURAS.COL_DESCRIPCION);
                int indexCOLOR = rows.getColumnIndexOrThrow(DBContract.TABLA_ASIGNATURAS.COL_COLOR);
                int indexTEACHER = rows.getColumnIndexOrThrow(DBContract.TABLA_ASIGNATURAS.COL_DOCENTE);
                Asignatura c = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    c = new Asignatura(
                            rows.getInt(indexID),
                            rows.getString(indexNAME),
                            rows.getString(indexDESC),
                            rows.getString(indexCOLOR),
                            rows.getString(indexTEACHER)
                    );
                }else {
                    throw new Exception("SDK antiguo!");
                }
                asignaturas.add(c);

            }catch (Exception e){}
        }
        rows.close();
        return asignaturas;
    }

    public Asignatura get(int id){
        SQLiteDatabase db = manager.getReadableDatabase();
        Asignatura asignatura = null;
        Cursor rows = db.rawQuery("SELECT * FROM "+DBContract.TABLA_ASIGNATURAS.NOMBRE+" WHERE id="+id+" LIMIT 1", null);
        if(rows.moveToFirst()){
            try{
                int indexID = rows.getColumnIndexOrThrow(DBContract.TABLA_ASIGNATURAS.COL_ID);
                int indexNAME = rows.getColumnIndexOrThrow(DBContract.TABLA_ASIGNATURAS.COL_NOMBRE);
                int indexDESC = rows.getColumnIndexOrThrow(DBContract.TABLA_ASIGNATURAS.COL_DESCRIPCION);
                int indexCOLOR = rows.getColumnIndexOrThrow(DBContract.TABLA_ASIGNATURAS.COL_COLOR);
                int indexTEACHER = rows.getColumnIndexOrThrow(DBContract.TABLA_ASIGNATURAS.COL_DOCENTE);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    asignatura = new Asignatura(
                            rows.getInt(indexID),
                            rows.getString(indexNAME),
                            rows.getString(indexDESC),
                            rows.getString(indexCOLOR),
                            rows.getString(indexTEACHER)
                    );
                }else {
                    throw new Exception("SDK antiguo!");
                }
            }catch (Exception e){}
        }
        return asignatura;
    }

    public void insert(Asignatura asignatura, Semestre semestre){
        SQLiteDatabase db = manager.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.TABLA_ASIGNATURAS.COL_ID_SEMESTRE, semestre.getId());
        values.put(DBContract.TABLA_ASIGNATURAS.COL_NOMBRE, asignatura.getNombre());
        values.put(DBContract.TABLA_ASIGNATURAS.COL_DESCRIPCION, asignatura.getDescripcion());
        values.put(DBContract.TABLA_ASIGNATURAS.COL_DOCENTE, asignatura.getDocente());
        values.put(DBContract.TABLA_ASIGNATURAS.COL_COLOR, asignatura.getColor());
        db.insert(DBContract.TABLA_ASIGNATURAS.NOMBRE, null, values);
    }
    public void update(Asignatura asignatura){
        SQLiteDatabase db = manager.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.TABLA_ASIGNATURAS.COL_NOMBRE, asignatura.getNombre());
        values.put(DBContract.TABLA_ASIGNATURAS.COL_DESCRIPCION, asignatura.getDescripcion());
        values.put(DBContract.TABLA_ASIGNATURAS.COL_DOCENTE, asignatura.getDocente());
        values.put(DBContract.TABLA_ASIGNATURAS.COL_COLOR, asignatura.getColor());
        db.update(DBContract.TABLA_ASIGNATURAS.NOMBRE, values, "id="+asignatura.getId(), null);
    }
    public void delete(Asignatura asignatura){
        SQLiteDatabase db = manager.getWritableDatabase();
        db.delete(DBContract.TABLA_ASIGNATURAS.NOMBRE, "id="+asignatura.getId(), null);
    }
}
