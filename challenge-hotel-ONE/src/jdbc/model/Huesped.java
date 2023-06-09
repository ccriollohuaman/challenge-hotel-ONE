package jdbc.model;

import java.sql.Date;

public class Huesped {

	private Integer id;
	private String nombre;
	private String apellido;
	private Date fechaNacimiento;
	private String nacionalidad;
	private String telefono;
	private Integer reservaId;

	public Huesped(String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono, Integer reservaId) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.nacionalidad = nacionalidad;
		this.telefono = telefono;
		this.reservaId = reservaId;
	}


	public Huesped(Integer id, String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono, Integer reservaId) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.nacionalidad = nacionalidad;
		this.telefono = telefono;
		this.reservaId = reservaId;	
	}




	public String getNombre() {
		return nombre;
	}


	public String getApellido() {
		return apellido;
	}


	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}


	public String getNacionalidad() {
		return nacionalidad;
	}


	public String getTelefono() {
		return telefono;
	}


	public int getReservaId() {
		return reservaId;
	}


	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
		
	}
	
	@Override
	public String toString() {
		return String.format("id %s, apellido %s", id, apellido);
	}
	
}
