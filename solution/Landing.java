package solution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Landing {
    private static final int CAPACITY = 2;

    private List<Ship> docked;
    private final Semaphore waitingQueue;

    public Landing() {
        this.docked = new ArrayList<>();
        this.waitingQueue = new Semaphore(2, true);
    }

    public void dock() {
        try {
            waitingQueue.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void undock(Ship ship) {
        waitingQueue.release();
    }


}
