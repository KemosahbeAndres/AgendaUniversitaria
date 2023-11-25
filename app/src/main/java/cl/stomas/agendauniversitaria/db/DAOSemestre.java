package cl.stomas.agendauniversitaria.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
        Cursor rows = db.rawQuery("SELECT * FROM "+ DBContract.TABLA_CARRERAS.NOMBRE, null);
        while(rows.moveToNext()){
            try{
                int indexID = rows.getColumnIndexOrThrow(DBContract.TABLA_SEMESTRES.COL_ID);
                int indexSTART = rows.getColumnIndexOrThrow(DBContract.TABLA_SEMESTRES.COL_FECHA_INICIO);
                int indexEND = rows.getColumnIndexOrThrow(DBContract.TABLA_SEMESTRES.COL_FECHA_FIN);
                Semestre c = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    c = new Semestre(
                            rows.getInt(indexID),
                            Date.from(Instant.ofEpochSecond(rows.getInt(indexSTART))),
                            Date.from(Instant.ofEpochSecond(rows.getInt(indexEND)))
                    );
                }else {
                    throw new Exception("SDK antiguo!");
                }
                semestres.add(c);

            }catch (Exception e){}
        }
        rows.close();
        return semestres;
    }

    public ArrayList<Semestre> getFrom(Carrera carrera){
        ArrayList<Semestre> semestres = new ArrayList<>();
        SQLiteDatabase db = manager.getReadableDatabase();
        Cursor rows = db.rawQuery(
                "SELECT * FROM "+ DBContract.TABLA_CARRERAS.NOMBRE+ " WHERE ?=?",
                new String[]{
                    DBContract.TABLA_SEMESTRES.COL_ID_CARRERA,
                    String.valueOf(carrera.getId())
                });
        while(rows.moveToNext()){
            try{
                int indexID = rows.getColumnIndexOrThrow(DBContract.TABLA_SEMESTRES.COL_ID);
                int indexSTART = rows.getColumnIndexOrThrow(DBContract.TABLA_SEMESTRES.COL_FECHA_INICIO);
                int indexEND = rows.getColumnIndexOrThrow(DBContract.TABLA_SEMESTRES.COL_FECHA_FIN);
                Semestre c = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    c = new Semestre(
                            rows.getInt(indexID),
                            Date.from(Instant.ofEpochSecond(rows.getInt(indexSTART))),
                            Date.from(Instant.ofEpochSecond(rows.getInt(indexEND))),
                            carrera
                    );
                }else {
                    throw new Exception("SDK antiguo!");
                }
                semestres.add(c);

            }catch (Exception e){}
        }
        rows.close();
        return semestres;
    }
}
