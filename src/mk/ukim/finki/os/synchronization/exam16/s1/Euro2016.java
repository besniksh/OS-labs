package mk.ukim.finki.os.synchronization.exam16.s1;

import mk.ukim.finki.os.synchronization.ProblemExecution;
import mk.ukim.finki.os.synchronization.TemplateThread;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.Semaphore;


public class Euro2016 {

    static Euro2016State state = new Euro2016State();

    public static Semaphore checkForTaxi = new Semaphore(1);
    public static Semaphore aWaitting = new Semaphore(0);
    public static Semaphore bWaitting = new Semaphore(0);
    public static Semaphore allowed = new Semaphore(0);
    public static int nrFanA , nrFanB = 0;

    public static void init() {
    }

    static class FanA extends TemplateThread {
        public FanA(int numRuns) {
            super(numRuns);
        }

        public void execute() throws InterruptedException {
            boolean imCaptain = false;
            checkForTaxi.acquire();
            nrFanA++;
            if(nrFanA==4){
                nrFanA = 0;
                imCaptain = true;
                aWaitting.release(4);
            }else if(nrFanA == 2 && nrFanB>=2){
                nrFanA = 0;
                nrFanB-=2;
                aWaitting.release(2);
                bWaitting.release(2);
                imCaptain = true;
            }else{
                checkForTaxi.release();
            }

            aWaitting.acquire();
            state.board();

            if(!imCaptain){
                allowed.release();
            }

            if(imCaptain){
                allowed.acquire(3);
                state.departure();
                checkForTaxi.release();
            }
        }
    }

    static class FanB extends TemplateThread {

        public FanB(int numRuns) {
            super(numRuns);
        }

        public void execute() throws InterruptedException {
            boolean imCaptain = false;
            checkForTaxi.acquire();
            nrFanB++;
            if(nrFanB == 4){
                nrFanB = 0;
                imCaptain = true;
                bWaitting.release(4);
            }else if(nrFanB == 2 && nrFanA>=2){
                nrFanB = 0;
                nrFanA-=2;
                bWaitting.release(2);
                aWaitting.release(2);
                imCaptain=true;
            }else{
                checkForTaxi.release();
            }

            bWaitting.acquire();
            state.board();

            if(!imCaptain){
                allowed.release();
            }

            if(imCaptain){
                allowed.acquire(3);
                state.departure();
                checkForTaxi.release();
            }

        }
    }



    public static void main(String[] args) {
        for (int i = 0; i < 15; i++)
            run();
    }

    public static void run() {
        try {
            int numRuns = 1;
            int numIterations = 120;

            HashSet<Thread> threads = new HashSet<Thread>();

            for (int i = 0; i < numIterations; i++) {
                FanA h = new FanA(numRuns);
                FanB s = new FanB(numRuns);
                threads.add(h);
                threads.add(s);
            }

            init();

            ProblemExecution.start(threads, state);
            System.out.println(new Date().getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}