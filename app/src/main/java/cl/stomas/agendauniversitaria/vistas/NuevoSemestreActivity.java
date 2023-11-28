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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

import cl.stomas.agendauniversitaria.R;
import cl.stomas.agendauniversitaria.db.Config;
import cl.stomas.agendauniversitaria.db.DB;
import cl.stomas.agendauniversitaria.modelos.Carrera;
import cl.stomas.agendauniversitaria.modelos.Semestre;

public class NuevoSemestreActivity extends AppCompatActivity {
    private Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_semestre);

        config = Config.getConfig(this);

        EditText startDate = findViewById(R.id.startDate);
        EditText endDate = findViewById(R.id.endDate);
        Button btnSave = findViewById(R.id.btnSave);

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EleccionFecha(startDate);
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EleccionFecha(endDate);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Guardar Semestre
                if(startDate.getText().toString().isEmpty()){
                    Toast.makeText(NuevoSemestreActivity.this, "Debes ingresar una fecha de inicio!", Toast.LENGTH_SHORT).show();
                }else if (endDate.getText().toString().isEmpty()){
                    Toast.makeText(NuevoSemestreActivity.this, "Debes ingresar una fecha de fin!", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        config.load(); // Cargo la configuracion
                        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd"); // Objeto para formatear y parsear
                        Date fecha_inicio = format.parse(startDate.getText().toString()); // Parseo(Convierto) la fecha de inicio (String -> Date)
                        Date fecha_fin = format.parse(endDate.getText().toString()); // Parseo(Convierto) la fecha de fin (String -> Date)
                        if (fecha_inicio != null && fecha_fin != null){ // Si las fechas no son nulas (conversion exitosa)
                            Semestre semestre = new Semestre(fecha_inicio, fecha_fin); // Creo un nuevo semestre con las fechas
                            Carrera carrera = DB.carreras(NuevoSemestreActivity.this).get(config.getIdCarrera()); // Obtengo la carrera con el id en la configuracion
                            long insertedID = DB.semestres(NuevoSemestreActivity.this).insert(semestre, carrera); // Inserto en la Base de Datos el semestre enlazado con la carrera
                            Date hoy = new Date(); // Fecha de hoy para comparar
                            Toast.makeText(NuevoSemestreActivity.this, "ID: "+insertedID, Toast.LENGTH_SHORT).show();
                            if(insertedID >= 0 && fecha_inicio.before(hoy) && fecha_fin.after(hoy)){ // Si el id insertado es correcto Y las fechas indican que es el semestre actual
                                config.setIdSemestre(insertedID); // Cambio en la configuracion el ID del semestre
                                config.save(); // Guardo la configuracion
                            }
                            Toast.makeText(NuevoSemestreActivity.this, "Semestre agregado con exito!", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            throw new Exception("Formato incorrecto de las fechas!");
                        }
                    } catch (Exception e) {
                        Toast.makeText(NuevoSemestreActivity.this, "No se pudo guardar! "+e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void EleccionFecha(TextView textView){
        Calendar calendario = Calendar.getInstance();
        if(!textView.getText().toString().isEmpty()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try{
                    SimpleDateFormat sformat = new SimpleDateFormat("yyyy/MM/dd");
                    Date date = sformat.parse(textView.getText().toString());
                    //Toast.makeText(NuevoSemestreActivity.this, "Fecha: "+date.toString(), Toast.LENGTH_SHORT).show();
                    calendario.setTime(date);
                }catch (Exception e){
                    Log.w("[DatePicker]", e.toString());
                }
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