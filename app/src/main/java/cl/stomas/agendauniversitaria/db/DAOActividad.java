package cl.stomas.agendauniversitaria.db;

import android.content.Context;

import java.util.ArrayList;

import cl.stomas.agendauniversitaria.modelos.Actividad;
import cl.stomas.agendauniversitaria.modelos.Asignatura;

public class DAOActividad {
    private Context ctx;

    public DAOActividad(Context ctx) {
        this.ctx = ctx;
    }

    public ArrayList<Actividad> getFrom(Asignatura asignatura){
        return new ArrayList<>();
    }
}
