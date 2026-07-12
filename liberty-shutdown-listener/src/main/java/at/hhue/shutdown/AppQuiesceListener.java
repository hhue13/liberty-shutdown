package at.hhue.shutdown;

import com.ibm.wsspi.kernel.service.utils.ServerQuiesceListener;
import java.util.logging.Logger;

public class AppQuiesceListener implements ServerQuiesceListener {

    private static final Logger logger = Logger.getLogger(AppQuiesceListener.class.getName());
    public static final String PROP_STOPPING = "at.hhue.server.stopping";

    @Override
    public void serverStopping() {
        logger.info("Server is quiescing. Setting serverIsQuiecing flag to true and sleeping for 20 seconds.");
        // Called by Liberty on graceful stop (not --force).
        // This is the ONLY thing we do: write to the JVM-global System properties.
        System.setProperty(PROP_STOPPING, "true");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logger.info("Server quiescing delay completed.");
    }
}
