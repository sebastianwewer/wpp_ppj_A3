package solution;

import _untouchable_.shipPart5.Ship_A;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ship extends Ship_A implements Runnable {

    private final int id;
    private Direction direction;
    private final int maximumNumberOfSmurfs;
    private int currentNumberOfSmurfs;
    private Landing[] landings;
    private int position;
    private int nextPosition;

    // Synchronisierung
    Lock shipMutex;
    Condition[] exitConditions;

    public Ship(int id, Direction direction, int maximumNumberOfSmurfs, Landing[] landings, int position) {
        this.id = id;
        this.direction = direction;
        this.maximumNumberOfSmurfs = maximumNumberOfSmurfs;
        this.currentNumberOfSmurfs = 0;
        this.landings = landings;
        this.position = position;
        setNextPosition();

        this.shipMutex = new ReentrantLock();
        this.exitConditions = new Condition[landings.length];

        for (int i = 0; i < landings.length; i++) {
            exitConditions[i] = shipMutex.newCondition();
        }
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
    public void terminate() {

    }

    @Override
    public void run() {
        while (running) {

        }

    }

    private void setNextPosition() {
        if (direction.equals(Direction.CLOCKWISE)) {
            this.nextPosition = (position + 1) % landings.length;
        } else {
            this.nextPosition = ((position - 1) + landings.length) % landings.length;
        }
    }

    public boolean isFull() {
        return currentNumberOfSmurfs == maximumNumberOfSmurfs;
    }

    public void enterShip() {
        currentNumberOfSmurfs++;
    }

    public void exitShip() {
        currentNumberOfSmurfs--;
    }


    // Getter and Setter

    public Direction getDirection() {
        return direction;
    }

    public int getNextPosition() {
        return nextPosition;
    }

    private void dockAtLanding(Landing landing) {
        landing.dock();
    }

    public Lock getShipMutex() {
        return shipMutex;
    }

    public Condition getExitCondition(int landing) {
        return exitConditions[landing];
    }


}
