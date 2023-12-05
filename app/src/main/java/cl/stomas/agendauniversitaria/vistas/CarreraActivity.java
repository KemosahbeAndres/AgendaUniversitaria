package cl.stomas.agendauniversitaria.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import cl.stomas.agendauniversitaria.R;
import cl.stomas.agendauniversitaria.controladores.CarreraController;
import cl.stomas.agendauniversitaria.db.Config;
import cl.stomas.agendauniversitaria.modelos.Carrera;
import cl.stomas.agendauniversitaria.modelos.Semestre;

public class CarreraActivity extends AppCompatActivity {
    private TextView txtNombre;
    private TextView txtAnio;
    private ListView listSemestres;

    private Config config;
    private CarreraController finder;

    private Carrera carrera;
    private ArrayList<String> nombres;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrera);

        config = Config.getConfig(this);
        config.load();

        finder = new CarreraController(this);

        nombres = new ArrayList<String>();

        txtNombre = findViewById(R.id.txtNombreCarrera);
        txtAnio = findViewById(R.id.txtAnioCarrera);
        listSemestres = findViewById(R.id.listSemestres);
        FloatingActionButton btnAddSemestre = findViewById(R.id.btnAddSemestre);

        carrera = finder.execute(config.getIdCarrera());

        Toast.makeText(this, "Semestre: "+carrera.getSemestreActual().getId(), Toast.LENGTH_SHORT).show();

        txtNombre.setText(carrera.getNombre());
        txtAnio.setText(String.valueOf(carrera.getAnio()));
        ArrayList<Semestre> semestres = carrera.getSemestres();

        for (Semestre semestre: semestres){
            nombres.add((semestres.indexOf(semestre)+1)+" => ");
        }
        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                nombres
        );

        listSemestres.setAdapter(adapter);

        btnAddSemestre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarreraActivity.this, NuevoSemestreActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        carrera = finder.execute(config.getIdCarrera());

        nombres.clear();

        for (Semestre semestre: carrera.getSemestres()){
            nombres.add(semestre.getFecha_inicio().toString() + " || "+ semestre.getFecha_fin().toString());
        }

        adapter.clear();
        adapter.addAll(nombres);
        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                nombres
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        carrera = finder.execute(config.getIdCarrera());

        nombres.clear();

        for (Semestre semestre: carrera.getSemestres()){
            nombres.add((nombres.indexOf(semestre)+1)+" => ");
        }

        adapter.clear();
        adapter.addAll(nombres);
        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                nombres
        );
    }
}