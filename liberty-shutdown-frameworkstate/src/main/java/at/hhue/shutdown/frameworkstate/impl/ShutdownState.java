package at.hhue.shutdown.frameworkstate.impl;

import at.hhue.shutdown.frameworkstate.ShutdownStateInterface;
import com.ibm.wsspi.kernel.service.utils.FrameworkState;
import java.util.logging.Logger;

public class ShutdownState implements ShutdownStateInterface {
    private static final Logger logger = Logger.getLogger(ShutdownState.class.getName());

    @Override
    public boolean isStopping() {
        // Safely invoke the Liberty internal SPI method
        logger.info("Calling FrameworkState.isStopping()");
        return FrameworkState.isStopping();
    }
}
