package com.callcenter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.PriorityBlockingQueue;

import javax.validation.constraints.Null;

import org.junit.Test;

import com.callcenter.model.Empleado;
import com.callcenter.model.Call;
import com.callcenter.model.TipoEmpleado;
import com.callcenter.dispatcher.Dispatcher;
import com.callcenter.manager.CallManager;
import com.callcenter.manager.GestorDeEmpleado;

/**
 * @author mmontano
 */
public class Tests{
	private PriorityBlockingQueue<Empleado> lista;
	private Dispatcher dispatcher;
	private int llamadasAtendidas;
	private int llamadasRecibidas;;
	
	/**
	 * constructor de datos por defecto, 
	 * para los tests que no necesiten generar datos particulares
	 */
	public void builder() throws InterruptedException {
		builder(null, null);
	}
	
	/**
	 * constructor de datos necesarios para el test
	 * @param callsQty, cantidad de llamadas que se quieren crear
	 */
	public void builderWithCallsQty(int callsQty) throws InterruptedException {
		builder(null, callsQty);
	}
	
	/**
	 * constructor de datos necesarios para el test
	 * @param resourcesQty, cantidad de recursos/telefonos que se quieren crear
	 */
	public void builderWithResourcesQty(int resourcesQty) throws InterruptedException {
		builder(resourcesQty, null);
	}
	
	/**
	 * constructor de datos necesarios para el test
	 * @param resourcesQty, cantidad de recursos,telefonos que se quieren crear
	 * @param callsQty, cantidad de llamadas que se quieren crear
	 */
	public void builder(@Null Integer resourcesQty, @Null Integer callsQty) throws InterruptedException {
        lista = GestorDeEmpleado.crearEmpleados();
        dispatcher = resourcesQty != null ? new Dispatcher(resourcesQty, lista) : new Dispatcher(lista);
		dispatcher.dispatchCalls(callsQty != null ? CallManager.callsQtyRandom(callsQty) : CallManager.callsQtyRandom());
	}
	
	/**
	 * test base de las 10 llamadas
	 * con 10 recursos/telefonos
	 */
	@Test
	public void test10Llamados() throws InterruptedException {
		builderWithCallsQty(10);
		dispatcher.finish(25);
		llamadasAtendidas = dispatcher.getLlamadasAtendidas();
		llamadasRecibidas = dispatcher.getLlamadasRecibidas();
		
		assertThat(llamadasAtendidas).isEqualTo(10);
		assertThat(llamadasRecibidas).isEqualTo(10);
		assertThat(llamadasAtendidas).isEqualTo(llamadasRecibidas);
	}
	
	/**
	 * generacion de una cantidad random de llamadas 
	 * y recursos/telefonos
	 */
	@Test
	public void testCallsQtyResourcesQtyRamdon() throws InterruptedException {
		builder();
		dispatcher.finish(25);
		llamadasAtendidas = dispatcher.getLlamadasAtendidas();
		llamadasRecibidas = dispatcher.getLlamadasRecibidas();
		
		assertThat(llamadasAtendidas).isGreaterThanOrEqualTo(10);
		assertThat(llamadasRecibidas).isGreaterThanOrEqualTo(10);
		assertThat(llamadasAtendidas).isEqualTo(llamadasRecibidas);
	}
	
	/**
	 * Generacion de 20 llamadas 
	 * con 10 recursos/telefonos
	 * Las llamadas esperan encoladas hasta que algun empleado se libere
	 * Todas son atendidas
	 */
	@Test
	public void test20Llamados() throws InterruptedException {
		builderWithCallsQty(20);
		dispatcher.finish(25);
		llamadasAtendidas = dispatcher.getLlamadasAtendidas();
		llamadasRecibidas = dispatcher.getLlamadasRecibidas();
		
		assertThat(llamadasAtendidas).isEqualTo(20);
		assertThat(llamadasRecibidas).isEqualTo(20);
		assertThat(llamadasAtendidas).isEqualTo(llamadasRecibidas);
	}
	
	/**
	 * Generacion de 5 llamadas y 5 recursos/telefonos.
	 * Generacion de 1 llamada adicional que queda fuera 
	 * del rango de tiempo de trabajo del call center
	 * por lo que nunca es atendida
	 */
	@Test
	public void testLlamadaSinAtender() throws InterruptedException {
		builder(5, 5);
		dispatcher.dispatchCall(new Call(40));
		dispatcher.finish(25);
		llamadasAtendidas = dispatcher.getLlamadasAtendidas();
		llamadasRecibidas = dispatcher.getLlamadasRecibidas();
		
		assertThat(llamadasAtendidas).isEqualTo(5);
		assertThat(llamadasRecibidas).isEqualTo(6);
		assertThat(llamadasAtendidas).isLessThan(llamadasRecibidas);
	}
	
	/**
	 * validacion de la prioridad en el orden de atencion de los empleados
	 */
	@Test
	public void testPriorizacionDeEmpleados() throws InterruptedException {
		final Empleado supervisor = GestorDeEmpleado.buildSupervisor("Marcelo Montano");
		final Empleado operador = GestorDeEmpleado.buildOperator("Franco Tarditi");
		final Empleado director = GestorDeEmpleado.buildDirector("Marianno Benitez");
		final Empleado operador2 = GestorDeEmpleado.buildOperator("Guido Rodriguez");
		
		final PriorityBlockingQueue<Empleado> lista = new PriorityBlockingQueue<Empleado>();
		lista.add(operador);
		lista.add(director);
		lista.add(supervisor);
		lista.add(operador2);
		
		assertThat(lista.take().getTipo()).isEqualTo(TipoEmpleado.OPERADOR);
		assertThat(lista.take().getTipo()).isEqualTo(TipoEmpleado.OPERADOR);
		assertThat(lista.take().getTipo()).isEqualTo(TipoEmpleado.SUPERVISOR);
		assertThat(lista.take().getTipo()).isEqualTo(TipoEmpleado.DIRECTOR);
	}

}
