package cl.stomas.agendauniversitaria.modelos;

import java.time.LocalTime;
import java.util.Date;

public class Bloque {

    private int id;
    private String tipo;
    private LocalTime hora;
    private int duracion;
    private int dia_semana;
    private Date fecha;
    private Asignatura asignatura;

    public Bloque(int id, String tipo, int duracion, int dia_semana, Date fecha, Asignatura asignatura) {
        this(tipo, duracion, dia_semana, fecha, asignatura);
        this.id = id;
    }

    public Bloque(String tipo, int duracion, int dia_semana, Date fecha, Asignatura asignatura) {
        this(tipo, duracion, dia_semana, fecha);
        this.asignatura = asignatura;
    }

    public Bloque(int id, String tipo, int duracion, int dia_semana, Date fecha) {
        this(tipo, duracion, dia_semana, fecha);
        this.id = id;
    }

    public Bloque(String tipo, int duracion, int dia_semana, Date fecha) {
        this.id = -1;
        this.tipo = tipo;
        this.duracion = duracion;
        this.dia_semana = dia_semana;
        this.fecha = fecha;
        this.asignatura = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getDia_semana() {
        return dia_semana;
    }

    public void setDia_semana(int dia_semana) {
        this.dia_semana = dia_semana;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }
}
