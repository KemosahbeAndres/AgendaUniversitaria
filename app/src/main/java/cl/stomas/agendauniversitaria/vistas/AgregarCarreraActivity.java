package cl.stomas.agendauniversitaria.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cl.stomas.agendauniversitaria.R;
import cl.stomas.agendauniversitaria.db.Config;
import cl.stomas.agendauniversitaria.db.DB;
import cl.stomas.agendauniversitaria.modelos.Carrera;
import cl.stomas.agendauniversitaria.modelos.Semestre;

public class AgregarCarreraActivity extends AppCompatActivity {

    private Config config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_carrera);

        config = Config.getConfig(this);
        config.load();

        EditText editNombre = findViewById(R.id.editName);
        EditText editAnio = findViewById(R.id.editAge);
        EditText editFechaInicio = findViewById(R.id.editStartDate);
        EditText editFechaFin = findViewById(R.id.editEndDate);

        Button btnSave = findViewById(R.id.btnSaveSemestre);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.load();
                int anio = Integer.parseInt(editAnio.getText().toString());
                int actual = Calendar.getInstance().get(Calendar.YEAR);
                if(anio < 2000 || anio > actual){
                    anio = actual;
                }
                Carrera carrera = new Carrera(
                    editNombre.getText().toString(),
                    anio
                );
                long idCarrera = DB.carreras(AgregarCarreraActivity.this).insert(carrera);
                carrera.setId(idCarrera);
                config.setIdCarrera(idCarrera);
                config.save();

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    Date start = Date.from(Instant.parse(editFechaInicio.getText().toString()));
                    Date end = Date.from(Instant.parse(editFechaFin.getText().toString()));

                    Semestre semestre = new Semestre(start, end);
                    long idSemestre = DB.semestres(AgregarCarreraActivity.this).insert(semestre, carrera);
                    config.setIdSemestre(idSemestre);
                    config.save();

                    config.load();
                    Carrera c = DB.carreras(AgregarCarreraActivity.this).get(config.getIdCarrera());
                    if(config.getIdSemestre() < 0){
                        if(DB.semestres(AgregarCarreraActivity.this).getAll().size() <= 0){
                            //Crear Semestre
                            Toast.makeText(AgregarCarreraActivity.this, "Semestre no encontrado!", Toast.LENGTH_SHORT).show();
                            finishActivity(0);
                            finish();
                            System.exit(0);
                        }else{
                            Date hoy = null;
                            hoy = Date.from(Instant.now());
                            Semestre semestreSeleccionado = null;
                            ArrayList<Semestre> semestres = DB.semestres(AgregarCarreraActivity.this).getFrom(c);
                            for (Semestre sem: semestres){
                                if(hoy.before(sem.getFecha_inicio()) && hoy.after(sem.getFecha_fin())){
                                    semestreSeleccionado = sem;
                                }
                            }
                            if(semestreSeleccionado == null){
                                semestreSeleccionado = semestres.get(semestres.size()-1);
                            }
                            config.setIdSemestre(semestreSeleccionado.getId());
                            config.save();

                        }
                    }

                }else{
                    Toast.makeText(AgregarCarreraActivity.this, "Error del SDK!", Toast.LENGTH_SHORT).show();
                }

                finishActivity(0);
            }
        });
    }
}