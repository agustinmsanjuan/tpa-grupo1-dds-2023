package dominio.servicios;

import javax.persistence.*;
import java.util.List;

@Entity

public class Agrupacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agrupacion_id")
    private int id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Servicio> servicios;

    public Agrupacion(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    public Agrupacion() {
    }
}
