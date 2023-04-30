package jdbc.controller;

import java.sql.Date;
import java.util.List;

import jdbc.dao.ReservaDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.model.Reserva;


public class ReservaController {
	
	private ReservaDAO reservaDAO;
	
	public ReservaController() {
		var factory = new ConnectionFactory();
		this.reservaDAO = new ReservaDAO(factory.recuperaConexion());
	}
	
	public void generarIdReserva(Reserva reserva) {		
		reservaDAO.generarIdReserva(reserva);
	}
	
	public String calcularValorReserva(Date fechaEntrada, Date fechaSalida) {
		Float valorDiario = (float)25.99;
		long diferenciaFechas = fechaSalida.getTime() - fechaEntrada.getTime();
		Integer diferenciaDias = (int) (diferenciaFechas / (24 * 60 * 60 * 1000));
		Float valorReserva = diferenciaDias * valorDiario;
		String valorReservaString = String.valueOf(valorReserva);
		return String.valueOf(valorReserva);
	}

	public List<Reserva> cargarTablaReservas() {
		return reservaDAO.cargarTablaReservas();
	}


	public List<Reserva> buscarReservaPorId(Integer idReservaBuscar) {
		return reservaDAO.buscarReservaPorId(idReservaBuscar);
	}
	
	public List<Reserva> buscarReservaPorApellidoHuesped(String apellido) {
		return reservaDAO.buscarReservaPorApellido(apellido);
	}

	public Integer editarDatosReserva(Integer idReserva, Date fechaEntrada, Date fecaSalida, String formaPago) {
		String valorReserva = calcularValorReserva(fechaEntrada, fecaSalida);
		return reservaDAO.editarDatosReserva(idReserva, fechaEntrada, fecaSalida, Float.valueOf(valorReserva), formaPago);
		
	}

	public int eliminarDesdeReserva(Integer id) {
		return reservaDAO.eliminarDesdeReserva(id);
	}
}
