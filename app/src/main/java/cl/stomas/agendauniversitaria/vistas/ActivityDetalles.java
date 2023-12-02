package cl.stomas.agendauniversitaria.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cl.stomas.agendauniversitaria.R;
import cl.stomas.agendauniversitaria.modelos.Actividad;

public class ActivityDetalles extends AppCompatActivity {

    // Y aqui se recibirian cada dato según el elemento del RecyclerView seleccionado
    private Actividad actividad;
    private TextView trabSet;
    private TextView asigSet;
    private TextView porSet;
    private TextView statSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        try {
            Bundle parametros = getIntent().getExtras();
            actividad = (Actividad) parametros.getSerializable("actividad");
            if(actividad == null){
                throw new Exception();
            }
        } catch (Exception e) {
            Toast.makeText(this, "No pudimos obtener la actividad!", Toast.LENGTH_SHORT).show();
            finish();
        }
        Toast.makeText(this, "Actividad lista", Toast.LENGTH_SHORT).show();
        trabSet = (TextView) findViewById(R.id.trabSet);
        asigSet = (TextView) findViewById(R.id.asigSet);
        porSet = (TextView) findViewById(R.id.porSet);
        statSet = (TextView) findViewById(R.id.statSet);
        //Este boton seria para volver hacia atras

        Button buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(actividad != null){
            trabSet.setText(actividad.getNombre());
            asigSet.setText(actividad.getAsignatura().getNombre());
            String porcentaje = actividad.getPorcentaje() + "%";
            porSet.setText(porcentaje);
            if(actividad.completado()){
                statSet.setText("Completado");
            }else{
                statSet.setText("Pendiente");
            }
        }
    }
}