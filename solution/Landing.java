package solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Landing {

    private final List<Ship> dockedAny;
    private final List<Ship> dockedCW;
    private final List<Ship> dockedCCW;
    private final int capacity;
    private int currentlyDocked;

    private final ReentrantLock landingMutex;
    private final Condition travelCCWCondition;
    private final Condition travelCWCondition;
    private final Condition travelANYCondition;
    private final Condition fullCondition;

    public Landing(int capacity) {

        this.dockedAny = new ArrayList<>();
        this.dockedCW = new ArrayList<>();
        this.dockedCCW = new ArrayList<>();

        this.landingMutex = new ReentrantLock();
        this.travelCWCondition = landingMutex.newCondition();
        this.travelCCWCondition = landingMutex.newCondition();
        this.travelANYCondition = landingMutex.newCondition();
        this.fullCondition = landingMutex.newCondition();
        this.capacity = capacity;
        this.currentlyDocked = 0;
    }

    public Condition getLandingFullCondition() {
        return fullCondition;
    }

    public boolean dock(Ship ship) {
        if (currentlyDocked >= capacity) {
            return false;
        }

        Direction direction = ship.getDirection();
        dockedAny.add(ship);
        if (direction.equals(Direction.CLOCKWISE)) {
            dockedCW.add(ship);
        } else {
            dockedCCW.add(ship);
        }
        currentlyDocked++;
        return true;
    }

    public void undock(Ship ship) {
        Direction direction = ship.getDirection();
        dockedAny.remove(ship);
        if (direction.equals(Direction.CLOCKWISE)) {
            dockedCW.remove(ship);
        } else {
            dockedCCW.remove(ship);
        }
        currentlyDocked--;
    }

    public ReentrantLock getLandingMutex() {
        return landingMutex;
    }

    public List<Ship> getShips(Direction direction) {
        if (currentlyDocked == 0) {
            return new ArrayList<>();
        }
        return switch (direction) {
            case ANY -> dockedAny;
            default -> direction.equals(Direction.CLOCKWISE) ? dockedCW : dockedCCW;
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
