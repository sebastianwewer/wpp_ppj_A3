package solution;

import _untouchable_.shipPart5.TestAndEnvironment_A;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TestFrame extends TestAndEnvironment_A {

    public static boolean DEBUG_STATE = false;
    public static int finishedSmurfs = 0;

    @Override
    public String getAuthor() {
        return null;
    }

    @Override
    public Integer getWantedNumberOfSmurfs() {
        return 10000;
    }

    @Override
    public Integer getWantedNumberOfShips() {
        return 6;
    }

    @Override
    public Integer getWantedNumberOfLandings() {
        return 6;
    }

    @Override
    public Integer getWantedMaximumNumberOfSmurfsPerShip() {
        return 157;
    }

    @Override
    public Integer getWantedMaximumNumberOfShipsPerLanding() {
        return 2;
    }

    @Override
    public void doTest(Integer requestedNumberOfSmurfs, Integer requestedNumberOfShips, Integer requestedNumberOfLandings, Integer requestedMaximumNumberOfSmurfsPerShip, Integer requestedMaximumNumberOfShipsPerLanding) {
        DEBUG_STATE = false;
        List<Thread> smurfs = new ArrayList<>();
        List<Thread> shipThreads = new ArrayList<>();
        List<Ship> ships = new ArrayList<>();
        List<Landing> landings = new ArrayList<>();
        CountDownLatch cdl = new CountDownLatch(getWantedNumberOfShips());

        //Landings
        for (int i = 0; i < getWantedNumberOfLandings(); i++) {
            Landing station = new Landing(getWantedMaximumNumberOfShipsPerLanding());
            landings.add(station);
        }

        //Ships
        for (int i = 0; i < getWantedNumberOfShips(); i++) {
            int position = i;
            Ship ship = new Ship(i, i % 2 == 0 ? Direction.CLOCKWISE : Direction.COUNTERCLOCKWISE, getWantedMaximumNumberOfSmurfsPerShip(), landings, position);
            ships.add(ship);
            Thread shipThread = new Thread(ship);
            shipThread.setName("Ship " + i);
            shipThreads.add(shipThread);
            shipThread.start();
        }

        //Smurfs
        for (int i = 0; i < requestedNumberOfSmurfs; i++) {
            Smurf smurf = new Smurf(i, landings);
            Thread smurfThread = new Thread(smurf);
            smurfThread.setName("Smurf " + i);

            smurfs.add(smurfThread);
            smurfThread.start();
            try {
                Smurf.waitUntilNextArrival();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Join smurfs
        for (int i = 0; i < requestedNumberOfSmurfs; i++) {
            try {
                smurfs.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Terminate ships
        for (Ship ship : ships) {
            ship.terminate();
        }

        //Join ships
        for (int i = 0; i < requestedNumberOfShips; i++) {
            try {
                shipThreads.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        TestFrame tfo = new TestFrame();
        tfo.letThereBeLife();
    }
}
