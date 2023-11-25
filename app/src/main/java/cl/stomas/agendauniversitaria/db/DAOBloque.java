package cl.stomas.agendauniversitaria.db;

import android.content.Context;

import java.util.ArrayList;

import cl.stomas.agendauniversitaria.modelos.Asignatura;
import cl.stomas.agendauniversitaria.modelos.Bloque;
import cl.stomas.agendauniversitaria.modelos.Semestre;

public class DAOBloque {

    public final static class Tipos {

    }

    private DBConnectionManager manager;

    public DAOBloque(Context context) {
        manager = DBConnectionManager.getInstance(context);
    }

    public ArrayList<Bloque> getAll(){
        return new ArrayList<Bloque>();
    }
    public ArrayList<Bloque> getFrom(Asignatura asignatura){
        return new ArrayList<Bloque>();
    }

    public Bloque get(int id){
        return new Bloque();
    }

    public void insert(Asignatura asignatura){

    }
    public void update(Asignatura asignatura){

    }
    public void delete(Asignatura asignatura){

    }
}
