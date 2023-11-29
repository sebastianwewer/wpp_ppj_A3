package _untouchable_.common;


@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2013/05/20",
    currentRevision = "0.93",
    lastModified    = "2016/04/09",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
enum EventType {                                                                // friendly on purpose
    
    // environment
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
    S4AIW,    // Smurf 4.: (is) Active Inside Whatsoever - takes a deep brace / shows ticket / ...  <= THIS HAS TO BE DONE BY SMURF ITSELF (!)
    S5LVW,    // Smurf 5.: LeaVes Whatsoever                                    W4AEN  <  S5LVW  <  W7FEN
    
    W6WP2,    // WhatsOEver-6.: Waits at Position/location (end of)
    W7FEN,    // WhatsOEver-7.: Forbids ENtrance                                S3ENW | S5LVW  <  W7FEN
    W8VFP,    // WhatsOEver-8.: Vanishes From Position/location
    //
    WxxLD,    // Whatsoever Last Deed
    
    SxxLD,    // Smurf Last Deed
    
    
    
    // stuff
    ERR,      // ERRor
    OTHER;    //
    
    
    
    
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    public boolean checkAgainstWOE( final EventType woe ){                      // __???___<130616> wird das irgendwo genutzt ?
         if (this==S3ENW || this==S5LVW){
             return  woe==W4AEN || woe==W5WP1;
         }else{
             throw new IllegalStateException( "INTNAL PROGRAMMING ERROR - unexpected control flow - it was NOT expected to end up here" );
        }//if
    }//method()
    
    
    @ChunkPreamble ( lastModified="2013/05/16", lastModifiedBy="Michael Schäfers" )
    boolean isSmurfActivityValid( final EventType lastSmurfActivity ){
        if ( lastSmurfActivity == null )  return this==S1BDS;                   // handle 1st action
        switch ( lastSmurfActivity ){                                           // handle following actions
            case S1BDS: return this==S2EDS;
            case S2EDS: return this==S3ENW || this==SxxLD;
            case S3ENW: return this==S4AIW;
            case S4AIW: return this==S5LVW;
            case S5LVW: return this==S1BDS;
            case SxxLD: return false;
            default: throw new IllegalStateException( String.format( "INTNAL PROGRAMMING ERROR - unexpected control flow - it was NOT expected to end up here -> last=%s current=%s",  lastSmurfActivity, this ) );
        }//switch
    }//method()
    
    
    @ChunkPreamble ( lastModified="2016/04/26", lastModifiedBy="Michael Schäfers" )
    boolean isWoeActivityValid( final EventType lastWoeActivity ){
        if ( lastWoeActivity == null ){
            // handle 1st action
            //
            final WoeType woeType = Global.getData().woeType;
            return (this==W3AAP && woeType == WoeType.BUS)                      // 1st bus event    synchronous start was requested - W3AAP makes most sense
                || (this==W3AAP && woeType == WoeType.SHIP);                    // 1st ship event   synchronous start was requested - W3AAP makes most sense
        }//if
        switch ( lastWoeActivity ){                                             // handle following actions
            case W1MO1: return this==W2MO2;
            case W2MO2: return this==W3AAP || this==WxxLD;                      // __???__<130516>
            case W3AAP: return this==W4AEN;
            case W4AEN: return this==W5WP1;
            case W5WP1: return this==W6WP2;
            case W6WP2: return this==W7FEN;
            case W7FEN: return this==W8VFP;
            case W8VFP: return this==W1MO1 || this==WxxLD;                      // __???__<130516>
            case WxxLD: return false;
            default: throw new IllegalStateException( String.format( "INTNAL PROGRAMMING ERROR - unexpected control flow - it was NOT expected to end up here -> last=%s current=%s",  lastWoeActivity, this ) );
        }//switch
    }//method()
    
    @ChunkPreamble ( lastModified="2013/05/16", lastModifiedBy="Michael Schäfers" )
    boolean isActivityCausedBySmurf(){
        return  this == S1BDS
            ||  this == S2EDS
            ||  this == S3ENW
            ||  this == S4AIW
            ||  this == S5LVW
            ||  this == SxxLD;
    }//method()
    
    @ChunkPreamble ( lastModified="2013/05/16", lastModifiedBy="Michael Schäfers" )
    boolean isActivityCausedByWOE(){
        return  this == W1MO1
            ||  this == W2MO2
            ||  this == W3AAP
            ||  this == W4AEN
            ||  this == W5WP1
            ||  this == W6WP2
            ||  this == W7FEN
            ||  this == W8VFP
            ||  this == WxxLD;
    }//method()
    
    
    
    
    
    @ChunkPreamble ( lastModified="2013/05/30", lastModifiedBy="Michael Schäfers" )
    Integer toCodedSmurfState(){
        switch ( this ){
            case S1BDS: return 1;
            case S2EDS: return 2;
            case S3ENW: return 3;
            case S4AIW: return 4;
            case S5LVW: return 5;
            default:    return null;    // invalid value on purpose to provocate error
        }//switch
    }//method()
    
    @ChunkPreamble ( lastModified="2013/05/30", lastModifiedBy="Michael Schäfers" )
    Integer toCodedWoeState(){
        switch ( this ){
            case W1MO1: return 1;
            case W2MO2: return 2;
            case W3AAP: return 3;
            case W4AEN: return 4;
            case W5WP1: return 5;
            case W6WP2: return 6;
            case W7FEN: return 7;
            case W8VFP: return 8;
            default:    return null;    // invalid value on purpose to provocate error
        }//switch
    }//method()
    
    
    @ChunkPreamble ( lastModified="2013/05/30", lastModifiedBy="Michael Schäfers" )
    static EventType codedSmurfState2smurfEventType( final int codedSmurfState ){
        switch ( codedSmurfState ){
            case 1:  return S1BDS;
            case 2:  return S2EDS;
            case 3:  return S3ENW;
            case 4:  return S4AIW;
            case 5:  return S5LVW;
            default: return null;    // invalid value on purpose to provocate error
        }//switch
    }//method()
    
    @ChunkPreamble ( lastModified="2013/05/30", lastModifiedBy="Michael Schäfers" )
    static EventType codedWoeState2woeEventType( final int codedWoeState ){
        switch ( codedWoeState ){
            case 1:  return W1MO1;
            case 2:  return W2MO2;
            case 3:  return W3AAP;
            case 4:  return W4AEN;
            case 5:  return W5WP1;
            case 6:  return W6WP2;
            case 7:  return W7FEN;
            case 8:  return W8VFP;
            default: return null;    // invalid value on purpose to provocate error
        }//switch
    }//method()
    
}//enum
