package Presentacion;

import Presentacion.dto.IncidenteDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dominio.clasesTecnicas.HibernateProxyTypeAdapter;
import dominio.comunidades.*;
import dominio.servicios.RepoServicios;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetIncidentesPorEstadoHandler implements Handler {
    @Override
    public void handle(Context context) throws Exception {
        String idSesion = context.pathParam("id_sesion");
        String filtro = context.queryParam("estado");
        List<Incidente> incidentes = new ArrayList<>();
        GsonBuilder b = new GsonBuilder();
        b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
        Gson gson = b.create();

        Map<String,Object> session  = SesionManager.get().obtenerAtributos(idSesion);

        Miembro miembroLogueado = (Miembro) session.get("usuario");
       // List<Comunidad> comunidades = RepoComunidades.getInstance().filtrarPorMiembro(miembroLogueado.getId());
        List<Comunidad> comunidades = (List<Comunidad>) session.get("comunidades");
        comunidades.forEach(c->System.out.println(c.getId() + ", " + c.getNombre()));
        comunidades.forEach(c->System.out.println(c.getIncidentesAbiertos()));

        switch(filtro){
            case "abierto":
                HashSet<Incidente> incidentesAbiertos = new HashSet<>(incidentes = comunidades.stream()
                    .flatMap(comunidad -> comunidad.getIncidentesAbiertos().stream())
                    .collect(Collectors.toList()));
                incidentes = incidentesAbiertos.stream().toList();
                break;
            case "cerrado":
                incidentes = RepoIncidentes.getInstance().obtenerIncidentesPorEstado(EstadoIncidente.CERRADO);
                break;
            default:
                incidentes = RepoIncidentes.getInstance().obtenerIncidentes();
                break;
        }
        List<IncidenteDTO> incidenteDTOs = new ArrayList<>();
        for (Incidente incidente : incidentes) {
            IncidenteDTO incidenteDTO = new IncidenteDTO(incidente);
            // Agregar el DTO a la lista
            incidenteDTOs.add(incidenteDTO);
        }
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonString = mapper.writeValueAsString(incidentes);
//        System.out.println(jsonString);
        String jsonString= gson.toJson(incidenteDTOs);
        System.out.println(jsonString);
        context.json(jsonString);
    }
}
