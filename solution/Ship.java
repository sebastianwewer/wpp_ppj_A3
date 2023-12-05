package solution;

import _untouchable_.shipPart5.Ship_A;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ship extends Ship_A implements Runnable {

    private final int id;
    private Direction direction;
    private final int maximumNumberOfSmurfs;

    private final List<Landing> landings;
    private Landing currentLanding;
    private int position;
    private int nextPosition;
    private boolean running;

    // Synchronisierung
    private final Lock shipMutex;
    private final List<Condition> exitConditions;
    private final Semaphore seats;

    public Ship(int id, Direction direction, int maximumNumberOfSmurfs, List<Landing> landings, int position) {
        this.id = id;
        this.direction = direction;
        this.maximumNumberOfSmurfs = maximumNumberOfSmurfs;
        this.seats = new Semaphore(maximumNumberOfSmurfs);
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
            currentLanding = landings.get(position);
            setNextPosition();


            currentLanding.dock(this);
            dockAt(position);
            signalPassengers();

            try {
                currentLanding.getLandingMutex().lock();
                currentLanding.signalWaitingSmurfs(direction);
            } finally {
                currentLanding.getLandingMutex().unlock();
            }

            try {
                takeTimeForBoardingAt(position);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            try {
                currentLanding.getLandingMutex().lock();
                currentLanding.undock(this);
                castOff(position);
            } finally {
                currentLanding.getLandingMutex().unlock();
            }

            try {
                takeTimeForSailingTo(nextPosition);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            position = nextPosition;
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

    public Semaphore getSeats() {
        return seats;
    }
}
