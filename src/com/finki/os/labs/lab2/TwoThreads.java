package com.finki.os.labs.lab2;

public class TwoThreads {
    public static class ThreadAB implements Runnable{
        String string1;
        String string2;

        public ThreadAB(String string1,String string2){
            this.string1=string1;
            this.string2=string2;
        }

        @Override
        public void run() {
            System.out.println(string1);
            System.out.println(string2);
        }
    }

    public static void main(String[] args) {
        ThreadAB th1 = new ThreadAB("A","B");
        ThreadAB th2 = new ThreadAB("1","2");

        Thread nitka1 = new Thread(th1);
        Thread nitka2 = new Thread(th2);

        nitka1.start();
        nitka2.start();
    }
}
