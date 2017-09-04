package mk.ukim.finki.os.synchronization.exam14.march.ca3n2;

import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import mk.ukim.finki.os.synchronization.ProblemExecution;
import mk.ukim.finki.os.synchronization.TemplateThread;

public class CalciumNitride {

//TODO : Ca3 N2
	public static Semaphore ca = new Semaphore(3);
	public static Semaphore n = new Semaphore(2);

	public static Semaphore ready = new Semaphore(0);
	public static Semaphore lock  = new Semaphore(1);

	public static Semaphore done = new Semaphore(0);

	public static int caCount = 0;
	public static void init() {


	}

	public static class Calcium extends TemplateThread {

		public Calcium(int numRuns) {
			super(numRuns);
		}

		@Override
		public void execute() throws InterruptedException {
			ca.acquire();
			lock.acquire();
			caCount++;
			if(caCount==3){
				lock.release();
				ready.release(4);
				state.bond();

				done.acquire(4);
				state.validate();

				lock.acquire();
				caCount=0;
				ca.release(3);
				n.release(2);
				lock.release();

			}
			else{
				lock.release();
				ready.acquire();
				state.bond();
				done.release();
			}
		}
	}
	public static class Nitrogen extends TemplateThread {

		public Nitrogen(int numRuns) {
			super(numRuns);
		}

		@Override
		public void execute() throws InterruptedException {
			n.acquire();
			ready.acquire();
			state.bond();
			done.release();
		}


	}

	static CalciumNitrideState state = new CalciumNitrideState();

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			run();
		}
	}

	public static void run() {
		try {
			Scanner s = new Scanner(System.in);
			int numRuns = 1;
			int numIterations = 100;
			s.close();

			HashSet<Thread> threads = new HashSet<Thread>();

			for (int i = 0; i < numIterations; i++) {
				Nitrogen n = new Nitrogen(numRuns);
				threads.add(n);
				Calcium ca = new Calcium(numRuns);
				threads.add(ca);
				ca = new Calcium(numRuns);
				threads.add(ca);
				n = new Nitrogen(numRuns);
				threads.add(n);
				ca = new Calcium(numRuns);
				threads.add(ca);
			}

			init();

			ProblemExecution.start(threads, state);
			System.out.println(new Date().getTime());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
