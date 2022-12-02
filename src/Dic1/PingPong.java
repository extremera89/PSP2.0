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


        System.out.println("Se acabó la partida con las siguientes puntuaciones: => Santi: "+jugada.puntuacionsanti+" /// Gabi: "+jugada.puntuaciongabi );

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


    synchronized public String getNombre() {
        return nombre;
    }


    @Override
    public void run() {

        while(!jugada.finPartida()) {

            if (this.nombre.equals("Santi")) {
                synchronized (jugada) {

                    while(!jugada.saberTurno(this)) {
                        try {
                            jugada.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    if(jugada.finPartida()) {
                        return;
                    }
                    System.out.println("La jugada la ejecuta => " +this.getNombre());
                    simulaQuienGanaElPunto();
                    jugada.notify();


                }

            }


            else {
                synchronized (jugada) {

                    while(!jugada.saberTurno(this)) {
                        try {
                            jugada.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    if(jugada.finPartida()) {
                        return;
                    }
                    System.out.println("La jugada la ejecuta => " +this.getNombre());
                    simulaQuienGanaElPunto();
                    jugada.notify();


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
            jugada.puntuaciongabi++;
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



    public boolean finPartida(){
        if(puntuaciongabi == 15 || puntuacionsanti == 15){
            return true;
        }else{
            return false;
        }
    }

    public boolean saberTurno(Jugador j){
        if(j.getNombre().equals("Santi")){
            return turno%2==0;
        }
        else{
            return turno%2==1;
        }
    }

}