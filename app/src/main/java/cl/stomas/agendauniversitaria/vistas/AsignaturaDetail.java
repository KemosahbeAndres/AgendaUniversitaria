package cl.stomas.agendauniversitaria.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import cl.stomas.agendauniversitaria.R;
import cl.stomas.agendauniversitaria.controladores.SemestreControler;
import cl.stomas.agendauniversitaria.db.DB;
import cl.stomas.agendauniversitaria.modelos.Asignatura;

public class AsignaturaDetail extends AppCompatActivity {

    private RecyclerView lista;
    private ListAdapter adapter;
    private SemestreControler controller;
    private Asignatura asignatura;
    private TextView txtId, txtNombre, txtDescripcion, txtDocente, txtPromedio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignatura_detail);

        ActionBar actionbar = getSupportActionBar();

        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle("Detalle Asignatura");
            actionbar.setSubtitle(R.string.actionbar_subtitle);
        }

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            asignatura = (Asignatura) extras.getSerializable("asignatura");
        }

        if(asignatura == null){
            Toast.makeText(this, "No se pudo cargar la actividad!", Toast.LENGTH_SHORT).show();
            finish();
        }

        txtId = findViewById(R.id.txt_subject_id);
        txtNombre = findViewById(R.id.txt_subject_name);
        txtDescripcion = findViewById(R.id.txt_subject_description);
        txtDocente = findViewById(R.id.txt_subject_teacher);
        txtPromedio = findViewById(R.id.txt_subject_average);
        lista = findViewById(R.id.tablaA);
        controller = new SemestreControler(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_asignatura_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.edit_subject_item_menu){
            Intent intent = new Intent(AsignaturaDetail.this, AgregarAsignaturasActivity.class);
            intent.putExtra("asignatura", asignatura);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.delete_subject_item_menu){
            ConfirmDialog.BuildConfirmDialog(this, "Eliminar", "¿Desea eliminar esta asignatura?", new onDialogResponseListener() {
                @Override
                public void onDialogConfirm(boolean confirmed) {
                    if(confirmed){
                        DB.asignaturas(AsignaturaDetail.this).delete(asignatura);
                        Toast.makeText(AsignaturaDetail.this, "Has sido Eliminado!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }).show(getSupportFragmentManager(), "DELETE_DIALOG");
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setValues();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setValues();
    }

    private void setValues(){
        txtId.setText(String.valueOf(asignatura.getId()));
        txtNombre.setText(asignatura.getNombre());
        txtDescripcion.setText(asignatura.getDescripcion());
        txtDocente.setText(asignatura.getDocente());
        txtPromedio.setText(String.valueOf(asignatura.promedio()));
        adapter = new ListAdapter(asignatura.getActividades(),this);
        lista.setAdapter(adapter);
        lista.setLayoutManager(new LinearLayoutManager(this));
    }

}