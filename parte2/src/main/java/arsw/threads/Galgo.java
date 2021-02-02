package arsw.threads;

/**
 * Un galgo que puede correr en un carril
 *
 * @author rlopez
 */
public class Galgo extends Thread {
    private int paso;
    private Carril carril;
    private final Semaforo semaforo;
    final RegistroLlegada regl;

    public Galgo(Carril carril, String name, RegistroLlegada reg, Semaforo semaforo) {
        super(name);
        this.carril = carril;
        paso = 0;
        this.regl = reg;
        this.semaforo = semaforo;
    }

    public void corra() throws InterruptedException {
        while (paso < carril.size()) {
            Thread.sleep(100);
            carril.setPasoOn(paso++);
            carril.displayPasos(paso);
            checkPause();

            if (paso == carril.size()) {
                carril.finish();
                int ubicacion;
                synchronized (regl) {
                    ubicacion = regl.getUltimaPosicionAlcanzada();
                    regl.setUltimaPosicionAlcanzada(ubicacion + 1);
                }
                System.out.println("El galgo " + this.getName() + " llegó en la posición " + ubicacion);
                if (ubicacion == 1) {
                    regl.setGanador(this.getName());
                }
            }
        }
    }

    public void checkPause() throws InterruptedException {
        synchronized (semaforo) {
            while (semaforo.isRed()) {
                semaforo.wait();
            }
        }
    }

    @Override
    public void run() {

        try {
            corra();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
