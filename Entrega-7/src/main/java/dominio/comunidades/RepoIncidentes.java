package dominio.comunidades;

import Utils.BDUtils;
import dominio.servicios.Agrupacion;
import dominio.servicios.Servicio;

import javax.persistence.EntityManager;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RepoIncidentes {
    static RepoIncidentes instance = new RepoIncidentes();
    List<Incidente> incidentes = new ArrayList<>();

    private EntityManager em = BDUtils.getEntityManager();

    public static RepoIncidentes getInstance() {
        return instance;
    }

    public List<Incidente> obtenerIncidentesPorEstado(EstadoIncidente estado){
        return em.createQuery("from Incidente where estadoIncidente = :estado", Incidente.class)
                .setParameter("estado", estado)
                .getResultList();
    }

    public void agregar(Incidente incidente) {
        incidentes.add(incidente);
    }

    public List<Incidente> obtenerIncidentes(){
        return em.createQuery("from Incidente").getResultList();
//        return em.createQuery(
//                "SELECT i.id, i.observacion, CAST(i.horarioApertura AS string), i.servicio.id " +
//                    "FROM Incidente i")
//            .getResultList();
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

    public void persistirIncidente(Incidente incidente){
        BDUtils.comenzarTransaccion(em);
        em.merge(incidente); // con persist tiraba error en el servicio asociado
        BDUtils.commit(em);
        em.close();
    }


    public List<Incidente> obtenerIncidentesPorMiembro(Miembro miembroLogueado) {
        List<Comunidad> comunidadesDeMiembro = RepoComunidades.getInstance().obtenerComunidadesPor(miembroLogueado);
        return comunidadesDeMiembro.stream().flatMap(c -> c.getIncidentesAbiertos().stream()).toList();
    }
    public void actualizar(Incidente incidente) {
        BDUtils.comenzarTransaccion(em);
        em.merge(incidente);
        BDUtils.commit(em);
        em.close();
    }
}
