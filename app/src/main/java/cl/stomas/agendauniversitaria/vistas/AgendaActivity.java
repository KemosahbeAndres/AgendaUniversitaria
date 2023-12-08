package cl.stomas.agendauniversitaria.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cl.stomas.agendauniversitaria.R;
import cl.stomas.agendauniversitaria.controladores.SemestreControler;
import cl.stomas.agendauniversitaria.db.Config;
import cl.stomas.agendauniversitaria.modelos.Actividad;
import cl.stomas.agendauniversitaria.modelos.Semestre;
import cl.stomas.agendauniversitaria.vistas.dates.AddDatesActivity;

public class AgendaActivity extends AppCompatActivity {
    private ArrayList<Actividad> elements;
    private ListAdapter adapter;
    private Config config;
    private SemestreControler controller;
    private CalendarView calendario;
    private Date fecha_seleccionada;

    private static Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.agendaactionbar);
            actionBar.setSubtitle(R.string.actionbar_subtitle);

        }

        config = Config.getConfig(this);

        controller = new SemestreControler(this);

        FloatingActionButton btnAddEvent = findViewById(R.id.btnAddEvent);

        calendario = findViewById(R.id.calendarView);
        calendario.setDate(new Date().getTime());

        try {
            fecha_seleccionada = new Date(calendario.getDate());
        } catch (Exception e) {
            fecha_seleccionada = new Date();
        }

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                try {
                    fecha_seleccionada = formater.parse(year + "/" + (month + 1) + "/" + dayOfMonth + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
                } catch (ParseException e) {
                    fecha_seleccionada = new Date();
                }
                init();
            }
        });
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AgendaActivity.this, AddDatesActivity.class);
                intent.putExtra("date", fecha_seleccionada);
                startActivity(intent);
            }
        });


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

    public void init(){
        config.load();
        long id = config.getIdSemestre();

        if(id >= 0){
            elements = new ArrayList<>();
            Semestre semestre = controller.execute(id);
            elements.addAll(semestre.getAllActividades(fecha_seleccionada));
            adapter = new ListAdapter(elements, this);
            RecyclerView recyclerView = findViewById(R.id.listRecyclerViw);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }
}