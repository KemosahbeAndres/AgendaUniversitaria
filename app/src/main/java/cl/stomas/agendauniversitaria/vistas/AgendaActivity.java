package cl.stomas.agendauniversitaria.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cl.stomas.agendauniversitaria.R;
import cl.stomas.agendauniversitaria.controladores.SemestreControler;
import cl.stomas.agendauniversitaria.db.Config;
import cl.stomas.agendauniversitaria.modelos.Actividad;
import cl.stomas.agendauniversitaria.modelos.Semestre;

public class AgendaActivity extends AppCompatActivity {
    private ArrayList<Actividad> elements;
    private ListAdapter adapter;
    private Config config;
    private SemestreControler controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        config = Config.getConfig(this);

        controller = new SemestreControler(this);

        FloatingActionButton btnAddEvent = findViewById(R.id.btnAddEvent);
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AgendaActivity.this, AddDatesActivity.class);
                startActivity(intent);
            }
        });

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

    public void init(){
        config.load();
        elements = new ArrayList<>();

        long id = config.getIdSemestre();
        if(id >= 0){
            Semestre semestre = controller.execute(id);

            elements.addAll(semestre.getAllActividades());

            Toast.makeText(this, "Found: "+elements.size(), Toast.LENGTH_SHORT).show();
            adapter = new ListAdapter(elements, this);
            RecyclerView recyclerView = findViewById(R.id.listRecyclerViw);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }
}