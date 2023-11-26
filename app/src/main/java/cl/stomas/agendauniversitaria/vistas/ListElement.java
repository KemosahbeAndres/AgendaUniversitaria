package cl.stomas.agendauniversitaria.vistas;

public class ListElement {
    public String trabajo;
    public String asignatura;
    public String porcentaje;
    public String status;

    public ListElement(String trabajo, String asignatura, String porcentaje, String status) {
        this.trabajo = trabajo;
        this.asignatura = asignatura;
        this.porcentaje = porcentaje;
        this.status = status;
    }

    public String getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(String trabajo) {
        this.trabajo = trabajo;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public String getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
