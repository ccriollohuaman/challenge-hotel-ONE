package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jdbc.common.exception.UsuarioExistenteException;
import jdbc.common.test.ContadorConexiones;
import jdbc.model.Usuario;

public class UsuarioDAO {

	final private Connection con;
	final private ContadorConexiones conexion;

	public UsuarioDAO(Connection con) {
		this.con = con;
		this.conexion = new ContadorConexiones();
	}

	public void guardar(Usuario usuario) throws UsuarioExistenteException {
		try {
			conexion.increment();
			final PreparedStatement statement = con.prepareStatement(
					"INSERT INTO USUARIO (dni, nombre, apellido, usuario, contrasena, tipo) VALUES(?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			try (statement) {
				statement.setString(1, usuario.getDni());
				statement.setString(2, usuario.getNombre());
				statement.setString(3, usuario.getApellido());
				statement.setString(4, usuario.getUsuario());
				statement.setString(5, usuario.getContrasena());
				statement.setString(6, usuario.getTipo());

				statement.execute();

				final ResultSet resultSet = statement.getGeneratedKeys();
				try (resultSet) {
					while (resultSet.next()) {
						usuario.setId(resultSet.getInt(1));
						System.out.println(String.format("Fue creado correctamente el usuario con %s", usuario));

					}
				}
			}
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				String campoDuplicado = null;
				if (e.getMessage().contains("usuario.dni")) {
					campoDuplicado = "DNI";
				} else if (e.getMessage().contains("usuario.usuario")) {
					campoDuplicado = "USUARIO";
				}
				if (campoDuplicado != null) {
					throw new UsuarioExistenteException(campoDuplicado, usuario.getValorCampo(campoDuplicado).toString());
				} else {
					throw new RuntimeException(e);
				}
			} else {
				throw new RuntimeException(e);
			}
		} finally {
			conexion.decrement();
		}
	}

}
