package at.hhue.shutdown;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ServiceLoader;
import java.util.logging.Logger;

@WebServlet(name = "HelloWorldServlet", urlPatterns = "/hello")
public class HelloWorldServlet extends HttpServlet {
    private static final String PROP_STOPPING = "at.hhue.server.stopping";
    private static final Logger logger = Logger.getLogger(HelloWorldServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LocalDateTime localDateTime;
        boolean frameworkIsStopping = false;
        int numLoops = 30;
        int i;

        // Check early — bail out if the server is shutting down.
        if ("true".equals(System.getProperty(PROP_STOPPING))) {
            resp.sendError(
                    HttpServletResponse.SC_SERVICE_UNAVAILABLE,
                    "Server is shutting down — test aborted.");
            return;
        }

        localDateTime = LocalDateTime.now();
        resp.setContentType("text/plain");
        resp.getWriter().write("Hello Servlet! " + localDateTime.toString() + "\n");

        for (i = 0; i < numLoops; i++) {
            localDateTime = LocalDateTime.now();
            logger.info("Getting state from Listener");
            resp.getWriter().write(
                    "Shutdown isStopping at " + localDateTime.toString() + ": "
                            + System.getProperty(PROP_STOPPING, "false")
                            + "\n");
            resp.getWriter().write("Loop Count: " + String.valueOf(i) + "\n");
            //
            // Exit if we are in the shutdown state
            // Re-check on every iteration so an in-flight request can also exit.

            /**
             * if ("true".equals(System.getProperty(PROP_STOPPING))) {
             * resp.getWriter().println("Stopped early at iteration " + i);
             * return;
             * }
             **/
            resp.getWriter().flush();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }
    }
}
