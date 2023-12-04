package cl.stomas.agendauniversitaria.vistas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cl.stomas.agendauniversitaria.R;
import cl.stomas.agendauniversitaria.modelos.Asignatura;

public class AsignaturaArrayAdapter extends RecyclerView.Adapter<AsignaturaArrayAdapter.AsignaturaViewHolder>{
    public class AsignaturaViewHolder extends RecyclerView.ViewHolder {
        private TextView inicial, nombre, docente;
        public AsignaturaViewHolder(View itemView) {
            super(itemView);
            inicial = itemView.findViewById(R.id.inicialAsignatura);
            nombre = itemView.findViewById(R.id.nombre_asignatura);
            docente = itemView.findViewById(R.id.nombre_docente);
        }
        public void bindData(final Asignatura item){
            try{
                String substring = item.getNombre().substring(0,1);
                inicial.setText(substring);
                nombre.setText(item.getNombre());
                docente.setText(item.getDocente());
            }catch (Exception e){
                nombre.setText("Error al cargar la asignatura!");
            }
        }
    }

    private ArrayList<Asignatura> asignaturas;
    public AsignaturaArrayAdapter(){
        this.asignaturas = new ArrayList<>();
    }

    public AsignaturaArrayAdapter(ArrayList<Asignatura> asignaturas) {
        this();
        this.asignaturas.addAll(asignaturas);
    }
    public void replaceAll(ArrayList<Asignatura> asignaturas_nuevas){
        this.asignaturas.clear();
        this.asignaturas.addAll(asignaturas_nuevas);
    }
    @NonNull
    @Override
    public AsignaturaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.asignatura_item, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new AsignaturaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AsignaturaViewHolder holder, int position) {
        holder.bindData(asignaturas.get(position));
    }

    @Override
    public int getItemCount() {
        return asignaturas.size();
    }

}
