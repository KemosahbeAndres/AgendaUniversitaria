package cl.stomas.agendauniversitaria.controladores;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import cl.stomas.agendauniversitaria.db.DB;
import cl.stomas.agendauniversitaria.modelos.Carrera;
import cl.stomas.agendauniversitaria.modelos.Semestre;

public final class CarreraController {
    private Context context;
    private SemestreControler semestreControler;
    public CarreraController(Context context) {
        this.context = context;
        this.semestreControler = new SemestreControler(context);
    }

    public Carrera execute(Carrera carrera){
        if(carrera == null) return null;
        ArrayList<Semestre> semestres = new ArrayList<>();
        //Toast.makeText(context, "CARRERA: "+carrera.getNombre(), Toast.LENGTH_SHORT).show();
        ArrayList<Semestre> founded = DB.semestres(context).getFrom(carrera);
        //Toast.makeText(context, "SEMESTRES: "+founded.size(), Toast.LENGTH_SHORT).show();
        for (Semestre semestre: founded){
            semestres.add(semestreControler.execute(semestre));
        }
        carrera.addSemestres(semestres);
        return carrera;
    }
    public Carrera execute(long id){
        return this.execute(DB.carreras(context).get(id));
    }
}
