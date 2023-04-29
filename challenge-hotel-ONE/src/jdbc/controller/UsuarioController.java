package jdbc.controller;

import jdbc.dao.UsuarioDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.model.Usuario;

public class UsuarioController {

	private UsuarioDAO usuarioDAO;
	
	public UsuarioController() {
		var factory = new ConnectionFactory();
		this.usuarioDAO = new UsuarioDAO(factory.recuperaConexion());
	}
	
	public void guardar(Usuario usuario) {
		usuarioDAO.guardar(usuario);
	}
	
}
