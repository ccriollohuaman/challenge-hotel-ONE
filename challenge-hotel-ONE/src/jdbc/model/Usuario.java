package jdbc.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Usuario {

	private Integer id;

	private String dni;
	
	private String nombre;
	
	private String apellido;

	private String usuario;

	private String contrasena;
	
	private String tipo;

	public Usuario(String dni, String nombre, String apellido, String usuario, char[] contrasena, String tipo) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.usuario = usuario;
		this.contrasena = encriptarContrasena(contrasena);
		this.tipo = tipo;
	}

	public Usuario(String usuario, char[] contrasena) {
		this.usuario = usuario;
		this.contrasena = encriptarContrasena(contrasena);
	}
	
	public Usuario(String dni, String nombre, String apellido, String usuario, String tipo) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.usuario = usuario;
		this.tipo = tipo;
	}

	public String getDni() {
		return dni;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getApellido() {
		return apellido;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getContrasena() {
		return contrasena;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setId(int id) {
		this.id = id;

	}

	private String encriptarContrasena(char[] contrasena) {
		String contrasenaString = new String(contrasena);
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(contrasenaString.getBytes(StandardCharsets.UTF_8));

			StringBuilder hexHash = new StringBuilder();
			for (byte b : hash) {
				hexHash.append(String.format("%02x", b));
			}

			return hexHash.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Object getValorCampo(String campo) {
	    switch (campo) {
	        case "DNI":
	            return this.dni;
	        case "USUARIO":
	            return this.usuario;
	        default:
	        	return null;
	    }
	}
	
	@Override
	public String toString() {
		return String.format("dni: %s",  dni);
	}
	
}
