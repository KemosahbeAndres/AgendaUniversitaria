package cl.stomas.agendauniversitaria.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import cl.stomas.agendauniversitaria.modelos.Asignatura;
import cl.stomas.agendauniversitaria.modelos.Bloque;
import cl.stomas.agendauniversitaria.modelos.Semestre;

public class DAOBloque {

    public final static class Tipos {
        public final static String RECUPERACION = "RECUPERACION";
        public final static String CATEDRA = "CATEDRA";
        public final static String LABORATORIO = "LABORATORIO";
    }

    private DBConnectionManager manager;

    public DAOBloque(Context context) {
        manager = DBConnectionManager.getInstance(context);
    }

    public ArrayList<Bloque> getAll(){
        ArrayList<Bloque> bloques = new ArrayList<Bloque>();
        SQLiteDatabase db = manager.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+DBContract.TABLA_BLOQUES.NOMBRE, null);
        while(cursor.moveToNext()){
            int idxID = cursor.getColumnIndex(DBContract.TABLA_BLOQUES.COL_ID);
            int idxIDTYPE = cursor.getColumnIndex(DBContract.TABLA_BLOQUES.COL_ID_TIPO);
            int idxDURATION = cursor.getColumnIndex(DBContract.TABLA_BLOQUES.COL_DURACION);
            int idxDAYOFWEEK = cursor.getColumnIndex(DBContract.TABLA_BLOQUES.COL_DIA_SEMANA);
            int idxDATE = cursor.getColumnIndex(DBContract.TABLA_BLOQUES.COL_FECHA);
            Bloque bloque = null;
            // Obtenemos el nombre del tipo de bloque
            Cursor rows = db.rawQuery("SELECT * FROM "+ DBContract.TABLA_TIPO_BLOQUE.NOMBRE+ " WHERE id="+idxIDTYPE, null);
            String tipo = Tipos.CATEDRA;
            if (rows.moveToFirst()){
                int idxTYPE = rows.getColumnIndex(DBContract.TABLA_TIPO_BLOQUE.COL_NOMBRE);
                tipo = String.valueOf(rows.getString(idxTYPE));
            }
            rows.close();
            //Continuacion del codigo para obtener el bloque
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                bloque = new Bloque(
                        cursor.getInt(idxID),
                        tipo,
                        cursor.getInt(idxDURATION),
                        cursor.getInt(idxDAYOFWEEK),
                        Date.from(Instant.ofEpochSecond(cursor.getInt(idxDATE)))
                );
            }
            bloques.add(bloque);
        }
        cursor.close();
        return bloques;
    }
    public ArrayList<Bloque> getFrom(Asignatura asignatura){
        ArrayList<Bloque> bloques = new ArrayList<Bloque>();
        SQLiteDatabase db = manager.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM "+DBContract.TABLA_BLOQUES.NOMBRE + " WHERE ?=?",
                new String[]{
                        DBContract.TABLA_BLOQUES.COL_ID_ASIGNATURA,
                        String.valueOf(asignatura.getId())
                }
        );
        while(cursor.moveToNext()){
            int idxID = cursor.getColumnIndex(DBContract.TABLA_BLOQUES.COL_ID);
            int idxIDTYPE = cursor.getColumnIndex(DBContract.TABLA_BLOQUES.COL_ID_TIPO);
            int idxDURATION = cursor.getColumnIndex(DBContract.TABLA_BLOQUES.COL_DURACION);
            int idxDAYOFWEEK = cursor.getColumnIndex(DBContract.TABLA_BLOQUES.COL_DIA_SEMANA);
            int idxDATE = cursor.getColumnIndex(DBContract.TABLA_BLOQUES.COL_FECHA);
            Bloque bloque = null;
            // Obtenemos el nombre del tipo de bloque
            String tipo = this.nombreTipoBloque(cursor.getInt(idxIDTYPE));
            //Continuacion del codigo para obtener el bloque
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                bloque = new Bloque(
                        cursor.getInt(idxID),
                        tipo,
                        cursor.getInt(idxDURATION),
                        cursor.getInt(idxDAYOFWEEK),
                        Date.from(Instant.ofEpochSecond(cursor.getInt(idxDATE)))
                );
            }
            bloques.add(bloque);
        }
        cursor.close();
        return bloques;
    }

    public Bloque get(int id) throws Exception {
        SQLiteDatabase db = manager.getReadableDatabase();
        Bloque bloque = null;
        Cursor cursor = db.rawQuery("SELECT * FROM "+ DBContract.TABLA_BLOQUES.NOMBRE +" WHERE id="+id+ " LIMIT 1", null);
        if(cursor.moveToFirst()){
            // Si existe el bloque lo obtengo segun su id
            int idxID = cursor.getColumnIndex(DBContract.TABLA_BLOQUES.COL_ID);
            int idxIDTYPE = cursor.getColumnIndex(DBContract.TABLA_BLOQUES.COL_ID_TIPO);
            int idxDURATION = cursor.getColumnIndex(DBContract.TABLA_BLOQUES.COL_DURACION);
            int idxDAYOFWEEK = cursor.getColumnIndex(DBContract.TABLA_BLOQUES.COL_DIA_SEMANA);
            int idxDATE = cursor.getColumnIndex(DBContract.TABLA_BLOQUES.COL_FECHA);
            // Obtenemos el nombre del tipo de bloque
            String tipo = this.nombreTipoBloque(cursor.getInt(idxIDTYPE));
            //Continuacion del codigo para obtener el bloque
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                bloque = new Bloque(
                        cursor.getInt(idxID),
                        tipo,
                        cursor.getInt(idxDURATION),
                        cursor.getInt(idxDAYOFWEEK),
                        Date.from(Instant.ofEpochSecond(cursor.getInt(idxDATE)))
                );
            }else{
                throw new Exception("No se puede obtener bloque, SDK antiguo!");
            }
        }
        cursor.close();
        return bloque;
    }

    public void insert(Bloque bloque, Asignatura asignatura){
        SQLiteDatabase db = manager.getWritableDatabase();
        int typeID = this.idTipoBloque(bloque.getTipo());
        if(typeID < 0){
            typeID = this.idTipoBloque(Tipos.CATEDRA);
        }
        ContentValues values = new ContentValues();
        values.put(DBContract.TABLA_BLOQUES.COL_ID_ASIGNATURA, asignatura.getId());
        values.put(DBContract.TABLA_BLOQUES.COL_ID_TIPO, typeID);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            values.put(DBContract.TABLA_BLOQUES.COL_FECHA, bloque.getFecha().toInstant().getEpochSecond());
        }
        values.put(DBContract.TABLA_BLOQUES.COL_DURACION, bloque.getDuracion());
        values.put(DBContract.TABLA_BLOQUES.COL_DIA_SEMANA, bloque.getDia_semana());

        db.insert(DBContract.TABLA_BLOQUES.NOMBRE, null, values);
    }
    public void update(Bloque bloque){
        SQLiteDatabase db = manager.getWritableDatabase();
        int typeID = this.idTipoBloque(bloque.getTipo());
        if(typeID < 0){
            typeID = this.idTipoBloque(Tipos.CATEDRA);
        }
        ContentValues values = new ContentValues();
        values.put(DBContract.TABLA_BLOQUES.COL_ID_TIPO, typeID);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            values.put(DBContract.TABLA_BLOQUES.COL_FECHA, bloque.getFecha().toInstant().getEpochSecond());
        }
        values.put(DBContract.TABLA_BLOQUES.COL_DURACION, bloque.getDuracion());
        values.put(DBContract.TABLA_BLOQUES.COL_DIA_SEMANA, bloque.getDia_semana());

        db.update(DBContract.TABLA_BLOQUES.NOMBRE, values, "id="+bloque.getId(), null);
    }
    public void delete(Bloque bloque){
        SQLiteDatabase db = manager.getWritableDatabase();
        db.delete(DBContract.TABLA_BLOQUES.NOMBRE, "id="+bloque.getId(), null);
    }

    private int idTipoBloque(String tipo){
        String value = tipo.trim().toUpperCase();
        int id = -1;
        SQLiteDatabase db = manager.getReadableDatabase();
        Cursor rows = null;
        try {
            rows = db.rawQuery("SELECT * FROM "+ DBContract.TABLA_TIPO_BLOQUE.NOMBRE+ " WHERE "+DBContract.TABLA_TIPO_BLOQUE.COL_NOMBRE+"="+value + " LIMIT 1", null);
            if (rows.moveToFirst()){
                int idxID = rows.getColumnIndex(DBContract.TABLA_TIPO_BLOQUE.COL_ID);
                id = rows.getInt(idxID);
            }
            rows.close();
        }catch(Exception e){
            if(rows != null){
                rows.close();
            }
        }
        return id;
    }

    private String nombreTipoBloque(int id){
        String tipo = "";
        SQLiteDatabase db = manager.getReadableDatabase();
        Cursor rows = null;
        try {
            rows = db.rawQuery("SELECT * FROM "+ DBContract.TABLA_TIPO_BLOQUE.NOMBRE+ " WHERE id="+id + " LIMIT 1", null);
            if (rows.moveToFirst()){
                int idxTYPE = rows.getColumnIndex(DBContract.TABLA_TIPO_BLOQUE.COL_NOMBRE);
                tipo = String.valueOf(rows.getString(idxTYPE));
            }
            rows.close();
        }catch (Exception e){
            if (rows != null){
                rows.close();
            }
        }
        return tipo;
    }
}
