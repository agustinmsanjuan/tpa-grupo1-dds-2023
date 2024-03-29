package dominio.comunidades;

import dominio.servicios.Agrupacion;
import dominio.servicios.Servicio;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Setter
public class Incidente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "incidente_id")
    private int id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id", referencedColumnName = "servicio_id")
    @Getter
    private Servicio servicio;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "agrupacion_id")
    private Agrupacion agrupacion;
    @Getter
    private String observacion;

    //@ManyToMany(mappedBy = "incidentesAbiertos", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
   /*@JoinTable(name = "incidente_comunidad",
        joinColumns = @JoinColumn(name = "incidente_id"),
        inverseJoinColumns = @JoinColumn(name = "comunidad_id")
    )*/
    private List<Comunidad> comunidades = new ArrayList<>();

    private LocalDateTime horarioApertura;
    private LocalDateTime horarioCierre;
    //TODO: tendriamos que agregar enumerated string
    private EstadoIncidente estadoIncidente;

    public Incidente(Servicio servicio, Agrupacion agrupacion, String observacion,
                     List<Comunidad> comunidades, LocalDateTime horarioApertura,
                     LocalDateTime horarioCierre, EstadoIncidente estadoIncidente) {
        this.servicio = servicio;
        this.agrupacion = agrupacion;
        this.observacion = observacion;
        this.comunidades = comunidades;
        this.horarioApertura = horarioApertura;
        this.horarioCierre = horarioCierre;
        this.estadoIncidente = estadoIncidente;
        comunidades.forEach(c->c.agregarIncidente(this));
    }
    // Constructor para test ranking
    public Incidente(Servicio servicio, LocalDateTime horarioApertura, LocalDateTime horarioCierre, String obs){
        this.servicio = servicio;
        this.horarioApertura = horarioApertura;
        this.horarioCierre = horarioCierre;
        this.observacion = obs;
        this.estadoIncidente = EstadoIncidente.CERRADO;
        RepoIncidentes repoIncidentes = RepoIncidentes.getInstance();
        repoIncidentes.agregar(this);
    }
    public Incidente(Agrupacion agrupacion, LocalDateTime horarioApertura, LocalDateTime horarioCierre, String obs){
        this.agrupacion = agrupacion;
        this.horarioApertura = horarioApertura;
        this.horarioCierre = horarioCierre;
        this.observacion = obs;
        this.estadoIncidente = EstadoIncidente.CERRADO;
        RepoIncidentes repoIncidentes = RepoIncidentes.getInstance();
        repoIncidentes.agregar(this);
    }

    public Incidente(Servicio servicio, LocalDateTime horarioApertura, String obs){
        this.servicio = servicio;
        this.horarioApertura = horarioApertura;
        this.observacion = obs;
        this.estadoIncidente = EstadoIncidente.ABIERTO;
        RepoIncidentes repoIncidentes = RepoIncidentes.getInstance();
        repoIncidentes.agregar(this);
    }

    public Incidente(Servicio servicio, LocalDateTime horarioApertura, LocalDateTime horarioCierre){
        this.servicio = servicio;
        this.horarioApertura = horarioApertura;
        this.horarioCierre = horarioCierre;
        this.estadoIncidente = EstadoIncidente.CERRADO;
        RepoIncidentes repoIncidentes = RepoIncidentes.getInstance();
        repoIncidentes.agregar(this);
    }

    public Incidente(Agrupacion agrupacion, LocalDateTime horarioApertura, LocalDateTime horarioCierre){
        this.agrupacion = agrupacion;
        this.horarioApertura = horarioApertura;
        this.horarioCierre = horarioCierre;
        this.estadoIncidente = EstadoIncidente.CERRADO;
        RepoIncidentes repoIncidentes = RepoIncidentes.getInstance();
        repoIncidentes.agregar(this);
    }

    public Incidente(Servicio servicio, String observacion, List<Comunidad> comunidades, LocalDateTime horarioApertura) {
        this.servicio = servicio;
        this.observacion = observacion;
        this.comunidades = comunidades;
        this.horarioApertura = horarioApertura;
        this.estadoIncidente = EstadoIncidente.ABIERTO;
        RepoIncidentes repoIncidentes = RepoIncidentes.getInstance();
        repoIncidentes.agregar(this);
        if(comunidades != null) {
            comunidades.forEach(c ->c.agregarIncidente(this));
        }

    }

    public Incidente(Agrupacion agrupacion, String observacion, List<Comunidad> comunidades, LocalDateTime horarioApertura) {
        this.agrupacion = agrupacion;
        this.observacion = observacion;
        this.comunidades = comunidades;
        this.horarioApertura = horarioApertura;
        this.estadoIncidente = EstadoIncidente.ABIERTO;
        RepoIncidentes repoIncidentes = RepoIncidentes.getInstance();
        repoIncidentes.agregar(this);
        comunidades.forEach(c ->c.agregarIncidente(this));
    }

    public Incidente() {
    }

    public Duration obtenerTiempoCierre(){
        return Duration.between(horarioApertura, horarioCierre);
    }

    public void cerrar(){
        comunidades.forEach(c->c.removerIncidente(this));
        this.horarioCierre = LocalDateTime.now();
        this.estadoIncidente = EstadoIncidente.CERRADO;
        RepoIncidentes.getInstance().actualizar(this);
    }

    public boolean tieneEstado(EstadoIncidente estado){
        return this.estadoIncidente.equals(estado);
    }

    public boolean cerradoUltimaSemana(){
       return Duration.between(this.getHorarioCierre() , LocalDateTime.now()).minusDays(7).isNegative();
    }

    public void agregarComunidad(Comunidad... comunidades){
        for(Comunidad comunidad:comunidades){
            if(! this.comunidades.contains(comunidad)){
                this.comunidades.add(comunidad);
            }
        }
    }

    @Override
    public String toString() {
        String mensaje;
        if (servicio==null){
            mensaje = "Incidente en la agrupacion: "+agrupacion.getId()+ ", abierto:"+
                DateTimeFormatter.ofPattern("dd-MM-yyyy 'a las' HH:mm:ss").format(horarioApertura);
        }else{
            mensaje = "Incidente en el servicio: "+servicio.nombre+ ", abierto:"+
                DateTimeFormatter.ofPattern("dd-MM-yyyy 'a las' HH:mm:ss").format(horarioApertura);
        }
        return mensaje;
    }
}
