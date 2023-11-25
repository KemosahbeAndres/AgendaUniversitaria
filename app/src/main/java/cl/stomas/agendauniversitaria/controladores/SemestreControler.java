package cl.stomas.agendauniversitaria.controladores;

import android.content.Context;

import java.util.ArrayList;

import cl.stomas.agendauniversitaria.db.DB;
import cl.stomas.agendauniversitaria.modelos.Asignatura;
import cl.stomas.agendauniversitaria.modelos.Bloque;
import cl.stomas.agendauniversitaria.modelos.Carrera;
import cl.stomas.agendauniversitaria.modelos.Semestre;

public class SemestreControler {
    private Context ctx;
    public SemestreControler(Context ctx) {
        this.ctx = ctx;
    }

    public ArrayList<Semestre> execute(Carrera carrera){
        ArrayList<Semestre> semestres = DB.semestres(ctx).getFrom(carrera);
        for (Semestre semestre: semestres){
            ArrayList<Asignatura> asignaturas = DB.asignaturas().getFrom(semestre);

        }
        return semestres;
    }
}
