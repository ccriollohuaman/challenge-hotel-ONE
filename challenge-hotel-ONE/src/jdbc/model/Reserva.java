package jdbc.model;

import java.sql.Date;

public class Reserva {
	
	private Integer id;
	private Date fechaEntrada;
	private Date fechaSalida;
	private Float valor;
	private String formaPago;
	
	public Reserva(Date fechaEntrada, Date fechaSalida, Float valor, String formaPago) {
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.valor = valor;
		this.formaPago = formaPago;
	}


	public Reserva(Integer id, Date fechaEntrada, Date fechaSalida, Float valor, String formaPago) {
		this.id = id;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.valor = valor;
		this.formaPago = formaPago;
	}


	public Date getFechaEntrada() {
		return fechaEntrada;
	}


	public Date getFechaSalida() {
		return fechaSalida;
	}
	

	public float getValor() {
		return valor;
	}
	

	public String getFormaPago() {
		return formaPago;
	}


	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	
	@Override
	public String toString() {
		return String.format("id %s", id);
	}
}
