package cl.stomas.agendauniversitaria.vistas;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cl.stomas.agendauniversitaria.R;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListElement> mData;
    private LayoutInflater mInlater;
    private Context context;

    public ListAdapter(List<ListElement> itemList, Context context){
        this.mInlater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount() {return mData.size();}

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInlater.inflate(R.layout.list_element, null);
        return  new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        holder.binData(mData.get(position));
        // Cuando se haga click en cualquier elemento del RecyclerView se enviara al ActivityDetalles
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Aquí se esta enviando cada elemento con putExtra
                Intent intento = new Intent(context,ActivityDetalles.class);
                intento.putExtra("trab",holder.trabajo.getText());
                intento.putExtra("asig",holder.asignatura.getText());
                intento.putExtra("por",holder.porcentaje.getText());
                intento.putExtra("stat",holder.status.getText());
                context.startActivity(intento);
            }
        });
    }

    public void setItems(List<ListElement> items){ mData = items; }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView trabajo, asignatura, porcentaje, status;

        ViewHolder(View itemView) {
            super(itemView);
            trabajo = itemView.findViewById(R.id.txtTrabajo);
            asignatura = itemView.findViewById(R.id.txtAsignatura);
            porcentaje = itemView.findViewById(R.id.txtPorcentaje);
            status = itemView.findViewById(R.id.status);
        }

        void binData(final ListElement item){
            trabajo.setText(item.getTrabajo());
            asignatura.setText(item.getAsignatura());
            porcentaje.setText(item.getPorcentaje());
            status.setText(item.getStatus());
        }

    }

}
