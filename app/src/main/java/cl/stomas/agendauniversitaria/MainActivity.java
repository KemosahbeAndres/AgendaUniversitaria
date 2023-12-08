package cl.stomas.agendauniversitaria;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import cl.stomas.agendauniversitaria.modelos.Semestre;
import cl.stomas.agendauniversitaria.vistas.AgendaActivity;
import cl.stomas.agendauniversitaria.vistas.AgregarAsignaturasActivity;
import cl.stomas.agendauniversitaria.vistas.AgregarCarreraActivity;
import cl.stomas.agendauniversitaria.vistas.AsignaturasActivity;
import cl.stomas.agendauniversitaria.vistas.CarreraActivity;
import cl.stomas.agendauniversitaria.vistas.ListAdapter;
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
    private TextView txtFechaHoy, txtCarrera, promedioGral, promedioSemetre;
    private RecyclerView activityList;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        finder = new CarreraController(this);
        config = Config.getConfig(this);

        initApplicationState();

        config.load();

        txtFechaHoy = findViewById(R.id.txtDia);
        txtCarrera = findViewById(R.id.txtCarrera);
        Button btnAgenda = findViewById(R.id.btnAbrirAgenda);
        promedioGral = findViewById(R.id.txtNotaGeneral);
        promedioSemetre = findViewById(R.id.txtNotaSemestre);
        activityList = findViewById(R.id.recyclerMain);

        btnAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AgendaActivity.class);
                startActivity(intent);
            }
        });

        Button btnAsignatura = findViewById(R.id.btnAbrirSemestre);
        btnAsignatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AsignaturasActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.carreras_menu_item){
            Intent intent = new Intent(MainActivity.this, SeleccionarCarreraActivity.class);
            startActivity(intent);
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        initMainView();
    }

    private void initMainView(){
        config = Config.getConfig(this);
        config.load();
        promedioSemetre.setText("0");
        promedioGral.setText("0");
        Carrera carrera = finder.execute(config.getIdCarrera());
        //Carrera carrera = DB.carreras(this).get(config.getIdCarrera());
        if(carrera != null){
            txtCarrera.setText(carrera.getNombre());
            Semestre semestre = carrera.getSemestreActual();
            if(semestre != null){
                long id = semestre.getId();
                config.setIdSemestre(id);
                config.save();
                promedioSemetre.setText(String.valueOf(semestre.promedio()));
                // Rellenamos el recycler view
                adapter = new ListAdapter(carrera.getSemestreActual().getAllActividadesDesde(new Date()), this);
                activityList.setLayoutManager(new LinearLayoutManager(this));
                activityList.setAdapter(adapter);
            }else{
                Toast.makeText(this, "No hay un semestre seleccionado!", Toast.LENGTH_SHORT).show();
            }
            promedioGral.setText(String.valueOf(carrera.promedio()));
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        int dia_semana = calendar.get(Calendar.DAY_OF_WEEK);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        txtFechaHoy.setText(dias[dia_semana]+" "+(dia)+" de "+meses[mes]);
    }

    private void initApplicationState(){
        config = Config.getConfig(this);
        config.load();
        // Verificamos si existen carreras en la base de datos
        if(DB.carreras(this).getAll().size() <= 0){
            // Si no existen entonces creamos una Carrera y la seleccionamos
            Intent add_semestre_intent = new Intent(MainActivity.this, AgregarCarreraActivity.class);
            startActivity(add_semestre_intent);
        }else{
            // Si hay carreras en la base de datos entonces verificamos seleccion
            if(config.getIdCarrera() < 0) { // No hay carrera seleccionada en la configuracion entonces pedimos una
                Intent sel_carrera_intent = new Intent(MainActivity.this, SeleccionarCarreraActivity.class);
                startActivity(sel_carrera_intent);
            }else{ // Hay una carrera seleccionada entonces
                // Verificamos que el id seleccionado sea correcto y exista en la base de datos
                if(DB.carreras(this).get(config.getIdCarrera()) == null){
                    // Si no existe entonces la creamos y seleccionamos
                    Intent add_semestre_intent = new Intent(MainActivity.this, AgregarCarreraActivity.class);
                    startActivity(add_semestre_intent);
                }
                // Si existe entonces podemos proseguir
            }
        }
        config.load(); // Recargamos cualquier configuracion que haya sido modificada!
    }
}