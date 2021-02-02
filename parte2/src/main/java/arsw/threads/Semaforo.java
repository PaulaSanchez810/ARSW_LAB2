package arsw.threads;

public class Semaforo {

    private boolean red = false;

    public void pauseRace() {
        red = true;
    }

    public synchronized void continueRace() {
        red = false;
        notifyAll();
    }

    public boolean isRed() {
        return red;
    }
}
