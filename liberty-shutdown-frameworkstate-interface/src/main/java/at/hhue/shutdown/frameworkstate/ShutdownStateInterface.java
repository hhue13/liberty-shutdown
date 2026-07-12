package at.hhue.shutdown.frameworkstate;

public interface ShutdownStateInterface {

    /**
     * Checks if the framework state is stopping.
     * 
     * @return true if stopping, false otherwise
     */
    boolean isStopping();
}
