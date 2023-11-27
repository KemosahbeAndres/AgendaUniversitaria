package cl.stomas.agendauniversitaria.controladores;

import android.content.Context;

import java.util.ArrayList;

import cl.stomas.agendauniversitaria.db.DB;
import cl.stomas.agendauniversitaria.modelos.Carrera;
import cl.stomas.agendauniversitaria.modelos.Semestre;

public final class CarreraController {
    private Context ctx;
    private SemestreControler semestreControler;
    public CarreraController(Context ctx) {
        this.ctx = ctx;
        this.semestreControler = new SemestreControler(ctx);
    }

    public Carrera execute(Carrera carrera){
        ArrayList<Semestre> semestres = new ArrayList<>();
        for (Semestre semestre: DB.semestres(ctx).getFrom(carrera)){
            semestres.add(semestreControler.execute(semestre));
        }
        carrera.addSemestres(semestres);
        return carrera;
    }
    public Carrera execute(long id){
        return this.execute(DB.carreras(ctx).get(id));
    }
}
