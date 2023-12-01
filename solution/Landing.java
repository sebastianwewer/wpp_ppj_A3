package solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Landing {

    private List<Ship> docked;

    // Synchronisierung
    private final ReentrantLock landingMutex;
    private Condition travelCCWCondition;
    private Condition travelCWCondition;
    private Condition travelANYCondition;
    private final Semaphore waitingQueue;

    public Landing(int capacity) {
        this.docked = new ArrayList<>();
        this.waitingQueue = new Semaphore(capacity, true);

        this.landingMutex = new ReentrantLock();
        this.travelCWCondition = landingMutex.newCondition();
        this.travelCCWCondition = landingMutex.newCondition();
        this.travelANYCondition = landingMutex.newCondition();
    }

    public void dock(Ship ship) {
        try {
            waitingQueue.acquire();
            docked.add(ship);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void undock(Ship ship) {
        docked.remove(ship);
        waitingQueue.release();
    }

    public ReentrantLock getLandingMutex() {
        return landingMutex;
    }

    public Optional<Ship> getShip(Direction direction) {
        // TODO Ist es Threadsafe auf isFull() zu prüfen, ohne das Schiff vorher zu locken?
        return docked.stream().filter((ship -> ship.getDirection().equals(direction) && !ship.isFull())).findAny();
    }

    public Condition getTravelCondition(Direction direction) {
        return switch (direction) {
            case CLOCKWISE -> travelCWCondition;
            case COUNTERCLOCKWISE -> travelCCWCondition;
            case ANY -> travelANYCondition;
        };
    }


    public void signalWaitingSmurfs(Direction direction) {
        if (direction.equals(Direction.ANY)) {
            throw new IllegalArgumentException("'direction' must be CLOCKWISE or COUNTERCLOCKWISE!");
        }

        switch (direction) {
            case CLOCKWISE -> travelCWCondition.signalAll();
            case COUNTERCLOCKWISE -> travelCCWCondition.signalAll();
        }
        travelANYCondition.signalAll();
    }
}
