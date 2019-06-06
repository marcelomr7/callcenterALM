package com.callcenter.model;

import javax.validation.constraints.NotNull;

import com.callcenter.manager.CallManager;

/**
 * @author mmontano
 * Duracion minima de la llamada 5
 * Duracion maxima de la llamada 10
 */
public class Call {

	public static final Integer MIN_CALL_DURATION = 5;
	public static final Integer MAX_CALL_DURATION = 10;
	
	private Integer duracion;
	
	public Call(@NotNull Integer duracion) {
		this.duracion = duracion;
	}
	
	public Call() {
		this.duracion = CallManager.durationRandom();
	}

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}
	
}
