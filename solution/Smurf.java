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
                    optionalShip = currentLanding.getShip(direction);
                    while (!tryEnterShip(optionalShip)) {
                        waitForShip(direction);
                        optionalShip = currentLanding.getShip(direction);
                    }
                    currentShip = optionalShip.get();
                    enter(currentShip);
                } finally {
                    currentLanding.getLandingMutex().unlock();
                }
                beThere(currentShip);

                waitForArrival();

                currentLanding = landings.get(nextPosition);
                position = nextPosition;

                currentShip.getSeats().release();
                // TODO Schäfers-Methode aufrufen bevor wir selbst das Schiff verlassen haben?
                leave(currentShip);

                takeTimeForDoingStuffAtCurrentPosition(position, ssi);
            }
            lastDeed();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void waitForArrival() {
        try {
            currentShip.getShipMutex().lock();
            currentShip.getExitCondition(nextPosition).await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            currentShip.getShipMutex().unlock();
        }
    }

    private void waitForShip(Direction direction) {
        try {
            currentLanding.getTravelCondition(direction).await();
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

            return ship.getSeats().tryAcquire();

        }

        return false;
    }
}
