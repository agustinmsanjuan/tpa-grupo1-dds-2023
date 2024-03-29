package dominio.comunidades;

import dominio.servicios.Agrupacion;
import dominio.servicios.Servicio;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RepoIncidentes {
    static RepoIncidentes instance = new RepoIncidentes();
    List<Incidente> incidentes = new ArrayList<>();

    public static RepoIncidentes getInstance() {
        return instance;
    }

    public List<Incidente> obtenerIncidentesPorEstado(EstadoIncidente estado){
        return (List<Incidente>) incidentes.stream().filter(i->i.tieneEstado(estado));
    }

    public void agregar(Incidente incidente) {
        incidentes.add(incidente);
    }

    public List<Duration> obtenerTiempos(){
        return new ArrayList<>();
    }

    public List<Incidente> obtenerIncidentesDe(List<Agrupacion>agrupaciones, List <Servicio> servicios){
        if (agrupaciones.isEmpty() && servicios.isEmpty()) {
            return Collections.emptyList(); // Devuelve una lista vacía si ambas listas están vacías.
        }
        return incidentes.stream()
            .filter(i -> i.getHorarioCierre() != null && i.cerradoUltimaSemana())
            .filter(incidente -> (agrupaciones.isEmpty() || agrupaciones.contains(incidente.getAgrupacion()))
                || (servicios.isEmpty() || servicios.contains(incidente.getServicio())))
            .toList();
    }

    public List<Incidente> obtenerIncidentesDe(List <Servicio> servicios){
        if (servicios == null || servicios.isEmpty()) {
            return Collections.emptyList(); // Devuelve una lista vacía si servicios está vacía.
        }
        return incidentes.stream()
                .filter(i-> i.getHorarioCierre()!=null && i.cerradoUltimaSemana())
                .filter(incidente->incidente.getServicio() != null && servicios.contains(incidente.getServicio()))
                .collect(Collectors.toList());
    }


}
