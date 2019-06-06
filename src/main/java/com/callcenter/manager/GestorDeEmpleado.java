package com.callcenter.manager;

import java.util.concurrent.PriorityBlockingQueue;

import javax.validation.constraints.NotNull;

import com.callcenter.model.Call;
import com.callcenter.model.Empleado;
import com.callcenter.model.TipoEmpleado;

import java.util.logging.Logger;

/**
 * @author mmontano
 */
public class GestorDeEmpleado 
{
	private final static Logger LOGGER = Logger.getLogger(Empleado.class.getName());

	/**
	 * generacion de Empleados fijos
	 * @return lista de 10 empleados
	 */
    public static PriorityBlockingQueue<Empleado> crearEmpleados(){
    		final PriorityBlockingQueue<Empleado> empleados = new PriorityBlockingQueue<Empleado>();

    		empleados.add(buildOperator("Mariano Sampol"));
    		empleados.add(buildOperator("Flora Rodriguez"));
    		empleados.add(buildOperator("Lihuel Castillo"));
    		empleados.add(buildOperator("Micaela Morinigo"));
    		empleados.add(buildOperator("Nelson Rodriguez"));
    		empleados.add(buildOperator("Guido Rodriguez"));
    		empleados.add(buildOperator("Franco Tarditi"));
    		
    		empleados.add(buildSupervisor("Marcelo Montano"));
    		empleados.add(buildSupervisor("Fredy Almanza"));
    		
    		empleados.add(buildDirector("Marianno Benitez"));
    		
    		return empleados;
    }
    
    /**
     * Constructor de Empleados de tipo OPERADOR
     * @param name
     * @return OPERADOR
     */
	public static Empleado buildOperator(@NotNull String name) {
		return new Empleado(TipoEmpleado.OPERADOR, name);
	}

    /**
     * Constructor de Empleados de tipo SUPERVISOR
     * @param name
     * @return SUPERVISOR
     */
	public static Empleado buildSupervisor(@NotNull String name) {
		return new Empleado(TipoEmpleado.SUPERVISOR, name);
	}

    /**
     * Constructor de Empleados de tipo DIRECTOR
     * @param name
     * @return DIRECTOR
     */
	public static Empleado buildDirector(@NotNull String name) {
		return new Empleado(TipoEmpleado.DIRECTOR, name);
	}
	
	/**
	 * Empleado toma la llamada asignada
	 * @param empleado
	 * @param call
	 */
	public static void atender(@NotNull Empleado empleado, @NotNull Call call) 
	{
		LOGGER.info(empleado.toString()+" tomo la proxima llamada");
		try {
			Thread.sleep(call.getDuracion() * 1000);
			LOGGER.info(empleado.toString()+" finalizo correctamente la atencion, con una duración de "+call.getDuracion()+" segundos");
		} catch (InterruptedException e) {
			LOGGER.warning(empleado.toString()+": No se pudo atender o completar la llamada");
		}
	}
	
}
