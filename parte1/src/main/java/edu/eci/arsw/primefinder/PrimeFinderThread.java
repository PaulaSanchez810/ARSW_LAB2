package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

/**
 * Authors: Laura Alejandra Bernal Hernandez, Paula Andrea Guevara Sánchez, Daniel Felipe Rincón Muñoz
 */
public class PrimeFinderThread extends Thread{

	private int a,b;
	private boolean waiting = false;
	private boolean continueThread = false;
	private final long timeForPause = 2500;
	
	private final List<Integer> primes=new LinkedList<Integer>();

	/**
	 * Clase que encuentra los números primos en un rango
	 * @param a Límite inferior del rango
	 * @param b Límite superior del rango
	 */
	public PrimeFinderThread(int a, int b) {
		super();
		this.a = a;
		this.b = b;
	}

	@Override
	public void run(){
		long startingTime = System.currentTimeMillis();

		for (int i=a;i<=b;i++){						
			if (isPrime(i)){
				primes.add(i);
				System.out.println(i);
			}

			synchronized (this) {
				if (System.currentTimeMillis() - startingTime >= timeForPause && !continueThread) {
					try {
						waiting = true;
						wait();
						continueThread = true;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Verifica si el número n es primo
	 * @param n El número a verificar
	 * @return Si n es primo
	 */
	private boolean isPrime(int n) {
	    if (n%2==0) return false;
	    for(int i=3;i*i<=n;i+=2) {
	        if(n%i==0)
	            return false;
	    }
	    return true;
	}

	/**
	 * @return La lista de números primos encontrados
	 */
	public List<Integer> getPrimes() {
		return primes;
	}

	/**
	 * @return Si el hilo se encuentra en espera
	 */
	public boolean isWaiting() {
		return waiting;
	}
	
}
