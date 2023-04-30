package jdbc.exception;

public class UsuarioExistenteException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String campoDuplicado;

    public UsuarioExistenteException(String campoDuplicado, String valorCampo) {
        super("El valor '" + valorCampo + "' para el campo '" + campoDuplicado + "' ya existe.");
        this.campoDuplicado = campoDuplicado;
    }

    public String getCampoDuplicado() {
        return campoDuplicado;
    }
	
}
