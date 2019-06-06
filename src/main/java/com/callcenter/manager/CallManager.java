package com.callcenter.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.callcenter.model.Call;

/**
 * @author mmontano
 * gestor de llamadas
 */
public class CallManager 
{
	private static final int MIN_CALL_QTY = 10;
	private static final int MAX_CALL_QTY = 100;
    
	/**
	 * generacion de llamadas random
	 * @return
	 */
	public static List<Call> callsQtyRandom() {
		return callsQtyRandom(callQtyRandom());
	}
	
	/**
	 * generacion de una cantidad de llamadas determinadas
	 * @param callQty, cantidad de llamadas a generar
	 * @return
	 */
	public static List<Call> callsQtyRandom(int callQty) {
		List<Call> calls = new ArrayList<>();
		for(int i = 0; i<callQty; i++) {
			calls.add(new Call(durationRandom()));
		}
		return calls;
	}
	

	/**
	 * generacion de duracion random de una llamada
	 * @return
	 */
	public static int durationRandom() {
		return random(Call.MIN_CALL_DURATION, Call.MAX_CALL_DURATION);
	}
	
	/**
	 * generacion de cantidad random de llamadas
	 * @return
	 */
	public static Integer callQtyRandom() {
		return random(MIN_CALL_QTY, MAX_CALL_QTY);
	}
    
	public static Integer random(int min, int max) {
		Random rn = new Random();
		int range = max - min + 1;
		int randomNum =  rn.nextInt(range) + min;
		return randomNum;
	}
}
