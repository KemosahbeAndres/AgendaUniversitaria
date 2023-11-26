package cl.stomas.agendauniversitaria.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBConnectionManager extends SQLiteOpenHelper {

    private static DBConnectionManager instance;
    public static synchronized DBConnectionManager getInstance(Context context){
        if(instance == null){
            instance = new DBConnectionManager(context.getApplicationContext());
        }
        return instance;
    }

    public DBConnectionManager(@Nullable Context context) {
        super(context, DBContract.DB_NAME, null, DBContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_carreras_query = "CREATE TABLE IF NOT EXISTS '"+DBContract.TABLA_CARRERAS.NOMBRE+"' (" +
                DBContract.TABLA_CARRERAS.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContract.TABLA_CARRERAS.COL_NOMBRE + " TEXT NOT NULL," +
                DBContract.TABLA_CARRERAS.COL_ANIO + " INTEGER" +
                ");";
        db.execSQL(create_carreras_query);

        String create_semestres_query = "CREATE TABLE IF NOT EXISTS '"+DBContract.TABLA_SEMESTRES.NOMBRE+"' (" +
                DBContract.TABLA_SEMESTRES.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContract.TABLA_SEMESTRES.COL_ID_CARRERA + " INTEGER NOT NULL," +
                DBContract.TABLA_SEMESTRES.COL_FECHA_INICIO + " DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                DBContract.TABLA_SEMESTRES.COL_FECHA_FIN + "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP" +
                ",FOREIGN KEY ("+DBContract.TABLA_SEMESTRES.COL_ID_CARRERA+") " +
                "REFERENCES "+ DBContract.TABLA_CARRERAS.NOMBRE + "("+DBContract.TABLA_CARRERAS.COL_ID+")" +
                "ON DELETE SET NULL ON UPDATE CASCADE" +
                ");";
        db.execSQL(create_semestres_query);

        String create_asignaturas_query = "CREATE TABLE IF NOT EXISTS '"+DBContract.TABLA_ASIGNATURAS.NOMBRE+"' (" +
                DBContract.TABLA_ASIGNATURAS.COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContract.TABLA_ASIGNATURAS.COL_ID_SEMESTRE+" INTEGER," +
                DBContract.TABLA_ASIGNATURAS.COL_NOMBRE+" TEXT NOT NULL DEFAULT 'Asignatura Sin Nombre'," +
                DBContract.TABLA_ASIGNATURAS.COL_DESCRIPCION+" TEXT NOT NULL DEFAULT ''," +
                DBContract.TABLA_ASIGNATURAS.COL_COLOR+" TEXT," +
                DBContract.TABLA_ASIGNATURAS.COL_DOCENTE+" TEXT DEFAULT ''" +
                ",FOREIGN KEY ("+DBContract.TABLA_ASIGNATURAS.COL_ID_SEMESTRE+")" +
                "REFERENCES "+DBContract.TABLA_SEMESTRES.NOMBRE + "("+DBContract.TABLA_SEMESTRES.COL_ID+")" +
                "ON DELETE SET NULL ON UPDATE CASCADE" +
                ");";
        db.execSQL(create_asignaturas_query);

        String create_tipo_bloque_query = "CREATE TABLE IF NOT EXISTS '"+DBContract.TABLA_TIPO_BLOQUE.NOMBRE+"' (" +
                DBContract.TABLA_TIPO_BLOQUE.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBContract.TABLA_TIPO_BLOQUE.COL_NOMBRE+" TEXT NOT NULL DEFAULT 'CATEDRA' " +
                ");";
        db.execSQL(create_tipo_bloque_query);

        String create_bloques_query = "CREATE TABLE IF NOT EXISTS '"+DBContract.TABLA_BLOQUES.NOMBRE+"' (" +
                DBContract.TABLA_BLOQUES.COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBContract.TABLA_BLOQUES.COL_ID_ASIGNATURA+" INTEGER, " +
                DBContract.TABLA_BLOQUES.COL_ID_TIPO + " INTEGER, " +
                DBContract.TABLA_BLOQUES.COL_FECHA+" DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                DBContract.TABLA_BLOQUES.COL_DURACION+" INTEGER DEFAULT "+DBContract.TABLA_BLOQUES.DURACION_DEFAULT+", " +
                DBContract.TABLA_BLOQUES.COL_DIA_SEMANA+" INTEGER DEFAULT "+DBContract.TABLA_BLOQUES.DIA_DEFAULT +
                ",FOREIGN KEY ("+DBContract.TABLA_BLOQUES.COL_ID_ASIGNATURA+")" +
                "REFERENCES "+ DBContract.TABLA_ASIGNATURAS.NOMBRE + "("+DBContract.TABLA_ASIGNATURAS.COL_ID+")" +
                "ON DELETE SET NULL ON UPDATE CASCADE " +
                ",FOREIGN KEY ("+DBContract.TABLA_BLOQUES.COL_ID_TIPO+")" +
                "REFERENCES " + DBContract.TABLA_TIPO_BLOQUE.NOMBRE + "("+DBContract.TABLA_TIPO_BLOQUE.COL_ID+")" +
                "ON DELETE RESTRICT ON UPDATE CASCADE" +
                ");";
        db.execSQL(create_bloques_query);

        String create_tipo_actividad_query = "CREATE TABLE IF NOT EXISTS "+DBContract.TABLA_TIPO_ACTIVIDAD.NOMBRE+ "(" +
                DBContract.TABLA_TIPO_ACTIVIDAD.COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBContract.TABLA_TIPO_ACTIVIDAD.COL_NOMBRE + " TEXT NOT NULL DEFAULT 'EVALUACION'" +
                ");";
        db.execSQL(create_tipo_actividad_query);

        String create_actividades_query = "CREATE TABLE IF NOT EXISTS "+ DBContract.TABLA_ACTIVIDADES.NOMBRE+" (" +
                DBContract.TABLA_ACTIVIDADES.COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBContract.TABLA_ACTIVIDADES.COL_ID_ASIGNATURA+" INTEGER, " +
                DBContract.TABLA_ACTIVIDADES.COL_ID_TIPO+" INTEGER, " +
                DBContract.TABLA_ACTIVIDADES.COL_NOMBRE+" TEXT NOT NULL DEFAULT 'Actividad Sin Nombre', " +
                DBContract.TABLA_ACTIVIDADES.COL_DESCRIPCION+" TEXT NOT NULL DEFAULT '', " +
                DBContract.TABLA_ACTIVIDADES.COL_FECHA+" DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                DBContract.TABLA_ACTIVIDADES.COL_DURACION+" INTEGER DEFAULT 0, " +
                DBContract.TABLA_ACTIVIDADES.COL_IMPORTANCIA+" TEXT DEFAULT 'BAJA', " +
                DBContract.TABLA_ACTIVIDADES.COL_PORCENTAJE+" INTEGER NOT NULL DEFAULT 0, " +
                DBContract.TABLA_ACTIVIDADES.COL_NOTA+" INTEGER DEFAULT 0," +
                DBContract.TABLA_ACTIVIDADES.COL_COMPLETADO+" INTEGER DEFAULT 0 " +
                ",FOREIGN KEY ("+ DBContract.TABLA_ACTIVIDADES.COL_ID_ASIGNATURA+")" +
                "REFERENCES "+ DBContract.TABLA_ASIGNATURAS.NOMBRE + "("+ DBContract.TABLA_ASIGNATURAS.COL_ID+") " +
                "ON DELETE SET NULL ON UPDATE CASCADE " +
                ",FOREIGN KEY ("+ DBContract.TABLA_ACTIVIDADES.COL_ID_TIPO+")" +
                "REFERENCES "+ DBContract.TABLA_ASIGNATURAS.NOMBRE+"("+ DBContract.TABLA_ASIGNATURAS.COL_ID+") " +
                "ON DELETE RESTRICT ON UPDATE CASCADE " +
                ");";
        db.execSQL(create_actividades_query);
        this.insertInitialValues(db);

    }

    private void insertInitialValues(SQLiteDatabase db){
        Cursor tbloques = db.rawQuery("SELECT * FROM "+DBContract.TABLA_TIPO_BLOQUE.NOMBRE, null);
        Cursor tactividades = db.rawQuery("SELECT * FROM "+DBContract.TABLA_TIPO_ACTIVIDAD.NOMBRE, null);
        if(tbloques.getCount() <= 0){
            // Insertar Tipos de Bloques
            ContentValues tipo_bloque = new ContentValues();
            tipo_bloque.put(DBContract.TABLA_TIPO_BLOQUE.COL_NOMBRE, "CATEDRA");
            db.insert(DBContract.TABLA_TIPO_BLOQUE.NOMBRE, null, tipo_bloque);

            tipo_bloque = new ContentValues();
            tipo_bloque.put(DBContract.TABLA_TIPO_BLOQUE.COL_NOMBRE, "EXPOSICION");
            db.insert(DBContract.TABLA_TIPO_BLOQUE.NOMBRE, null, tipo_bloque);

            tipo_bloque = new ContentValues();
            tipo_bloque.put(DBContract.TABLA_TIPO_BLOQUE.COL_NOMBRE, "RECUPERACION");
            db.insert(DBContract.TABLA_TIPO_BLOQUE.NOMBRE, null, tipo_bloque);
        }

        if(tactividades.getCount() <= 0){
            // Insertar Tipos de Actividades
            ContentValues tipo_act = new ContentValues();
            tipo_act.put(DBContract.TABLA_TIPO_ACTIVIDAD.COL_NOMBRE, "EVALUACION");
            db.insert(DBContract.TABLA_TIPO_ACTIVIDAD.NOMBRE, null, tipo_act);

            tipo_act = new ContentValues();
            tipo_act.put(DBContract.TABLA_TIPO_ACTIVIDAD.COL_NOMBRE, "EXPOSICION");
            db.insert(DBContract.TABLA_TIPO_ACTIVIDAD.NOMBRE, null, tipo_act);

            tipo_act = new ContentValues();
            tipo_act.put(DBContract.TABLA_TIPO_ACTIVIDAD.COL_NOMBRE, "TRABAJO");
            db.insert(DBContract.TABLA_TIPO_ACTIVIDAD.NOMBRE, null, tipo_act);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+ DBContract.TABLA_TIPO_ACTIVIDAD.NOMBRE);
            db.execSQL("DROP TABLE IF EXISTS "+ DBContract.TABLA_TIPO_BLOQUE.NOMBRE);
            db.execSQL("DROP TABLE IF EXISTS "+ DBContract.TABLA_ASIGNATURAS.NOMBRE);
            db.execSQL("DROP TABLE IF EXISTS "+ DBContract.TABLA_SEMESTRES.NOMBRE);
            db.execSQL("DROP TABLE IF EXISTS "+ DBContract.TABLA_CARRERAS.NOMBRE);
            db.execSQL("DROP TABLE IF EXISTS "+ DBContract.TABLA_ACTIVIDADES.NOMBRE);
            db.execSQL("DROP TABLE IF EXISTS "+ DBContract.TABLA_BLOQUES.NOMBRE);
            onCreate(db);
        }
    }
}
