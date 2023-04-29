package jdbc.controller;

import java.util.Arrays;

import jdbc.dao.LoginDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.model.Usuario;


public class LoginController {
	
	private LoginDAO loginDAO;
	
	public LoginController() {
		var factory = new ConnectionFactory();
		this.loginDAO = new LoginDAO(factory.recuperaConexion());
	}

	public Usuario autentica(String usuario, char[] contrasena) {
		Usuario resultado = loginDAO.autentica(usuario, contrasena);
		System.out.println(contrasena);
	    Arrays.fill(contrasena, '\0');
	    System.out.println(contrasena);
	    return resultado;
	}

}
