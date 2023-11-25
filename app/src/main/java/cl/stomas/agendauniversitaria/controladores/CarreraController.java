package cl.stomas.agendauniversitaria.controladores;

import android.content.Context;

import cl.stomas.agendauniversitaria.db.Repo;
import cl.stomas.agendauniversitaria.modelos.Carrera;

public final class CarreraController {
    private Context ctx;
    private SemestreControler controller;
    public CarreraController(Context ctx) {
        this.ctx = ctx;
        this.controller = new SemestreControler(ctx);
    }

    public Carrera execute(int id){
        Carrera carrera = Repo.carreras(ctx).get(id);
        carrera.addSemestres(this.controller.execute(carrera));
        return carrera;
    }
}
