package cl.stomas.agendauniversitaria.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Field;
import java.util.ArrayList;

import cl.stomas.agendauniversitaria.R;
import cl.stomas.agendauniversitaria.controladores.CarreraController;
import cl.stomas.agendauniversitaria.db.Config;
import cl.stomas.agendauniversitaria.db.DAOAsignatura;
import cl.stomas.agendauniversitaria.db.DB;
import cl.stomas.agendauniversitaria.modelos.Asignatura;
import cl.stomas.agendauniversitaria.modelos.Carrera;

public class AgregarAsignaturasActivity extends AppCompatActivity {
    private final static String[] colores = new String[]{
            "Rojo",
            "Azul",
            "Amarillo",
            "Verde",
            "Morado",
            "Cafe",
            "Negro",
            "Blanco"
    };
    TextInputEditText nameAsign;
    TextInputEditText descAsign;
    TextInputEditText docenAsign;
    Spinner colorAsign;
    private Asignatura asignatura;
    private boolean editionMode;
    private Config config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_asignaturas);

        ActionBar actionbar = getSupportActionBar();

        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle("Agregar Asignatura");
            actionbar.setSubtitle(R.string.actionbar_subtitle);
        }

        config = Config.getConfig(this);

        editionMode = false;

        asignatura = null;

        nameAsign = findViewById(R.id.txtnameasig);
        descAsign = findViewById(R.id.txtdescasig);
        docenAsign = findViewById(R.id.txtdescdocente);
        colorAsign = findViewById(R.id.spColor);

        try {
            ArrayAdapter<String> colorsAdapter;
            Field[] fields = Class.forName(getPackageName()+".R$color").getDeclaredFields();
            ArrayList<String> colors = new ArrayList<>();
            for(Field field : fields) {
                String colorName = field.getName();
                int colorId = field.getInt(colorName);
                int color = getResources().getColor(colorId, getTheme());
                colors.add(colorName);
            }
            colorsAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    colors
            );
            colorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            colorAsign.setAdapter(colorsAdapter);
        } catch (Exception e) {

        }


        Bundle extras = getIntent().getExtras();

        if (extras != null){
            asignatura = (Asignatura) extras.getSerializable("asignatura");
            if(asignatura != null){
                editionMode = true;
                if (actionbar != null) {
                    actionbar.setTitle("Editar Asignatura");
                }

            }
        }

        initValues();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_or_save_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_menu_item){
            save();
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    private void initValues(){
        if(editionMode && asignatura != null){
            nameAsign.setText(asignatura.getNombre());
            descAsign.setText(asignatura.getDescripcion());
            docenAsign.setText(asignatura.getDocente());

        }
    }

    private void save(){
        if(passChecks()){
            config.load();
            DAOAsignatura enviofinal = DB.asignaturas(AgregarAsignaturasActivity.this);
            if(editionMode){
                //Editar
                if(asignatura != null){
                    asignatura.setNombre(nameAsign.getText().toString());
                    asignatura.setDescripcion(descAsign.getText().toString());
                    String color = (String) colorAsign.getSelectedItem();
                    try {
                        for(Field field : Class.forName(getPackageName()+".R$color").getDeclaredFields()) {
                            String colorName = field.getName();
                            int colorId = field.getInt(colorName);
                            String colorString = getString(colorId);
                            if(colorName.equals(color)){
                                asignatura.setColor(colorString);
                            }
                        }
                    } catch (Exception e) {
                        asignatura.setColor(color);
                    }
                    asignatura.setDocente(docenAsign.getText().toString());

                    if(enviofinal.update(asignatura)){
                        Toast.makeText(this, "Asignatura modificada", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(this, "No se modifico nada!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "No se pudo actualizar la asignatura!", Toast.LENGTH_SHORT).show();
                }
            }else{
                // Crear
                //aqui añadir la transferencia de los datos a la bd y el intent final
                asignatura = new Asignatura(nameAsign.getText().toString(),descAsign.getText().toString(),(String) colorAsign.getSelectedItem(),docenAsign.getText().toString());
                CarreraController controlador = new CarreraController(this);
                long idCarrera = config.getIdCarrera();
                Carrera carrera = controlador.execute(idCarrera);
                enviofinal.insert(asignatura, carrera.getSemestreActual());
                finish();
            }
        }
    }

    private boolean passChecks(){
        if (nameAsign.getText().toString().isEmpty()){
            Toast.makeText(AgregarAsignaturasActivity.this,"No dejar el campo nombre vacio", Toast.LENGTH_SHORT).show();
        }else if (descAsign.getText().toString().isEmpty()){
            Toast.makeText(AgregarAsignaturasActivity.this,"No dejar el campo descripcion vacio", Toast.LENGTH_SHORT).show();
        }else if (docenAsign.getText().toString().isEmpty()){
            Toast.makeText(AgregarAsignaturasActivity.this,"No dejar el campo docente vacio", Toast.LENGTH_SHORT).show();
        }else if (((String) colorAsign.getSelectedItem()).isEmpty()) {
            Toast.makeText(AgregarAsignaturasActivity.this, "No dejar el campo color vacio", Toast.LENGTH_SHORT).show();
        }else{
            return true;
        }
        return false;
    }

}