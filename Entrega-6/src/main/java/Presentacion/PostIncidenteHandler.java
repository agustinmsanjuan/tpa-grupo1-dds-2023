package Presentacion;

import com.google.gson.Gson;
import dominio.comunidades.*;
import dominio.servicios.Agrupacion;
import dominio.servicios.RepoServicios;
import dominio.servicios.Servicio;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostIncidenteHandler implements Handler {
    @Override
    public void handle(Context context) throws Exception {
        String incidenteJson = context.body();
        System.out.println(incidenteJson);

        IncidenteParser incidenteParser = new Gson().fromJson(incidenteJson, IncidenteParser.class);

        List<Integer> idsServicios = incidenteParser.getServicios().stream()
                .map(Integer::parseInt)
                .toList();

        //todo: traer comunidades del usuario logueado
        Miembro miembroLogueado = (Miembro) SesionManager.get().obtenerAtributos(incidenteParser.getIdSesion()).get("miembro");
        List<Comunidad> comunidades = RepoComunidades.getInstance().filtrarPorMiembro(miembroLogueado.getId());
        System.out.println(comunidades);
        Incidente incidente = crearIncidente(idsServicios, incidenteParser, comunidades);

        RepoIncidentes.getInstance().persistirIncidente(incidente);
        context.status(201);
    }

    public Incidente crearIncidente(List<Integer> idsServicios, IncidenteParser incidenteParser, List<Comunidad> comunidades) {
        if(incidenteParser.servicios.size() > 1){
            Agrupacion agrupacion = new Agrupacion(idsServicios.stream()
                    .map(id -> RepoServicios.getInstance().obtenerServicioPorId(id))
                    .toList());
            return new Incidente(agrupacion, incidenteParser.getObservacion(), comunidades, LocalDateTime.now());
        }
        else{
            Servicio servicio = RepoServicios.getInstance().obtenerServicioPorId(idsServicios.get(0));
            return new Incidente(servicio, incidenteParser.getObservacion(), comunidades, LocalDateTime.now());
        }
    }
}
