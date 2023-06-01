package dominio.excepciones;

public class ContraseniaIncorrectaExcepcion extends RuntimeException {
  public ContraseniaIncorrectaExcepcion() {
    super("Contrasenia invalida");
  }

  public ContraseniaIncorrectaExcepcion(String mensaje) {
    super(mensaje);
  }
}
