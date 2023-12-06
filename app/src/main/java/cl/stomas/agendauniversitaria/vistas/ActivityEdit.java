package cl.stomas.agendauniversitaria.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

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
    String [] asigna;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        trabGet = (EditText) findViewById(R.id.trabGet);
        String trabRec = getIntent().getStringExtra("trab");
        trabGet.setText(trabRec);

        asigGet = (Spinner) findViewById(R.id.asigGet);

        porGet = (EditText) findViewById(R.id.porGet);
        String porRec = getIntent().getStringExtra("por");
        porGet.setText(porRec);

        descGet = (EditText) findViewById(R.id.descGet);
        String descRec = getIntent().getStringExtra("desc");
        descGet.setText(descRec);

        imporGet = (Spinner) findViewById(R.id.imporGet);

        duraGet = (EditText) findViewById(R.id.duraGet);
        fechaGet = (EditText) findViewById(R.id.fechaGet);
        tipoGet = (Spinner) findViewById(R.id.tipoGet);
        notaGet =(EditText) findViewById(R.id.notaGet);
        statGet = (Spinner) findViewById(R.id.statGet);
        actId= getIntent().getIntExtra("id",-1);

        String[] tipos = DB.actividades(this).allTypes();
        String[] importancias = new String[]{"BAJA","MEDIA","ALTA"};
        ArrayAdapter<String> adapterImpor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, importancias);
        adapterImpor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapterTipos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tipos);
        adapterTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoGet.setAdapter(adapterTipos);
        imporGet.setAdapter(adapterImpor);

        String[] status = new String[]{"Completado","Pendiente"};
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,status);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statGet.setAdapter(statusAdapter);
        DAOAsignatura listasigna=new DAOAsignatura(ActivityEdit.this);

        ArrayList<Asignatura> asignaturas = listasigna.getAll();
        ArrayList<String> asigText = new ArrayList<>();
        for(Asignatura newasig : asignaturas){
            asigText.add(newasig.getNombre());
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


        Button buttonNewBack = (Button) findViewById(R.id.buttonNewBack);
        buttonNewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trab = trabGet.getText().toString();
                String asig= asigGet.getSelectedItem().toString();
                int por= Integer.parseInt(porGet.getText().toString());
                String desc= descGet.getText().toString();
                String impor=imporGet.getSelectedItem().toString();
                int dura=Integer.parseInt(duraGet.getText().toString());
                String fecha=fechaGet.getText().toString();
                String tipo= tipoGet.getSelectedItem().toString();
                String stat = statGet.getSelectedItem().toString();
                int not = Integer.parseInt(notaGet.getText().toString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Date fecha2;
                try {
                    fecha2 = sdf.parse(fecha);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if (stat.equals("Completado")){
                    orstat = false;
                    Actividad activi = new Actividad(actId,tipo,trab,desc,fecha2,dura,impor,orstat,por,not);
                    db = new DAOActividad(ActivityEdit.this);
                    db.update(activi);
                } else if (stat == "Pendiente") {
                    orstat = true;
                    Actividad activi = new Actividad(actId,tipo,trab,desc,fecha2,dura,impor,orstat,por,not);
                    db = new DAOActividad(ActivityEdit.this);
                    db.update(activi);

                }

                Toast.makeText(ActivityEdit.this,"Actividad ACTUALIZADA", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }
    private void EleccionFecha(){
        DatePickerDialog select = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fechaGet.setText(year +"/"+(month+1)+"/"+dayOfMonth);
            }
        }, selectfechas.get(Calendar.YEAR),selectfechas.get(Calendar.MONTH),selectfechas.get(Calendar.DAY_OF_MONTH));
        select.show();
    }
}
