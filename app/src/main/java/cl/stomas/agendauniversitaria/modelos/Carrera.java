package cl.stomas.agendauniversitaria.modelos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Carrera implements Serializable {

    private long id;
    private String nombre;
    private int anio;

    private ArrayList<Semestre> semestres;

    public Carrera (long id, String name, int anio, ArrayList<Semestre> semestres){
        this(id, name, anio);
        this.addSemestres(semestres);
    }

    public Carrera(String nombre, int anio) {
        this.id = -1;
        this.nombre = nombre;
        this.anio = anio;
        this.semestres = new ArrayList<>();
    }

    public Carrera(long id, String name, int anio) {
        this(name, anio);
        this.id = id;
    }

    public ArrayList<Semestre> getSemestres() {
        return semestres;
    }

    public void addSemestres(ArrayList<Semestre> semestres) {
        this.semestres.addAll(semestres);
    }

    public void addSemestre(Semestre semestre){
        this.semestres.add(semestre);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Semestre getSemestreActual(){
        Date fecha = new Date();
        Semestre seleccionado = null;
        for(Semestre semestre: semestres){
            if(semestre.getFecha_inicio().before(fecha) && semestre.getFecha_fin().after(fecha)){
                seleccionado = semestre;
                break;
            }
        }
        return seleccionado;
    }

    @Override
    public String toString() {
        return "Carrera {" +
                "# ID = " + id +
                "# Nombre = '" + nombre + '\'' +
                "# Año = " + anio +
                "Semestres = " + semestres.size() +
                '}';
    }
}
