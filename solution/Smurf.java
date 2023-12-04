package solution;

import _untouchable_.shipPart5.SSI;
import _untouchable_.shipPart5.Smurf_A;

import java.util.List;
import java.util.Optional;

public class Smurf extends Smurf_A implements Runnable {

    private final int id;
    private final List<Landing> landings;
    private Landing currentLanding;
    private int position;
    private int nextPosition;
    private Ship currentShip;
    private Direction direction;

    public Smurf(int id, List<Landing> landings) {
        this.id = id;
        this.landings = landings;
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
    public void run() {
        try {
            Optional<Ship> optionalShip;

            SSI ssi = schedule.next();
            position = ssi.getPlanedPosition();

            takeTimeForDoingStuffAtCurrentPosition(position, ssi);
            while (schedule.hasNext()) {
                currentLanding = landings.get(position);
                ssi = schedule.next();
                nextPosition = ssi.getPlanedPosition();
                direction = desiredDirection();

                try {
                    currentLanding.getLandingMutex().lock();
                    //System.err.println("Smurf " + identify() + " hat Landing " + position + " gelocked! Enter Ship");
                    optionalShip = currentLanding.getShip(direction);
                    while (!tryEnterShip(optionalShip)) {
                        waitForShip(direction);
                        optionalShip = currentLanding.getShip(direction);
                    }
                } finally {
                    currentLanding.getLandingMutex().unlock();
                    //System.err.println("Smurf " + identify() + " hat Landing " + position + " unlocked! Enter Ship");
                }
                beThere(currentShip);

                waitForArrival();

                currentLanding = landings.get(nextPosition);
                position = nextPosition;

                // TODO Schäfers-Methode aufrufen bevor wir selbst das Schiff verlassen haben?
                leave(currentShip);

                try {
                    currentLanding.getLandingMutex().lock();
                    //System.err.println("Smurf " + identify() + " hat Landing " + nextPosition + " gelocked! Exit Ship");
                    currentShip.exitShip();
//                    currentLanding.signalWaitingSmurfs(currentShip.getDirection());
                } finally {
                    currentLanding.getLandingMutex().unlock();
                    //System.err.println("Smurf " + identify() + " hat Landing " + nextPosition + " gelocked! Exited Ship");
                }

                takeTimeForDoingStuffAtCurrentPosition(position, ssi);
            }
//            System.err.println("Smurf " + identify() + " ist fertig");
            lastDeed();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void waitForArrival() {

        try {
            currentShip.shipMutex.lock();
//           System.err.println("Smurf " + identify() + " hat Schiff " + currentShip.identify() + " gelocked. Wait for arrival");
//            System.err.println("Smurf " + identify() + " wartet auf Exit Condition von Schiff " + currentShip.identify());
            currentShip.getExitCondition(nextPosition).await();
//            System.err.println("Smurf " + identify() + " hat Signal von Exit Condition von " + currentShip.identify() + " bekommen.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            currentShip.shipMutex.unlock();
//            System.err.println("Smurf " + identify() + " hat Schiff " + currentShip.identify() + " unlocked. After Wait for arrival");
        }
    }

    private void waitForShip(Direction direction) {
        try {
            //System.err.println("Smurf " + identify() + " hat Travel Condition von Landing " + position + " bekommen.");
            currentLanding.getTravelCondition(direction).await();
            //System.err.println("Smurf " + identify() + " hat Travel Condition Signal von Landing " + position + " bekommen.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Direction desiredDirection() {
        int clockwiseDistance = (nextPosition - position + 6) % 6;
        int counterclockwiseDistance = (position - nextPosition + 6) % 6;

        if (clockwiseDistance == 3 || counterclockwiseDistance == 3) {
            return Direction.ANY;
        } else if (clockwiseDistance < counterclockwiseDistance) {
            return Direction.CLOCKWISE;
        } else {
            return Direction.COUNTERCLOCKWISE;
        }
    }

    private boolean tryEnterShip(Optional<Ship> optionalShip) {
        if (optionalShip.isPresent()) {
            Ship ship = optionalShip.get();
            try {
                ship.shipMutex.lock();
                if (!ship.isFull()) {
                    enter(ship);
                    ship.enterShip();
                    currentShip = ship;
                    return true;
                }
            } finally {
                ship.shipMutex.unlock();
            }
        }

        return false;
    }
}
