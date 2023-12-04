package solution;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Landing {

    private final Set<Ship> docked;

    // Synchronisierung
    private final ReentrantLock landingMutex;
    private final Condition travelCCWCondition;
    private final Condition travelCWCondition;
    private final Condition travelANYCondition;
    private final Semaphore waitingQueue;

    public Landing(int capacity) {
//        this.docked = new ArrayList<>();
        this.docked = new HashSet<>();
        this.waitingQueue = new Semaphore(capacity, true);

        this.landingMutex = new ReentrantLock();
        this.travelCWCondition = landingMutex.newCondition();
        this.travelCCWCondition = landingMutex.newCondition();
        this.travelANYCondition = landingMutex.newCondition();
    }

    public void dock(Ship ship) {
        try {
//            System.err.print("Schiff " + ship.identify() + " versucht an Landing zu docken. Schiffe im Dock: ");
//            docked.forEach(ship1 -> System.err.print("Schiff " + ship1.identify() + " "));
//            System.err.println();
            waitingQueue.acquire();
//            System.err.println("Schiff " + ship.identify() + " hat an Landing gedockt. Tickets: " + waitingQueue.availablePermits());
            docked.add(ship);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void undock(Ship ship) {
        docked.remove(ship);
        waitingQueue.release();
//        System.err.println("Schiff " + ship.identify() + " hat Landing verlassen. Tickets: " + waitingQueue.availablePermits());
    }

    public ReentrantLock getLandingMutex() {
        return landingMutex;
    }

    public Optional<Ship> getShip(Direction direction) {
        // TODO Ist es Threadsafe auf isFull() zu prüfen, ohne das Schiff vorher zu locken?
        if(docked.isEmpty()) {
            return Optional.empty();
        }
        return switch (direction) {
//            case ANY -> docked.stream().filter((ship -> !ship.isFull())).findAny();
            case ANY -> docked.stream().findAny();
//            default -> docked.stream().filter((ship -> ship.getDirection().equals(direction) && !ship.isFull())).findAny();
            default -> docked.stream().filter((ship -> ship.getDirection().equals(direction))).findAny();
        };
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
