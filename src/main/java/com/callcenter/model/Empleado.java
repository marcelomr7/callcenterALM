package com.callcenter.model;

import javax.validation.constraints.NotNull;

/**
 * @author mmontano
 */
public class Empleado implements Comparable<Empleado> {

	private TipoEmpleado tipo;
	private String nombre;
	
	public Empleado(@NotNull TipoEmpleado rango) {
		this.tipo = rango;
	}
	
	public Empleado(@NotNull TipoEmpleado rango, @NotNull String nombre) {
		this.tipo = rango;
		this.nombre = nombre;
	}

	public String getNombre() {
		return this.nombre;
	}
	
	public void setNombre(@NotNull String nombre) {
		this.nombre = nombre;
	}
	
	public TipoEmpleado getTipo() {
		return this.tipo;
	}
	
	public void setRango(@NotNull TipoEmpleado rango) {
		this.tipo = rango;
	}
	
	public int compareTo(@NotNull Empleado otro) {
		return this.tipo.compareTo(otro.getTipo());
	}
	
	@Override
	public String toString() {
		return this.getNombre() + " ("+this.tipo.toString()+")";
	}

}
