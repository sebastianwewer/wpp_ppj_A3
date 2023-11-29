package _untouchable_.common;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@ClassPreamble (
    author = "Michael Schäfers",
    organization = "Dept.Informatik; HAW Hamburg",
    date = "2013/05/26",
    currentRevision = "0.93",
    lastModified = "2013/05/26",
    lastModifiedBy = "Michael Schäfers",
    reviewers = ( "none" )
)
public class Global {
    
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" )
    public static synchronized Global createSingleInstanceOfGlobal( final Integer[] par,  final WoeType woeType,  final boolean grantWishes ){
        if (instance == null) {
            instance = new Global( par, woeType, grantWishes );
            instance.testExecutionLoopCount = new AtomicLong( 1 );
        }else{
            throw new IllegalStateException( "Only single configuration allowed and supported" );
        }//if
        return instance;
    }//method()
    
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" )
    private static Global instance = null;
    
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" )
    public static Global getData(){ return instance; }
    
    
    
    
    
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" )
    private Global( final Integer[] par,  final WoeType woeType,  final boolean grantWishes ){
        
        // check for crazy "stuff" or missusage of environment
        final String here = new Object(){}.getClass().getPackage().getName();
        if (! here.matches( "_untouchable_.common$" )){
            System.err.printf( "Sie handeln nicht gemaess Aufgabenstellung - Pfade/package path fehlerhaft\n" );
            System.err.printf( "-> Stimmmt die Package.Struktur?\n" );
            System.err.printf( "-> Stimmen die Import-Listen?\n" );
            System.err.flush();
            throw new IllegalStateException( "unexpected package path -> " + here );
        }//if
        //----------------------------------------------------------------------
        //
        //
        
        
        partId              = par[ 0];    // part#
        
        wnos                = par[ 1];    // Wanted Number Of Smurfs
        wnow                = par[ 3];    // Wanted Number Of Whatsoever  ( bus,      elevator, ship,    .. )
        wnol                = par[ 5];    // Wanted Number Of Locations   ( bus stop, level,    landing, .. )
        wmnspw              = par[ 7];    // Maximum Number of Smurfs Per Woe
        wmnwpl              = par[ 9];    // Maximum Number of Woe Per Location
        
        
        rnos                = par[ 2];    // Requested Number Of Smurfs
        rnow                = par[ 4];    // Requested Number Of Whatsoever  ( bus,      elevator, ship,    .. )
        rnol                = par[ 6];    // Requested Number Of Locations   ( bus stop, level,    landing, .. )
        rmnspw              = par[ 8]!=null  ?  par[ 8]  :  Integer.MAX_VALUE;    // Maximum Number of Smurfs Per Woe    - null::= "don't care" <=> MAX_VALUE
        rmnwpl              = par[10]!=null  ?  par[10]  :  Integer.MAX_VALUE;    // Maximum Number of Woe Per Location  - null::= "don't care" <=> MAX_VALUE
        
        
        fnos   =  grantWishes  ?  wnos    :  rnos;                              // <<<<<===== FINAL DECISION  160425
        fnow   =  grantWishes  ?  wnow    :  rnow;                              // <<<<<===== FINAL DECISION  160425
        fnol   =  grantWishes  ?  wnol    :  rnol;                              // <<<<<===== FINAL DECISION  160425
        fmnspw =  grantWishes  ?  wmnspw  :  rmnspw;                            // <<<<<===== FINAL DECISION  160425
        fmnwpl =  grantWishes  ?  wmnwpl  :  rmnwpl;                            // <<<<<===== FINAL DECISION  160425
        
        
        minNumberOfStops    = par[11];
        maxNumberOfStops    = par[12];
        
        minSmurfArrivalTime = par[13];
        maxSmurfArrivalTime = par[14];
        minSmurfDwell       = par[15];
        maxSmurfDwell       = par[16];
        
        minWoeMotionTime    = par[17];
        maxWoeMotionTime    = par[18];
        minWoeDwell         = par[19];
        maxWoeDwell         = par[20];
        
        unit                = par[21];
        
        
        this.woeType        = woeType;
        
        
        
        // number of smurfs
        if ( wnos <= 0 ){
            System.err.println( "Think twice about your parameter values for number of smurfs (nos) - nos must be truely positive (>0)" );
            System.err.flush();
            throw new IllegalStateException( "Faulty configuration for unit" );
        }//if
        //
        // number of whatsoever
        if ( wnow <= 0 ){
            System.err.println( "Think twice about your parameter values for whatsoever (woe) - woe must be truely positive (>0)" );
            System.err.flush();
            throw new IllegalStateException( "Faulty configuration for unit" );
        }//if
        //
        // number of locations
        if ( wnol <= 1 ){
            System.err.println( "Think twice about your parameter values for number of locations (nol) - nol must be greater than 1" );
            System.err.flush();
            throw new IllegalStateException( "Faulty configuration for unit" );
        }//if
        //----------------------------------------------------------------------
        // number of stops
        if ( maxNumberOfStops < minNumberOfStops  ||  minNumberOfStops < 0  ||  maxNumberOfStops < 1 ){
            System.err.println( "Think twice about your parameter values for min and/or max number of stops - max value must not be smaller than min value and both must be positive" );
            System.err.flush();
            throw new IllegalStateException( "Faulty configuration for NumberOfStops" );
        }//if
        //----------------------------------------------------------------------
        // smurf arrival rate
        if ( maxSmurfArrivalTime < minSmurfArrivalTime  ||  minSmurfArrivalTime < 0  ||  maxSmurfArrivalTime < 1 ){
            System.err.println( "Think twice about your parameter values for min and/or max smurf arrival time - max value must not be smaller than min value and both must be positive" );
            System.err.flush();
            throw new IllegalStateException( "Faulty configuration for SmurfArrivaltTime" );
        }//if
        //
        // smurf dwell
        if ( maxSmurfDwell < minSmurfDwell ){
            System.err.println( "Think twice about your parameter values for min and/or max smurf dwell - max value must not be smaller than min value and both must be positive" );
            System.err.flush();
            throw new IllegalStateException( "Faulty configuration for SmurfDwell" );
        }//if
        //----------------------------------------------------------------------
        // woe motion time
        if ( maxWoeMotionTime < minWoeMotionTime ){
            System.err.println( "Think twice about your parameter values for min and/or max woe motion time - max value must not be smaller than min value and both must be positive" );
            System.err.flush();
            throw new IllegalStateException( "Faulty configuration for WoeMotionTime" );
        }//if
        //
        // woe dwell
        if ( maxWoeDwell < minWoeDwell  ||  minWoeDwell < 0  ||  maxWoeDwell < 1 ){
            System.err.println( "Think twice about your parameter values for min and/or max woe dwell - max value must not be smaller than min value and both must be positive" );
            System.err.flush();
            throw new IllegalStateException( "Faulty configuration for WoeDwell" );
        }//if
        //----------------------------------------------------------------------
        // unit
        if ( unit <= 0 ){
            System.err.println( "Think twice about your parameter values for unit - unit must be truely positive (>0)" );
            System.err.flush();
            throw new IllegalStateException( "Faulty configuration for unit" );
        }//if
        //----------------------------------------------------------------------
        // mnspw
        if ( wmnspw!=null  &&  wmnspw<=0 ){
            System.err.println( "Think twice about your parameter values for mnspwoe - unit must be truely positive (>0)" );
            System.err.flush();
            throw new IllegalStateException( "Faulty configuration for unit" );
        }//if
        //----------------------------------------------------------------------
        // mnwpl
        if ( wmnwpl!=null  &&  wmnwpl<=0 ){
            System.err.println( "Think twice about your parameter values for mnspwoe - unit must be truely positive (>0)" );
            System.err.flush();
            throw new IllegalStateException( "Faulty configuration for unit" );
        }//if
        
    }//constructor()
    
    
    
    
    
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final Integer partId;                //
    
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final Integer wnos;                  // Wanted Number Of Smurfs
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final Integer wnow;                  // Wanted Number Of Whatsoever  ( bus,      elevator, ship,    .. )
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final Integer wnol;                  // Wanted Number Of Locations   ( bus stop, level,    landing, .. )
    //
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final Integer wmnspw;                // Wanted Maximum Number of Smurfs Per Woe
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final Integer wmnwpl;                // Wanted Maximum Number of Woe Perl Location
    
    
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final Integer rnos;                  // Requested Number Of Smurfs
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final Integer rnow;                  // Requested Number Of Whatsoever  ( bus,      elevator, ship,    .. )
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final Integer rnol;                  // Requested Number Of Locations   ( bus stop, level,    landing, .. )
    //
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final Integer rmnspw;                // Requested Maximum Number of Smurfs Per Woe
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final Integer rmnwpl;                // Requested Maximum Number of Woe Perl Location
    
    
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" ) public final Integer fnos;                  // Final Number Of Smurfs
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" ) public final Integer fnow;                  // Final Number Of Whatsoever  ( bus,      elevator, ship,    .. )
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" ) public final Integer fnol;                  // Final Number Of Locations   ( bus stop, level,    landing, .. )
    //
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" ) public final Integer fmnspw;                // Final Maximum Number of Smurfs Per Woe
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" ) public final Integer fmnwpl;                // Final Maximum Number of Woe Perl Location
    
    
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final Integer minNumberOfStops;      //
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final Integer maxNumberOfStops;      //
    
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final Integer minSmurfArrivalTime;   //
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final Integer maxSmurfArrivalTime;   //
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final Integer minSmurfDwell;         //
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final Integer maxSmurfDwell;         //
    
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final Integer minWoeMotionTime;      //
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final Integer maxWoeMotionTime;      //
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final Integer minWoeDwell;           //
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final Integer maxWoeDwell;           //
    
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final Integer unit;                  //
    
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final WoeType woeType;               //
    
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final Boolean checkThread = false;   //
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public final boolean heavyTest = false;     //
    
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) public AtomicLong testExecutionLoopCount;   //
    
    
    
    
    //
    //
    //
    // _HERE_ : Added for Verification -----------------------------------------
    
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) static final long av_startTime = Tool.nanoTime();
    
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) static final Lock av_syncWithGlobalMutex = new ReentrantLock();
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) static final Condition av_condVar = av_syncWithGlobalMutex.newCondition();
    //
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) static final List<List<Event>>    av_eventLLst = new ArrayList<>();         // guarded by av_syncWithGlobalMutex
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) static final List<Event>          av_eventList = new ArrayList<>();         // guarded by av_syncWithGlobalMutex
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) static final List<RawSmurf>       av_smurfList = new ArrayList<>();         // guarded by av_syncWithGlobalMutex
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) static final List<RawWOE>         av_woeList   = new ArrayList<>();         // guarded by av_syncWithGlobalMutex
    
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) static final boolean av_stopOnError   = false;
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) static final boolean av_outputOnError = true;
    @ChunkPreamble ( lastModified="2012/07/26", lastModifiedBy="Michael Schäfers" ) static final int     av_maxLocErrCnt  = 23;
    
}//class Global
