package com.callcenter;

import com.callcenter.dispatcher.Dispatcher;
import com.callcenter.manager.CallManager;
import com.callcenter.manager.GestorDeEmpleado;
import java.util.logging.Logger;

/**
 * @author mmontano
 */
public class CallCenter 
{
	private final static Logger LOGGER = Logger.getLogger(Dispatcher.class.getName());

    public static void main( String[] args ) throws InterruptedException
    {
		LOGGER.info("Start Call Center");

        final Dispatcher dispatch = new Dispatcher(GestorDeEmpleado.crearEmpleados());

        dispatch.dispatchCalls(CallManager.callsQtyRandom());
        dispatch.finish(15);
    }
}
