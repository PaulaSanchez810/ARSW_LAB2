package edu.eci.arsw.primefinder;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Authors: Laura Alejandra Bernal Hernandez, Paula Andrea Guevara Sánchez, Daniel Felipe Rincón Muñoz
 */
public class PrimeFinder {

    /**
     * Encuentra los números primos hasta 30.000.000, utilizando 3 threads, los cuales se detienen después de un tiempo
     * y se reanuda el programa presionando la tecla "Enter"
     * @throws InterruptedException Cuándo el thread es interrumpido mientras duerme
     */
    public void findPrimes() throws InterruptedException {
        int primesTo = 30000000;
        int threadNumber = 3;

        ArrayList<PrimeFinderThread> primeFinders = new ArrayList<>();
        Scanner scan = new Scanner(System.in);

        for (int i = 0; i < threadNumber; i++) {
            int lowerLimit = primesTo / threadNumber * i;
            int upperLimit = i == threadNumber - 1 ? primesTo : primesTo / threadNumber * (i + 1) - 1;
            primeFinders.add(new PrimeFinderThread(lowerLimit, upperLimit));
        }
        for (PrimeFinderThread pft : primeFinders) {
            pft.start();
        }

        boolean runningThreads = true;
        while (runningThreads) {
            Thread.sleep(100);
            boolean flag = true;
            for (PrimeFinderThread pft : primeFinders) {
                flag = flag && !pft.isWaiting();
            }
            runningThreads = flag;
        }

        int size = 0;
        for (PrimeFinderThread pft : primeFinders) {
            size += pft.getPrimes().size();
        }

        System.out.println("Se han encontrado " + String.valueOf(size) + " números primos.");
        System.out.println("\nPresione Enter para continuar la ejecución...");
        scan.nextLine();

        for (PrimeFinderThread pft : primeFinders) {
            synchronized (pft) {
                pft.notify();
            }
        }

        for (PrimeFinderThread pft : primeFinders) {
            pft.join();
        }
    }
}
