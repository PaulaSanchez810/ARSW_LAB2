package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread{

	private int a,b;
	private boolean waiting = false;
	private boolean continueThread = false;
	private final long timeForPause = 2500;
	
	private final List<Integer> primes=new LinkedList<Integer>();
	
	public PrimeFinderThread(int a, int b) {
		super();
		this.a = a;
		this.b = b;
	}

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
	
	private boolean isPrime(int n) {
	    if (n%2==0) return false;
	    for(int i=3;i*i<=n;i+=2) {
	        if(n%i==0)
	            return false;
	    }
	    return true;
	}

	public List<Integer> getPrimes() {
		return primes;
	}
	public boolean isWaiting() {
		return waiting;
	}
	
}
