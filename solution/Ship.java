package solution;

import _untouchable_.shipPart5.Ship_A;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ship extends Ship_A implements Runnable {

    private final int id;
    private Direction direction;
    private final int maximumNumberOfSmurfs;
    private int currentNumberOfSmurfs;
    private final List<Landing> landings;
    private Landing currentLanding;
    private int position;
    private int nextPosition;
    private boolean running;

    // Synchronisierung
    Lock shipMutex;
    List<Condition> exitConditions;

    public Ship(int id, Direction direction, int maximumNumberOfSmurfs, List<Landing> landings, int position) {
        this.id = id;
        this.direction = direction;
        this.maximumNumberOfSmurfs = maximumNumberOfSmurfs;
        this.currentNumberOfSmurfs = 0;
        this.landings = landings;
        this.currentLanding = landings.get(position);
        this.position = position;

        setNextPosition();

        this.shipMutex = new ReentrantLock();
        this.exitConditions = new ArrayList<>();

        for (int i = 0; i < landings.size(); i++) {
            exitConditions.add(shipMutex.newCondition());
        }
        this.running = true;
    }

    @Override
    public int identify() {
        return id;
    }

    @Override
    public boolean getDebugState() {
        return TestFrame.DEBUG_STATE;
    }

    @Override
    public void terminate() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            setNextPosition();

//            try {
//                currentLanding.getLandingMutex().lock();
//                System.err.println("Schiff "+identify()+" hat Landing "+position+" gelocked! Docking");
//                currentLanding.dock(this);
//                dockAt(position);
//            } finally {
//                currentLanding.getLandingMutex().unlock();
//                System.err.println("Schiff "+identify()+" hat Landing "+position+" unlocked! Docked successfully");
//            }

            currentLanding.dock(this);
            dockAt(position);
            signalPassengers();

            try {
                currentLanding.getLandingMutex().lock();
                //System.err.println("Schiff " + identify() + " hat Landing " + position + " gelocked! Signal Waiting");
                currentLanding.signalWaitingSmurfs(direction);
            } finally {
                currentLanding.getLandingMutex().unlock();
                //System.err.println("Schiff " + identify() + " hat Landing " + position + " unlocked! Signal Waiting");
            }



            try {
                takeTimeForBoardingAt(position);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            try {
                currentLanding.getLandingMutex().lock();
                //System.err.println("Schiff " + identify() + " hat Landing " + position + " gelocked! Undocking");
                currentLanding.undock(this);
                castOff(position);
            } finally {
                currentLanding.getLandingMutex().unlock();
                //System.err.println("Schiff " + identify() + " hat Landing " + position + " unlocked! Undocking");
            }

            try {
                takeTimeForSailingTo(nextPosition);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            position = nextPosition;
            currentLanding = landings.get(position);
        }
        lastDeed();
    }

    private void signalPassengers() {
        shipMutex.lock();
        try {
            exitConditions.get(position).signalAll();
        } finally {
            shipMutex.unlock();
        }
    }

    private void setNextPosition() {
        if (direction.equals(Direction.CLOCKWISE)) {
            this.nextPosition = (position + 1) % landings.size();
        } else {
            this.nextPosition = ((position - 1) + landings.size()) % landings.size();
        }
    }

    public boolean isFull() {
        return currentNumberOfSmurfs == maximumNumberOfSmurfs;
    }

    public void enterShip() {
        currentNumberOfSmurfs++;
        //System.err.println("Smurf entered Ship " + identify() + ": " + currentNumberOfSmurfs + "/" + maximumNumberOfSmurfs);
    }

    public void exitShip() {
        currentNumberOfSmurfs--;
        //System.err.println("Smurf exited Ship " + identify() + ": " + currentNumberOfSmurfs + "/" + maximumNumberOfSmurfs);
    }


    // Getter and Setter

    public Direction getDirection() {
        return direction;
    }

    public int getNextPosition() {
        return nextPosition;
    }

    private void dockAtLanding(Landing landing) {
        landing.dock(this);
        currentLanding = landing;
    }

    private void undockAtLanding(Landing landing) {
        landing.dock(this);
    }

    public Lock getShipMutex() {
        return shipMutex;
    }

    public Condition getExitCondition(int landing) {
        return exitConditions.get(landing);
    }


}
