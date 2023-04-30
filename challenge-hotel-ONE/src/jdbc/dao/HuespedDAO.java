package jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import jdbc.model.Huesped;
import jdbc.test.ContadorConexiones;

public class HuespedDAO {

	final private Connection con;
	final private ContadorConexiones conexion;

	public HuespedDAO(Connection con) {
		this.con = con;
		this.conexion = new ContadorConexiones();
	}

	public void guardarHuesped(Huesped huesped) {

		try {
			conexion.increment();
			final PreparedStatement statement = con.prepareStatement(
					"INSERT INTO HUESPED (nombre, apellido, fecha_nacimiento, nacionalidad, telefono, reserva_id) "
							+ "VALUES(?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			try (statement) {
				statement.setString(1, huesped.getNombre());
				statement.setString(2, huesped.getApellido());
				statement.setDate(3, huesped.getFechaNacimiento());
				statement.setString(4, huesped.getNacionalidad());
				statement.setString(5, huesped.getTelefono());
				statement.setInt(6, huesped.getReservaId());

				statement.execute();

				ResultSet resultSet = statement.getGeneratedKeys();
				try (resultSet) {
					while (resultSet.next()) {
						huesped.setId(resultSet.getInt(1));
						System.out.println(String.format("Fue guardado el huesped con id: %s", huesped.getId()));
					}
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			conexion.decrement();
		}
	}

	public List<Huesped> cargarTablaHuesped() {
		List<Huesped> resultado = new ArrayList<>();

		try {
			conexion.increment();
			final PreparedStatement statement = con.prepareStatement(
					"SELECT ID, NOMBRE, APELLIDO, FECHA_NACIMIENTO, NACIONALIDAD, TELEFONO, RESERVA_ID FROM HUESPED");

			try (statement) {

				statement.execute();

				final ResultSet resultSet = statement.getResultSet();
				trasformarResulSetEnHuespedes(resultado, resultSet);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			conexion.decrement();
		}

		return resultado;
	}

	public List<Huesped> buscarHuespedPorId(Integer idReservaBuscar) {
		List<Huesped> buscarPorId = new ArrayList<>();

		try {
			conexion.increment();
			final PreparedStatement statement = con.prepareStatement(
					"SELECT ID, NOMBRE, APELLIDO, FECHA_NACIMIENTO, NACIONALIDAD, TELEFONO, RESERVA_ID FROM HUESPED WHERE RESERVA_ID = ?");

			try (statement) {
				statement.setInt(1, idReservaBuscar);
				statement.execute();

				final ResultSet resultSet = statement.getResultSet();
				resultSetEnElementoBuscado(buscarPorId, resultSet);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			conexion.decrement();
		}

		return buscarPorId;
	}

	public List<Huesped> buscarHuespedPorApellido(String apellidoHuesped) {
		List<Huesped> buscarPorId = new ArrayList<>();

		try {
			conexion.increment();
			final PreparedStatement statement = con.prepareStatement(
					"SELECT ID, NOMBRE, APELLIDO, FECHA_NACIMIENTO, NACIONALIDAD, TELEFONO, RESERVA_ID FROM HUESPED WHERE APELLIDO = ?");

			try (statement) {
				statement.setString(1, apellidoHuesped);
				statement.execute();

				final ResultSet resultSet = statement.getResultSet();
				resultSetEnElementoBuscado(buscarPorId, resultSet);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			conexion.decrement();
		}

		return buscarPorId;
	}

	
	public int editarDatosHuesped(Integer id, String nombre, String apellido, Date fechaNacimiento, String nacionalidad,
			String telefono, Integer reservaId) {
		System.out.println("Estoy en el DAO");
		try {
			String sql = "UPDATE HUESPED SET NOMBRE = ?, APELLIDO = ?, FECHA_NACIMIENTO = ?, NACIONALIDAD = ?, TELEFONO = ?, RESERVA_ID = ? WHERE ID = ?";
			final PreparedStatement statement = con.prepareStatement(
					sql);

			try (statement) {
				statement.setString(1, nombre);
				statement.setString(2, apellido);
				statement.setDate(3, fechaNacimiento);
				statement.setString(4, nacionalidad);
				statement.setString(5, telefono);
				statement.setInt(6, reservaId);
				statement.setInt(7, id);

				statement.execute();

				return statement.getUpdateCount();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	private void trasformarResulSetEnHuespedes(List<Huesped> reservasEncontradas, final ResultSet resultSet) {
		try (resultSet) {
			conexion.increment();
			while (resultSet.next()) {
				reservasEncontradas.add(new Huesped(resultSet.getInt("ID"), resultSet.getString("NOMBRE"),
						resultSet.getString("APELLIDO"), resultSet.getDate("FECHA_NACIMIENTO"),
						resultSet.getString("NACIONALIDAD"), resultSet.getString("TELEFONO"),
						resultSet.getInt("RESERVA_ID")));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			conexion.decrement();
		}
	}
	
	private void resultSetEnElementoBuscado(List<Huesped> reservasEncontradas, final ResultSet resultSet) {
		try (resultSet) {
			conexion.increment();
			if (resultSet.next()) {
				reservasEncontradas.add(new Huesped(resultSet.getInt("ID"), resultSet.getString("NOMBRE"),
						resultSet.getString("APELLIDO"), resultSet.getDate("FECHA_NACIMIENTO"),
						resultSet.getString("NACIONALIDAD"), resultSet.getString("TELEFONO"),
						resultSet.getInt("RESERVA_ID")));
			} else {
				JOptionPane.showMessageDialog(null, "No se encontró huésped para el dato ingresado, valida nuevamente");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			conexion.decrement();
		}
	}

	public int eliminarDesdeHuesped(Integer id) {
		try {
			final PreparedStatement statement = con.prepareStatement("DELETE FROM HUESPED WHERE RESERVA_ID = ?");

			try (statement) {
				statement.setInt(1, id);
				statement.execute();
				return statement.getUpdateCount();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
