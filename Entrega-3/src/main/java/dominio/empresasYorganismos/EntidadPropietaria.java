package dominio.empresasYorganismos;

import dominio.Miembro;
import dominio.entidades.Organizacion;
import dominio.entidades.ServicioTransporte;

import java.util.List;

public class EntidadPropietaria {
    String nombre;
    List<Organizacion> organizaciones;
    List<ServicioTransporte> serviciosTransporte;
    Miembro responsable;

    public EntidadPropietaria(String nombre, List<ServicioTransporte> serviciosTransporte, List<Organizacion> organizaciones, Miembro responsable) {
        this.nombre = nombre;
        this.serviciosTransporte = serviciosTransporte;
        this.organizaciones = organizaciones;
        this.responsable = responsable;
    }
}
