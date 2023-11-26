package cl.stomas.agendauniversitaria.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import cl.stomas.agendauniversitaria.R;

public class AgendaActivity extends AppCompatActivity {
    List<ListElement> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        FloatingActionButton btnAddEvent = findViewById(R.id.btnAddEvent);
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AgendaActivity.this, AddDatesActivity.class);
                startActivity(intent);
            }
        });

        init();
    }

    public void init(){
        elements = new ArrayList<>();
        elements.add(new ListElement("Expocicion", "Algebra", "15%", "Pendiente"));
        elements.add(new ListElement("Expocicion", "Ingles", "20%", "Pendiente"));
        elements.add(new ListElement("Proyecto", "Programcion Android", "40%", "Entregado"));
        elements.add(new ListElement("Trabajo", "Arquitectura de sistema", "40%", "Pendiente"));
        elements.add(new ListElement("Expocicion", "Emprendimiento", "35%", "Entregado"));

        ListAdapter listAdapter = new ListAdapter(elements, this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerViw);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }
}