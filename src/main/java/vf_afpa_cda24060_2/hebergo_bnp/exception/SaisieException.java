package vf_afpa_cda24060_2.hebergo_bnp.exception;

public class SaisieException extends Exception {

    public SaisieException() {
        super("Erreur Saisie");
    }

    public SaisieException(String message) {
        super(message);
    }
}
