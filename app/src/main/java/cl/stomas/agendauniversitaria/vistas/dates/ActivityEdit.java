package cl.stomas.agendauniversitaria.vistas.dates;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cl.stomas.agendauniversitaria.R;
import cl.stomas.agendauniversitaria.db.DAOActividad;
import cl.stomas.agendauniversitaria.db.DAOAsignatura;
import cl.stomas.agendauniversitaria.db.DB;
import cl.stomas.agendauniversitaria.modelos.Actividad;
import cl.stomas.agendauniversitaria.modelos.Asignatura;

public class ActivityEdit extends AppCompatActivity {
    private EditText trabGet;
    private EditText porGet;
    private EditText descGet,fechaGet,duraGet,notaGet;
    private Spinner asigGet,tipoGet, imporGet, statGet;;
    private DAOActividad db;
    Calendar selectfechas = Calendar.getInstance();
    boolean orstat;
    int actId;
    String [] asigna, importancias, tipos;
    private Actividad actividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            actividad = (Actividad) extras.getSerializable("activity");
            if(actividad == null){
                Toast.makeText(this, "Error al mostrar la actividad!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        trabGet = (EditText) findViewById(R.id.trabGet);

        porGet = (EditText) findViewById(R.id.porGet);

        descGet = (EditText) findViewById(R.id.descGet);

        imporGet = (Spinner) findViewById(R.id.imporGet);

        duraGet = (EditText) findViewById(R.id.duraGet);

        fechaGet = (EditText) findViewById(R.id.fechaGet);

        tipoGet = (Spinner) findViewById(R.id.tipoGet);
        notaGet =(EditText) findViewById(R.id.notaGet);

        statGet = (Spinner) findViewById(R.id.statGet);

        importancias = getResources().getStringArray(R.array.importancias);
        ArrayAdapter<String> adapterImpor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, importancias);
        adapterImpor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        imporGet.setAdapter(adapterImpor);


        tipos = DB.actividades(this).allTypes();
        ArrayAdapter<String> adapterTipos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tipos);
        adapterTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoGet.setAdapter(adapterTipos);



        String[] status = getResources().getStringArray(R.array.estados);
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,status);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statGet.setAdapter(statusAdapter);

        fechaGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EleccionFecha();
            }
        });

        Button buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trab = trabGet.getText().toString();
                int por= Integer.parseInt(porGet.getText().toString());
                String desc= descGet.getText().toString();
                String impor=imporGet.getSelectedItem().toString();
                int dura=Integer.parseInt(duraGet.getText().toString());
                String fecha=fechaGet.getText().toString();
                String tipo= tipoGet.getSelectedItem().toString();
                String stat = statGet.getSelectedItem().toString();
                int not = Integer.parseInt(notaGet.getText().toString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                Date fecha2;
                try {
                    fecha2 = sdf.parse(fecha);
                } catch (ParseException e) {
                    fecha2 = new Date();
                }
                if (stat.equals("Completado")){
                    orstat = true;
                } else if (stat.equals("Pendiente")) {
                    orstat = false;
                }else{
                    orstat = false;
                }
                Actividad activi = new Actividad(actividad.getId(),tipo,trab,desc,fecha2,dura,impor,orstat,por,not);
                db = new DAOActividad(ActivityEdit.this);
                db.update(activi);

                Toast.makeText(ActivityEdit.this,"Actividad ACTUALIZADA", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fillData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillData();
    }

    private void fillData(){
        trabGet.setText(actividad.getNombre()); // NOMBRE ACTIVIDAD
        descGet.setText(actividad.getDescripcion()); // DESCRIPCION
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        fechaGet.setText(formatter.format(actividad.getFecha())); // FECHA Y HORA
        duraGet.setText(String.valueOf(actividad.getDuracion())); // DURACION
        porGet.setText(String.valueOf(actividad.getPorcentaje())); // PORCENTAJE
        notaGet.setText(String.valueOf(actividad.getNota())); // NOTA
        for (int i = 0; i < importancias.length; i++){ //IMPORTANCIA
            if(importancias[i].equals(actividad.getImportancia().toUpperCase())){
                imporGet.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < tipos.length; i++){ // TIPO
            if(tipos[i].equals(actividad.getTipo().toUpperCase())){
                tipoGet.setSelection(i);
                break;
            }
        }
        if(actividad.completado()){ //COMPLETADO
            statGet.setSelection(0);
        }else{
            statGet.setSelection(1);
        }
    }

    private void EleccionFecha(){
        DatePickerDialog select = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fechaGet.setText(year +"/"+(month+1)+"/"+dayOfMonth);
                EleccionHora();
            }
        }, selectfechas.get(Calendar.YEAR),selectfechas.get(Calendar.MONTH),selectfechas.get(Calendar.DAY_OF_MONTH));
        select.show();
    }
    private void EleccionHora(){
        TimePickerDialog select = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hprefix = "";
                        String mprefix = "";
                        if(String.valueOf(hourOfDay).length() < 2){
                            hprefix += "0";
                        }
                        if(String.valueOf(minute).length() < 2){
                            mprefix += "0";
                        }
                        fechaGet.setText(fechaGet.getText() + " "+hprefix+hourOfDay+":"+mprefix+minute);
                    }
                },
                selectfechas.get(Calendar.HOUR_OF_DAY),
                selectfechas.get(Calendar.MINUTE),
                true
        );
        select.show();
    }
}
