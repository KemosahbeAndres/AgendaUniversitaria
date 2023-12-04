package cl.stomas.agendauniversitaria.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import cl.stomas.agendauniversitaria.controladores.CarreraController;
import cl.stomas.agendauniversitaria.db.DB;
import cl.stomas.agendauniversitaria.modelos.Carrera;
import cl.stomas.agendauniversitaria.db.Config;
import cl.stomas.agendauniversitaria.modelos.Semestre;

public class SeleccionarCarreraActivity extends AppCompatActivity {

    private ArrayList<Carrera> carreras;
    private ArrayList<String> nombres;
    private Config config;
    private ListView vistaCarreras;
    private CarreraController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_carrera);

        ActionBar actionbar = getSupportActionBar();

        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true);
        }

        vistaCarreras = findViewById(R.id.listaCarreras);

        config = Config.getConfig(this);

        controller = new CarreraController(this);

        vistaCarreras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                config.load();
                String nombre = nombres.get(position);
                long idCarrera = 0;
                for (Carrera c: carreras){
                    if(nombre.trim().equals(c.getNombre().trim())){
                        idCarrera = c.getId();
                        break;
                    }
                }
                config.setIdCarrera(idCarrera);
                config.save();

                Carrera carrera = controller.execute(idCarrera);

                if(carrera != null){
                    Semestre semestre = carrera.getSemestreActual();
                    if(semestre != null){
                        config.setIdSemestre(semestre.getId());
                        config.save();
                    }else{
                        config.setIdSemestre(-1);
                        config.save();
                    }
                }

                config.load();

                if(config.getIdSemestre() < 0){
                    Intent intent = new Intent(SeleccionarCarreraActivity.this, NuevoSemestreActivity.class);
                    startActivity(intent);
                    return;
                }
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    private void init(){
        nombres = new ArrayList<>();

        carreras = DB.carreras(this).getAll();
        for (Carrera carrera: carreras){
            nombres.add(carrera.getNombre());
        }

        vistaCarreras.setAdapter(new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                nombres
        ));
    }

    @Override
    public boolean onSupportNavigateUp() {
        config.load();
        if(config.getIdCarrera() >= 0){
            finish();
        }else {
            Toast.makeText(this, "Debes seleccionar una carrera!", Toast.LENGTH_SHORT).show();
        }
        return super.onSupportNavigateUp();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.seleccion_carrera_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_carrera_menu_item){
            Intent intent = new Intent(SeleccionarCarreraActivity.this, AgregarCarreraActivity.class);
            startActivity(intent);
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }
}