package cl.stomas.agendauniversitaria.vistas.dates;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    DAOAsignatura listasigna = DB.asignaturas(this);
    Calendar selectfechas = Calendar.getInstance();
    boolean orstat;
    int actId;
    String [] asigna, importancias, tipos;
    private Actividad actividad;
    private ArrayList<Asignatura> asignaturas;
    int selectedAsignatura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ActionBar actionbar = getSupportActionBar();

        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true);
        }

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
        asigGet = (Spinner) findViewById(R.id.asigGet);
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

        asignaturas = listasigna.getAll();
        ArrayList<String> asigText = new ArrayList<>();
        ArrayList<Long> idsAsignaturas = new ArrayList<>();
        for(int i = 0; i < asignaturas.size(); i++){
            asigText.add(asignaturas.get(i).getNombre());
            if(actividad.getId() == asignaturas.get(i).getId()){
                selectedAsignatura = i;
            }
            idsAsignaturas.add(asignaturas.get(i).getId());
        }

        ArrayAdapter<String> adapterAsig = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, asigText);
        adapterAsig.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        asigGet.setAdapter(adapterAsig);

        fechaGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EleccionFecha();
            }
        });
        asigGet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAsignatura = Math.toIntExact(idsAsignaturas.get(position));
                //Toast.makeText(ActivityEdit.this, "id-asignatura: "+selectedAsignatura, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validacionEdit()) {
                    String trab = trabGet.getText().toString();
                    String asig = asigGet.getSelectedItem().toString();
                    int por = 0;
                    try {
                        por = Integer.parseInt(porGet.getText().toString());
                    }catch (Exception e){ }
                    String desc = descGet.getText().toString();
                    String impor = imporGet.getSelectedItem().toString();
                    int dura = Integer.parseInt(duraGet.getText().toString());
                    String fecha = fechaGet.getText().toString();
                    String tipo = tipoGet.getSelectedItem().toString();
                    String stat = statGet.getSelectedItem().toString();
                    int not = Integer.parseInt(notaGet.getText().toString());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                    Date fecha2;
                    try {
                        fecha2 = sdf.parse(fecha);
                    } catch (ParseException e) {
                        fecha2 = new Date();
                    }
                    if (stat.equals("Completado")) {
                        orstat = true;
                    } else if (stat.equals("Pendiente")) {
                        orstat = false;
                    } else {
                        orstat = false;
                    }
                    db = new DAOActividad(ActivityEdit.this);
                    Actividad activi = new Actividad(actividad.getId(), tipo, trab, desc, fecha2, dura, impor, orstat, por, not);
                    db.update(activi, listasigna.get(selectedAsignatura));
                    Toast.makeText(ActivityEdit.this, "Actividad Actualizada", Toast.LENGTH_SHORT).show();
                    finish();
                }
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
        for(int i = 0; i < asignaturas.size(); i++){ // ASIGNATURA
            if(asignaturas.get(i).getNombre().equals(actividad.getAsignatura().getNombre())){
                asigGet.setSelection(i);
                break;
            }
        }
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
    private boolean validacionEdit(){
        if (trabGet.getText().toString().equals("")) {
            Toast.makeText(ActivityEdit.this, "No dejar el campo nombre actividad vacio", Toast.LENGTH_SHORT).show();
        //}else if (porGet.getText().toString().isEmpty()){
            //Toast.makeText(ActivityEdit.this,"No dejar el campo porcentaje vacio", Toast.LENGTH_SHORT).show();
        //}else if(porGet.getText().toString().equals("0") || porGet.getText().toString().equals("00")){
            //Toast.makeText(ActivityEdit.this,"El porcentaje no puede ser igual a 0 o 00", Toast.LENGTH_SHORT).show();
        }else if (fechaGet.getText().toString().equals("")){
            Toast.makeText(ActivityEdit.this,"No dejar el campo fecha vacio", Toast.LENGTH_SHORT).show();
        }else if (asigGet.getSelectedItem().toString().equals("")){
            Toast.makeText(ActivityEdit.this,"No dejar el campo materia vacio", Toast.LENGTH_SHORT).show();
        }else if (imporGet.getSelectedItem().toString().equals("")){
            Toast.makeText(ActivityEdit.this,"No dejar el campo importancia vacio", Toast.LENGTH_SHORT).show();
        }else if (tipoGet.getSelectedItem().toString().equals("")){
            Toast.makeText(ActivityEdit.this,"No dejar el campo tipo vacio", Toast.LENGTH_SHORT).show();
        }else if (duraGet.getText().toString().equals("")){
            Toast.makeText(ActivityEdit.this,"No dejar el campo duracion vacio", Toast.LENGTH_SHORT).show();
        }else{
            return true;
        }
        return false;
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
