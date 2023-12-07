package cl.stomas.agendauniversitaria.vistas;

import android.content.Context;
import android.content.Intent;
import android.graphics.BlendMode;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import cl.stomas.agendauniversitaria.R;
import cl.stomas.agendauniversitaria.modelos.Asignatura;

public class AsignaturaArrayAdapter extends RecyclerView.Adapter<AsignaturaArrayAdapter.AsignaturaViewHolder>{
    public class AsignaturaViewHolder extends RecyclerView.ViewHolder {
        private TextView inicial, nombre, docente;
        private ImageView shape;

        private Asignatura asignatura;
        public AsignaturaViewHolder(View itemView) {
            super(itemView);
            inicial = itemView.findViewById(R.id.inicialAsignatura);
            nombre = itemView.findViewById(R.id.nombre_asignatura);
            docente = itemView.findViewById(R.id.nombre_docente);
            shape = itemView.findViewById(R.id.backgroundShape);
        }
        public void bindData(final Asignatura item){
            asignatura = item;
            try{
                String substring = item.getNombre().substring(0,1);
                inicial.setText(substring);
                nombre.setText(item.getNombre());
                docente.setText(item.getDocente());
            }catch (Exception e){
                nombre.setText("Error al cargar la asignatura!");
            }
            try{
                Drawable background = shape.getBackground();
                background.setColorFilter(Color.parseColor(asignatura.getColor()), PorterDuff.Mode.SRC_ATOP);
            }catch (Exception e){}
        }

        public Asignatura getAsignatura() {
            return asignatura;
        }
    }

    private ArrayList<Asignatura> asignaturas;
    private Context context;
    public AsignaturaArrayAdapter(){
        this.asignaturas = new ArrayList<>();
        this.context = null;
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
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.asignatura_item, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new AsignaturaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AsignaturaViewHolder holder, int position) {
        holder.bindData(asignaturas.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AsignaturaDetail.class);
                intent.putExtra("asignatura", holder.getAsignatura());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return asignaturas.size();
    }

}
