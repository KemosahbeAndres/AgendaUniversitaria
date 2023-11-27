package cl.stomas.agendauniversitaria;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import cl.stomas.agendauniversitaria.controladores.CarreraController;
import cl.stomas.agendauniversitaria.db.Config;
import cl.stomas.agendauniversitaria.db.DB;
import cl.stomas.agendauniversitaria.modelos.Carrera;
import cl.stomas.agendauniversitaria.vistas.AgendaActivity;
import cl.stomas.agendauniversitaria.vistas.AgregarCarreraActivity;
import cl.stomas.agendauniversitaria.vistas.SeleccionarCarreraActivity;

public class MainActivity extends AppCompatActivity {
    private Config config;
    private CarreraController finder;
    private final static String[] dias = new String[]{
            "Sabado", "Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes"
    };
    private final static String[] meses = new String[]{
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initApplicationState();
        }

        TextView txtFechaHoy = findViewById(R.id.txtDia);
        TextView txtCarrera = findViewById(R.id.txtCarrera);
        Button btnAgenda = findViewById(R.id.btnAgenda);

        finder = new CarreraController(this);

        btnAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AgendaActivity.class);
                startActivity(intent);
            }
        });

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        int dia_semana = calendar.get(Calendar.DAY_OF_WEEK);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        txtFechaHoy.setText(dias[dia_semana]+" "+dia+" de "+meses[mes]);

        Carrera carrera = finder.execute(config.getIdCarrera());
        //Carrera carrera = DB.carreras(this).get(config.getIdCarrera());
        txtCarrera.setText(carrera.getNombre());

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initApplicationState(){
        config = Config.getConfig(this);
        config.load();
        // Seleccionar carrera
        if(config.getIdCarrera() < 0){
            if(DB.carreras(this).getAll().size() <= 0){
                // Crear Carrera
                Intent add_semestre_intent = new Intent(MainActivity.this, AgregarCarreraActivity.class);
                startActivity(add_semestre_intent);
            }else{
                // Seleccionar Carrera
                Intent sel_carrera_intent = new Intent(MainActivity.this, SeleccionarCarreraActivity.class);
                startActivity(sel_carrera_intent);
            }
        }
        config.load();
    }
}