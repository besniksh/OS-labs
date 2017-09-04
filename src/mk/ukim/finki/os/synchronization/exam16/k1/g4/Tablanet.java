package mk.ukim.finki.os.synchronization.exam16.k1.g4;

import mk.ukim.finki.os.synchronization.ProblemExecution;
import mk.ukim.finki.os.synchronization.TemplateThread;

import java.util.HashSet;
import java.util.concurrent.Semaphore;

public class Tablanet {

    public static Semaphore seats = new Semaphore(4);
    public static Semaphore playersHere = new Semaphore(0);
    public static Semaphore letsPlay = new Semaphore(0);

    public static Semaphore groupDone = new Semaphore(0);

    public static Semaphore lock = new Semaphore(1);
    public static Semaphore newCycle = new Semaphore(0);
    public static int total = 0;

    static TablanetState state = new TablanetState();
    public static void init() {
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            run();
        }
    }

    public static void run() {
        try {
            int numCycles = 10;
            int numIterations = 20;

            HashSet<Thread> threads = new HashSet<Thread>();

            Dealer d = new Dealer(50);
            threads.add(d);
            for (int i = 0; i < numIterations; i++) {
                Player c = new Player(numCycles);
                threads.add(c);
            }

            init();

            ProblemExecution.start(threads, state);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static class Dealer extends TemplateThread {

        public Dealer(int numRuns) {
            super(numRuns);
        }

        @Override
        public void execute() throws InterruptedException {
            playersHere.acquire(4);
            state.dealCards();
            letsPlay.release(4);
            groupDone.acquire(4);
            state.nextGroup();
            seats.release(4);
        }
    }

    public static class Player extends TemplateThread {

        public Player(int numRuns) {
            super(numRuns);
        }


        @Override
        public void execute() throws InterruptedException {
            seats.acquire();
            state.playerSeat();
            playersHere.release();
            letsPlay.acquire();
            state.play();
            groupDone.release();

            lock.acquire();
            total++;
            if(total == 20){
                total = 0;
                state.endCycle();
                newCycle.release(20);
            }
            lock.release();

            newCycle.acquire();
        }

    }
}
