package cl.stomas.agendauniversitaria;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cl.stomas.agendauniversitaria.db.Config;
import cl.stomas.agendauniversitaria.db.DB;
import cl.stomas.agendauniversitaria.modelos.Carrera;
import cl.stomas.agendauniversitaria.modelos.Semestre;
import cl.stomas.agendauniversitaria.vistas.AgendaActivity;
import cl.stomas.agendauniversitaria.vistas.AgregarSemestreActivity;
import cl.stomas.agendauniversitaria.vistas.SeleccionarCarreraActivity;

public class MainActivity extends AppCompatActivity {
    private Config config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initApplicationState();
        }

        Button btnAgenda = findViewById(R.id.btnAgenda);

        btnAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AgendaActivity.class);
                startActivity(intent);
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initApplicationState(){
        config = Config.getConfig(this);
        config.load();
        // Seleccionar carrera
        if(config.getIdCarrera() < 0){
            if(DB.carreras(this).getAll().size() <= 0){
                // Crear Carrera
                Intent add_semestre_intent = new Intent(MainActivity.this, AgregarSemestreActivity.class);
                startActivity(add_semestre_intent);
            }else{
                // Seleccionar Carrera
                Intent sel_carrera_intent = new Intent(MainActivity.this, SeleccionarCarreraActivity.class);
                startActivity(sel_carrera_intent);
            }
        }
    }
}