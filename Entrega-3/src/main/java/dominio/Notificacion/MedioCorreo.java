package dominio.Notificacion;

import dominio.comunidades.Comunidad;
import dominio.comunidades.Incidente;
import dominio.comunidades.Miembro;
import java.util.List;

public class MedioCorreo extends MedioDeComunicacion {
    private static MedioCorreo instance = null;
    CorreoAPI correoAPI = null;

    public static MedioCorreo getInstance(CorreoAPI correoAPI) {
        if (instance == null) {
            instance = new MedioCorreo();
            instance.correoAPI = correoAPI;
        }
        return instance;
    }

    @Override
    public void notificarIncidentes(Miembro miembro, List<Incidente> incidentes, Comunidad comunidad) {
        adaptarMensaje(incidentes, comunidad);
        //correoAPI.enviarCorreo(miembro.getCorreo(), mensajes); TODO:descomentar para enviar mail
    }
}
