package Presentacion.dto;

import dominio.Localizacion.Localizacion;
import dominio.Notificacion.MedioDeComunicacion;
import dominio.clasesTecnicas.Usuario;
import dominio.comunidades.Comunidad;
import dominio.comunidades.Interes;
import dominio.comunidades.Miembro;
import dominio.comunidades.TipoMiembro;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class UsuarioDTO {
    private int id;
    private String nombre;
    private String apellido;
    private String correoElectronico;
    private MedioDeComunicacion medioPreferido;
    private String nroDeTelefono;
    private Interes interes;
    private Localizacion localizacion;
    private TipoMiembro tipo;
    private List<Comunidad> comunidades;

    public UsuarioDTO(){

    }

    public UsuarioDTO(int id, String nombre, String apellido, String correoElectronico, MedioDeComunicacion medioPreferido, String nroDeTelefono, Interes interes, Localizacion localizacion, TipoMiembro tipo, List<Comunidad> comunidades) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.medioPreferido = medioPreferido;
        this.nroDeTelefono = nroDeTelefono;
        this.interes = interes;
        this.localizacion = localizacion;
        this.tipo = tipo;
        this.comunidades = comunidades;
    }

    public UsuarioDTO(Miembro miembro, TipoMiembro tipo) {
        this.id = miembro.getId();
        this.nombre = miembro.getNombre();
        this.apellido = miembro.getApellido();
        this.correoElectronico = miembro.getCorreoElectronico();
        //this.medioPreferido = miembro.getMedioPreferido();
        this.nroDeTelefono = miembro.getNroDeTelefono();
        //this.interes = miembro.getInteres();
        //this.localizacion = miembro.getLocalizacion();
        this.tipo = tipo;
    }
}