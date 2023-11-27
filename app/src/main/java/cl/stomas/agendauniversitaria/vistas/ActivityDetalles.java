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

import java.util.ArrayList;
import java.util.List;

import cl.stomas.agendauniversitaria.R;

public class ActivityDetalles extends AppCompatActivity {
    //y aqui se recibirian cada dato según el elemento del RecyclerView seleccionado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        Bundle parametros =getIntent().getExtras();


        String trab =  parametros.getString("trab");
        String asig = parametros.getString("asig");
        String por = parametros.getString("por");
        String stat = parametros.getString("stat");

        TextView trabSet = (TextView) findViewById(R.id.trabSet);
        TextView asigSet = (TextView) findViewById(R.id.asigSet);
        TextView porSet = (TextView) findViewById(R.id.porSet);
        TextView statSet = (TextView) findViewById(R.id.statSet);
        trabSet.setText(trab+ ".");
        asigSet.setText(asig+ ".");
        porSet.setText(por+ ".");
        statSet.setText(stat+ ".");

        //Este boton seria para volver hacia atras

        Button buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                finishActivity(0);
            }
        });


    }
}