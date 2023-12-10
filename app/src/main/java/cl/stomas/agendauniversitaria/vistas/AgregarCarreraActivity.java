package cl.stomas.agendauniversitaria.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cl.stomas.agendauniversitaria.R;
import cl.stomas.agendauniversitaria.db.Config;
import cl.stomas.agendauniversitaria.db.DB;
import cl.stomas.agendauniversitaria.modelos.Carrera;
import cl.stomas.agendauniversitaria.modelos.Semestre;

public class AgregarCarreraActivity extends AppCompatActivity {

    private Config config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_carrera);

        config = Config.getConfig(this);
        config.load();

        EditText editNombre = findViewById(R.id.editName);
        EditText editAnio = findViewById(R.id.editAge);
        EditText editFechaInicio = findViewById(R.id.editStartDate);
        EditText editFechaFin = findViewById(R.id.editEndDate);
        EditText editUserName = findViewById(R.id.editUserName);

        editFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EleccionFecha(editFechaInicio);
            }
        });

        editFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EleccionFecha(editFechaFin);
            }
        });

        Button btnSave = findViewById(R.id.btnSaveSemestre);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editUserName.getText().toString().isEmpty()){
                    Toast.makeText(AgregarCarreraActivity.this, "Debes ingresar tu nombre!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editNombre.getText().toString().isEmpty()){
                    Toast.makeText(AgregarCarreraActivity.this, "Debes ingresar un nombre de carrera!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editAnio.getText().toString().isEmpty()){
                    Toast.makeText(AgregarCarreraActivity.this, "Debes ingresar un año valido!", Toast.LENGTH_SHORT).show();
                    return;
                }
                config = Config.getConfig(AgregarCarreraActivity.this);
                config.load();

                config.setUsername(editUserName.getText().toString());

                int anio = Integer.parseInt(editAnio.getText().toString());
                int actual = Calendar.getInstance().get(Calendar.YEAR);
                if(anio < 2000 || anio > actual+2){
                    anio = actual;
                }
                Carrera carrera = new Carrera(
                    editNombre.getText().toString(),
                    anio
                );
                long idCarrera = DB.carreras(AgregarCarreraActivity.this).insert(carrera);
                if (idCarrera >= 0){
                    carrera.setId(idCarrera);
                    config.setIdCarrera(idCarrera);
                    config.save();
                }else{
                    Toast.makeText(AgregarCarreraActivity.this, "No se pudo guardar la carrera!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(editFechaInicio.getText().toString().isEmpty()){
                    Toast.makeText(AgregarCarreraActivity.this, "Debes ingresar una fecha de inicio valida!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(editFechaFin.getText().toString().isEmpty()){
                    Toast.makeText(AgregarCarreraActivity.this, "Debes ingresar una fecha de fin valida!", Toast.LENGTH_SHORT).show();
                    return;
                }
                try{ // Intentamos guardar el semestre con las fechas entregadas
                    SimpleDateFormat sformat = new SimpleDateFormat("yyyy/MM/dd");
                    Date start = sformat.parse(editFechaInicio.getText().toString());
                    Date end = sformat.parse(editFechaFin.getText().toString());
                    Date hoy = new Date();
                    if(start != null && end != null ){
                        Semestre semestre = new Semestre(start, end);
                        long idSemestre = DB.semestres(AgregarCarreraActivity.this).insert(semestre, carrera);
                        if(start.before(hoy) && end.after(hoy) && idSemestre >= 0){
                            config.setIdSemestre(idSemestre);
                            config.save();
                        }else{
                            Toast.makeText(AgregarCarreraActivity.this, "No se pudo guardar el semestre!", Toast.LENGTH_SHORT).show();
                            config.setIdSemestre(-1);
                            config.save();
                        }
                    }else{
                        Toast.makeText(AgregarCarreraActivity.this, "Formato de fechas incorrecto!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    finish();
                }catch(Exception e){
                    Toast.makeText(AgregarCarreraActivity.this, "Formato de fechas incorrecto!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void EleccionFecha(TextView textView){
        Calendar calendario = Calendar.getInstance();
        if(!textView.getText().toString().isEmpty()){
            try{
                SimpleDateFormat sformat = new SimpleDateFormat("yyyy/MM/dd");
                Date date = sformat.parse(textView.getText().toString());
                //Toast.makeText(NuevoSemestreActivity.this, "Fecha: "+date.toString(), Toast.LENGTH_SHORT).show();
                if (date != null) {
                    calendario.setTime(date);
                }
            }catch (Exception e){
                Log.w("[DatePicker]", e.toString());
            }

        }
        DatePickerDialog select = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String format = year +"/"+(month+1)+"/"+dayOfMonth;
                textView.setText(format);
            }
        }, calendario.get(Calendar.YEAR),calendario.get(Calendar.MONTH),calendario.get(Calendar.DAY_OF_MONTH));

        select.show();
    }
}