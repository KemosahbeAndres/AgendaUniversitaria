package cl.stomas.agendauniversitaria.modelos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.function.Consumer;

public class Semestre implements Serializable {

    private long id;
    private Date fecha_inicio;
    private Date fecha_fin;
    private ArrayList<Asignatura> asignaturas;
    private Carrera carrera;

    public Semestre(Date fecha_inicio, Date fecha_fin) {
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.id = -1;
        this.asignaturas = new ArrayList<>();
        this.carrera = null;
    }

    public Semestre(long id, Date fecha_inicio, Date fecha_fin) {
        this(fecha_inicio, fecha_fin);
        this.id = id;
        this.asignaturas = new ArrayList<>();
        this.carrera = null;
    }

    public Semestre(long id, Date fecha_inicio, Date fecha_fin, Carrera carrera) {
        this(id, fecha_inicio, fecha_fin);
        this.carrera = carrera;
    }

    public Semestre(long id, Date fecha_inicio, Date fecha_fin, ArrayList<Asignatura> asignaturas) {
        this(id, fecha_inicio, fecha_fin);
        this.addAsignaturas(asignaturas);
    }

    public Semestre(long id, Date fecha_inicio, Date fecha_fin, ArrayList<Asignatura> asignaturas, Carrera carrera) {
        this(id, fecha_inicio, fecha_fin);
        this.addAsignaturas(asignaturas);
        this.carrera = carrera;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public ArrayList<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void addAsignatura(Asignatura asignatura){
        this.asignaturas.add(asignatura);
    }

    public void addAsignaturas(ArrayList<Asignatura> asignaturas) {
        this.asignaturas.addAll(asignaturas);
        this.asignaturas.forEach(new Consumer<Asignatura>() {
            @Override
            public void accept(Asignatura asignatura) {
                asignatura.setSemestre(Semestre.this);
            }
        });
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public ArrayList<Actividad> getAllActividades(){
        ArrayList<Actividad> actividades = new ArrayList<>();
        for (Asignatura asignatura: this.asignaturas){
            actividades.addAll(asignatura.getActividades());
        }
        actividades.sort(new Comparator<Actividad>() {
            @Override
            public int compare(Actividad o1, Actividad o2) {
                return o1.getFecha().compareTo(o2.getFecha());
            }
        });
        return actividades;
    }

    public ArrayList<Actividad> getAllActividadesDesde(Date fecha){
        ArrayList<Actividad> allActividades = this.getAllActividades();
        ArrayList<Actividad> filtradas = new ArrayList<>();
        for (Actividad actividad: allActividades){
            if(actividad.getFecha().compareTo(fecha) == 0){
                filtradas.add(actividad);
            }
        }
        return filtradas;
    }
}
