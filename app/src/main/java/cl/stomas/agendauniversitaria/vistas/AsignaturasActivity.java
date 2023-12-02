package cl.stomas.agendauniversitaria.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

import cl.stomas.agendauniversitaria.R;
import cl.stomas.agendauniversitaria.controladores.SemestreControler;
import cl.stomas.agendauniversitaria.db.Config;
import cl.stomas.agendauniversitaria.modelos.Asignatura;
import cl.stomas.agendauniversitaria.modelos.Semestre;

public class AsignaturasActivity extends AppCompatActivity {

    private Config config;
    private SemestreControler controller;
    private AsignaturaArrayAdapter adapter;
    private ArrayList<Asignatura> asignaturas;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignaturas);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {

            // showing the back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setSubtitle("Subtitulo");

        }

        recyclerView = findViewById(R.id.recyclerAsignaturas);

        config = Config.getConfig(this);

        controller = new SemestreControler(this);

        asignaturas = new ArrayList<>();

        adapter = new AsignaturaArrayAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_menu_item){
            Intent intent = new Intent(this, AgregarAsignaturasActivity.class);
            startActivity(intent);
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init(){
        config.load();
        long idSemestre = config.getIdSemestre();
        if(idSemestre >= 0){
            Semestre semestre = controller.execute(idSemestre);
            asignaturas = semestre.getAsignaturas();
            adapter.replaceAll(asignaturas);

        }
    }
}