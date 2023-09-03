package inicializacion;

import dominio.establecimientos.Estacion;
import dominio.establecimientos.Sucursal;
import dominio.servicios.Servicio;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class InstanciasEstablecimientos {
  private Estacion estacionA1;
  private Estacion estacionA2;
  private Estacion estacionB1;
  private Estacion estacionB2;

  private Sucursal sucursalCoto1;
  private Sucursal sucursalCoto2;
  private Sucursal sucursalDia1;
  private Sucursal sucursalDia2;

  public InstanciasEstablecimientos(InstanciasServicios servicios) {
    this.estacionA1 = estacion(servicios.getServiciosEstacionA1());
    this.estacionA2 = estacion(servicios.getServiciosEstacionA2());
    this.estacionB1 = estacion(servicios.getServiciosEstacionB1());
    this.estacionB2 = estacion(servicios.getServiciosEstacionB2());
    this.sucursalCoto1 = sucursal(servicios.getServiciosCoto1());
    this.sucursalCoto2 = sucursal(servicios.getServiciosCoto2());
    this.sucursalDia1 = sucursal(servicios.getServiciosDia1());
    this.sucursalDia2 = sucursal(servicios.getServiciosDia2());

  }
  private Estacion estacion(List<Servicio> servicios){
    return new Estacion(servicios);
  }
  private Sucursal sucursal(List<Servicio> servicios){
    return new Sucursal(servicios);
  }
}
