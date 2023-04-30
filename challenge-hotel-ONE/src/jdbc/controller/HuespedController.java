package jdbc.controller;

import java.sql.Date;
import java.util.List;

import jdbc.dao.HuespedDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.model.Huesped;

public class HuespedController {

private HuespedDAO huespedDAO;
	
	public HuespedController() {
		var factory = new ConnectionFactory();
		this.huespedDAO = new HuespedDAO(factory.recuperaConexion());
	}
	
	public void guardarHuesped(Huesped huesped) {
		huespedDAO.guardarHuesped(huesped);
	}

	public List<Huesped> cargarTablaHuesped() {
		return huespedDAO.cargarTablaHuesped();
	}

	public List<Huesped> buscarHuespedPorIdReserva(Integer idReservaBuscar) {
		return huespedDAO.buscarHuespedPorId(idReservaBuscar);
	}

	public List<Huesped> buscarHuespedPorApellido(String apellidoHuesped) {
		return huespedDAO.buscarHuespedPorApellido(apellidoHuesped);
	}

	public int editarDatosHuesped(Integer id, String nombre, String apellido, Date fechaNacimiento, String nacionalidad,
			String telefono, Integer reservaId) {
		return huespedDAO.editarDatosHuesped(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, reservaId);
	}

	public int eliminarDesdeHuesped(Integer id) {
		return huespedDAO.eliminarDesdeHuesped(id);
	}

	
}
