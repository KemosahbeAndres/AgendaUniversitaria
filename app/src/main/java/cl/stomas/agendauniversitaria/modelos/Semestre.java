package cl.stomas.agendauniversitaria.modelos;

import java.util.ArrayList;
import java.util.Date;

public class Semestre {

    private int id;
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

    public Semestre(int id, Date fecha_inicio, Date fecha_fin) {
        this(fecha_inicio, fecha_fin);
        this.id = id;
        this.asignaturas = new ArrayList<>();
        this.carrera = null;
    }

    public Semestre(int id, Date fecha_inicio, Date fecha_fin, Carrera carrera) {
        this(id, fecha_inicio, fecha_fin);
        this.carrera = carrera;
    }

    public Semestre(int id, Date fecha_inicio, Date fecha_fin, ArrayList<Asignatura> asignaturas) {
        this(id, fecha_inicio, fecha_fin);
        this.addAsignaturas(asignaturas);
    }

    public Semestre(int id, Date fecha_inicio, Date fecha_fin, ArrayList<Asignatura> asignaturas, Carrera carrera) {
        this(id, fecha_inicio, fecha_fin);
        this.addAsignaturas(asignaturas);
        this.carrera = carrera;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }
}
