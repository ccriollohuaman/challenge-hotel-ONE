package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.model.Usuario;
import jdbc.test.ContadorConexiones;

public class LoginDAO {

	final private Connection con;
	final private ContadorConexiones conexion;

	public LoginDAO(Connection con) {
		this.con = con;
		this.conexion = new ContadorConexiones();
	}

	public Usuario autentica(String usuario, char[] contrasena) {
	    Usuario usuarioConEncrip = new Usuario(usuario, contrasena);

	    try {
	        conexion.increment();
	        final PreparedStatement statement = con.prepareStatement("SELECT * FROM USUARIO WHERE usuario = ? AND contrasena = ?");

	        try (statement) {
	            statement.setString(1, usuarioConEncrip.getUsuario());
	            statement.setString(2, usuarioConEncrip.getContrasena());

	            final ResultSet resultSet = statement.executeQuery();

	            try (resultSet) {
	                if (resultSet.next()) {
	                    Usuario usuarioBD = new Usuario(
	                        resultSet.getString("dni"),
	                        resultSet.getString("nombre"),
	                        resultSet.getString("apellido"),
	                        resultSet.getString("usuario"),
	                        resultSet.getString("tipo")
	                    );
	                    return usuarioBD;
	                } else {
	                    return null;
	                }
	            }

	        }

	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    } finally {
	        conexion.decrement();
	    }
	}
	
}
