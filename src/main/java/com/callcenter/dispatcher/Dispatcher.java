package com.callcenter.dispatcher;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.validation.constraints.NotNull;

import com.callcenter.model.Empleado;
import com.callcenter.manager.GestorDeEmpleado;
import com.callcenter.model.Call;

/**
 * @author mmontano
 *
 */
public class Dispatcher {
	
	private final static int RESOURCE_QTY = 10;
	private final static Logger LOGGER = Logger.getLogger(Dispatcher.class.getName());

	private ExecutorService ex;
	private PriorityBlockingQueue<Empleado> cola;
	private int llamadasRecibidas;
	private int llamadasAtendidas;
	
	/**
	 * Despachador de llamadas
	 * @param lista de empleados
	 */
	public Dispatcher(@NotNull PriorityBlockingQueue<Empleado> empleados) {
		this(RESOURCE_QTY, empleados);
	}
	
	/**
	 * @param resourcesQty, cantidad de telefonos disponibles
	 * @param lista de empleados
	 */
	public Dispatcher(int resourcesQty, @NotNull PriorityBlockingQueue<Empleado> empleados) {
		this.ex = Executors.newFixedThreadPool(resourcesQty);
		this.cola = empleados;
		this.llamadasRecibidas = 0;
		this.llamadasAtendidas = 0;
	}
	
	/**
	 * Despacha una lista de llamadas
	 * @param calls
	 * @throws InterruptedException
	 */
	public void dispatchCalls(List<Call> calls) throws InterruptedException {
		calls.forEach(c->{
			try {
				dispatchCall(c);
			} catch (InterruptedException e) {
				LOGGER.warning("Se produjo un error");
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Despacha una llamada
	 * @param call
	 * @throws InterruptedException
	 */
	public void dispatchCall(@NotNull Call call) throws InterruptedException {
		Empleado emp = cola.take();
		ex.submit(asignarLlamada(emp,call));
	}
	
	/**
	 * Asigna una llamada a un empleado
	 * @param empleado
	 * @param call
	 */
	private Runnable asignarLlamada(@NotNull Empleado empleado, @NotNull Call call) {
		Runnable r = () -> {
			llamadasRecibidas++;
			GestorDeEmpleado.atender(empleado, call);
			llamadasAtendidas++;
			cola.add(empleado);
		};
		return r;
	}
	/**
	 *metodo para finalizar el día del call center
	 *@param timeout, tiempo de espera antes de cerrar
	*/
	public void finish(@NotNull long timeout) {
		ex.shutdown();
        try {
            if (!ex.awaitTermination(timeout, TimeUnit.SECONDS)) {
            		LOGGER.warning("Quedaron llamadas incompletas o sin atender");
                ex.shutdownNow();
            }
        } catch (InterruptedException e) {
        		LOGGER.warning("Se produjo un error");
        }
		LOGGER.info("Día terminado \n"+"En total se recibieron: "+llamadasRecibidas+" llamadas \n"+"En total se atendieron: "+llamadasAtendidas+" llamadas");
	}

	public int getLlamadasRecibidas() {
		return llamadasRecibidas;
	}

	public void setLlamadasRecibidas(int llamadasRecibidas) {
		this.llamadasRecibidas = llamadasRecibidas;
	}

	public int getLlamadasAtendidas() {
		return llamadasAtendidas;
	}

	public void setLlamadasAtendidas(int llamadasAtendidas) {
		this.llamadasAtendidas = llamadasAtendidas;
	}
	
}
