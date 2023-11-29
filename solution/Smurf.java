package solution;

import _untouchable_.shipPart5.SSI;
import _untouchable_.shipPart5.Smurf_A;

public class Smurf extends Smurf_A implements Runnable {

    private final int id;
    private int position;
    private int nextPosition;

    public Smurf(int id){
        this.id = id;
    }

    @Override
    public int identify() {
        return id;
    }

    @Override
    public boolean getDebugState() {
        return false;
    }

    @Override
    public void run(){
        SSI ssi = schedule.next();
        position = ssi.getPlanedPosition();


// …
        while ( schedule.hasNext() ){ // Attribut schedule wurde von Smurf_A geerbt
// …
            ssi = schedule.next(); // ssi ist vom importierten Typ SSI
            this.nextPosition = ssi.getPlanedPosition();
//            Falls nicht bereits "dort", begib Dich (per Schiff) so schnell wie möglich "dort" hin.
//                    Achtung: Ganz am Anfang befindet sich der Schlumpf bereits an der jeweils geforderten Position
//            und am Ende bleibt der Schlumpf am Ort des letzten Jobs. (Niemand wartet "zu Hause" auf den Schlumpf :‘-(.
// …
//            takeTimeForDoingStuffAtCurrentPosition( aktuelle-Position, ssi ); // Metapher für: Mach "dort" Deinen Job
// …
        }
// …
        lastDeed();
    }

    private boolean tryEnterShip(Ship ship) {
        if (ship != null && !ship.isFull()) {
            enter(ship);
            ship.enterShip();
            return true;
        }
        return false;
    }
}
