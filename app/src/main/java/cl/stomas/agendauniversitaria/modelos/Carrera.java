package cl.stomas.agendauniversitaria.modelos;

import java.util.ArrayList;

public class Carrera {

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
}
