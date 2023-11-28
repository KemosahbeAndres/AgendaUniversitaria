package cl.stomas.agendauniversitaria.vistas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

import cl.stomas.agendauniversitaria.MainActivity;
import cl.stomas.agendauniversitaria.R;
import cl.stomas.agendauniversitaria.controladores.CarreraController;
import cl.stomas.agendauniversitaria.db.Config;
import cl.stomas.agendauniversitaria.db.DB;
import cl.stomas.agendauniversitaria.modelos.Asignatura;
import cl.stomas.agendauniversitaria.modelos.Carrera;
import cl.stomas.agendauniversitaria.modelos.Semestre;

public class AddDatesActivity extends AppCompatActivity {

    TextInputEditText txtdate;
    TextInputEditText txtname;
    TextInputEditText txtdescr;
    TextInputEditText txtperc;
    Button buttonadd;
    Button backbutton;
    MaterialAutoCompleteTextView txttipo;
    MaterialAutoCompleteTextView txtmateria;
    MaterialAutoCompleteTextView txtimportance;
    Calendar fechas = Calendar.getInstance();
    ArrayList<Asignatura> asignaturas;
    Config config;
    Carrera carrera;
    CarreraController finder;
    private String[] materias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddates);

        config = Config.getConfig(this);
        config.load();

        finder = new CarreraController(this);

        txtdate = findViewById(R.id.txtfechaentrega);
        txtname = findViewById(R.id.txtnameactivity);
        txtimportance= findViewById(R.id.txtimportancia);
        txttipo = findViewById(R.id.txttrabajo);
        txtmateria = findViewById(R.id.txtmateria);
        txtperc = findViewById(R.id.txtpercent);
        txtdescr = findViewById(R.id.txtdescripcion);
        buttonadd = findViewById(R.id.addbutton);
        backbutton = findViewById(R.id.backbutton);

        carrera = finder.execute(config.getIdCarrera());

        Semestre semestre = carrera.getSemestreActual();

        if(semestre == null){
            Toast.makeText(this, "No hay un semestre activo", Toast.LENGTH_SHORT).show();
            finish();
        }else {
            asignaturas = semestre.getAsignaturas();
            materias = new String[asignaturas.size()];

            for(int i = 0; i < asignaturas.size(); i++){
                materias[i] = asignaturas.get(i).getNombre();
            }

            txtmateria.setSimpleItems(materias);
        }

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                finishActivity(0);
            }
        });
        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtname.getText().toString().equals("")){
                    Toast.makeText(AddDatesActivity.this,"No dejar el campo nombre actividad vacio", Toast.LENGTH_SHORT).show();
                }else if (txtdescr.getText().toString().equals("")){
                    Toast.makeText(AddDatesActivity.this,"No dejar el campo descripcion vacio", Toast.LENGTH_SHORT).show();
                }else if (txtperc.getText().toString().equals("")){
                    Toast.makeText(AddDatesActivity.this,"No dejar el campo porcentaje vacio", Toast.LENGTH_SHORT).show();
                }else if(txtperc.getText().toString().equals("0") || txtperc.getText().toString().equals("00")){
                    Toast.makeText(AddDatesActivity.this,"El porcentaje no puede ser igual a 0 o 00", Toast.LENGTH_SHORT).show();
                } else if (txtdate.getText().toString().equals("")){
                    Toast.makeText(AddDatesActivity.this,"No dejar el campo fecha vacio", Toast.LENGTH_SHORT).show();
                }else if (txtmateria.getText().toString().equals("")){
                    Toast.makeText(AddDatesActivity.this,"No dejar el campo materia vacio", Toast.LENGTH_SHORT).show();
                }else if (txtimportance.getText().toString().equals("")){
                    Toast.makeText(AddDatesActivity.this,"No dejar el campo importancia vacio", Toast.LENGTH_SHORT).show();
                }else if (txttipo.getText().toString().equals("")){
                    Toast.makeText(AddDatesActivity.this,"No dejar el campo tipo vacio", Toast.LENGTH_SHORT).show();
                }else{
                    //aqui añadir la transferencia de los datos a la bd y el intent final

                    Toast.makeText(AddDatesActivity.this,"yippee", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
        txtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EleccionFecha();
            }
        });
        txtimportance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String selectedImportance = editable.toString();
                switch (selectedImportance){
                    case "Bajo":
                        txtimportance.setTypeface(Typeface.DEFAULT_BOLD);
                        txtimportance.setTextColor(Color.GREEN);
                        break;
                    case "Medio":
                        txtimportance.setTypeface(Typeface.DEFAULT_BOLD);
                        txtimportance.setTextColor(Color.MAGENTA);
                        break;
                    case "Alto":
                        txtimportance.setTypeface(Typeface.DEFAULT_BOLD);
                        txtimportance.setTextColor(Color.RED);
                        break;
                }
            }
        });

    }
    private void EleccionFecha(){
        DatePickerDialog select = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                txtdate.setText(year +"/"+(month+1)+"/"+dayOfMonth);
            }
        }, fechas.get(Calendar.YEAR),fechas.get(Calendar.MONTH),fechas.get(Calendar.DAY_OF_MONTH));
        select.show();
    }
}