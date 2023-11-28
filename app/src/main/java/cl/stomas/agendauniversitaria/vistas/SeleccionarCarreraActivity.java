package cl.stomas.agendauniversitaria.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cl.stomas.agendauniversitaria.MainActivity;
import cl.stomas.agendauniversitaria.R;
import cl.stomas.agendauniversitaria.db.DB;
import cl.stomas.agendauniversitaria.modelos.Carrera;
import cl.stomas.agendauniversitaria.db.Config;
import cl.stomas.agendauniversitaria.modelos.Semestre;

public class SeleccionarCarreraActivity extends AppCompatActivity {

    private ArrayList<Carrera> carreras;
    private ArrayList<String> nombres;
    private Config config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_carrera);

        config = Config.getConfig(this);
        config.load();

        nombres = new ArrayList<>();

        if(config.getIdCarrera() >= 0){
            finishActivity(0);
        }

        carreras = DB.carreras(this).getAll();
        for (Carrera carrera: carreras){
            nombres.add(carrera.getNombre());
        }

        ListView vistaCarreras = findViewById(R.id.listaCarreras);

        vistaCarreras.setAdapter(new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                nombres
        ));

        vistaCarreras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nombre = nombres.get(position);
                long idCarrera = 0;
                for (Carrera carrera: carreras){
                    if(nombre.trim().equals(carrera.getNombre().trim())){
                        idCarrera = carrera.getId();
                        break;
                    }
                }
                config.setIdCarrera(idCarrera);
                config.save();
                finish();
            }
        });
    }
}