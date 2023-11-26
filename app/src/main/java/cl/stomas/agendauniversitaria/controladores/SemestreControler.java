package cl.stomas.agendauniversitaria.controladores;

import android.content.Context;

import java.util.ArrayList;

import cl.stomas.agendauniversitaria.db.DB;
import cl.stomas.agendauniversitaria.modelos.Actividad;
import cl.stomas.agendauniversitaria.modelos.Asignatura;
import cl.stomas.agendauniversitaria.modelos.Bloque;
import cl.stomas.agendauniversitaria.modelos.Carrera;
import cl.stomas.agendauniversitaria.modelos.Semestre;

public class SemestreControler {
    private Context ctx;
    public SemestreControler(Context ctx) {
        this.ctx = ctx;
    }

    public Semestre execute(Semestre semestre){
        ArrayList<Asignatura> asignaturas = DB.asignaturas(ctx).getFrom(semestre);
        for(Asignatura asignatura: asignaturas){
            asignatura.addBloques(DB.bloques(ctx).getFrom(asignatura));
            asignatura.addActividades(DB.actividades(ctx).getFrom(asignatura));
        }
        semestre.addAsignaturas(asignaturas);
        return semestre;
    }

    public Semestre execute(int id){
        return this.execute(DB.semestres(ctx).get(id));
    }
}
