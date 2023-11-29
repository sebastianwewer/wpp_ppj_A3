package _untouchable_.common;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2013/05/20",
    currentRevision = "0.93",
    lastModified    = "2016/04/09",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
// the WHOLE CLASS and ALL of its ELEMENTS are friendly on purpose (!)
abstract class RawSmurf {
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    static AtomicInteger smurfCnt = new AtomicInteger( 0 );
    
    @ChunkPreamble ( lastModified="2012/07/20", lastModifiedBy="Michael Schäfers" ) 
    static final List<Event> arrivalEventList = new ArrayList<>();
    //
    @ChunkPreamble ( lastModified="2012/07/20", lastModifiedBy="Michael Schäfers" )
    static Event lastArrivalEvent = null;
    //
    @ChunkPreamble ( lastModified="2013/05/11", lastModifiedBy="Michael Schäfers" )
    static final Lock mtx = new ReentrantLock();
    
    @ChunkPreamble ( lastModified="2016/04/09", lastModifiedBy="Michael Schäfers" )
    static void waitUntilNextArrival() throws InterruptedException {
        mtx.lock();                                                             // SYNCHRONISATION ! => Sichtbarkeit - sp�ter muss(!) gepr�ft werden (anhand der Thread-ID), ob Routine vom Anstart-Thread aufgerufen wird.!
        Event currEvent = new Event(
            EventType.EWUA1,                                                    // et
            Tool.nanoElapsedTime(),                                             // time
            lastArrivalEvent,                                                   // lastEvent
            (int)Thread.currentThread().getId(),                                // current thread id
            //
            smurfCnt.get(),                                                     // smurfIdInternal
            null,                                                               // smurfIdUser
            null,                                                               // smurfPositionLocated
            null,                                                               // smurfPositionParameter
            null,                                                               // smurfRequestedPosition
            //
            null,                                                               // woeIdInternal
            null,                                                               // woeIdUser
            null,                                                               // woePositionLocated
            null                                                                // woePositionParameter
        );
        arrivalEventList.add( currEvent );
        lastArrivalEvent = currEvent;
        mtx.unlock();                                                           // SYNCHRONISATION ! S.o.
        //
        TimeUnit.MILLISECONDS.sleep((Global.getData().minSmurfArrivalTime+(long)((Global.getData().maxSmurfArrivalTime-Global.getData().minSmurfArrivalTime)*Math.random())*Global.getData().unit));
        //
        mtx.lock();                                                             // SYNCHRONISATION ! => Sichtbarkeit - sp�ter muss(!) gepr�ft werden (anhand der Thread-ID), ob Routine vom Anstart-Thread aufgerufen wird.!
        currEvent = new Event(
            EventType.EWUA2,                                                    // et
            Tool.nanoElapsedTime(),                                             // time
            lastArrivalEvent,                                                   // lastEvent
            (int)Thread.currentThread().getId(),                                // current thread id
            //
            smurfCnt.get(),                                                     // smurfIdInternal
            null,                                                               // smurfIdUser
            null,                                                               // smurfPositionLocated
            null,                                                               // smurfPositionParameter
            null,                                                               // smurfRequestedPosition
            //
            null,                                                               // woeIdInternal
            null,                                                               // woeIdUser
            null,                                                               // woePositionLocated
            null                                                                // woePositionParameter
        );
        arrivalEventList.add( currEvent );
        lastArrivalEvent = currEvent;
        mtx.unlock();                                                           // SYNCHRONISATION ! S.o.
    }//method()
    
    
    
    
    
    @ChunkPreamble ( lastModified="2016/04/09", lastModifiedBy="Michael Schäfers" )
    RawSmurf( final CommonScheduleFactory factory ){
        // check for crazy "stuff" or malpractice of environment
        if ( Global.getData() == null ){
            System.out.flush();
            System.err.printf( "Sie handeln nicht gemaess Aufgabenstellung - die interne Konfiguration ist unterblieben!\n" );
            System.err.printf( "\\--> Wurde letThereBeLife() aufgerufen?\n" );
            System.err.printf( "\\--> Sind alle Initialisierungen innerhalb von dotest(), die hinein gehoeren?\n" );
            System.err.flush();
            throw new IllegalStateException( "missing configuration" );
        }//if
        final String here = new Object(){}.getClass().getPackage().getName();
        if (! here.matches( "_untouchable_.common$" )){
            System.out.flush();
            System.err.printf( "Sie handeln nicht gemaess Aufgabenstellung - Pfade/package path fehlerhaft\n" );
            System.err.printf( "\\--> Stimmmt die Package.Struktur?\n" );
            System.err.printf( "\\--> Stimmen die Import-Listen?\n" );
            System.err.flush();
            throw new IllegalStateException( "unexpected package path -> " + here );
        }//if
        
        
        // construct
        internalSmurfID = smurfCnt.getAndIncrement();                           // Synchronisation ! => Sichtbarkeit, aber nur beim "Anstart-Thread"
        schedule = factory.createSchedule();
        eventList = new ArrayList<>();
        lastEvent = null;
    }//constructor()
    
    
    
    
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    abstract int identify();
    //
    /*                                                                          removing locate()
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    abstract int locate();
    */
    //
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    abstract boolean getDebugState();
    
    
    
    
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    final int internalSmurfID;
    //
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    final CommonSchedule schedule;
    //
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    final List<Event> eventList;
    //
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    Event lastEvent;
    
    
    
    
    
    @ChunkPreamble ( lastModified="2016/04/09", lastModifiedBy="Michael Schäfers" )
    void takeTimeForDoingStuffAtCurrentPosition( final boolean enableReport,  final int positionParameter,  final CommonSSI ssi ) throws InterruptedException {
        Event currEvent = new Event(
            EventType.S1BDS,                                                    // et
            Tool.nanoElapsedTime(),                                             // time
            lastEvent,                                                          // lastEvent
            null,                                                               // current thread id
            //
            internalSmurfID,                                                    // smurfIdInternal
            identify(),                                                         // smurfIdUser
            null,                                                               // smurfPositionLocated     __???__<130725> <-> locate() hat vermutlich Sichtbarkeitkonsequenzen
            positionParameter,                                                  // smurfPositionParameter
            ssi.getPlanedPosition(),                                            // smrfRequestedPosition
            //
            null,                                                               // woeIdInternal
            null,                                                               // woeIdUser
            null,                                                               // woePositionLocated
            null                                                                // woePositionParameter
        );
        eventList.add( currEvent );
        new ActivityReporter( enableReport, currEvent ).report();
        lastEvent = currEvent;
        //
        TimeUnit.MILLISECONDS.sleep( ssi.getDwell() * Global.getData().unit );
        //
        currEvent = new Event(
            EventType.S2EDS,                                                    // et
            Tool.nanoElapsedTime(),                                             // time
            lastEvent,                                                          // lastEvent
            null,                                                               // current thread id
            //
            internalSmurfID,                                                    // smurfIdInternal
            identify(),                                                         // smurfIdUser
            null,                                                               // smurfPositionLocated  __???__<130725> <-> locate() hat vermutlich Sichtbarkeitkonsequenzen
            positionParameter,                                                  // smurfPositionParameter
            ssi.getPlanedPosition(),                                            // smrfRequestedPosition
            //
            null,                                                               // woeIdInternal
            null,                                                               // woeIdUser
            null,                                                               // woePositionLocated
            null                                                                // woePositionParameter
        );
        eventList.add( currEvent );
        lastEvent = currEvent;
        new ActivityReporter( enableReport, currEvent ).report();
    }//method()
    
    
    
    @ChunkPreamble ( lastModified="2016/04/09", lastModifiedBy="Michael Schäfers" )
    void enter( final boolean enableReport,  final CommonWOE woe,  final String woeText ){
        Event currEvent = new Event(
            EventType.S3ENW,                                                    // et
            Tool.nanoElapsedTime(),                                             // time
            lastEvent,                                                          // lastEvent
            null,                                                               // current thread id
            //
            internalSmurfID,                                                    // smurfIdInternal
            identify(),                                                         // smurfIdUser
            null,                                                               // smurfPositionLocated  __???__<130725> <-> locate() hat vermutlich Sichtbarkeitkonsequenzen
            null,                                                               // smurfPositionParameter
            null,                                                               // smurfRequestedPosition
            //
            woe.internalWoeID,                                                  // woeIdInternal
            woe.identify(),                                                     // woeIdUser
            null,                                                               // woePositionLocated  __???__<130725> <-> locate() hat vermutlich Sichtbarkeitkonsequenzen
            null                                                                // woePositionParameter
        );
        eventList.add( currEvent );
        lastEvent = currEvent;
        new ActivityReporter( enableReport, currEvent ).report( woeText );
    }//method()
    
    
    @ChunkPreamble ( lastModified="2016/04/09", lastModifiedBy="Michael Schäfers" )
    void beThere( final boolean enableReport, final CommonWOE woe,  final String woeText ){
        Event currEvent = new Event(
            EventType.S4AIW,                                                    // et
            Tool.nanoElapsedTime(),                                             // time
            lastEvent,                                                          // lastEvent
            Global.getData().checkThread  ?  (int)Thread.currentThread().getId()  :  null,  // current thread id <-> (int)Thread.currentThread().getId() <-> Sichtbarkeit
            //
            internalSmurfID,                                                    // smurfIdInternal
            identify(),                                                         // smurfIdUser
            null,                                                               // smurfPositionLocated
            null,                                                               // smurfPositionParameter
            null,                                                               // smurfRequestedPosition
            //
            woe.internalWoeID,                                                  // woeIdInternal
            woe.identify(),                                                     // woeIdUser
            null,                                                               // woePositionLocated  __???__<130725> <-> locate() hat vermutlich Sichtbarkeitkonsequenzen
            null                                                                // woePositionParameter
        );
        eventList.add( currEvent );
        lastEvent = currEvent;
        new ActivityReporter( enableReport, currEvent ).report( woeText );
    }//method()
    
    
    @ChunkPreamble ( lastModified="2016/04/09", lastModifiedBy="Michael Schäfers" )
    void leave( final boolean enableReport,  final CommonWOE woe,  final String woeText ){
        Event currEvent = new Event(
            EventType.S5LVW,                                                    // et
            Tool.nanoElapsedTime(),                                             // time
            lastEvent,                                                          // lastEvent
            null,                                                               // current thread id
            //
            internalSmurfID,                                                    // smurfIdInternal
            identify(),                                                         // smurfIdUser
            null,                                                               // smurfPositionLocated  __???__<130725> <-> locate() hat vermutlich Sichtbarkeitkonsequenzen
            null,                                                               // smurfPositionParameter
            null,                                                               // smurfRequestedPosition
            //
            woe.internalWoeID,                                                  // woeIdInternal
            woe.identify(),                                                     // woeIdUser
            null,                                                               // woePositionLocated  __???__<130725> <-> locate() hat vermutlich Sichtbarkeitkonsequenzen
            null                                                                // woePositionParameter
        );
        eventList.add( currEvent );
        lastEvent = currEvent;
        new ActivityReporter( enableReport, currEvent ).report( woeText );
    }//method()
    
    
    
    @ChunkPreamble ( lastModified="2016/04/09", lastModifiedBy="Michael Schäfers" )
    void lastDeed( final boolean enableReport ){
        Event currEvent = new Event(
            EventType.SxxLD,                                                    // et
            Tool.nanoElapsedTime(),                                             // time
            lastEvent,                                                          // lastEvent
            null,                                                               // current thread id
            //
            internalSmurfID,                                                    // smurfIdInternal
            identify(),                                                         // smurfIdUser
            null,                                                               // smurfPositionLocated  __???__<130725> <-> locate() hat vermutlich Sichtbarkeitkonsequenzen
            null,                                                               // smurfPositionParameter
            null,                                                               // smurfRequestedPosition
            //
            null,                                                               // woeIdInternal
            null,                                                               // woeIdUser
            null,                                                               // woePositionLocated
            null                                                                // woePositionParameter
        );
        eventList.add( currEvent );
        lastEvent = currEvent;
        new ActivityReporter( enableReport, currEvent ).report();
        //
        //
        //
        //######################################################################
        //
        // "last" action => start delayed thread to registrate smurf and enter event list
        //
        @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
        class DelayedRawSmurf implements Runnable {
            //
            @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
            DelayedRawSmurf( final RawSmurf smurf,  final List<Event> eventList ){
                this.smurf = smurf;
                this.eventList = eventList;
            }//DelayedRawSmurf()
            //
            final List<Event> eventList;
            final RawSmurf smurf;
            //
            @Override
            @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
            public void run(){
                try{
                    TimeUnit.MILLISECONDS.sleep( 500 );
                    Global.av_syncWithGlobalMutex.lock();
                    Global.av_smurfList.add( smurf );
                    Global.av_eventLLst.add( eventList );
                    Global.av_condVar.signal();
                    Global.av_syncWithGlobalMutex.unlock();
                }catch( InterruptedException ex ){
                    System.out.flush();
                    System.err.printf( "\n\n\nThread interrupted while waiting - this was neither expected to happen nor programmed (inside the environment)!\n" );
                    ex.printStackTrace();
                }//try
            }//run()
        }//class DelayedRawSmurf
        //
        //
        new Thread( new DelayedRawSmurf( this, eventList ) ).start();
        //
    }//method()
    
    
    
    
    
    // internalSmurfID is unique and clone() is forbidden
    //
    @ChunkPreamble ( lastModified="2013/05/23", lastModifiedBy="Michael Schäfers" )
    @Override public boolean equals( Object other ){ return this == other; }
    //
    @ChunkPreamble ( lastModified="2013/05/23", lastModifiedBy="Michael Schäfers" )
    @Override public int hashCode(){ return internalSmurfID; }
    
}//class

/*
--------------------------------------------------------------------------------

    EWUA1     -> waitUntilNextArrival()
    EWUA2     -> waitUntilNextArrival()
    
    S1BDS     -> takeTimeForDoingStuffAtCurrentPosition()
    S2EDS     -> takeTimeForDoingStuffAtCurrentPosition()
    
    S3ENW     -> enter()
    S4TIW     -> relax()
    S5LVW     -> leave()
    
    SxxLD     -> lastDeed()
    
--------------------------------------------------------------------------------

    EWUA1,    // Environment: Wait until next Arrival (begin)
    EWUA2,    // Environment: Wait until next Arrival (end)
    
    
    
    S1BDS,    // Smurf 1.: does stuff #1 (takeTimeForDoingStuff / Begins to Do Stuff)
    S2EDS,    // Smurf 2.: does stuff #2 (takeTimeForDoingStuff / Ends Doing stuff)
    
    
    
    W1MO1,    // WhatsOEver-1.: MOves on to next position/location (start/begin)
    W2MO2,    // WhatsOEver-2.: MOves on to next position/location (arrival/end)
    W3AAP,    // WhatsOEver-3.: Appears At Position/location
    W4AEN,    // WhatsOEver-4.: Allows ENtranace                                W4AEN  <  S3ENW | S5LVW
    W5WP1,    // WhatsOEver-5.: Waits at Position/location (start)
    
    S3ENW,    // Smurf 3.: ENters Whatsoever                                    W4AEN  <  S3ENW  <  W7FEN
    S4AIW,    // Smurf 4.: (is) Active Inside Whatsoever
    S5LVW,    // Smurf 5.: LeaVes Whatsoever                                    W4AEN  <  S5LVW  <  W7FEN
    
    W6WP2,    // WhatsOEver-6.: Waits at Position/location (end of)
    W7FEN,    // WhatsOEver-7.: Forbidds ENtrance                               S3ENW | S5LVW  <  W7FEN
    W8VFP,    // WhatsOEver-8.: Vanishes From Position/location
    //
    WxxLD,    // Whatsoever Last Deed
    
    SxxLD,    // Smurf Last Deed
    
    
    
    // stuff
    ERR,      // ERRor
    OTHER;    //

 */
