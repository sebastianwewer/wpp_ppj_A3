package _untouchable_.common;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2013/05/20",
    currentRevision = "0.93",
    lastModified    = "2016/04/09",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
//the WHOLE CLASS and ALL of its ELEMENTS are friendly on purpose (!)
abstract class RawWOE {
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    static AtomicInteger woeCnt = new AtomicInteger( 0 );
    
    
    
    
    
    @ChunkPreamble ( lastModified="2013/05/11", lastModifiedBy="Michael Schäfers" )
    RawWOE(){
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
        internalWoeID = woeCnt.getAndIncrement();                               // Synchronisation ! => Sichtbarkeit, aber nur beim "Anstart-Thread"
    }//constructor()
    
    
    
    
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    abstract void terminate();
    //
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
    final int internalWoeID;
    //
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    final List<Event> eventList = new ArrayList<>();
    //
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    Event lastEvent;
    
    
    
    
    
    @ChunkPreamble ( lastModified="2016/03/28", lastModifiedBy="Michael Schäfers" )
    void takeTimeForMotionTo(
        final boolean enableReport,
        final int destinationPositionParameter,
        final String woeText,
        final String actionText1,
        final String actionText2
    ) throws InterruptedException {
        if( 0 > destinationPositionParameter ){
            throw new IllegalArgumentException( String.format( "destinationPositionParameter = %d",  destinationPositionParameter ));
        }//if
        //
        //
        Event currEvent = new Event(
            EventType.W1MO1,                                                    // et
            Tool.nanoElapsedTime(),                                             // time
            lastEvent,                                                          // lastEvent
            null,                                                               // current thread id
            //
            null,                                                               // smurfIdInternal
            null,                                                               // smurfIdUser
            null,                                                               // smurfPositionLocated
            null,                                                               // smurfPositionParameter
            null,                                                               // smurfRequestedPosition
            //
            internalWoeID,                                                      // woeIdInternal
            identify(),                                                         // woeIdUser
            null,                                                               // woePositionLocated  __???__<130725> <-> locate() hat vermutlich Sichtbarkeitkonsequenzen
            destinationPositionParameter                                        // woePositionParameter
        );
        eventList.add( currEvent );
        new ActivityReporter( enableReport, currEvent ).report( woeText, actionText1 );
        lastEvent = currEvent;
        //
        TimeUnit.MILLISECONDS.sleep(  ( Global.getData().minWoeMotionTime  +  (int)( (Global.getData().maxWoeMotionTime-Global.getData().minWoeMotionTime) *Math.random() )  ) * Global.getData().unit );
        //
        currEvent = new Event(
            EventType.W2MO2,                                                    // et
            Tool.nanoElapsedTime(),                                             // time
            lastEvent,                                                          // lastEvent
            null,                                                               // current thread id
            //
            null,                                                               // smurfIdInternal
            null,                                                               // smurfIdUser
            null,                                                               // smurfPositionLocated
            null,                                                               // smurfPositionParameter
            null,                                                               // smurfRequestedPosition
            //
            internalWoeID,                                                      // woeIdInternal
            identify(),                                                         // woeIdUser
            null,                                                               // woePositionLocated  __???__<130725> <-> locate() hat vermutlich Sichtbarkeitkonsequenzen
            destinationPositionParameter                                        // woePositionParameter
        );
        eventList.add( currEvent );
        new ActivityReporter( enableReport, currEvent ).report( woeText, actionText2 );
        lastEvent = currEvent;
    }//method()
    
    
    @ChunkPreamble ( lastModified="2016/03/28", lastModifiedBy="Michael Schäfers" )
    void appear( final boolean enableReport,  final int positionParameter,  final String woeText,  final String actionText ){
        if( 0 > positionParameter ){
            throw new IllegalArgumentException( String.format( "positionParameter = %d",  positionParameter ));
        }//if
        //
        //
        Event currEvent = new Event(
            EventType.W3AAP,                                                    // et
            Tool.nanoElapsedTime(),                                             // time
            lastEvent,                                                          // lastEvent
            null,                                                               // current thread id
            //
            null,                                                               // smurfIdInternal
            null,                                                               // smurfIdUser
            null,                                                               // smurfPositionLocated
            null,                                                               // smurfPositionParameter
            null,                                                               // smurfRequestedPosition
            //
            internalWoeID,                                                      // woeIdInternal
            identify(),                                                         // woeIdUser
            null,                                                               // woePositionLocated  __???__<130725> <-> locate() hat vermutlich Sichtbarkeitkonsequenzen
            positionParameter                                                   // woePositionParameter
        );
        eventList.add( currEvent );
        new ActivityReporter( enableReport, currEvent ).report( woeText, actionText );
        lastEvent = currEvent;
    }//method()
    
    
    @ChunkPreamble ( lastModified="2016/04/09", lastModifiedBy="Michael Schäfers" )
    void allowEntrance( final boolean enableReport,  final String woeText,  final String actionText ){
        Event currEvent = new Event(
            EventType.W4AEN,                                                    // et
            Tool.nanoElapsedTime(),                                             // time
            lastEvent,                                                          // lastEvent
            null,                                                               // current thread id
            //
            null,                                                               // smurfIdInternal
            null,                                                               // smurfIdUser
            null,                                                               // smurfPositionLocated
            null,                                                               // smurfPositionParameter
            null,                                                               // smurfRequestedPosition
            //
            internalWoeID,                                                      // woeIdInternal
            identify(),                                                         // woeIdUser
            null,                                                               // woePositionLocated  __???__<130725> <-> locate() hat vermutlich Sichtbarkeitkonsequenzen
            null                                                                // woePositionParameter
        );
        eventList.add( currEvent );
        new ActivityReporter( enableReport, currEvent ).report( woeText, actionText );
        lastEvent = currEvent;
    }//method()
    //
    @ChunkPreamble ( lastModified="2016/04/09", lastModifiedBy="Michael Schäfers" )
    void allowEntrance(){
        allowEntrance( false, null, null );
    }//method()
    
    
    @ChunkPreamble ( lastModified="2016/04/09", lastModifiedBy="Michael Schäfers" )
    void takeTimeForStopover( final boolean enableReport,  final int positionParameter ) throws InterruptedException {
        if( 0 > positionParameter ){
            throw new IllegalArgumentException( String.format( "positionParameter = %d",  positionParameter ));
        }//if
        //
        //
        Event currEvent = new Event(
            EventType.W5WP1,                                                    // et
            Tool.nanoElapsedTime(),                                             // time
            lastEvent,                                                          // lastEvent
            null,                                                               // current thread id
            //
            null,                                                               // smurfIdInternal
            null,                                                               // smurfIdUser
            null,                                                               // smurfPositionLocated
            null,                                                               // smurfPositionParameter
            null,                                                               // smurfRequestedPosition
            //
            internalWoeID,                                                      // woeIdInternal
            identify(),                                                         // woeIdUser
            null,                                                               // woePositionLocated  __???__<130725> <-> locate() hat vermutlich Sichtbarkeitkonsequenzen
            positionParameter                                                   // woePositionParameter
        );
        eventList.add( currEvent );
        lastEvent = currEvent;
        //
        TimeUnit.MILLISECONDS.sleep( ( Global.getData().minWoeDwell + (int)( (Global.getData().maxWoeDwell-Global.getData().minWoeDwell) * Math.random() ) ) * Global.getData().unit );
        //
        currEvent = new Event(
            EventType.W6WP2,                                                    // et
            Tool.nanoElapsedTime(),                                             // time
            lastEvent,                                                          // lastEvent
            null,                                                               // current thread id
            //
            null,                                                               // smurfIdInternal
            null,                                                               // smurfIdUser
            null,                                                               // smurfPositionLocated
            null,                                                               // smurfPositionParameter
            null,                                                               // smurfRequestedPosition
            //
            internalWoeID,                                                      // woeIdInternal
            identify(),                                                         // woeIdUser
            null,                                                               // woePositionLocated  __???__<130725> <-> locate() hat vermutlich Sichtbarkeitkonsequenzen
            positionParameter                                                   // woePositionParameter
        );
        eventList.add( currEvent );
        lastEvent = currEvent;
    }//method()
    
    
    @ChunkPreamble ( lastModified="2016/04/09", lastModifiedBy="Michael Schäfers" )
    void forbidEntrance( final boolean enableReport,  final String woeText,  final String actionText ){
        Event currEvent = new Event(
            EventType.W7FEN,                                                    // et
            Tool.nanoElapsedTime(),                                             // time
            lastEvent,                                                          // lastEvent
            null,                                                               // current thread id
            //
            null,                                                               // smurfIdInternal
            null,                                                               // smurfIdUser
            null,                                                               // smurfPositionLocated
            null,                                                               // smurfPositionParameter
            null,                                                               // smurfRequestedPosition
            //
            internalWoeID,                                                      // woeIdInternal
            identify(),                                                         // woeIdUser
            null,                                                               // woePositionLocated  __???__<130725> <-> locate() hat vermutlich Sichtbarkeitkonsequenzen
            null                                                                // woePositionParameter
        );
        eventList.add( currEvent );
        new ActivityReporter( enableReport, currEvent ).report( woeText, actionText );
        lastEvent = currEvent;
    }//method()
    //
    @ChunkPreamble ( lastModified="2016/04/09", lastModifiedBy="Michael Schäfers" )
    void forbidEntrance(){
        forbidEntrance( false, null, null );
    }//method()
    
    
    @ChunkPreamble ( lastModified="2016/04/09", lastModifiedBy="Michael Schäfers" )
    void vanish( final boolean enableReport,  final int positionParameter,  final String woeText,  final String actionText ){
        if( 0 > positionParameter ){
            throw new IllegalArgumentException( String.format( "positionParameter = %d",  positionParameter ));
        }//if
        //
        //
        Event currEvent = new Event(
            EventType.W8VFP,                                                    // et
            Tool.nanoElapsedTime(),                                             // time
            lastEvent,                                                          // lastEvent
            null,                                                               // current thread id
            //
            null,                                                               // smurfIdInternal
            null,                                                               // smurfIdUser
            null,                                                               // smurfPositionLocated
            null,                                                               // smurfPositionParameter
            null,                                                               // smurfRequestedPosition
            //
            internalWoeID,                                                      // woeIdInternal
            identify(),                                                         // woeIdUser
            null,                                                               // woePositionLocated  __???__<130725> <-> locate() hat vermutlich Sichtbarkeitkonsequenzen
            positionParameter                                                   // woePositionParameter
        );
        eventList.add( currEvent );
        new ActivityReporter( enableReport, currEvent ).report( woeText, actionText );
        lastEvent = currEvent;
    }//method()
    
    
    @ChunkPreamble ( lastModified="2016/04/09", lastModifiedBy="Michael Schäfers" )
    void lastDeed( final boolean enableReport,  final String woeText ){
        Event currEvent = new Event(
            EventType.WxxLD,                                                    // et
            Tool.nanoElapsedTime(),                                             // time
            lastEvent,                                                          // lastEvent
            null,                                                               // current thread id
            //
            null,                                                               // smurfIdInternal
            null,                                                               // smurfIdUser
            null,                                                               // smurfPositionLocated
            null,                                                               // smurfPositionParameter
            null,                                                               // smurfRequestedPosition
            //
            internalWoeID,                                                      // woeIdInternal
            identify(),                                                         // woeIdUser
            null,                                                               // woePositionLocated  __???__<130725> <-> locate() hat vermutlich Sichtbarkeitkonsequenzen
            null                                                                // woePositionParameter
        );
        eventList.add( currEvent );
        new ActivityReporter( enableReport, currEvent ).report( woeText );
        lastEvent = currEvent;
        //
        //######################################################################
        //
        // "last" action => start delayed thread to registrate bus and enter event list
        //
        @ChunkPreamble ( lastModified="2016/04/09", lastModifiedBy="Michael Schäfers" )
        class DelayedRawWOE implements Runnable {
            //
            final List<Event> eventList;
            final RawWOE woe;
            //
            @ChunkPreamble ( lastModified="2016/04/09", lastModifiedBy="Michael Schäfers" )
            DelayedRawWOE( final RawWOE woe,  final List<Event> eventList ){
                this.woe = woe;
                this.eventList = eventList;
            }//constructor()
            //
            @ChunkPreamble ( lastModified="2016/04/09", lastModifiedBy="Michael Schäfers" )
            @Override
            public void run(){
                try{
                    TimeUnit.MILLISECONDS.sleep( 500 );
                    Global.av_syncWithGlobalMutex.lock();
                    Global.av_woeList.add( woe );
                    Global.av_eventLLst.add( eventList );
                    Global.av_condVar.signal();
                    Global.av_syncWithGlobalMutex.unlock();
                }catch( InterruptedException ex ){
                    ex.printStackTrace();
                }//try
            }//method()
        }//class
        //
        new Thread( new DelayedRawWOE( this, eventList ) ).start();
        //
    }//method()
    
    
    
    
    
    // internalWoeID is unique and clone() is forbidden
    //
    @ChunkPreamble ( lastModified="2013/05/23", lastModifiedBy="Michael Schäfers" )
    @Override public boolean equals( Object other ){ return this == other; }
    //
    @ChunkPreamble ( lastModified="2013/05/23", lastModifiedBy="Michael Schäfers" )
    @Override public int hashCode(){ return internalWoeID; }
    
}//class


/*
--------------------------------------------------------------------------------

    W1MO1     -> takeTimeForMotionTo()
    W2MO2     -> takeTimeForMotionTo()
    
    W3AAP     -> appear()
    
    W4AEN     -> allowEntrance()
    
    W5WP1     -> takeTimeForStopover()
    W6WP2     -> takeTimeForStopover()
    
    W7FEN     -> forbidEntrance()
    
    W8VFP     -> vanish()
    
    WxxLD     -> lastDeed()
    
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
