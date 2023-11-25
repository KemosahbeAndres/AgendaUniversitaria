package cl.stomas.agendauniversitaria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cl.stomas.agendauniversitaria.db.DAOCarrera;
import cl.stomas.agendauniversitaria.db.DAOSemestre;
import cl.stomas.agendauniversitaria.modelos.Carrera;
import cl.stomas.agendauniversitaria.modelos.Semestre;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DAOCarrera dao = new DAOCarrera(this);
        DAOSemestre daoSemestre = new DAOSemestre(this);

        ArrayList<Carrera> lista = dao.getAll();

        TextView ncarrera = findViewById(R.id.txtCarrera);
        ncarrera.setText("Carrera: "+lista.get(0).getNombre());

        ArrayList<Semestre> semestres = daoSemestre.getAll();

        TextView csemestres = findViewById(R.id.txtSemestres);
        csemestres.setText("Cantidad Semestres: "+semestres.size());

        Toast.makeText(this, "Cantidad: "+lista.size() , Toast.LENGTH_SHORT).show();
    }
}