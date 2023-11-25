package cl.stomas.agendauniversitaria.db;

import android.content.Context;

public final class DB {
    private static DAOCarrera daoCarrera;
    private static DAOSemestre daoSemestre;
    private static DAOAsignatura daoAsignatura;
    private static DAOBloque daoBloque;

    public static DAOCarrera carreras(Context context){
        if(daoCarrera == null){
            daoCarrera = new DAOCarrera(context);
        }
        return daoCarrera;
    }

    public static DAOSemestre semestres(Context context) {
        if(daoSemestre == null) {
            daoSemestre = new DAOSemestre(context);
        }
        return daoSemestre;
    }

    public static DAOBloque bloques(Context context) {
        if(daoBloque == null){
            daoBloque = new DAOBloque(context);
        }
        return daoBloque;
    }

    public static DAOAsignatura asignaturas(Context context) {
        if(daoAsignatura == null){
            daoAsignatura = new DAOAsignatura(context);
        }
        return daoAsignatura;
    }
}
