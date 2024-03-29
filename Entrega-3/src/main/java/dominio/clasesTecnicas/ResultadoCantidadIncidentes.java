package dominio.clasesTecnicas;

import dominio.entidades.Entidad;
import lombok.Getter;

import java.time.Duration;
@Getter
public class ResultadoCantidadIncidentes {
    private Entidad entidad;
    private Integer cantidadIncidentes;

    public ResultadoCantidadIncidentes(Entidad entidad, Integer cantidadIncidentes){
        this.entidad = entidad;
        this.cantidadIncidentes = cantidadIncidentes;
    }

    public int getCantidadIncidentes() {
        return cantidadIncidentes;
    }

    public int compararTiempo(ResultadoCantidadIncidentes otroResultado){
        return cantidadIncidentes.compareTo(otroResultado.getCantidadIncidentes());
    }
}
