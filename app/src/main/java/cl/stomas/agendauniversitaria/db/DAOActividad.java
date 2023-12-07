package cl.stomas.agendauniversitaria.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import cl.stomas.agendauniversitaria.modelos.Actividad;
import cl.stomas.agendauniversitaria.modelos.Asignatura;

public class DAOActividad {

    private DBConnectionManager manager;

    public DAOActividad(Context context) {
        this.manager = new DBConnectionManager(context);
    }
    public ArrayList<Actividad> getAll(){
        ArrayList<Actividad> actividades = new ArrayList<>();
        SQLiteDatabase db = manager.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DBContract.TABLA_ACTIVIDADES.NOMBRE,
                null
        );
        while(cursor.moveToNext()){
            Actividad actividad = null;
            int idxID = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_ID);
            int idxTYPEID = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_ID_TIPO);
            int idxNAME = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_NOMBRE);
            int idxDESC = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_DESCRIPCION);
            int idxDATE = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_FECHA);
            int idxDURATION = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_DURACION);
            int idxIMPORTANCE = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_IMPORTANCIA);
            int idxCOMPLETED = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_COMPLETADO);
            int idxPERCENT = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_PORCENTAJE);
            int idxVALUE = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_NOTA);
            boolean completed = false;
            if(cursor.getInt(idxCOMPLETED) >= 1){
                completed = true;
            }
            actividad = new Actividad(
                    cursor.getInt(idxID),
                    this.getNombreTipo(cursor.getInt(idxTYPEID)),
                    cursor.getString(idxNAME),
                    cursor.getString(idxDESC),
                    new Date(cursor.getLong(idxDATE)*1000),
                    cursor.getInt(idxDURATION),
                    cursor.getString(idxIMPORTANCE),
                    completed,
                    cursor.getInt(idxPERCENT),
                    cursor.getInt(idxVALUE)
            );
            actividades.add(actividad);
        }
        cursor.close();
        return actividades;
    }
    public ArrayList<Actividad> getFrom(Asignatura asignatura) {
        ArrayList<Actividad> actividades = new ArrayList<>();
        SQLiteDatabase db = manager.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DBContract.TABLA_ACTIVIDADES.NOMBRE,null
                );
        while(cursor.moveToNext()){
            int idxID_ASIG = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_ID_ASIGNATURA);
            long id_asignatura = cursor.getLong(idxID_ASIG);
            if(id_asignatura != asignatura.getId()){
                continue;
            }
            Actividad actividad = null;
            int idxID = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_ID);
            int idxTYPEID = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_ID_TIPO);
            int idxNAME = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_NOMBRE);
            int idxDESC = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_DESCRIPCION);
            int idxDATE = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_FECHA);
            int idxDURATION = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_DURACION);
            int idxIMPORTANCE = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_IMPORTANCIA);
            int idxCOMPLETED = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_COMPLETADO);
            int idxPERCENT = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_PORCENTAJE);
            int idxVALUE = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_NOTA);
            boolean completed = false;
            if(cursor.getInt(idxCOMPLETED) >= 1){
                completed = true;
            }
            actividad = new Actividad(
                    cursor.getInt(idxID),
                    this.getNombreTipo(cursor.getInt(idxTYPEID)),
                    cursor.getString(idxNAME),
                    cursor.getString(idxDESC),
                    new Date(cursor.getLong(idxDATE)*1000),
                    cursor.getInt(idxDURATION),
                    cursor.getString(idxIMPORTANCE),
                    completed,
                    cursor.getInt(idxPERCENT),
                    cursor.getInt(idxVALUE)
            );
            actividades.add(actividad);
        }
        cursor.close();
        return actividades;
    }
    public Actividad get(int id){
        Actividad actividad = null;
        SQLiteDatabase db = manager.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DBContract.TABLA_ACTIVIDADES.NOMBRE+ " WHERE id="+id+" LIMIT 1",
                null
        );
        if(cursor.moveToFirst()){
            int idxID = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_ID);
            int idxTYPEID = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_ID_TIPO);
            int idxNAME = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_NOMBRE);
            int idxDESC = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_DESCRIPCION);
            int idxDATE = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_FECHA);
            int idxDURATION = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_DURACION);
            int idxIMPORTANCE = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_IMPORTANCIA);
            int idxCOMPLETED = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_COMPLETADO);
            int idxPERCENT = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_PORCENTAJE);
            int idxVALUE = cursor.getColumnIndex(DBContract.TABLA_ACTIVIDADES.COL_NOTA);
            boolean completed = false;
            if(cursor.getInt(idxCOMPLETED) >= 1){
                completed = true;
            }
            actividad = new Actividad(
                    cursor.getInt(idxID),
                    this.getNombreTipo(cursor.getInt(idxTYPEID)),
                    cursor.getString(idxNAME),
                    cursor.getString(idxDESC),
                    new Date(cursor.getLong(idxDATE)*1000),
                    cursor.getInt(idxDURATION),
                    cursor.getString(idxIMPORTANCE),
                    completed,
                    cursor.getInt(idxPERCENT),
                    cursor.getInt(idxVALUE)
            );
        }
        cursor.close();
        return actividad;
    }
    public long insert(Actividad actividad, Asignatura asignatura){
        SQLiteDatabase db = manager.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.TABLA_ACTIVIDADES.COL_NOMBRE, actividad.getNombre().trim());
        values.put(DBContract.TABLA_ACTIVIDADES.COL_DESCRIPCION, actividad.getDescripcion().trim());
        values.put(DBContract.TABLA_ACTIVIDADES.COL_FECHA, actividad.getFecha().getTime()/1000);
        values.put(DBContract.TABLA_ACTIVIDADES.COL_DURACION, actividad.getDuracion());
        values.put(DBContract.TABLA_ACTIVIDADES.COL_IMPORTANCIA, actividad.getImportancia());
        int completedVal = 0;
        if(actividad.completado()){
            completedVal = 1;
        }
        values.put(DBContract.TABLA_ACTIVIDADES.COL_COMPLETADO, completedVal);
        values.put(DBContract.TABLA_ACTIVIDADES.COL_PORCENTAJE, actividad.getPorcentaje());
        values.put(DBContract.TABLA_ACTIVIDADES.COL_NOTA, actividad.getNota());
        values.put(DBContract.TABLA_ACTIVIDADES.COL_ID_TIPO, this.getIdTipo(actividad.getTipo()));
        values.put(DBContract.TABLA_ACTIVIDADES.COL_ID_ASIGNATURA, asignatura.getId());

        return db.insert(DBContract.TABLA_ACTIVIDADES.NOMBRE, null, values);
    }
    public int update(Actividad actividad){
        SQLiteDatabase db = manager.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.TABLA_ACTIVIDADES.COL_NOMBRE, actividad.getNombre().trim());
        values.put(DBContract.TABLA_ACTIVIDADES.COL_DESCRIPCION, actividad.getDescripcion().trim());
        values.put(DBContract.TABLA_ACTIVIDADES.COL_FECHA, actividad.getFecha().getTime()/1000);
        values.put(DBContract.TABLA_ACTIVIDADES.COL_DURACION, actividad.getDuracion());
        values.put(DBContract.TABLA_ACTIVIDADES.COL_IMPORTANCIA, actividad.getImportancia());
        int completedVal = 0;
        if(actividad.completado()){
            completedVal = 1;
        }
        values.put(DBContract.TABLA_ACTIVIDADES.COL_COMPLETADO, completedVal);
        values.put(DBContract.TABLA_ACTIVIDADES.COL_PORCENTAJE, actividad.getPorcentaje());
        values.put(DBContract.TABLA_ACTIVIDADES.COL_NOTA, actividad.getNota());
        long tipo = this.getIdTipo(actividad.getTipo());
        if(tipo < 0){
            tipo = this.getIdTipo(Actividad.Tipo.ACTIVIDAD);
        }
        values.put(DBContract.TABLA_ACTIVIDADES.COL_ID_TIPO, tipo);

        return db.update(DBContract.TABLA_ACTIVIDADES.NOMBRE, values, "id="+actividad.getId(), null);
    }
    public void delete(Actividad actividad){
        SQLiteDatabase db = manager.getWritableDatabase();
        db.delete(DBContract.TABLA_ACTIVIDADES.NOMBRE, "id="+actividad.getId(), null);
    }
    private long getIdTipo(String nombre) {
        long id = -1;
        SQLiteDatabase db = manager.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + DBContract.TABLA_TIPO_ACTIVIDAD.NOMBRE, null);
            while(cursor.moveToNext()) {
                int idxID = cursor.getColumnIndex(DBContract.TABLA_TIPO_ACTIVIDAD.COL_ID);
                int idxNAME = cursor.getColumnIndex(DBContract.TABLA_TIPO_ACTIVIDAD.COL_NOMBRE);
                String name = cursor.getString(idxNAME);
                if(nombre.equals(name)){
                    id = cursor.getLong(idxID);
                    break;
                }
            }
            cursor.close();
        }catch(Exception e){
            if(cursor != null){
                cursor.close();
            }
        }
        return id;
    }

    private String getNombreTipo(long id){
        String tipo = "";
        SQLiteDatabase db = manager.getReadableDatabase();
        Cursor cursor = null;
        try{
            cursor = db.rawQuery(
                    "SELECT * FROM "+ DBContract.TABLA_TIPO_ACTIVIDAD.NOMBRE,
                    null
            );
            while(cursor.moveToNext()){
                int idxID = cursor.getColumnIndex(DBContract.TABLA_TIPO_ACTIVIDAD.COL_ID);
                int idxNAME = cursor.getColumnIndex(DBContract.TABLA_TIPO_ACTIVIDAD.COL_NOMBRE);
                long idRow = cursor.getLong(idxID);
                if(id == idRow){
                    tipo = String.valueOf(cursor.getString(idxNAME));
                    break;
                }
            }
            cursor.close();
        }catch(Exception e){
            if(cursor != null){
                cursor.close();
            }
        }
        return tipo.toUpperCase();
    }

    public String[] allTypes(){
        SQLiteDatabase db = manager.getReadableDatabase();
        String[] tipos;
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBContract.TABLA_TIPO_ACTIVIDAD.NOMBRE, null);
        tipos = new String[cursor.getCount()];
        int index = 0;
        while(cursor.moveToNext()){
            int idxName = cursor.getColumnIndex(DBContract.TABLA_TIPO_ACTIVIDAD.COL_NOMBRE);
            tipos[index] = cursor.getString(idxName);
            index ++;
        }
        cursor.close();
        return tipos;
    }
}
