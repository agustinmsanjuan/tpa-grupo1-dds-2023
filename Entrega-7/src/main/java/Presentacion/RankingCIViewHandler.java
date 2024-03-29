package Presentacion;

import Presentacion.dto.EntidadDTO;
import Presentacion.dto.ResultadoCIDTO;
import Presentacion.dto.ResultadoTCDTO;
import dominio.clasesTecnicas.ResultadoCantidadIncidentes;
import dominio.entidades.Entidad;
import dominio.rankings.RepoRankings;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RankingCIViewHandler implements Handler {

    @Override
    public void handle(Context context) throws Exception {
        List<ResultadoCIDTO> resultadosRanking = RepoRankings.getInstance().obtenerRankingCIActual().getResultados().stream().map(this::mapResultadoCIToDTO).collect(Collectors.toList());;
        Integer posicion = 1;
        for(ResultadoCIDTO resultado : resultadosRanking) {
            resultado.setPosicion(posicion);
            posicion++;
        }
        var model = new HashMap<String, Object>();
        model.put("resultadosRanking", resultadosRanking);
        context.render("templates/vistasRankings/RankingCI.mustache", model);
    }

    private ResultadoCIDTO mapResultadoCIToDTO(ResultadoCantidadIncidentes resultadoCI) {
        EntidadDTO entidadDTO = this.mapEntidadToDTO(resultadoCI.getEntidad());

        return new ResultadoCIDTO(entidadDTO, resultadoCI.getCantidadIncidentes());
    }
    private EntidadDTO mapEntidadToDTO(Entidad entidad) {
        return new EntidadDTO(entidad.getId(), entidad.getNombre());
    }

}

