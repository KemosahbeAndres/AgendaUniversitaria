package cl.stomas.agendauniversitaria.vistas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cl.stomas.agendauniversitaria.R;
import cl.stomas.agendauniversitaria.modelos.Actividad;
import cl.stomas.agendauniversitaria.modelos.Asignatura;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private ArrayList<Actividad> mData;
    private LayoutInflater mInlater;
    private Context context;

    public ListAdapter(ArrayList<Actividad> itemList, Context context){
        this.mInlater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount() {
        if (mData.size() <= 0){
            return 1;
        }else{
            return mData.size();
        }
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = mInlater.inflate(R.layout.list_element, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position){
        if(mData.size() <= 0){
            holder.binEmpty();
        }else{
            holder.binData(mData.get(position));
            // Cuando se haga click en cualquier elemento del RecyclerView se enviara al ActivityDetalles
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Aquí se esta enviando cada elemento con putExtra
                    Intent intento = new Intent(context,ActivityDetalles.class);
                    intento.putExtra("actividad", mData.get(position));
                    context.startActivity(intento);
                }
            });
        }
    }

    public void setItems(ArrayList<Actividad> items){ mData = items; }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView trabajo, asignatura, porcentaje, status, fecha;

        ViewHolder(View itemView) {
            super(itemView);
            trabajo = itemView.findViewById(R.id.txtTrabajo);
            asignatura = itemView.findViewById(R.id.txtAsignatura);
            porcentaje = itemView.findViewById(R.id.txtPorcentaje);
            status = itemView.findViewById(R.id.status);
            fecha = itemView.findViewById(R.id.txtFecha);
        }

        void binData(final Actividad item){
            trabajo.setText(item.getNombre());
            fecha.setText(item.getHora());
            asignatura.setText(item.getAsignatura().getNombre());
            porcentaje.setText(item.getPorcentaje() + "%");
            if (item.completado()){
                status.setText("Completado");
            }else{
                status.setText("Pendiente");
            }
        }

        void binEmpty(){
            asignatura.setText("NO HAY ACTIVIDADES AUN!");
            trabajo.setText("");
            porcentaje.setText("");
            status.setText("");
            fecha.setText("");
        }

    }

}
