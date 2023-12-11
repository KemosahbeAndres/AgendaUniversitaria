package cl.stomas.agendauniversitaria.db;

public class DBContract {

    public final static String DB_NAME = "agenda_universitaria.db";
    public final static int DB_VERSION = 1;

    public final static class TABLA_CARRERAS {
        public final static String NOMBRE = "carreras";
        public final static String COL_ID = "id";
        public final static String COL_NOMBRE = "nombre";
        public final static String COL_ANIO = "anio";

    }
    public final static class TABLA_SEMESTRES {
        public final static String NOMBRE = "semestres";
        public final static String COL_ID = "id";
        public final static String COL_ID_CARRERA = "id_carrera";
        public final static String COL_FECHA_INICIO = "fecha_inicio";
        public final static String COL_FECHA_FIN = "fecha_fin";
    }
    public final static class TABLA_ASIGNATURAS {
        public final static String NOMBRE = "asignatura";
        public final static String COL_ID = "id";
        public final static String COL_ID_SEMESTRE = "id_semestre";
        public final static String COL_NOMBRE = "nombre";
        public final static String COL_DESCRIPCION = "descripcion";
        public final static String COL_COLOR = "color";
        public final static String COL_DOCENTE = "docente";
    }
    public final static class TABLA_TIPO_BLOQUE {
        public final static String NOMBRE = "tipo_bloque";
        public final static String COL_ID = "id";
        public final static String COL_NOMBRE = "nombre";
    }
    public final static class TABLA_BLOQUES {
        public final static String NOMBRE = "bloques";
        public final static int DURACION_DEFAULT = 40*60;
        public final static int DIA_DEFAULT = 1;
        public final static String COL_ID = "id";
        public final static String COL_ID_ASIGNATURA = "id_asignatura";
        public final static String COL_ID_TIPO = "id_tipo";
        public final static String COL_FECHA = "fecha";
        public final static String COL_DURACION = "duracion";
        public final static String COL_DIA_SEMANA = "dia_semana";
    }
    public final static class TABLA_TIPO_ACTIVIDAD {
        public final static String NOMBRE = "tipo_actividad";
        public final static String COL_ID = "id";
        public final static String COL_NOMBRE = "nombre";
    }
    public final static class TABLA_ACTIVIDADES {
        public final static String NOMBRE = "actividades";
        public final static String COL_ID = "id";
        public final static String COL_ID_ASIGNATURA = "id_asignatura";
        public final static String COL_ID_TIPO = "id_tipo";
        public final static String COL_NOMBRE = "nombre";
        public final static String COL_DESCRIPCION = "descripcion";
        public final static String COL_IMPORTANCIA = "importancia";
        public final static String COL_FECHA = "fecha";
        public final static String COL_DURACION = "duracion";
        public final static String COL_PORCENTAJE = "porcentaje";
        public final static String COL_NOTA = "nota";
        public final static String COL_COMPLETADO = "completado";
    }
}
