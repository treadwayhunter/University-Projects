package sort;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.XMLFormatter;

public class Logging {
    private static Logger logger = Logger.getLogger(Logging.class.getName());

    public Logging() {

        if(logger.getHandlers().length == 0) {
            try {
                
                FileHandler fileHandler = new FileHandler("sort_log.xml");
                XMLFormatter xmlFormatter = new XMLFormatter();
                fileHandler.setFormatter(xmlFormatter);
                fileHandler.setLevel(Level.INFO);

                ConsoleHandler consoleHandler = new ConsoleHandler();
                SimpleFormatter consoleFormatter = new SimpleFormatter();
                consoleHandler.setFormatter(consoleFormatter);
                consoleHandler.setLevel(Level.WARNING);

                logger.addHandler(fileHandler);
                logger.addHandler(consoleHandler);


                Logger rootLogger = Logger.getLogger("");
                rootLogger.removeHandler(rootLogger.getHandlers()[0]);
                
            }
            catch(IOException e) {
                warningLog("File 'mylog.xml' not created.");
            }
        }
    }

    public void infoLog(String message) {
        logger.info(message);
    }

    public void warningLog(String message) {
        logger.warning(message);
    }

    public void severeLog(String message) {
        logger.severe(message);
    }
}
