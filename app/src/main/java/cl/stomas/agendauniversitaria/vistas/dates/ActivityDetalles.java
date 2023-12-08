package cl.stomas.agendauniversitaria.vistas.dates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cl.stomas.agendauniversitaria.R;
import cl.stomas.agendauniversitaria.db.DB;
import cl.stomas.agendauniversitaria.modelos.Actividad;
import cl.stomas.agendauniversitaria.vistas.ConfirmDialog;
import cl.stomas.agendauniversitaria.vistas.onDialogResponseListener;

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

        ActionBar actionbar = getSupportActionBar();

        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle("Detalle Actividad");
            actionbar.setSubtitle(R.string.actionbar_subtitle);
        }

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
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.edit_activity_item_menu){
            Intent intent = new Intent(ActivityDetalles.this, ActivityEdit.class);
            intent.putExtra("activity", actividad);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.delete_activity_item_menu){
            ConfirmDialog.BuildConfirmDialog(this, "Eliminar", "¿Desea eliminar esta actividad?", new onDialogResponseListener() {
                @Override
                public void onDialogConfirm(boolean confirmed) {
                    if(confirmed){
                        DB.actividades(ActivityDetalles.this).delete(actividad);
                        Toast.makeText(ActivityDetalles.this, "Haz eliminado una actividad!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }).show(getSupportFragmentManager(), "DELETE_ACTIVITY_DIALOG");
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
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