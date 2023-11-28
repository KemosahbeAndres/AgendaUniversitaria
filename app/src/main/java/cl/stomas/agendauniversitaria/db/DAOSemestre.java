package cl.stomas.agendauniversitaria.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import cl.stomas.agendauniversitaria.modelos.Carrera;
import cl.stomas.agendauniversitaria.modelos.Semestre;

public class DAOSemestre {
    private DBConnectionManager manager;

    public DAOSemestre(Context context) {
        this.manager = DBConnectionManager.getInstance(context);
    }

    public ArrayList<Semestre> getAll(){
        ArrayList<Semestre> semestres = new ArrayList<>();
        SQLiteDatabase db = manager.getReadableDatabase();
        Cursor rows = db.rawQuery("SELECT * FROM "+ DBContract.TABLA_SEMESTRES.NOMBRE, null);
        while(rows.moveToNext()){
            try{
                int indexID = rows.getColumnIndexOrThrow(DBContract.TABLA_SEMESTRES.COL_ID);
                int indexSTART = rows.getColumnIndexOrThrow(DBContract.TABLA_SEMESTRES.COL_FECHA_INICIO);
                int indexEND = rows.getColumnIndexOrThrow(DBContract.TABLA_SEMESTRES.COL_FECHA_FIN);
                Semestre c = null;
                Date start = new Date(rows.getLong(indexSTART)*1000);
                Date end = new Date(rows.getLong(indexEND)*1000);
                c = new Semestre(
                        rows.getInt(indexID),
                        start,
                        end
                );
                semestres.add(c);

            }catch (Exception e){
                Log.println(Log.ERROR, "[ALL SEMESTRES]", "Encontrado pero no se pudo agregar");
            }
        }
        rows.close();
        return semestres;
    }

    public ArrayList<Semestre> getFrom(Carrera carrera){
        ArrayList<Semestre> semestres = new ArrayList<>();
        SQLiteDatabase db = manager.getReadableDatabase();
        Cursor rows = db.rawQuery(
                "SELECT * FROM "+ DBContract.TABLA_SEMESTRES.NOMBRE,
                null
        );
        while (rows.moveToNext()){
            try{
                int idxID_CARRERA = rows.getColumnIndex(DBContract.TABLA_SEMESTRES.COL_ID_CARRERA);
                long id_carrera = rows.getLong(idxID_CARRERA);
                if(id_carrera != carrera.getId()){
                    continue;
                }
                int indexID = rows.getColumnIndexOrThrow(DBContract.TABLA_SEMESTRES.COL_ID);
                int indexSTART = rows.getColumnIndexOrThrow(DBContract.TABLA_SEMESTRES.COL_FECHA_INICIO);
                int indexEND = rows.getColumnIndexOrThrow(DBContract.TABLA_SEMESTRES.COL_FECHA_FIN);
                Date start = new Date(rows.getLong(indexSTART)*1000);
                Date end = new Date(rows.getLong(indexEND)*1000);
                Semestre s = new Semestre(
                        rows.getLong(indexID),
                        start,
                        end,
                        carrera
                );
                Log.v("[DB SEMESTRES FROM]", "Found semestre for "+ carrera.getNombre());
                semestres.add(s);
                Log.println(Log.ERROR, "[SEMESTRES FROM]", "Encontrado PARa"+ carrera.getNombre());
            }catch (Exception e){
                Log.v("[DB SEMESTRES FROM]", e.toString());
                Log.println(Log.ERROR, "[SEMESTRES FROM]", "Encontrado pero no se pudo agregar");
            }
        }
        rows.close();
        return semestres;
    }

    public Semestre get(int id){
        Semestre semestre = null;
        SQLiteDatabase db = manager.getReadableDatabase();
        Cursor rows = db.rawQuery("SELECT * FROM "+ DBContract.TABLA_SEMESTRES.NOMBRE+ " WHERE id="+id+" LIMIT 1", null);
        if(rows.moveToFirst()){
            try{
                int indexID = rows.getColumnIndexOrThrow(DBContract.TABLA_SEMESTRES.COL_ID);
                int indexSTART = rows.getColumnIndexOrThrow(DBContract.TABLA_SEMESTRES.COL_FECHA_INICIO);
                int indexEND = rows.getColumnIndexOrThrow(DBContract.TABLA_SEMESTRES.COL_FECHA_FIN);

                Date start = new Date(rows.getLong(indexSTART)*1000);
                Date end = new Date(rows.getLong(indexEND)*1000);
                semestre = new Semestre(
                        rows.getLong(indexID),
                        start,
                        end
                );
            }catch (Exception e){}
        }
        rows.close();
        return semestre;
    }

    public long insert(Semestre semestre, Carrera carrera){
        SQLiteDatabase db = manager.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.TABLA_SEMESTRES.COL_ID_CARRERA, carrera.getId());
        values.put(DBContract.TABLA_SEMESTRES.COL_FECHA_INICIO, semestre.getFecha_inicio().getTime()/1000);
        values.put(DBContract.TABLA_SEMESTRES.COL_FECHA_FIN, semestre.getFecha_fin().getTime()/1000);

        return db.insert(DBContract.TABLA_SEMESTRES.NOMBRE, null, values);
    }
    public void update(Semestre semestre) throws Exception{
        SQLiteDatabase db = manager.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(DBContract.TABLA_SEMESTRES.COL_ID_CARRERA, carrera.getId());
        values.put(DBContract.TABLA_SEMESTRES.COL_FECHA_INICIO, semestre.getFecha_inicio().getTime()/1000);
        values.put(DBContract.TABLA_SEMESTRES.COL_FECHA_FIN, semestre.getFecha_fin().getTime()/1000);

        db.update(DBContract.TABLA_SEMESTRES.NOMBRE, values, "id="+semestre.getId(), null);
    }
    public void delete(Semestre semestre){
        SQLiteDatabase db = manager.getWritableDatabase();
        db.delete(DBContract.TABLA_SEMESTRES.NOMBRE, "id="+semestre.getId(), null);

    }
}
