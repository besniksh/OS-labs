package mk.ukim.finki.os.synchronization.juni17;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;


public class Scheduler {
    public static Random random = new Random();
    static List<Process> scheduled = new ArrayList<>();
    public static Semaphore semafor=new Semaphore(0);

    public static void main(String[] args)
    {

        Process procesi;

        for(int i=0;i<100;i++)
        {
            try {
                procesi=new Process();
                register(procesi);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



        }

        Scheduler sh=new Scheduler();
        sh.run();


        // TODO: kreirajte 100 Process nitki i registrirajte gi

        // TODO: kreirajte Scheduler i startuvajte go negovoto pozadinsko izvrsuvanje

    }

    public static void register(Process process) {
        scheduled.add(process);
    }

    public Process next() {
        if (!scheduled.isEmpty()) {
            return scheduled.remove(0);
        }
        return null;
    }

    public void run() {
        try {
            while (!scheduled.isEmpty()) {
                Thread.sleep(100);
                System.out.print(".");
                this.next().execute();
                semafor.acquire();
                if(semafor.availablePermits()==1)
                {
                    System.out.println("Terminated processing");
                    semafor.acquire();
                }
                else if(semafor.availablePermits()==0)
                {
                    System.out.println("Finished processing");
                }




            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done scheduling!");
    }
}




class Process extends Thread {

    public Integer duration;

    public Process() throws InterruptedException {
        this.duration = Scheduler.random.nextInt(1000);
        Thread.sleep(this.duration);
    }

    public void execute() throws InterruptedException {



        System.out.println("Executing[" + this + "]: " + duration);

        this.start();
        if(this.duration>700)
        {
            this.interrupt();
            Scheduler.semafor.release(2);
        }else{

            Scheduler.semafor.release(1);

        }
        this.join();
        // TODO: startuvajte go pozadinskoto izvrsuvanje
    }
}