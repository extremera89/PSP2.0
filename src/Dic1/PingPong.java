package Dic1;

import java.util.Random;

public class PingPong {
    public static void main(String[] args) {
        SimulaJugada jugada = new SimulaJugada();
        Jugador santi = new Jugador(0,"Santi",jugada);
        Jugador gabi = new Jugador(0,"Gabi",jugada);

        santi.start();
        gabi.start();


        try {
            santi.join();
            gabi.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }


        if(jugada.puntuaciongabi == 15){
            System.out.println("Acaba de ganar gabi con 15 de puntuacion");
        }
        else{
            System.out.println("Acaba de ganar santi con 15 de puntuacion");
        }

    }


}


class Jugador extends Thread{
    private int numgolpe;
    private String nombre;
    private final SimulaJugada jugada;


    public Jugador(int numgolpe, String nombre, SimulaJugada jugada){
        this.numgolpe = numgolpe;
        this.nombre = nombre;
        this.jugada = jugada;
    }


    @Override
    public void run() {

        while(true) {
            if (this.nombre.equals("Santi")) {
                synchronized (jugada) {
                    if (!jugada.finPartida(this)) {
                        simulaQuienGanaElPunto();
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        jugada.notify();
                        return;
                    }

                    jugada.notify();
                    try {
                        jugada.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            else {
                synchronized (jugada) {
                    if (!jugada.finPartida(this)) {
                        simulaQuienGanaElPunto();
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        jugada.notify();
                        return;
                    }

                    jugada.notify();
                    try {
                        jugada.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


    }

    synchronized public void simulaQuienGanaElPunto(){
        numgolpe = new Random().nextInt(0,11);

        if( numgolpe % 2 == 0){
            jugada.puntuacionsanti++;
            System.out.println("# SANTI Acaba de puntuar");
        }else{
            System.out.println("+ GABI Acaba de puntuar");
        }


        jugada.turno++;
        System.out.println("///Turno "+jugada.turno);

    }
}

class SimulaJugada{
    int turno;
    int puntuacionsanti;
    int puntuaciongabi;


    public SimulaJugada(){
        this.turno = 0;
        this.puntuacionsanti = 0;
        this.puntuaciongabi = 0;
    }



    public boolean finPartida(Jugador j){
        if(puntuaciongabi == 15 || puntuacionsanti == 15){
            return true;
        }else{
            return false;
        }
    }

}