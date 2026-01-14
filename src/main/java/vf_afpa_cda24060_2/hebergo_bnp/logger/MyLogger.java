package vf_afpa_cda24060_2.hebergo_bnp.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyLogger {

    //--------------------- STATIC VARIABLES -----------------------------------
    // création du logger via le loggerFactory de SLFJ
    private static final Logger LOGGER = LoggerFactory.getLogger(MyLogger.class.getName());

    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    public static Logger getLOGGER() {

        return LOGGER;

    }

    //--------------------- CONSTRUCTORS ---------------------------------------
    private MyLogger (){}

}