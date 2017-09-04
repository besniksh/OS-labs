package mk.ukim.finki.os.synchronization.exam14.march.aloh3;

import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.Semaphore;

import mk.ukim.finki.os.synchronization.ProblemExecution;
import mk.ukim.finki.os.synchronization.TemplateThread;

public class AluminiumHydroxide {
	//TODO : AL (OH)3
	public static Semaphore al = new Semaphore(1);
	public static Semaphore o = new Semaphore(3);
	public static Semaphore h = new Semaphore(3);

	public static Semaphore ohHere = new Semaphore(0);
	public static Semaphore hHere = new Semaphore(0);

	public static Semaphore ohReady = new Semaphore(0);

	public static Semaphore ready = new Semaphore(0);
	public static Semaphore done = new Semaphore(0);
	public static void init() {


	}

	public static class Hydrogen extends TemplateThread {

		public Hydrogen(int numRuns) {
			super(numRuns);
		}

		@Override
		public void execute() throws InterruptedException {
			h.acquire();
			hHere.release();
			ohReady.acquire();
			state.bondOH();
			ohHere.release();
			ready.acquire();
			state.bondAlOH3();
			done.release();
		}

	}

	public static class Oxygen extends TemplateThread {

		public Oxygen(int numRuns) {
			super(numRuns);
		}

		@Override
		public void execute() throws InterruptedException {
			o.acquire();
			hHere.acquire();
			ohReady.release();
			state.bondOH();
			ohHere.release();
			ready.acquire();
			state.bondAlOH3();
			done.release();

		}

	}

	public static class Aluminium extends TemplateThread {

		public Aluminium(int numRuns) {
			super(numRuns);
		}

		@Override
		public void execute() throws InterruptedException {
			al.acquire();
			ohHere.acquire(6);

			ready.release(6);
			state.bondAlOH3();

			done.acquire(6);
			state.validate();

			o.release(3);
			h.release(3);
			al.release();
		}

	}

	static AluminiumHydroxideState state = new AluminiumHydroxideState();

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			run();
		}
	}

	public static void run() {
		try {
			int numRuns = 1;
			int numScenarios = 300;

			HashSet<Thread> threads = new HashSet<Thread>();

			for (int i = 0; i < numScenarios; i++) {
				Oxygen o = new Oxygen(numRuns);
				Hydrogen h = new Hydrogen(numRuns);
				threads.add(o);
				if (i % 3 == 0) {
					Aluminium al = new Aluminium(numRuns);
					threads.add(al);
				}
				threads.add(h);
			}

			init();

			ProblemExecution.start(threads, state);
			System.out.println(new Date().getTime());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
