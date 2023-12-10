package cl.stomas.agendauniversitaria.db;

import android.content.Context;
import android.content.SharedPreferences;

public final class Config {
    private final static String preferencesName = "local_preferences";
    private final static String keyIdCarrera = "id_carrera";
    private final static String keyIdSemestre = "id_semestre";
    private final static String keyUserName = "username";
    private static Config instance;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    // Configuraciones
    private long idCarrera;
    private long idSemestre;
    private String username;

    // Fin Configuraciones
    private Config(Context context){
        preferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        editor = preferences.edit();
        this.load();
    }
    public static Config getConfig(Context context){
        if(instance == null){
            instance = new Config(context);
        }
        return instance;
    }
    public void load(){
        this.idCarrera = preferences.getLong(keyIdCarrera, -1);
        this.idSemestre = preferences.getLong(keyIdSemestre, -1);
        this.username = preferences.getString(keyUserName, "");
    }

    public boolean save(){
        editor.putLong(keyIdCarrera, this.idCarrera);
        editor.putLong(keyIdSemestre, this.idSemestre);
        editor.putString(keyUserName, this.username);
        return editor.commit();
    }

    public void reset(){
        setIdCarrera(-1);
        setIdSemestre(-1);
        setUsername("");
        this.save();
    }

    public long getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(long idCarrera) {
        this.idCarrera = idCarrera;
    }

    public long getIdSemestre() {
        return idSemestre;
    }

    public void setIdSemestre(long idSemestre) {
        this.idSemestre = idSemestre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
