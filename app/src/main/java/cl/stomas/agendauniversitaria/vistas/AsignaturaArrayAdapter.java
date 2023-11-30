package cl.stomas.agendauniversitaria.vistas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cl.stomas.agendauniversitaria.R;
import cl.stomas.agendauniversitaria.modelos.Asignatura;

public class AsignaturaArrayAdapter extends RecyclerView.Adapter<AsignaturaArrayAdapter.AsignaturaViewHolder>{
    public class AsignaturaViewHolder extends RecyclerView.ViewHolder {

        public AsignaturaViewHolder(View itemView) {
            super(itemView);
        }
        public void bindData(final Asignatura item){

        }
    }

    public AsignaturaArrayAdapter(ArrayList<Asignatura> asignaturas, Context context) {
        this.asignaturas = asignaturas;
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
    }

    private ArrayList<Asignatura> asignaturas;
    private LayoutInflater inflater;
    private Context context;

    @NonNull
    @Override
    public AsignaturaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.asignatura_item, null);
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
