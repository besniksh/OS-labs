package mk.ukim.finki.os.synchronization.exam16.s3.t2;

import mk.ukim.finki.os.synchronization.ProblemExecution;
import mk.ukim.finki.os.synchronization.TemplateThread;

import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.Semaphore;

public class HypophosphoricAcid {

    public static Semaphore h = new Semaphore(4);
    public static Semaphore p = new Semaphore(2);
    public static Semaphore o = new Semaphore(6);

    public static Semaphore hHere = new Semaphore(0);
    public static Semaphore oHere = new Semaphore(0);

    public static Semaphore ready = new Semaphore(0);
    public static Semaphore done = new Semaphore(0);
    public static int pCount = 0;


    public static Semaphore lock = new Semaphore(1);
    static HypophosphoricAcidState state = new HypophosphoricAcidState();

    public static void init() {

    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            run();
        }
    }

    public static void run() {
        try {
            int numRuns = 1;
            int numScenarios = 100;

            HashSet<Thread> threads = new HashSet<Thread>();

            for (int i = 0; i < numScenarios; i++) {
                for (int j = 0; j < state.O_ATOMS; j++) {
                    Oxygen o = new Oxygen(numRuns);
                    threads.add(o);
                }
                for (int j = 0; j < state.H_ATOMS; j++) {
                    Hydrogen h = new Hydrogen(numRuns);
                    threads.add(h);
                }

                for (int j = 0; j < state.P_ATOMS; j++) {
                    Phosphorus p = new Phosphorus(numRuns);
                    threads.add(p);
                }

            }

            init();

            ProblemExecution.start(threads, state);
            System.out.println(new Date().getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static class Phosphorus extends TemplateThread {

        public Phosphorus(int numRuns) {
            super(numRuns);
        }

        @Override
        public void execute() throws InterruptedException {
            p.acquire();

            lock.acquire();
            pCount++;
            if (pCount == 2) {
                lock.release();
                hHere.acquire(6);
                oHere.acquire(4);

                ready.release(11);
                state.bond();

                done.acquire(11);
                state.validate();

                lock.acquire();
                pCount=0;
                h.release(4);
                p.release(2);
                o.release(6);
                lock.release();

            } else {
                lock.release();
                ready.acquire();
                state.bond();
                done.release();

            }


        }

    }

    public static class Hydrogen extends TemplateThread {

        public Hydrogen(int numRuns) {
            super(numRuns);
        }

        @Override
        public void execute() throws InterruptedException {
            h.acquire();
            hHere.release();
            ready.acquire();
            state.bond();
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
            oHere.release();
            ready.acquire();
            state.bond();
            done.release();
        }

    }

}