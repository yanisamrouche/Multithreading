import java.math.BigInteger;
import java.util.*;

public class PremiersNombresPremiersAvecThreads extends Thread {

    static int nbThreads = 4;
    long part;
    static final long borne = 10_000_000 ;
    static volatile List<Object> premiersNombresPremiers = Collections.synchronizedList(new ArrayList<>()) ;

    public PremiersNombresPremiersAvecThreads(long part){
        this.part = part;
    }

    public static void main (String args[]) throws InterruptedException
    {

        final long début = System.nanoTime() ;
        PremiersNombresPremiersAvecThreads[] T = new PremiersNombresPremiersAvecThreads[nbThreads];
        for(int i=0; i<nbThreads; i++){
            T[i] = new PremiersNombresPremiersAvecThreads(borne/nbThreads);
            T[i].start();
        }
        for(int i=0; i<nbThreads; i++){
            T[i].join();
        }

        final long fin = System.nanoTime();
        final long durée = (fin - début) / 1_000_000 ;
        System.out.print("Nombre de nombres premiers inférieurs à " + borne + " : ") ;
        System.out.println(premiersNombresPremiers.size());
        System.out.format("Durée du calcul: %.3f s.%n", (double) durée/1000);

    }
    public synchronized void run()
    {
        try
        {
            long i;
            long x = Long.parseLong(Thread.currentThread().getName().substring(7));
            System.out.println(Thread.currentThread().getName());
            for (i = x*part; i <= (x+1)*part; i++) {
                if (BigInteger.valueOf(i).isProbablePrime(50)) {premiersNombresPremiers.add(i);
                }
            }

       }
        catch (Exception e)
        {
            // Throwing an exception
            System.out.println("Exception is caught");
        }
    }
}
