package cl.stomas.agendauniversitaria.controladores;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import cl.stomas.agendauniversitaria.db.DB;
import cl.stomas.agendauniversitaria.modelos.Actividad;
import cl.stomas.agendauniversitaria.modelos.Asignatura;
import cl.stomas.agendauniversitaria.modelos.Bloque;
import cl.stomas.agendauniversitaria.modelos.Carrera;
import cl.stomas.agendauniversitaria.modelos.Semestre;

public class SemestreControler {
    private Context context;
    public SemestreControler(Context context) {
        this.context = context;
    }

    public Semestre execute(Semestre semestre){
        ArrayList<Asignatura> asignaturas = DB.asignaturas(context).getFrom(semestre);
        for(Asignatura asignatura: asignaturas){
            //asignatura.addBloques(DB.bloques(context).getFrom(asignatura));
            asignatura.addActividades(DB.actividades(context).getFrom(asignatura));
        }
        semestre.addAsignaturas(asignaturas);
        return semestre;
    }

    public Semestre execute(long id){
        return this.execute(DB.semestres(context).get(id));
    }
}
