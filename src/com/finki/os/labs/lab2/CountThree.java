package com.finki.os.labs.lab2;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by besniksh on 3/12/17.
 */

public class CountThree {

    public static int NUM_RUNS = 100;
    /**
     * Promenlivata koja treba da go sodrzi brojot na pojavuvanja na elementot 3
     */
    int count = 0;
    Lock lock = new ReentrantLock();
    Semaphore s1 = new Semaphore(1);
    /**
     * TODO: definirajte gi potrebnite elementi za sinhronizacija
     */


    public void init(){
    }

    class Counter extends Thread {

        public void count(int[] data) throws InterruptedException {
            // da se implementira

            int brojac=0;
            for (int broj : data){
                if(broj==3){
                    brojac++;
                }
            }

            s1.acquire();
            count+=brojac;
            s1.release();

        }
        private int[] data;

        public Counter(int[] data) {
            this.data = data;
        }

        @Override
        public void run() {
            try {
                count(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            CountThree environment = new CountThree();
            environment.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void start() throws Exception {

        init();

        HashSet<Thread> threads = new HashSet<Thread>();
        Scanner s = new Scanner(System.in);
        int total=s.nextInt();

        Random r = new Random();


        for (int i = 1; i <= NUM_RUNS; i++) {
            int[] data = new int[total];
            for (int j = 0; j < total; j++) {
                //data[j] = r.nextInt(4);
               data[j] = 3;
                System.out.print(data[j]);

            }
            System.out.print(" " +i);
            System.out.println();
            Counter c = new Counter(data);
            threads.add(c);
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
        System.out.println(count);


    }
}

