package dominio.comunidades;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Embeddable
public class Notificador {
  @Getter
  @ElementCollection
  @CollectionTable(name="horarios_notificacion", joinColumns=@JoinColumn(name="miembro_id"))
  private List<LocalTime> horarios;
  public Notificador(){}

  public Notificador(List<LocalTime> horariosDeNotificacion) {
    this.horarios = horariosDeNotificacion;
  }

  public boolean compararHorarioActual(){
    return horarios.stream().anyMatch(horario -> horario.getHour() == LocalTime.now().getHour());
  }

  public List<Incidente> incidenteDentroDeRangoHorario(List<Incidente> incidentes, LocalTime horarioNotificacion){
    List<Incidente> incidentesANotificar = incidentes;
    System.out.println("Rango horario del miembro: " + horarios.toString());
    if(horarios.size()>1){
      LocalTime horarioAnterior = obtenerHorarioAnterior(horarioNotificacion);
      incidentesANotificar = obtenerListaSegunRango(incidentes, horarioAnterior, horarioNotificacion);
    }
    return incidentesANotificar;
  }

  public LocalTime obtenerHorarioAnterior(LocalTime horarioNotificacion) {
    Optional<LocalTime> horarioAnterior = horarios.stream()
        .filter(horario -> horario.getHour() == horarioNotificacion.getHour())
        .findFirst()
        .map(horario -> {
          int index = horarios.indexOf(horario);
          return index == 0 ? horarios.get(horarios.size() - 1) : horarios.get(index - 1);
        });

    return horarioAnterior.orElse(null);
  }

  private List<Incidente> obtenerListaSegunRango(List<Incidente> incidentes, LocalTime horarioAnterior, LocalTime horarioNotificacion){
    if(horarios.indexOf(horarioAnterior) == horarios.size()-1){
        return incidentes.stream()
            .filter(incidente -> incidente.getHorarioApertura().toLocalTime().isAfter(horarioAnterior)||
                incidente.getHorarioApertura().toLocalTime().isBefore(horarioNotificacion))
            .toList();
    } else {
      return incidentes.stream()
          .filter(incidente -> incidente.getHorarioApertura().toLocalTime().isAfter(horarioAnterior)&&
              incidente.getHorarioApertura().toLocalTime().isBefore(horarioNotificacion))
          .toList();
    }
  }
}
