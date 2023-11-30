package cl.stomas.agendauniversitaria.modelos;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Actividad implements Serializable {

    public final static class Tipo {
        public final static String ACTIVIDAD = "ACTIVIDAD";
        public final static String INFORME = "INFORME";
        public final static String EVALUACION = "EVALUACION";
        public final static String EXPOSICION = "EXPOSICION";
        public final static String PROYECTO = "PROYECTO";
        public final static String TERRENO = "TERRENO";
    }
    public final static class Importancia {
        public final static String HIGH = "ALTA";
        public final static String MID = "MEDIA";
        public final static String LOW = "BAJA";
    }
    private int id;
    private String tipo;
    private String nombre;
    private String descripcion;
    private Date fecha;
    private int duracion;
    private String importancia;
    private boolean completado;
    private int porcentaje;
    private int nota;
    private Asignatura asignatura;

    public Actividad(int id, String tipo, String nombre, String descripcion, Date fecha, int duracion, String importancia, boolean completado, int porcentaje, int nota, Asignatura asignatura) {
        this(id, tipo, nombre, descripcion, fecha, duracion, importancia, completado, porcentaje, nota);
        this.asignatura = asignatura;
    }

    public Actividad(int id, String tipo, String nombre, String descripcion, Date fecha, int duracion, String importancia, boolean completado, int porcentaje, int nota) {
        this(tipo, nombre, descripcion, fecha, duracion, importancia, completado, porcentaje, nota);
        this.id = id;
    }

    public Actividad(String tipo, String nombre, String descripcion, Date fecha, int duracion, String importancia, boolean completado, int porcentaje, int nota) {
        this.id = -1;
        this.tipo = tipo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.duracion = duracion;
        if(importancia.trim().length() <= 0){
            this.importancia = Importancia.MID;
        }else{
            this.importancia = importancia.trim().toUpperCase();
        }
        this.completado = completado;
        this.porcentaje = porcentaje;
        this.nota = nota;
        this.asignatura = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }
    public String getHora(){
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        return formatter.format(this.getFecha());
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getImportancia() {
        return importancia;
    }

    public void setImportancia(String importancia) {
        this.importancia = importancia;
    }

    public boolean completado() {
        return completado;
    }

    public void completar(boolean completado) {
        this.completado = completado;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }
}
