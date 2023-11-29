package _untouchable_.common;


@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2013/05/20",
    currentRevision = "0.93",
    lastModified    = "2013/05/27",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
//the WHOLE CLASS and ALL of its ELEMENTS are friendly on purpose (!)
class Event implements Comparable<Event> {
    
    @ChunkPreamble ( lastModified="2016/03/26", lastModifiedBy="Michael Schäfers" )
    Event(
        final EventType et,            final Long time,           final Event lastEvent,              final Integer currThreadId,
        final Integer smurfIdInternal, final Integer smurfIdUser, final Integer smurfPositionLocated, final Integer smurfPositionParameter, final Integer smurfRequestedPosition,
        final Integer woeIdInternal,   final Integer woeIdUser,   final Integer woePositionLocated,   final Integer woePositionParameter
    ){
        assert  null == smurfPositionLocated : String.format( "INTERNAL INTEGRITY CHECK  ->  null != %d == smurfPositionLocated", smurfPositionLocated );   // __???__<160326> sicherstellen, dass __???__<130725> gilt ;-)
        assert  null == woePositionLocated :   String.format( "INTERNAL INTEGRITY CHECK  ->  null != %d == woePositionLocated",   woePositionLocated );     // __???__<160326> sicherstellen, dass __???__<130725> gilt ;-)
        //
        this.et = et;                                                           // event type
        this.time = time;                                                       // time when event happened
        this.lastEvent = lastEvent;                                             // last event of smurf resp. WOE
        this.currThreadId = currThreadId;                                       // __???__<160326> to be checked - should be final set during generation und checked with death
        //
        this.smurfIdInternal = smurfIdInternal;                                 // smurf ID given by RawSmurf
        this.smurfIdUser = smurfIdUser;                                         // smurf ID given by user
        this.smurfPositionLocated = smurfPositionLocated;                       // smurfPositionLocated __???__<130725> <-> locate() hat vermutlich Sichtbarkeitkonsequenzen und wird NICHT mehr genutzt bzw. ist IMMER null __???__<160326>
        this.smurfPositionParameter = smurfPositionParameter;
        this.smurfRequestedPosition = smurfRequestedPosition;
        //
        this.woeIdInternal = woeIdInternal;                                     // whatsOEver ID given by RawWOE
        this.woeIdUser = woeIdUser;                                             // whatsOEver ID given by user
        this.woePositionLocated = woePositionLocated;                           // woePositionLocated   __???__<130725> <-> locate() hat vermutlich Sichtbarkeitkonsequenzen und wird NICHT mehr genutzt bzw. ist IMMER null __???__<160326>
        this.woePositionParameter = woePositionParameter;                       //
    }//constructor()
    
    
    
    
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" ) final EventType et;
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" ) final Long time;
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" ) final Event lastEvent;
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" ) final Integer currThreadId;
    
    @ChunkPreamble ( lastModified="2013/05/13", lastModifiedBy="Michael Schäfers" ) final Integer smurfIdUser;
    @ChunkPreamble ( lastModified="2013/05/13", lastModifiedBy="Michael Schäfers" ) final Integer smurfIdInternal;
    @ChunkPreamble ( lastModified="2013/05/13", lastModifiedBy="Michael Schäfers" ) final Integer smurfPositionLocated;
    @ChunkPreamble ( lastModified="2013/05/13", lastModifiedBy="Michael Schäfers" ) final Integer smurfPositionParameter;
    @ChunkPreamble ( lastModified="2013/05/27", lastModifiedBy="Michael Schäfers" ) final Integer smurfRequestedPosition;
    
    @ChunkPreamble ( lastModified="2013/05/13", lastModifiedBy="Michael Schäfers" ) final Integer woeIdUser;
    @ChunkPreamble ( lastModified="2013/05/13", lastModifiedBy="Michael Schäfers" ) final Integer woeIdInternal;
    @ChunkPreamble ( lastModified="2013/05/13", lastModifiedBy="Michael Schäfers" ) final Integer woePositionLocated;
    @ChunkPreamble ( lastModified="2013/05/13", lastModifiedBy="Michael Schäfers" ) final Integer woePositionParameter;
    
    
    
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    boolean checkAgainstWOE( final Event woeEvent ){ return et.checkAgainstWOE( woeEvent.et ); }        // __???___<130616> wird das irgendwo genutzt ?
    
    @ChunkPreamble ( lastModified="2013/05/16", lastModifiedBy="Michael Schäfers" )
    boolean isSmurfActivityValid( final Event lastSmurfActivity ){
        final EventType lastSmurfActivityType = (lastSmurfActivity!=null) ? lastSmurfActivity.et : null;
        return et.isSmurfActivityValid( lastSmurfActivityType );
    }//method()
    
    @ChunkPreamble ( lastModified="2013/05/16", lastModifiedBy="Michael Schäfers" )
    boolean isWoeActivityValid( final Event lastWoeActivity ){
        final EventType lastWoeActivityType = (lastWoeActivity!=null) ? lastWoeActivity.et : null;
        return et.isWoeActivityValid( lastWoeActivityType );
    }//method()
    
    @ChunkPreamble ( lastModified="2013/05/16", lastModifiedBy="Michael Schäfers" )
    boolean isActivityCausedByWOE(){ return (et!=null) ? et.isActivityCausedByWOE() : false; }
    
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    long deltaTime(){ return (lastEvent!=null)  ?  time-lastEvent.time : 0; }
    
    
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    @Override
    public int compareTo( Event other ){
        if       ( this.time > other.time ){ return +1;
        }else if ( this.time < other.time ){ return -1;
        }else{
            if       ( this.et.ordinal() > other.et.ordinal() ){ return +1;
            }else if ( this.et.ordinal() < other.et.ordinal() ){ return -1;
            }else{ return 0;
            }//if
        }//if
    }//method()
    
    
    @ChunkPreamble ( lastModified="2013/05/13", lastModifiedBy="Michael Schäfers" )
    @Override
    public String toString(){
        return String.format(
            "[<AV_Event>  et:%s  time:%s  %s   %s %s %s %s   %s %s %s %s]",
            et,
            Tool.prettyTime( time ),
            // lastEvent skipped
            null != currThreadId           ? "cTID:"        + currThreadId           : "--",
            //
            null != smurfIdInternal        ? "smurfIdInt:"  + smurfIdInternal        : "--",
            null != smurfIdUser            ? "smurfIdUsr:"  + smurfIdUser            : "--",
            null != smurfPositionLocated   ? "smurfPosLoc:" + smurfPositionLocated   : "--",
            null != smurfPositionParameter ? "smurfPosPrm:" + smurfPositionParameter : "--",
            //
            null != woeIdInternal          ? "woeIdInt:"    + woeIdInternal          : "--",
            null != woeIdUser              ? "woeIdUsr:"    + woeIdUser              : "--",
            null != woePositionLocated     ? "woePosLoc:"   + woePositionLocated     : "--",
            null != woePositionParameter   ? "woePosPrm:"   + woePositionParameter   : "--"
        );
    }//method()
    
    
    
    
    // clone() is forbidden
    //
    @ChunkPreamble ( lastModified="2013/05/23", lastModifiedBy="Michael Schäfers" )
    @Override public boolean equals( Object other ) { return this==other; }
    //
    @ChunkPreamble ( lastModified="2013/05/23", lastModifiedBy="Michael Schäfers" )
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        //
        result = prime * result + ((et == null) ? 0 : et.hashCode());
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        result = prime * result + ((lastEvent == null) ? 0 : lastEvent.hashCode());
        result = prime * result + ((currThreadId == null) ? 0 : currThreadId.hashCode());
        //
        result = prime * result + ((smurfIdInternal == null) ? 0 : smurfIdInternal.hashCode());
        result = prime * result + ((smurfIdUser == null) ? 0 : smurfIdUser.hashCode());
        result = prime * result + ((smurfPositionLocated == null) ? 0 : smurfPositionLocated.hashCode());
        result = prime * result + ((smurfPositionParameter == null) ? 0 : smurfPositionParameter.hashCode());
        //
        result = prime * result + ((woeIdInternal == null) ? 0 : woeIdInternal.hashCode());
        result = prime * result + ((woeIdUser == null) ? 0 : woeIdUser.hashCode());
        result = prime * result + ((woePositionLocated == null) ? 0 : woePositionLocated.hashCode());
        result = prime * result + ((woePositionParameter == null) ? 0 : woePositionParameter.hashCode());
        //
        return result;
    }//method()
    
}//class
