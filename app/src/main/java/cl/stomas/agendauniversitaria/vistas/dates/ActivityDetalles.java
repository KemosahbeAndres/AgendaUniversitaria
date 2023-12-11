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
import cl.stomas.agendauniversitaria.db.DAOAsignatura;
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
    private TextView descSet, imporSet,fechaSet,duraSet,tipoSet,notaSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        ActionBar actionbar = getSupportActionBar();

        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle(R.string.detalle_actividad_actionbar);
            actionbar.setSubtitle(R.string.actionbar_subtitle);
        }

        try {
            Bundle parametros = getIntent().getExtras();
            actividad = (Actividad) parametros.getSerializable("actividad");
            if(actividad == null){
                finish();
            }
        } catch (Exception e) {
            Toast.makeText(this, "No pudimos obtener la actividad!", Toast.LENGTH_SHORT).show();
            finish();
        }
        //Toast.makeText(this, "Actividad lista", Toast.LENGTH_SHORT).show();
        trabSet = (TextView) findViewById(R.id.trabSet);
        asigSet = (TextView) findViewById(R.id.asigSet);
        porSet = (TextView) findViewById(R.id.porSet);
        descSet = (TextView) findViewById(R.id.descSet);
        imporSet = (TextView) findViewById(R.id.imporSet);
        duraSet = (TextView) findViewById(R.id.duraSet);
        fechaSet = (TextView) findViewById(R.id.fechaSet);
        tipoSet = (TextView) findViewById(R.id.tipoSet);
        statSet = (TextView) findViewById(R.id.statSet);
        notaSet = (TextView) findViewById(R.id.notaSet);
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
                        Toast.makeText(ActivityDetalles.this, "La actividad se elimino correctamente!", Toast.LENGTH_SHORT).show();
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
        updateModel();
        fillData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateModel();
        fillData();
    }
    private void updateModel() {
        Actividad updated = DB.actividades(this).get(actividad.getId());
        DAOAsignatura updateasign = DB.asignaturas(this);
        actividad.setNombre(updated.getNombre());
        actividad.setAsignatura(updateasign.get(updated.getIdasignatura()));
        actividad.setDescripcion(updated.getDescripcion());
        actividad.setTipo(updated.getTipo());
        actividad.setFecha(updated.getFecha());
        actividad.setDuracion(updated.getDuracion());
        actividad.setImportancia(updated.getImportancia());
        actividad.completar(updated.completado());
        actividad.setPorcentaje(updated.getPorcentaje());
        actividad.setNota(updated.getNota());
    }

    private void fillData(){
        if(actividad != null){
            trabSet.setText(actividad.getNombre());
            asigSet.setText(actividad.getAsignatura().getNombre());
            String porcentaje = actividad.getPorcentaje() + "%";
            porSet.setText(porcentaje);
            descSet.setText(actividad.getDescripcion());
            imporSet.setText(actividad.getImportancia());
            String duracion = String.valueOf(actividad.getDuracion());
            duraSet.setText(duracion);
            String fecha = actividad.getDia() + " " + actividad.getHora();
            fechaSet.setText(fecha);
            tipoSet.setText(actividad.getTipo());
            if(actividad.completado()){
                statSet.setText(getResources().getStringArray(R.array.estados)[0]);
                notaSet.setText(String.valueOf(actividad.getNota()));
            }else{
                statSet.setText(getResources().getStringArray(R.array.estados)[1]);
                notaSet.setText(R.string.nota_indefinida);
            }
        }
    }
}