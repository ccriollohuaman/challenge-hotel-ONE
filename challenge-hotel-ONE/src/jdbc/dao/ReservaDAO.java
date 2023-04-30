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

import jdbc.model.Reserva;
import jdbc.test.ContadorConexiones;


public class ReservaDAO {

	final private Connection con;
	final private ContadorConexiones conexion;

	public ReservaDAO(Connection con) {
		this.con = con;
		this.conexion = new ContadorConexiones();
	}

	public void generarIdReserva(Reserva reserva) {
		try {
			conexion.increment();
			final PreparedStatement statement = con.prepareStatement(
					"INSERT INTO RESERVA (fecha_entrada, fecha_salida, valor, forma_pago) " + "VALUES(?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			try (statement) {
				statement.setDate(1, (Date) reserva.getFechaEntrada());
				statement.setDate(2, (Date) reserva.getFechaSalida());
				statement.setFloat(3, reserva.getValor());
				System.out.println("Estoy en el reserva DAo para ver el valor de la resrva " + reserva.getValor());
				statement.setString(4, reserva.getFormaPago());

				statement.execute();

				final ResultSet resultSet = statement.getGeneratedKeys();
				try (resultSet) {
					while (resultSet.next()) {
						reserva.setId(resultSet.getInt(1));
						System.out.println(String.format("Se registro la reserva %s", reserva));
					}
				}

			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			conexion.decrement();
		}
	}

	public List<Reserva> cargarTablaReservas() {
		List<Reserva> tablaReservas = new ArrayList<>();

		try {
			conexion.increment();
			final PreparedStatement statement = con
					.prepareStatement("SELECT ID, FECHA_ENTRADA, FECHA_SALIDA, VALOR, FORMA_PAGO FROM RESERVA");

			try (statement) {
				statement.execute();

				final ResultSet resultSet = statement.getResultSet();
				trasformarResulSetEnReserva(tablaReservas, resultSet);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			conexion.decrement();
		}

		return tablaReservas;
	}

	public List<Reserva> buscarReservaPorId(Integer idReservaBuscar) {

		List<Reserva> buscarPorId = new ArrayList<>();

		try {
			conexion.increment();
			final PreparedStatement statement = con.prepareStatement(
					"SELECT ID, FECHA_ENTRADA, FECHA_SALIDA, VALOR, FORMA_PAGO FROM RESERVA WHERE ID = ?");

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

	public List<Reserva> buscarReservaPorApellido(String apellido) {
		List<Reserva> buscarPorApellido = new ArrayList<>();

		try {
			conexion.increment();

			final PreparedStatement statement = con
					.prepareStatement("SELECT R.ID, FECHA_ENTRADA, FECHA_SALIDA, VALOR, FORMA_PAGO " + "FROM RESERVA R "
							+ "INNER JOIN HUESPED H ON R.ID = H.RESERVA_ID " + "WHERE APELLIDO = ?");

			try (statement) {
				statement.setString(1, apellido);
				statement.execute();

				final ResultSet resultSet = statement.getResultSet();
				resultSetEnElementoBuscado(buscarPorApellido, resultSet);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			conexion.decrement();
		}

		return buscarPorApellido;

	}
	
	public Integer editarDatosReserva(Integer idReserva, Date fechaEntrada, Date fechaSalida, Float valorReserva, String formaPago) {
		try {
			String sql = "UPDATE RESERVA SET FECHA_ENTRADA = ?, FECHA_SALIDA = ?, VALOR = ?, FORMA_PAGO = ? WHERE ID = ?";
			final PreparedStatement statement = con.prepareStatement(
					sql);

			try (statement) {
				statement.setDate(1, fechaEntrada);
				statement.setDate(2, fechaSalida);
				statement.setFloat(3, valorReserva);
				statement.setString(4, formaPago);
				statement.setInt(5, idReserva);

				statement.execute();

				return statement.getUpdateCount();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int eliminarDesdeReserva(Integer id) {
		try {
			final PreparedStatement statement = con.prepareStatement("DELETE FROM RESERVA WHERE ID = ?");

			try (statement) {
				statement.setInt(1, id);
				statement.execute();
				return statement.getUpdateCount();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void trasformarResulSetEnReserva(List<Reserva> resultado, final ResultSet resultSet) {
		try (resultSet) {
			conexion.increment();
			while (resultSet.next()) {
				resultado.add(new Reserva(resultSet.getInt("ID"), resultSet.getDate("FECHA_ENTRADA"),
						resultSet.getDate("FECHA_SALIDA"), resultSet.getFloat("VALOR"),
						resultSet.getString("FORMA_PAGO")));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			conexion.decrement();
		}
	}

	private void resultSetEnElementoBuscado(List<Reserva> resultado, final ResultSet resultSet) {
		try (resultSet) {
			conexion.increment();
			if (resultSet.next()) {
				resultado.add(new Reserva(resultSet.getInt("ID"), resultSet.getDate("FECHA_ENTRADA"),
						resultSet.getDate("FECHA_SALIDA"), resultSet.getFloat("VALOR"),
						resultSet.getString("FORMA_PAGO")));
			} else {
				JOptionPane.showMessageDialog(null, "No se encontró reserva para el dato ingresado, valida nuevamente");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			conexion.decrement();
		}
	}
}