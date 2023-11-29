package _untouchable_.common;


import java.util.*;

import static _untouchable_.common.ErrorType.*;


@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2013/06/07",
    currentRevision = "0.94",
    lastModified    = "2013/06/07",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
//the WHOLE CLASS and ALL of its ELEMENTS are friendly (or private) on purpose (!)
class Analyzer {
    
    /*
     * #########################################################################
     * ###
     * ### "MAGIC NUMBERs" - das muss besser werden   __???__<130601>
     * ###
     */
    @ChunkPreamble ( lastModified="2013/06/01", lastModifiedBy="Michael Schäfers" )
    final long DawdleThreshold4Warning =   500_000;                             // ..[ns] => 500[us]
    @ChunkPreamble ( lastModified="2013/06/01", lastModifiedBy="Michael Schäfers" )
    final long DawdleThreshold4Error   = 4_000_000;                             // ..[ns] =>   4[ms]
    
    
    
    
    
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    static synchronized Analyzer createSingleInstanceOfAnalyzer( final List<Event> eventList ){
        if (instance == null) {
            instance = new Analyzer( eventList );
        }else{
            throw new IllegalStateException( "Only single configuration allowed and supported" );
        }//if
        return instance;
    }//createSingleInstanceOfAnalyzer()
    
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    private static Analyzer instance = null;
    
    
    
    
    
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    private Analyzer ( final List<Event> eventList ){
        this.eventList = eventList;
    }//AV_Analyzer()
    
    
    
    
    
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    private final ErrorHandler errHandler = new ErrorHandler();
    
    
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    private final List<Event> eventList;
    
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    private boolean smurfIdValuesUnchecked = true;
    //
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    private boolean woeIdValuesUnchecked = true;
    //
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    private boolean locationIdValuesUnchecked = true;
    
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    private boolean numberOfSmurfsUnchecked = true;
    //
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    private boolean numberOfWOEsUnchecked = true;
    //
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    private boolean numberOfLocationsUnchecked = true;
    
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    private boolean smurfIdConsistencyUnchecked = true;
    //
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    private boolean woeIdConsistencyUnchecked = true;
    
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    private boolean smurfActivityCycleUnchecked = true;
    //
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    private boolean woeActivityCycleUnchecked = true;
    
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    private boolean smurfScheduleFollowedUnchecked = true;
    //
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    private boolean woeRouteUnchecked = true;
    
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    private boolean smurfBehaviorDuringStopOverUnchecked = true;
    //
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    private boolean woeBehaviorDuringStopOverUnchecked = true;
    
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    private boolean generalBehaviorUnchecked = true;
    
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    private boolean lineBasedStuffUnchecked = true;
    //
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    private boolean cycleBasedStuffUnchecked = true;
    
    
    //--------------------------------------------------------------------------
    
    // check if all user defined smurf IDs are positive
    // this was requested by excercise - negative values are used as marks (e.g. "null")
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    final boolean chkIfUserSmurfIDsPositive(){
        for ( Event ev : eventList ){
            if ( ev.smurfIdInternal != null  &&  ev.smurfIdUser < 0 ){          // smurf related && smurfIdUser negative
                errHandler.reportError( UNEXPECTED_ID_SMURF, ev );
                return false;
            }//for
        }//for
        //
        smurfIdValuesUnchecked = false;
        return true;
        /* Memo:
         * Jetzt ist sicher, dass
         * -> die Smurf IDs im erwarteten Rahmen bzw. positive bzw. 0,..,n sind.
         */                                                                     // reviewed 130528
    }//chkIfUserSmurfIDsPositive()
    
    // check if all user defined WOE IDs are positive
    // this was requested by excercise - negative values are used as marks (e.g. "null")
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    final boolean chkIfUserWoeIDsPositive(){
        for ( Event ev : eventList ){
            if ( ev.woeIdInternal != null  &&  ev.woeIdUser < 0 ){              // woe related && ev.woeIdUser negative
                errHandler.reportError( UNEXPECTED_ID_WOE, ev );
                return false;
            }//for
        }//for
        //
        woeIdValuesUnchecked = false;
        return true;
        /* Memo:
         * Jetzt ist sicher, dass
         * -> die WOE IDs im erwarteten Rahmen bzw. positive bzw. 0,..,n sind.
         */                                                                     // reviewed 130528
    }//chkIfUserWoeIDsPositive()                                                
    
    // check if all user defined location IDs are positive
    // this was requested by excercise - negative values are used as marks (e.g. "null")
    @ChunkPreamble ( lastModified="2013/05/27", lastModifiedBy="Michael Schäfers" )
    final boolean chkIfUserLocationIDsPositive(){
        for ( Event ev : eventList ){
            /*
             * 130527: locate() removed
            if ( ev.smurfPositionLocated != null && ev.smurfPositionLocated < -1 ){
                errHandler.reportError( UNEXPECTED_POSITION_SMURF, ev );
                return false;
            }//if
            */
            if ( ev.smurfPositionParameter != null && ev.smurfPositionParameter < -1 ){
                errHandler.reportError( UNEXPECTED_POSITION_PARAMETER_SMURF, ev );
                return false;
            }//if
            /*
             * 130527: locate() removed
            if ( ev.woePositionLocated != null && ev.woePositionLocated < -1 ){
                errHandler.reportError( UNEXPECTED_POSITION_WOE, ev );
                return false;
            }//if
            */
            if ( ev.woePositionParameter != null && ev.woePositionParameter < -1 ){
                errHandler.reportError( UNEXPECTED_POSITION_PARAMETER_WOE, ev );
                return false;
            }//if
        }//for
        //
        locationIdValuesUnchecked = false;
        return true;
        /* Memo:
         * Jetzt ist sicher, dass
         * -> die Location/Position IDs im erwarteten Rahmen bzw. positive bzw. 0,..,n sind.
         * Bemerkung: Der Test von located() bzw. xxxPositionLocated macht gegenwärtig(130528) KEINEN Sinn, da located() nicht verwendet wird.
         */                                                                     // reviewed 130528
    }//chkIfUserLocationIDsPositive()                                           reviewed 130528
    
    
    //--------------------------------------------------------------------------
    
    
    // check number of smurfs
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" )
    final boolean chkSmurfNo(){
        final Global global = Global.getData();
        final int expectedNumberOfSmurfs  =  global.fnos;
        //
        // check number of generated smurf objects resp. internal smurf count
        if ( expectedNumberOfSmurfs != CommonSmurf.smurfCnt.get() ){
            errHandler.reportError( UNEXPECTED_NUMBER_OF_SMURF );
            return false;
        }//if
        //
        // check number of found user defined smurf IDs
        if ( smurfIdValuesUnchecked ) throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if values of smurf IDs are valid" );
        final Set<Integer> setOfSmurfIDs = new HashSet<Integer>();
        for ( Event ev : eventList ){
            if ( ev.smurfIdUser != null )  setOfSmurfIDs.add( ev.smurfIdUser );
        }//for
        if ( expectedNumberOfSmurfs != setOfSmurfIDs.size() ){
            errHandler.reportError( UNEXPECTED_NUMBER_OF_ID_SMURF, setOfSmurfIDs );
            return false;
        }//if
        //
        numberOfSmurfsUnchecked = false;
        return true;
        /* Memo:
         * Jetzt ist sicher, dass
         * -> die Anzahl der erzeugten Smurf,
         * -> die Anzahl der unterschiedlichen (User generierten) Smurf IDs
         * stimmt.
         */                                                                     // reviewed 130528
    }//chkSmurfNo()
    
    
    // check number of WOEs
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" )
    final boolean chkWoeNo(){
        final Global global = Global.getData();
        final int expectedNumberOfWOEs  =  global.fnow;
        //
        // check number of generated WOE objects resp. internal WOE count
        if ( expectedNumberOfWOEs != CommonWOE.woeCnt.get() ){
            errHandler.reportError( UNEXPECTED_NUMBER_OF_WOE );
            return false;
        }//if
        //
        // check number of found user defined WOE IDs
        if ( woeIdValuesUnchecked ) throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if values of WOE IDs are valid" );
        final Set<Integer> setOfWoeIDs = new HashSet<Integer>();
        for ( Event ev : eventList ){
            if ( ev.woeIdUser != null )  setOfWoeIDs.add( ev.woeIdUser );
        }//for
        if ( expectedNumberOfWOEs != setOfWoeIDs.size() ){
            errHandler.reportError( UNEXPECTED_NUMBER_OF_ID_WOE, setOfWoeIDs );
            return false;
        }//if
        //
        numberOfWOEsUnchecked = false;
        return true;
        /* Memo:
         * Jetzt ist sicher, dass
         * -> die Anzahl der erzeugten WOE,
         * -> die Anzahl der unterschiedlichen (User generierten) WOE IDs
         * stimmt.
         */                                                                     // reviewed 130528
    }//chkWoeNo()
    
    
    // check number of locations
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" )
    final boolean chkLocationNo(){
        final Global global = Global.getData();
        final int expectedNumberOfLocations  =  global.fnol;
        //
        // check number of found user defined location IDs
        if ( locationIdValuesUnchecked ) throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if values of location IDs are valid" );
        final Set<Integer> setOfLocations = new HashSet<Integer>();
        for ( Event ev : eventList ){
            if ( ev.smurfPositionLocated   != null )  setOfLocations.add( ev.smurfPositionLocated );     // __???__<130527> removing locate()  currently NOT needed - dead code
            if ( ev.smurfPositionParameter != null )  setOfLocations.add( ev.smurfPositionParameter );
            if ( ev.woePositionLocated     != null )  setOfLocations.add( ev.woePositionLocated );       // __???__<130527> removing locate()  currently NOT needed - dead code
            if ( ev.woePositionParameter   != null )  setOfLocations.add( ev.woePositionParameter );
        }//for
        int numberOfLocations = -1;
        if ( setOfLocations.contains( -1 ) ){
            numberOfLocations = setOfLocations.size() - 1;                      // -1 is expected to be a mark (and not a location)
        }else{
            numberOfLocations = setOfLocations.size();
        }//if
        if ( numberOfLocations != expectedNumberOfLocations ){
            errHandler.reportError( UNEXPECTED_NUMBER_OF_LOCATION, setOfLocations );
            return false;
        }//if
        //
        numberOfLocationsUnchecked = false;
        return true;
        /* Memo:
         * Jetzt ist sicher, dass
         * -> die Anzahl der unterschiedlichen (als Parameter uebergebenen) Position/Location IDs
         * stimmt.
         */                                                                     // reviewed 130528
    }//chkLocationNo()
    
    
    //--------------------------------------------------------------------------
    
    
    // check smurf ID consistency - does same user defined smurf ID always belong to same internal smurf ID ?
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" )
    final boolean chkSmurfIdConsistency(){
        if ( smurfIdValuesUnchecked )  throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if values of smurf IDs are valid" );
        if ( numberOfSmurfsUnchecked ) throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if number of found smurfs valid" );
        
        final Global global = Global.getData();
        final int expectedNumberOfSmurfs  =  global.fnos;
        final EvTab_IDxTime[] smurfIdMap = new EvTab_IDxTime[expectedNumberOfSmurfs];
      //for ( int si=smurfIdMap.length; --si>=0; smurfIdMap[si] = null );
        
        for ( Event ev : eventList ){
            if ( ev.smurfIdInternal != null ){
                if ( smurfIdMap[ev.smurfIdInternal] == null ){
                    // 1st respectively initial entry
                    smurfIdMap[ev.smurfIdInternal] = new EvTab_IDxTime( ev.smurfIdUser, ev.time );
                }else{
                    // check consistency
                    if ( smurfIdMap[ev.smurfIdInternal].id != ev.smurfIdUser ){
                        errHandler.reportError( FAULTY_VISIBILITY_SMURF, ev, smurfIdMap );
                        return false;
                    }else{
                        // update entry
                        smurfIdMap[ev.smurfIdInternal].time = ev.time;
                    }//if
                }//if
            }//if
        }//for
        //
        smurfIdConsistencyUnchecked = false;
        return true;
        /* Memo:
         * Jetzt ist sicher, dass
         * -> derselbe Smurf vom User immer mit derselben (User-)Smurf-ID angesprochen wird.
         * Bemerkung: Im Fehlerfall liegt der Verdacht eines Sichtbarkeitproblems nahe.)
         */                                                                     // reviewed 130528
    }//chkSmurfIdConsistency()
    
    
    // check WOE ID consistency - does same user defined WOE ID always belong to same internal WOE ID ?
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" )
    final boolean chkWoeIdConsistency(){
        if ( woeIdValuesUnchecked )  throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if values of WOE IDs are valid" );
        if ( numberOfWOEsUnchecked ) throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if number of found WOEs valid" );
        
        final Global global = Global.getData();
        final int expectedNumberOfWOEs  =  global.fnow;
        final EvTab_IDxTime[] woeIdMap = new EvTab_IDxTime[expectedNumberOfWOEs];
      //for ( int wi=woeIdMap.length; --wi>=0; woeIdMap[wi] = null );
        
        for ( Event ev : eventList ){
            if ( ev.woeIdInternal != null ){
                if ( woeIdMap[ev.woeIdInternal] == null ){
                    // 1st respectively initial entry
                    woeIdMap[ev.woeIdInternal] = new EvTab_IDxTime( ev.woeIdUser, ev.time );
                }else{
                    // check consistency
                    if ( woeIdMap[ev.woeIdInternal].id != ev.woeIdUser ){
                        errHandler.reportError( FAULTY_VISIBILITY_WOE, ev, woeIdMap );
                        return false;
                    }else{
                        // update entry
                        woeIdMap[ev.woeIdInternal].time = ev.time;
                    }//if
                }//if
            }//if
        }//for
        //
        woeIdConsistencyUnchecked = false;
        return true;
        /* Memo:
         * Jetzt ist sicher, dass
         * -> dasselbe WOE vom User immer mit derselben (User-)WOE-ID angesprochen wird.
         * Bemerkung: Im Fehlerfall liegt der Verdacht eines Sichtbarkeitproblems nahe.)
         */                                                                     // reviewed 130528
    }//chkWoeIdConsistency()
    
    
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" )
    final boolean chkSmurfsLastDeed(){ // ??? check
        if ( smurfIdValuesUnchecked )      throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if values of smurf IDs are valid" );
        if ( numberOfSmurfsUnchecked )     throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if number of found smurfs valid" );
        if ( smurfIdConsistencyUnchecked ) throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if found smurf IDs are concistent" );
        
        final Global global = Global.getData();
        final int expectedNumberOfSmurfs  =  global.fnos;
        final EvTab_IDxTime[] smurfTab = new EvTab_IDxTime[expectedNumberOfSmurfs];
      //for ( int si=smurfTab.length; --si>=0; smurfTab[si] = null );
        
        for ( Event ev : eventList ){
            if ( ev.smurfIdInternal != null ){
                assert ev.et != EventType.ERR : "UUUUPPPS - sollte nicht passieren - klaer mich"; // <---------------                
                if ( ev.et != EventType.ERR ){
                    if ( ev.et == EventType.SxxLD  &&  smurfTab[ev.smurfIdInternal] == null ){
                        smurfTab[ev.smurfIdInternal] = new EvTab_IDxTime( ev.smurfIdUser, ev.time );
                    }else if ( smurfTab[ev.smurfIdInternal] != null ){
                        errHandler.reportError( ACTIVE_AFTER_LASTDEED_SMURF, ev, smurfTab );
                        return false;
                    }//if
                }//if
            }//if
        }//for
        //
        return true;
        /* Memo:
         * Jetzt ist sicher, dass
         * -> lastDeed() wirklich die letzte Aktion des Smurf war.
         */                                                                     // reviewed 130528
    }//chkSmurfsLastDeed()
    
    
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" )
    final boolean chkWOEsLastDeed(){
        if ( woeIdValuesUnchecked )      throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if values of WOE IDs are valid" );
        if ( numberOfWOEsUnchecked )     throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if number of found WOEs valid" );
        if ( woeIdConsistencyUnchecked ) throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if found WOE IDs are concistent" );
        
        final Global global = Global.getData();
        final int expectedNumberOfWOEs  =  global.fnow;
        final EvTab_IDxTime[] woeTab = new EvTab_IDxTime[expectedNumberOfWOEs];
      //for ( int si=woeTab.length; --si>=0; woeTab[si] = null );
        
        for ( Event ev : eventList ){
            if ( ev.woeIdInternal != null ){
                assert ev.et != EventType.ERR : "UUUUPPPS - sollte nicht passieren - klaer mich"; // <---------------
                if ( ev.et != EventType.ERR ){
                    if ( ev.et == EventType.SxxLD  &&  woeTab[ev.woeIdInternal] == null ){
                        woeTab[ev.woeIdInternal] = new EvTab_IDxTime( ev.woeIdUser, ev.time );
                    }else if ( woeTab[ev.woeIdInternal] != null ){
                        errHandler.reportError( ACTIVE_AFTER_LASTDEED_WOE, ev, woeTab );
                        return false;
                    }//if
                }//if
            }//if
        }//for
        //
        return true;
        /* Memo:
         * Jetzt ist sicher, dass
         * -> lastDeed() wirklich die letzte Aktion des WOE war.
         */                                                                     // reviewed 130528
    }//chkWOEsLastDeed()
    
    //--------------------------------------------------------------------------
    
    
    // check general activity cycle of smurf
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" )
    final boolean chkSmurfActivityCycle(){
        if ( smurfIdValuesUnchecked )      throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if values of smurf IDs are valid" );
        if ( numberOfSmurfsUnchecked )     throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if number of found smurfs valid" );
        if ( smurfIdConsistencyUnchecked ) throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if found smurf IDs are concistent" );
        
        final Global global = Global.getData();
        final int expectedNumberOfSmurfs  =  global.fnos;
        final Event[] eta = new Event[expectedNumberOfSmurfs];
      //for ( int idx=eta.length; --idx>=0; )  eta[idx] = null;
        
        for ( Event ev : eventList ){
            if ( null!=ev.smurfIdInternal ){
                // event is caused by smurf activity, since ev.smurfIdInternal set
                //
                // check if current smurf activity is valid (after last (reported) smurf activity)
                if ( ! ev.isSmurfActivityValid( eta[ev.smurfIdInternal] ) ){
                    errHandler.reportError( FAULTY_BEHAVIOR_SMURF, ev, eta );
                    return false;
                }//if
                eta[ev.smurfIdInternal] = ev;                                   // store (current) valid-action of WOE as last-action for next check
            }//if
        }//for
        
        smurfActivityCycleUnchecked = false;
        return true;
        /* Memo:
         * Jetzt ist sicher, dass
         * -> der Smurf die Aktionen in der rechten Reihenfolge ausführt.
         */                                                                     // reviewed 130528
    }//chkSmurfActivityCycle()
    
    
    // check general activity cycle of WOE
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" )
    final boolean chkWoeActivityCycle(){
        if ( woeIdValuesUnchecked )      throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if values of WOE IDs are valid" );
        if ( numberOfWOEsUnchecked )     throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if number of found WOEs valid" );
        if ( woeIdConsistencyUnchecked ) throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if found WOE IDs are concistent" );
        
        final Global global = Global.getData();
        final int expectedNumberOfWOEs  =  global.fnow;
        final Event[] eta = new Event[expectedNumberOfWOEs];
      //for ( int idx=eta.length; --idx>=0; )  eta[idx] = null;
        
        for ( final Event ev : eventList ){
            if ( null!=ev.woeIdInternal ){
                // event is caused by WOE activity, since ev.woeIdInternal set
                //
                // check if current WOE activity is valid (after last (reported) WOE activity)
                if ( ev.isActivityCausedByWOE() ){
                    if ( ! ev.isWoeActivityValid( eta[ev.woeIdInternal] ) ){
                        errHandler.reportError( FAULTY_BEHAVIOR_WOE, ev, eta );
                        return false;
                    }//if
                    eta[ev.woeIdInternal] = ev;                                 // store (current) valid-action of WOE as last-action for next check
                }//if
            }//if
        }//for
        //
        woeActivityCycleUnchecked = false;
        return true;
        /* Memo:
         * Jetzt ist sicher, dass
         * -> das WOE die Aktionen in der rechten Reihenfolge ausführt.
         */                                                                     // reviewed 130528
    }//chkWoeActivityCycle()
    
    
    //--------------------------------------------------------------------------
    
    
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" )
    final boolean chkIfSmurfFollowsHisSchedule(){
        if ( smurfIdValuesUnchecked )      throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if values of smurf IDs are valid" );
        if ( numberOfSmurfsUnchecked )     throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if number of found smurfs valid" );
        if ( smurfIdConsistencyUnchecked ) throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if found smurf IDs are consistent" );
        if ( smurfActivityCycleUnchecked ) throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if smurf activity cycle valid" );
        if ( numberOfLocationsUnchecked )  throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if number of locations valid" );
        
        class Data {
            Data( final CommonSSI[] smurfSchedule, final int smurfScheduleIndex ){ scheduleData=smurfSchedule; scheduleIndx=smurfScheduleIndex; }
            Data(){ this( null, -1 ); }                                         // initialize with values that will cause exception if not overwritten
            CommonSSI[] scheduleData;
            int scheduleIndx;
        }//class Data
        
        final Global global = Global.getData();
        
        // for safety's sake
        for ( RawSmurf smurf : Global.av_smurfList ){                           // __???__<130529> check error message -> internal error ?
            if ( smurf.schedule == null )               throw new IllegalStateException( "ERROR: smurf (internal ID:" + smurf.internalSmurfID + ") has NO schedule" );
            if ( smurf.schedule.scheduleData == null )  throw new IllegalStateException( "ERROR: smurf (internal ID:" + smurf.internalSmurfID + ") has NO schedule data" );
        }//for
        
        final int expectedNumberOfSmurfs  =  global.fnos;
        final Data[] data = new Data[expectedNumberOfSmurfs];
        for ( int si=data.length; --si>=0; data[si] = new Data() );
        for ( RawSmurf smurf : Global.av_smurfList ){
            data[smurf.internalSmurfID].scheduleData = smurf.schedule.scheduleData;
            data[smurf.internalSmurfID].scheduleIndx = data[smurf.internalSmurfID].scheduleData.length-1;
        }//for
        
        for ( Event ev : eventList ){
            if (    ev.et==EventType.S1BDS
                ||  ev.et==EventType.S2EDS
            ){
                // action of smurf with defined position (at location resp. not inside WOE) respectively position parameter containing this information
                //
                final CommonSSI[] schedule = data[ev.smurfIdInternal].scheduleData;
                final int indx = data[ev.smurfIdInternal].scheduleIndx;
                /*                                                               __???__<130527> removing locate()
                if ( null!=ev.smurfPositionLocated  &&  ev.smurfPositionLocated != schedule[indx].getPlanedPosition() ){
                    errHandler.reportError( FAULTY_BEHAVIOR_SMURF_SCHEDULE, ev, schedule[indx].getPlanedPosition() );
                    return false;
                }//if
                */
                if ( null!=ev.smurfPositionParameter  &&  ev.smurfPositionParameter != schedule[indx].getPlanedPosition() ){
                    errHandler.reportError( FAULTY_BEHAVIOR_SMURF_SCHEDULE, ev, schedule[indx].getPlanedPosition() );            // __???__<130527> check message
                    return false;
                }//if
            }//if
            if ( ev.et == EventType.S4AIW ){
                // smurf is in WOE now and "next time" he shall be at other (resp. next scheduled) position
                //
                data[ev.smurfIdInternal].scheduleIndx--;                        // next time check next scheduled position
            }//if
        }//for
        // now it is known that:
        // -> smurf follows his schedule
        // -> smurf does NOT change location during his stay
        // -> smurf is never at places he should not be
        //
        smurfScheduleFollowedUnchecked = false;
        return true;
        /* Memo:
         * Jetzt ist sicher, dass
         * -> der Smurf seinem Schedule folgt
         * -> der Smurf seine Position während "eines Aufenthalts" NICHT verändert,
         * -> und daher NIEMALS an Plätzen ist, wo er nicht sein darf.
         */                                                                     // reviewed 130528        
    }//chkIfSmurfFollowsHisSchedule()
    
    
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" )
    final boolean chkWoeRoute( final int[][] route ){
        if ( woeIdValuesUnchecked )       throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if values of WOE IDs are valid" );
        if ( numberOfWOEsUnchecked )      throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if number of found WOEs valid" );
        if ( woeIdConsistencyUnchecked )  throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if found WOE IDs are concistent" );
        if ( woeActivityCycleUnchecked )  throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if WOE activity cycle valid" );
        if ( numberOfLocationsUnchecked ) throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if number of locations valid" );
        
        final Global global = Global.getData();
        final int expectedNumberOfWOEs  =  global.fnow;
        if ( route != null ){
            // there are some expected routes -> hence, they can be checked
            //
            // for safety's sake
            if ( expectedNumberOfWOEs != route.length         )      throw new IllegalStateException( "UUUPPS - This might be an INTERNAL ERROR - parameter invalid" ); // __???__<130529> check message
            if ( expectedNumberOfWOEs != Global.av_woeList.size() )  throw new IllegalStateException( "UUUPPS - This might be an INTERNAL ERROR - parameter invalid" ); // __???__<130529> check message
        }//if
        System.err.printf( "\n" );
        
        // check for cycles
        final Map<Integer,List<Integer>> trace = new HashMap<Integer,List<Integer>>();
        for ( RawWOE woe : Global.av_woeList ){
            
            // generate trace of WOE
            List<Integer> curTrace = new ArrayList<Integer>();
            for ( Event ev : woe.eventList ){
                if ( ev.et == EventType.W5WP1 ){
                    // either W5WP1 or W6WP2 (or W3AAP, W4AEN, W7FEN, W8VFP) define "stop-over-postion" of WOE - selecting W5WP1 seems best choice
                    //
                    curTrace.add( ev.woePositionParameter );                    // add (stop-over)-position to trace
                }//if
            }//for
            
            // check trace for cycles
            boolean cycleFound = false;                                         // NO cycle found, yet
            int clc;                                                            // Cycle_Length_Candidate
            final int clcBorderValue = curTrace.size() >> 1;
            chkCLCloop:
            for ( clc=1; clc<=clcBorderValue; clc++ ){                          // test CycleLengthCandidates
                for ( int i=curTrace.size()-1-clc; --i>0; ){                    // "!=", since Integer values are constants
                    if (! curTrace.get(i).equals( curTrace.get(i+clc) )){
                        // candidate failed
                        //
                        continue chkCLCloop;                                    // try next (CycleLength)Candidate
                    }//if
                }//for
                // candidate passed test => (smallest) cycle found
                
                cycleFound = true;
                break chkCLCloop;
            }//chkCLCloop-for
            
            if ( cycleFound ){
                trace.put( woe.internalWoeID, curTrace );
                System.err.printf(
                     "NOTE:  WOE internal_ID:%d  route_cycle :  ",
                     woe.internalWoeID
                );
                for ( int i=0; i<clc; i++ ){
                    System.err.printf( "%d->",  curTrace.get(i) );
                }//for
                System.err.printf(
                     "... ;  full_cycles:%d ;  cycle_length:%d ;  trace_length:%d\n",
                     curTrace.size()/clc,
                     clc,
                     curTrace.size()
                );
                System.err.flush();
                //
                for ( int i=curTrace.size(); --i>=clc; )  curTrace.remove(i);   // remove all entries after 1st cycle
            }else{
                trace.put( woe.internalWoeID, null );
                System.err.printf(
                    "NOTE:  WOE internal_ID:%d  has NO route cycles (with length<=%d)\n",
                    woe.internalWoeID,
                    clcBorderValue
                );
                System.err.flush();
            }//if
        }//for
        System.err.printf( "\n" );
        
        if ( null!=route ){
            if ( expectedNumberOfWOEs != trace.size() )  throw new IllegalStateException( "UUUPPS - This might be an INTERNAL ERROR" );
            
            boolean[][] checkField = new boolean[trace.size()][];
            for ( int wr=trace.size(); --wr>=0; ){                              // WOE route
                checkField[wr] = new boolean[route.length];
                for ( int er=route.length; --er>=0; ){                          // Expected Route
                    checkField[wr][er] = false;
                }//for
            }//for
            
            for ( int iwi=0; iwi<expectedNumberOfWOEs; iwi++ ){                 // Internal WOE ID
                List<Integer> curTrace = trace.get(iwi);                        // CURrent TRACE of current WOE
                
                System.err.printf(
                    "NOTE:  WOE internal_ID:%d  matches expected routes (#) :",
                    iwi
                );
                chkAgainstExpectedRouteLoop:
                for ( int er=0; er<route.length; er++ ){
                    if ( curTrace!=null && curTrace.size()==route[er].length ){
                        // both cycles have same length -> hence, it makes sense to compare them
                        for ( int i=curTrace.size(); --i>=0; ){
                            if ( curTrace.get(i) != route[er][i] ){
                                // routes differ
                                //
                                continue chkAgainstExpectedRouteLoop;           // move on -> check against next expected route
                            }//if
                        }//for
                        // both routes are equal
                        //
                        checkField[iwi][er] = true;
                        System.err.printf( "%4d", er );
                    }//if
                }//for
                System.err.printf( "\n" );
                System.err.flush();
            }//for
            System.err.printf( "\n" );
            
            for ( int er=0; er<route.length; er++ ){
                System.err.printf(
                    "NOTE:  expected route #%d is matched by WOE internal_ID:",
                    er
                );
                for ( int iwi=0; iwi<expectedNumberOfWOEs; iwi++ ){
                    if ( checkField[iwi][er] ){
                        System.err.printf( "%4d",  iwi );
                    }//if
                }//for
                System.err.printf( "\n" );
                System.err.flush();                
            }//for
            System.err.printf( "\n" );
            
            loop:
            for ( int er=0; er<route.length; er++ ){
                for ( int iwi=0; iwi<expectedNumberOfWOEs; iwi++ ){
                    if ( checkField[iwi][er] ){
                        continue loop;
                    }//if
                }//for
                System.err.printf( "ERROR: expected route #%d (", er );
                for ( int bs : route[er] )  System.err.printf( "->%d",  bs );
                System.err.printf( "->...) was NOT matched by any WOE\n" );
                System.err.flush();
                return false;
            }//for
                
        }//if
        // (only!) if route was given  it is known that:
        // -> WOE follows given route
        //
        woeRouteUnchecked = false;
        return true;
        /* Memo:
         * Sofern Routen für die WOE vorgegeben wurden, ist jetzt sicher, dass
         * -> das die WOE diese vorgegebenen Routen befolgen,
         * -> und daher jedes WOE NIEMALS an Plätzen ist, wo es nicht sein darf.
         */                                                                     // reviewed 130528        
    }//chkWoeRoute()
    
    
    //--------------------------------------------------------------------------
    
    
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" )
    final boolean chkSmurfBehaviorDuringStopOver(){
        if ( smurfIdValuesUnchecked )      throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if values of smurf IDs are valid" );
        if ( numberOfSmurfsUnchecked )     throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if number of found smurfs valid" );
        if ( smurfIdConsistencyUnchecked ) throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if found smurf IDs are concistent" );
        if ( smurfActivityCycleUnchecked ) throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if smurf activity cycle valid" );
        
        final Global global = Global.getData();
        final int expectedNumberOfWOEs  =  global.fnow;
        for ( int isi=0; isi<expectedNumberOfWOEs; isi++ ){                     // Internal Smurf ID
            int lastPosition = -1;
            for ( Event ev : eventList ){
                if ( ev.smurfIdInternal != null  &&  ev.smurfIdInternal == isi ){
                    
                    //1.)vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
                    if ( ev.et == EventType.S1BDS ){
                        lastPosition = ev.smurfPositionParameter;
                        
                    //2.)vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
                    }else if ( ev.et == EventType.S2EDS ){                      // das macht gegenwärtig keinen Sinn bzw. hat keinen Nutzen, da hier (gegenwärtig=130529) keine Fehler auftreten k�nen
                        if ( ev.smurfPositionParameter != lastPosition ){
                            errHandler.reportError( INCONSISTENCY_LOCATION_SMURF_S2EDS, ev, lastPosition );
                            return false;
                        }//if
                    
                    }//if
                    
                }//if
            }//for
        }//for
        //
        smurfBehaviorDuringStopOverUnchecked = false;
        return true;
        /* Memo:
         * Jetzt ist sicher, dass
         * -> der Smurf seine Position während "eines Aufenthalts" NICHT verändert.
         * Bemerkung:
         * -> Dies wurde bereits/auch mit chkIfSmurfFollowsHisSchedule() geprüft.
         * -> Jedoch so "Symmetrie" zu chkSmurfBehaviorDuringStopOver()             __???__<130530> Methode könnte entfernt werden
         */                                                                     // reviewed 130528        
    }//chkSmurfBehaviorDuringStopOver()
    
    
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" )
    final boolean chkWoeBehaviorDuringStopOver(){
        if ( woeIdValuesUnchecked )       throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if values of WOE IDs are valid" );
        if ( numberOfWOEsUnchecked )      throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if number of found WOEs valid" );
        if ( woeIdConsistencyUnchecked )  throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if found WOE IDs are concistent" );
        if ( woeActivityCycleUnchecked )  throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if WOE activity cycle valid" );
        
        final Global global = Global.getData();
        final int expectedNumberOfWOEs  =  global.fnow;
        for ( int iwi=0; iwi<expectedNumberOfWOEs; iwi++ ){                     // Internal WOE ID
            int currentRespLastReportedPosition = -1;                           // initialize as "somewhere" moving
            int lastReportedStopOverPosition    = -1;                           //
            int numberOfReportedPositionsSinceLastStop = 0;                     //
            for ( final Event ev : eventList ){
                if ( null != ev.woeIdInternal  &&  ev.woeIdInternal == iwi ){
                    if(( null != ev.woePositionParameter )  ||  ( ev.et != EventType.W4AEN  &&  ev.et != EventType.W7FEN )){
                        switch( ev.et ){
                            
                            // check ["ev.woePositionParameter != -1" => ERROR] in RawWOE.takeTimeForMotionTo() 
                            case W1MO1:                                         // takeTimeForMotionTo()-"START" => target defines position that will current stop position
                                if( currentRespLastReportedPosition != ev.woePositionParameter ){
                                    // position has changed somehow
                                    //
                                    if( -1 != ev.woePositionParameter ){
                                        // new position has to be destination
                                        //
                                        if( 1 <= numberOfReportedPositionsSinceLastStop ){
                                            // this at least second final destination reported -> hence, error
                                            //
                                            numberOfReportedPositionsSinceLastStop++;
                                            errHandler.reportError( INCONSISTENCY_LOCATION_WOE_W1MO1, ev, currentRespLastReportedPosition );
                                            return false;
                                        }//if
                                        numberOfReportedPositionsSinceLastStop = 1;
                                        //
                                        if( lastReportedStopOverPosition == ev.woePositionParameter ){
                                            // position has changed somehow, but stopover position is still the same (???)   ->  "x"->"-1"->"x"
                                            //
                                            errHandler.reportError( INCONSISTENCY_LOCATION_WOE_W1MO1, ev, currentRespLastReportedPosition );
                                            return false;
                                        }//if
                                        lastReportedStopOverPosition = ev.woePositionParameter;
                                    }else{
                                        // NO (re-)action required - "-1" itself is "harmless", possible problems resulting out of this are handled elsewhere
                                        // E.g.: "x"->"-1"->"x"            results in lastReportedStopOverPosition == ev.woePositionParameter
                                        // E.g.: "x"->"-1"->"x"->"-1"->"x" results in numberOfReportedPositionsSinceLastStop<-2 anyway
                                    }//if
                                    currentRespLastReportedPosition = ev.woePositionParameter;
                                }else{
                                    // NO (re-)action required - position has NOT changed
                                }//if
                            break;
                            
                            // check ["ev.woePositionParameter != -1" => ERROR] in RawWOE.takeTimeForMotionTo() 
                            case W2MO2:                                         // takeTimeForMotionTo()-"END" => target has to be unchanged
                                if( currentRespLastReportedPosition != ev.woePositionParameter ){
                                    // position has changed somehow
                                    //
                                    if( -1 != ev.woePositionParameter ){
                                        // new position has to be destination
                                        //
                                        if( 1 <= numberOfReportedPositionsSinceLastStop ){
                                            // this at least second final destination reported -> hence, error
                                            //
                                            numberOfReportedPositionsSinceLastStop++;
                                            errHandler.reportError( INCONSISTENCY_LOCATION_WOE_W2MO2, ev, currentRespLastReportedPosition );
                                            return false;
                                        }//if
                                        numberOfReportedPositionsSinceLastStop = 1;
                                        //
                                        if( lastReportedStopOverPosition == ev.woePositionParameter ){
                                            // position has changed somehow, but stopover position is still the same (???)   ->  "x"->"-1"->"x"
                                            //
                                            errHandler.reportError( INCONSISTENCY_LOCATION_WOE_W2MO2, ev, currentRespLastReportedPosition );
                                            return false;
                                        }//if
                                        lastReportedStopOverPosition = ev.woePositionParameter;
                                    }else{
                                        // NO (re-)action required - "-1" itself is "harmless", possible problems resulting out of this are handled elsewhere
                                        // E.g.: "x"->"-1"->"x"            results in lastReportedStopOverPosition == ev.woePositionParameter
                                        // E.g.: "x"->"-1"->"x"->"-1"->"x" results in numberOfReportedPositionsSinceLastStop<-2 anyway
                                    }//if
                                    currentRespLastReportedPosition = ev.woePositionParameter;
                                }else{
                                    // NO (re-)action required - position has NOT changed
                                }//if
                            break;
                            
                            // check ["ev.woePositionParameter != -1" => ERROR] in RawWOE.appear() 
                            case W3AAP:                                         // appear() => target has (to be) become current stop position
                                if( currentRespLastReportedPosition != ev.woePositionParameter ){
                                    // position has changed somehow
                                    //
                                    if( -1 != ev.woePositionParameter ){
                                        // new position has to be destination
                                        //
                                        if( 1 <= numberOfReportedPositionsSinceLastStop ){
                                            // this at least second final destination reported -> hence, error
                                            //
                                            numberOfReportedPositionsSinceLastStop++;
                                            errHandler.reportError( INCONSISTENCY_LOCATION_WOE_W3AAP, ev, currentRespLastReportedPosition );
                                            return false;
                                        }//if
                                        numberOfReportedPositionsSinceLastStop = 1;
                                        //
                                        if( lastReportedStopOverPosition == ev.woePositionParameter ){
                                            // position has changed somehow, but stopover position is still the same (???)   ->  "x"->"-1"->"x"
                                            //
                                            errHandler.reportError( INCONSISTENCY_LOCATION_WOE_W3AAP, ev, currentRespLastReportedPosition );
                                            return false;
                                        }//if
                                        lastReportedStopOverPosition = ev.woePositionParameter;
                                    }else{
                                        // NO (re-)action required - "-1" itself is "harmless", possible problems resulting out of this are handled elsewhere
                                        // E.g.: "x"->"-1"->"x"            results in lastReportedStopOverPosition == ev.woePositionParameter
                                        // E.g.: "x"->"-1"->"x"->"-1"->"x" results in numberOfReportedPositionsSinceLastStop<-2 anyway
                                    }//if
                                    currentRespLastReportedPosition = ev.woePositionParameter;
                                }else{
                                    // NO (re-)action required - position has NOT changed
                                }//if
                            break;
                            
                            case W4AEN:
                                // currently NO checks required as result of RawWOE.allowEntrance()
                            break;
                            
                            // check ["ev.woePositionParameter != -1" => ERROR] in RawWOE.takeTimeForStopover() 
                            case W5WP1:                                         // takeTimeForStopover()-"START"
                                if( -1 != ev.woePositionParameter ){
                                    // new position has to be destination
                                    //
                                    if( currentRespLastReportedPosition != ev.woePositionParameter ){
                                        // position has changed somehow
                                        //
                                        if( 1 <= numberOfReportedPositionsSinceLastStop ){
                                            // this at least second final destination reported -> hence, error
                                            //
                                            numberOfReportedPositionsSinceLastStop++;
                                            errHandler.reportError( INCONSISTENCY_LOCATION_WOE_W5WP1, ev, currentRespLastReportedPosition );
                                            return false;
                                        }//if
                                        if( lastReportedStopOverPosition == ev.woePositionParameter ){
                                            // position has changed somehow, but stopover position is still the same (???)   ->  "x"->"-1"->"x"
                                            //
                                            errHandler.reportError( INCONSISTENCY_LOCATION_WOE_W5WP1, ev, currentRespLastReportedPosition );
                                            return false;
                                        }//if
                                    }else{
                                        // NO (re-)action required - position has NOT changed
                                    }//if
                                }else{
                                    errHandler.reportError( UNEXPECTED_POSITION_PARAMETER_WOE, ev, currentRespLastReportedPosition );
                                    return false;
                                }//if
                                lastReportedStopOverPosition = ev.woePositionParameter;
                                currentRespLastReportedPosition = ev.woePositionParameter;
                                numberOfReportedPositionsSinceLastStop = 0;
                            break;
                            
                            // check ["ev.woePositionParameter != -1" => ERROR] in RawWOE.takeTimeForStopover() 
                            case W6WP2:                                         // takeTimeForStopover()-"END"
                                if( -1 == ev.woePositionParameter ){
                                    errHandler.reportError( UNEXPECTED_POSITION_PARAMETER_WOE, ev, currentRespLastReportedPosition );
                                    return false;
                                }//if
                                if( currentRespLastReportedPosition != ev.woePositionParameter ){
                                    errHandler.reportError( INCONSISTENCY_LOCATION_WOE_W6WP2, ev, currentRespLastReportedPosition );
                                    return false;
                                }//if
                                if( lastReportedStopOverPosition != ev.woePositionParameter ){
                                    errHandler.reportError( INCONSISTENCY_LOCATION_WOE_W6WP2, ev, currentRespLastReportedPosition );
                                    return false;
                                }//if
                            break;
                            
                            case W7FEN:
                                // currently NO checks required as result of RawWOE.forbidEntrance()
                            break;
                            
                            case W8VFP:                                         // vanish()
                                if( currentRespLastReportedPosition != ev.woePositionParameter ){
                                    // position has changed somehow
                                    //
                                    if( -1 != ev.woePositionParameter ){
                                        // new position has to be destination
                                        //
                                        if( 1 <= numberOfReportedPositionsSinceLastStop ){
                                            // this at least second final destination reported -> hence, error
                                            //
                                            numberOfReportedPositionsSinceLastStop++;
                                            errHandler.reportError( INCONSISTENCY_LOCATION_WOE_W8VFP, ev, currentRespLastReportedPosition );
                                            return false;
                                        }//if
                                        numberOfReportedPositionsSinceLastStop = 1;
                                        //
                                        if( lastReportedStopOverPosition == ev.woePositionParameter ){
                                            // position has changed somehow, but stopover position is still the same (???)   ->  "x"->"-1"->"x"
                                            //
                                            errHandler.reportError( INCONSISTENCY_LOCATION_WOE_W8VFP, ev, currentRespLastReportedPosition );
                                            return false;
                                        }//if
                                        lastReportedStopOverPosition = ev.woePositionParameter;
                                    }else{
                                        // NO (re-)action required - "-1" itself is "harmless", possible problems resulting out of this are handled elsewhere
                                        // E.g.: "x"->"-1"->"x"            results in lastReportedStopOverPosition == ev.woePositionParameter
                                        // E.g.: "x"->"-1"->"x"->"-1"->"x" results in numberOfReportedPositionsSinceLastStop<-2 anyway
                                    }//if
                                    currentRespLastReportedPosition = ev.woePositionParameter;
                                }else{
                                    // NO (re-)action required - position has NOT changed
                                }//if
                            break;
                            
                            default:
                                // NO CODE on purpose
                            break;
                            
                        }//switch
                    }//if
                }//if
            }//for
        }//for
        //
        woeBehaviorDuringStopOverUnchecked = false;
        return true;
        /* Memo:
         * Jetzt ist sicher, dass
         * -> das WOE seine Position während "eines Aufenthalts" NICHT verändert.
         * reviewed 130528
         * 
         * 160327
         * ======
         *      W       W       W       W       W       W       W       W       W       W       W       W       W       W       W       W
         *      6       7       8       1       2       3       4       5       6       7       8       1       2       3       4       5
         *      W       F       V       M       M       A       A       W       W       F       V       M       M       A       A       W
         *      P       E       F       O       O       A       E       P       P       E       F       O       O       A       E       P
         *      2       N       P       1       2       P       N       1       2       N       P       1       2       P       N       1
         *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
         *------v---|---v---|---v---|---v---|---v---|---v---|---v---|---v---|---v---|---v-------v-------v-------v-------v-------v-------v------
         *      A      -1      -1      -1      -1      -1      -1       B       B      -1      -1      -1      -1      -1      -1       C
         *      A       A      -1      -1      -1      -1       B       B       B       B      -1      -1      -1      -1       C       C
         *      A       A       A      -1      -1       B       B       B       B       B       B      -1      -1       C       C       C
         *      A       A       A       B       B       B       B       B       B       B       B       C       C       C       C       C       <<--
         *      A       A       A       A       B       B       B       B       B       B       B       B       C       C       C       C       <<--
         *      A     NULL      A       B       B       B     NULL      B       B     NULL      B       C       C       C     NULL      C       <<--
         *    ^^^-------------------------------------------------------^^^^^^^^^-------------------------------------------------------^^^
         *
         * NACH W6WP2 muss bis W5WP1 genau eine(!) Aenderung kommen(!)
         * Eine Aenderung ist "X -> Y" oder "X -> -1 -> Y" , NULL ausserhalb von W5WP1 & W6WP2 zählt als don't care
         */
    }//method()
    
    
    //--------------------------------------------------------------------------
    
    
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    class SmurfInfo {
        RawSmurf      smurf;
        CommonSSI[]   sched;                                                    // Schedule
        int           sIndx;                                                    // Schedule INDeX
        int           state;                                                    // S1BDS,S2EDS,S3ENW,S5LVW,...
        int           posi;                                                     // wo ist Schlumpf gerade
        long          time;                                                     // Zeitpunkt des letzten Events
        //
        SmurfInfo(){
            smurf = null;
            sched = null;
            sIndx = -1;
            state = -1;
            posi  = -1;
            time  = 0;
        }//SmurfInfo()
    }//class SmurfInfo
    
    
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    class WoeInfo {
        RawWOE  woe;
        int     state;
        int     posi;
        Collection<Integer>  passengerCollection;
        long    time;                                                           // Zeitpunkt des letzten Events
        //
        WoeInfo(){
            woe   = null;
            state = -1;
            posi  = -1;
            passengerCollection = new ArrayList<Integer>();                     // List / ArrayList since NO hashCode() is required ( and double-entries are supported )
            time  = 0;
        }//WoeInfo()
    }//class WoeInfo
    
    
    @ChunkPreamble ( lastModified="2013/05/29", lastModifiedBy="Michael Schäfers" )
    class LocationInfo {
        Collection<Integer> waitingSmurfsCollection;
        Collection<Integer> waitingWOEsCollection;
        LocationInfo(){
            waitingSmurfsCollection = new ArrayList<Integer>();                 // List / ArrayList since NO hashCode() is required ( and double-entries are supported )
            waitingWOEsCollection   = new ArrayList<Integer>();                 // List / ArrayList since NO hashCode() is required ( and double-entries are supported )
        }//LocationInfo()
    }//class LocationInfo
    
    
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" )
    final boolean chkGeneralBehavior(){
        if ( smurfIdValuesUnchecked )                throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if values of smurf IDs are valid" );
        if ( numberOfSmurfsUnchecked )               throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if number of found smurfs valid" );
        if ( smurfIdConsistencyUnchecked )           throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if found smurf IDs are concistent" );
        if ( smurfActivityCycleUnchecked )           throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if smurf activity cycle valid" );
        if ( smurfBehaviorDuringStopOverUnchecked )  throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if smurf behavior valid" );
        if ( smurfScheduleFollowedUnchecked )        throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if smurf follows schedule" );
        if ( woeIdValuesUnchecked )                  throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if values of WOE IDs are valid" );
        if ( numberOfWOEsUnchecked )                 throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if number of found WOEs valid" );
        if ( woeIdConsistencyUnchecked )             throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if found WOE IDs are concistent" );
        if ( woeActivityCycleUnchecked )             throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if WOE activity cycle valid" );
        if ( woeBehaviorDuringStopOverUnchecked )    throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if WOE behavior valid" );
        if ( woeRouteUnchecked )                     throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if WOE follows route" );
        if ( numberOfLocationsUnchecked )            throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if number of locations valid" );
        
        
        final Global global = Global.getData();
        final int expectedNumberOfSmurfs     =  global.fnos;
        final int expectedNumberOfWOEs       =  global.fnow;
        final int expectedNumberOfLocations  =  global.fnol;
        
        SmurfInfo[] sia = new SmurfInfo[expectedNumberOfSmurfs];                // Smurf Info Array
        for ( int isi=expectedNumberOfSmurfs; --isi>=0; ){
            loop:
            for ( RawSmurf smurf : Global.av_smurfList ){
                sia[isi] = new SmurfInfo();
                if ( smurf.internalSmurfID == isi ){
                    sia[isi].smurf = smurf;
                    sia[isi].sched = smurf.schedule.scheduleData;
                    break loop;
                }//if
            }//for
        }//for
        
        WoeInfo[] wia = new WoeInfo[expectedNumberOfWOEs];                      // Woe Info Array
        for ( int iwi=expectedNumberOfWOEs; --iwi>=0; ){
            wia[iwi] = new WoeInfo();
        }//for
        
        LocationInfo[] lia = new LocationInfo[expectedNumberOfLocations];       // Location(position) Info Array
        for ( int ili=expectedNumberOfLocations; --ili>=0; ){
            lia[ili] = new LocationInfo();
        }//for        
        
        long maxDeltaEW2VP = Long.MIN_VALUE;                                    // End Wait to Vanishing from Position
        long sumDeltaEW2VP = 0;                                                 // End Wait to Vanishing from Position
        int  cntDeltaEW2VP = 0;                                                 // End Wait to Vanishing from Position
        
        int isi;                                                                // Internal Smurf Id
        int iwi;                                                                // Internal Woe Id
        int ili;                                                                // ??? Location Id
        
        for ( Event ev : Global.av_eventList ){
            switch ( ev.et ){
                
                case S1BDS:
                    isi = ev.smurfIdInternal;                                   // Internal Smurf ID
                    
                    // 1st action of smurf (in general) ?
                    if ( sia[isi].sIndx == -1 ){
                        // 1st action of smurf
                        //
                        // initialisiere "alles"
                        sia[isi].sIndx = 1;                                     //
                        sia[isi].posi  = ev.smurfPositionParameter;             //
                        sia[isi].state = 5;                                     // 5th smurf action (S5LVW) would be action before
                    }//if
                    
                    //general smurf checks
                    if (! subGeneralSmurfChk( ev, sia, isi, 5, 1 ) )  return false;
                    
                    // smurf arrived at his target (2nd check)?
                    {   final SmurfInfo  si = sia[isi];                                     // current Smurf Info
                        final CommonSSI  cs = si.sched[ si.sched.length - si.sIndx ];       // current Common Ssi
                        final int target = cs.planedPosition;                               // current target
                        //
                        if ( target != ev.smurfPositionParameter ){
                            System.err.printf(
                                "ERROR: smurf stays at wrong postion:  smurf:%d   target = %d != %d = parameter   [%s]___{@%s}\n",
                                ev.smurfIdUser,
                                target,
                                ev.smurfPositionParameter,
                                ev.et,
                                Tool.prettyTime( ev.time )
                            );
                            System.err.flush();
                            return false;
                        }//if
                    }//scope
                    
                    /*
                    // had smurf changed his position respectively location
                    if ( sia[isi].posi != -1 ){
                        if ( sia[isi].posi != ev.smurfPositionParameter ){
                            System.err.printf(
                                "ERROR: smurf has changed position:  smurf:%d   last = %d != %d = parameter   [%s]___{@%s}\n",
                                ev.smurfIdUser,
                                sia[isi].posi,
                                ev.smurfPositionParameter,
                                ev.et,
                                Tool.prettyTime( ev.time )
                            );
                            System.err.flush();
                            return false;
                        }//if
                    }else{
                        sia[isi].posi = ev.smurfPositionParameter;
                    }//if
                    */
                    sia[isi].posi = ev.smurfPositionParameter;                  // __???__<130527> quick workaround for "if" above
                    
                    sia[isi].state = 1;                                         // "1st action" of smurf (S1BDS)
                    sia[isi].time = ev.time;                                    // Zeitpunkt des aktuellen Events
                    break;
                    
                    
                    
                case S2EDS:
                    isi = ev.smurfIdInternal;                                   // Internal Smurf ID
                    
                    //general smurf checks
                    if (! subGeneralSmurfChk( ev, sia, isi, 1, 2 ) )  return false;
                    
                    // Hat Schlumpf seine Position ver�ndert?
                    if ( sia[isi].posi != ev.smurfPositionParameter ){
                        System.err.printf(
                            "ERROR: smurf has changed position:  smurf:%d   last = %d != %d = parameter   [%s]___{@%s}\n",
                            ev.smurfIdUser,
                            sia[isi].posi,
                            ev.smurfPositionParameter,
                            ev.et,
                            Tool.prettyTime( ev.time )
                        );
                        System.err.flush();
                        return false;
                    }//if
                    
                    sia[isi].state = 2;                                         // "2nd action" of smurf (S2EDS)
                    sia[isi].time = ev.time;                                    // Zeitpunkt des aktuellen Events
                    break;
                    
                    
                    
                case S3ENW:
                    isi = ev.smurfIdInternal;                                   // Internal Smurf ID
                    iwi = ev.woeIdInternal;                                     // Internal WOE ID
                    
                    //general smurf checks
                    if (! subGeneralSmurfChk( ev, sia, isi, 2, 3 ) )  return false;
                    
                    //##########################################################
                    //vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
                    //
                    // JETZT KOMMEN DIE WIRKLICH WICHTIGEN SMURF-GETRIGGERTEN CHECKS !!!
                    // =================================================================
                    //
                    // "Zustand" des WOE ok? (Können Schlümpfe das WOE betreten?)
                    // -> WOE-Zustand (zunächst) "nach W4AEN" und "vor W7FEN"
                    // -> Achtung! Der Zustand bleibt "ab Abruf" bis "zum nächsten Aufruf".
                    // -> D.h. WOE muss W4AEN, W5WP1 oder W6WP2 sein
                    if ( wia[iwi].state < EventType.W4AEN.toCodedWoeState()   ||   EventType.W6WP2.toCodedWoeState() < wia[iwi].state ){
                        System.err.printf(
                            "ERROR: smurf:%d enters WOE:%d that is in false state - entrance forbidden   [%s]<~!~>[%s]___{@%s}\n",
                            ev.smurfIdUser,
                            ev.woeIdUser,
                            ev.et,
                            EventType.codedWoeState2woeEventType( wia[iwi].state ),
                            Tool.prettyTime( ev.time )
                        );
                        System.err.flush();
                        return false;
                    }//if
                    //
                    // Position des WOE ok? (Ist WOE überhaupt da?)
                    if ( sia[isi].posi != wia[iwi].posi ){
                        System.err.printf(
                            "ERROR: smurf enters WOE that is sonewhere else:  smurf:%d last reported at postion %d != %d last reported position of WOE:%d   [%s]___{@%s}\n",
                            ev.smurfIdUser,
                            sia[isi].posi,
                            wia[iwi].posi,
                            ev.woeIdUser,
                            ev.et,
                            Tool.prettyTime( ev.time )
                        );
                        System.err.flush();
                        return false;
                    }//if
                    //
                    // Anzahl Schlümpfe im WOE ok?
                    if ( wia[iwi].passengerCollection.size() >= expectedNumberOfSmurfs ){
                        System.err.printf(
                            "ERROR: too many smurfs in WOE:  WOE:%d, cnt:%d+1(>%d)   [%s]___{@%s}\n",
                            ev.woeIdUser,
                            wia[iwi].passengerCollection.size(),
                            expectedNumberOfSmurfs,
                            ev.et,
                            Tool.prettyTime( ev.time )
                        );
                        System.err.flush();
                        return false;
                    }//if
                    wia[iwi].passengerCollection.add( new Integer(isi) );                     // "new Integer(isi)" is constant
                    //
                    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                    //##########################################################
                    
                    /*
                    // Hat Schlumpf seine Position verändert?
                    if ( sia[isi].posi != ev.smurfPositionParameter ){
                        System.err.printf(
                            "ERROR: smurf has changed position:  smurf:%d   last = %d != %d = parameter   [%s]___{@%s}\n",
                            ev.smurfIdUser,
                            sia[isi].posi,
                            ev.smurfPositionParameter,
                            ev.et,
                            Tool.prettyTime( ev.time )
                        );
                        System.err.flush();
                        return false;
                    }//if
                    */
                    
                    sia[isi].sIndx++;                                           // smurf has entered ship -> location/position will change now __???__<130613>
                    sia[isi].state = 3;                                         // "3rd action" of smurf (S3ENW)
                    sia[isi].time = ev.time;                                    // Zeitpunkt des aktuellen Events
                    break;
                    
                    
                    
                case S4AIW:
                    isi = ev.smurfIdInternal;                                   // Internal Smurf ID
                    iwi = ev.woeIdInternal;                                     // Internal WOE ID
                    
                    
                    //general smurf checks
                    if (! subGeneralSmurfChk( ev, sia, isi, 3, 4 ) )  return false;
                    
                  //sia[isi].sIndx++;                                           // smurf is moving/travelling -> location/position will change now __???__<130613> hier falsch ????
                    sia[isi].state = 4;                                         // "4th action" of smurf (S4TIW)
                    sia[isi].time = ev.time;                                    // Zeitpunkt des aktuellen Events
                    break;
                    
                    
                    
                case S5LVW:
                    isi = ev.smurfIdInternal;                                   // Internal Smurf ID
                    iwi = ev.woeIdInternal;                                     // Internal WOE ID
                    
                    //general smurf checks
                    if (! subGeneralSmurfChk( ev, sia, isi, 4, 5 ) )  return false;
                    
                    //##########################################################
                    //vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
                    //
                    // JETZT KOMMEN DIE WIRKLICH WICHTIGEN SMURF-GETRIGGERTEN CHECKS !!!
                    // =================================================================
                    //
                    // "Zustand" des WOE ok? (Können Schlümpfe das WOE verlassen?)
                    // -> WOE-Zustand (zunächst) "nach W4AEN" und "vor W7FEN"
                    // -> Achtung! Der Zustand bleibt "ab Abruf" bis "zum nächsten Aufruf".
                    // -> D.h. WOE muss W4AEN, W5WP1 oder W6WP2 sein
                    if ( wia[iwi].state < EventType.W4AEN.toCodedWoeState()   ||   EventType.W6WP2.toCodedWoeState() < wia[iwi].state ){
                        System.err.printf(
                            "ERROR: smurf:%d leaves WOE:%d that is in false state - exit forbidden   [%s]<~!~>[%s]___{@%s}\n",
                            ev.smurfIdUser,
                            ev.woeIdUser,
                            ev.et,
                            EventType.codedWoeState2woeEventType( wia[iwi].state ),
                            Tool.prettyTime( ev.time )
                        );
                        System.err.flush();
                        return false;
                    }//if
                    //
                    // Position des WOE ok? (bzw. steigt Schlumpf an richtiger Stelle aus? - kann erst bei S1BDS geprüft werden)
                    if ( sia[isi].sched[ sia[isi].sched.length - sia[isi].sIndx ].planedPosition != wia[iwi].posi ){
                        System.err.printf(
                            "ERROR: smurf leaves WOE that is sonewhere else:  smurf:%d should leave at postion %d != %d last reported position of WOE:%d   [%s]___{@%s}\n",
                            ev.smurfIdUser,
                            sia[isi].sched[ sia[isi].sched.length - sia[isi].sIndx ].planedPosition,
                            wia[iwi].posi,
                            ev.woeIdUser,
                            ev.et,
                            Tool.prettyTime( ev.time )
                        );
                        System.err.flush();
                        return false;
                    }//if
                    //
                    // Ist Schlumpf überhaupt im WOE?
                    if ( ! wia[iwi].passengerCollection.remove( new Integer(isi) ) ){         // "new Integer(isi)" is constant
                        System.err.printf(
                            "ERROR: smurf leaves wrong WOE:  smurf:%d, WOE:%d   [%s]___{@%s}\n",
                            ev.smurfIdUser,
                            ev.woeIdUser,
                            ev.et,
                            Tool.prettyTime( ev.time )
                        );
                        System.err.flush();
                        return false;
                    }//if
                    
                    //
                    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                    //##########################################################
                    
                    /*                                                          NO parameter
                    // smurf arrived at his target (1st check)?
                    {   final int target = sia[isi].sched[ sia[isi].sched.length - sia[isi].sIndx ].planedPosition;
                        if ( target != ev.smurfPositionParameter ){
                            System.err.printf(
                                "ERROR: smurf leaves WOE at wrong postion:  smurf:%d, WOE:%d   target = %d != %d = parameter   [%s]___{@%s}\n",
                                ev.smurfIdUser,
                                ev.woeIdUser,
                                target,
                                ev.smurfPositionParameter,
                                ev.et,
                                Tool.prettyTime( ev.time )
                            );
                            System.err.flush();
                            return false;
                        }//if
                    }//scope
                    sia[isi].posi = ev.smurfPositionParameter;
                    */
                    
                    sia[isi].state = 5;                                         // "5th action" of smurf (S4LVW)
                    sia[isi].time = ev.time;                                    // Zeitpunkt des aktuellen Events
                    break;
                    
                    
                    
                case SxxLD:
                    isi = ev.smurfIdInternal;                                   // Internal Smurf ID
                    
                    //general smurf checks
                    if (! subGeneralSmurfChk( ev, sia, isi, 2, Integer.MAX_VALUE ) )  return false;
                    
                    sia[isi].state = Integer.MIN_VALUE;                         // invalid on purpose - it's over ;-)
                    sia[isi].posi  = Integer.MIN_VALUE;                         // invalid on purpose - it's over ;-)
                    sia[isi].time  = ev.time;                                   // Zeitpunkt des aktuellen Events
                    break;
                    
                    
                    
                    
                    
                case W1MO1:
                    iwi = ev.woeIdInternal;                                     // Internal WOE ID
                    
                    // general WOE checks
                    if (! subGeneralWoeChk( ev, wia, iwi, 8, 1 ) )  return false;
                    
                    // Sind auch alle ausgestiegen, die es wollten?
                    //  Smurf ändert/aktualisiert seine Position mit S4TIW
                    //  WOE ändert/aktualisiert seine Position mit W5WP1
                    List<Integer> criticalSmurfs = new ArrayList<Integer>();

                    for ( Integer lisi : wia[iwi].passengerCollection ){        // Local Internal Smurf ID
                        final int actualScheduleIndexOfCurrentsmurf = sia[lisi].sched.length - sia[lisi].sIndx;
                        final int planedPositionOfCurrentSmurf = sia[lisi].sched[ actualScheduleIndexOfCurrentsmurf ].planedPosition;
                        final int lastPositionOfWoe = wia[iwi].posi;
                        if ( planedPositionOfCurrentSmurf == lastPositionOfWoe ){
                            criticalSmurfs.add( lisi );
                        }//if
                    }//for
                    //
                    if ( criticalSmurfs.size() > 0 ){
                        String errMsg = String.format(
                            "ERROR: WOE:%d leaving location:%d  was NOT left by smurf(s):",
                            ev.woeIdUser,
                            wia[iwi].posi
                        );
                        for ( Integer lisi : criticalSmurfs ){
                            errMsg += " " + lisi;
                            /*
                            errMsg += "(";
                            final int codedScheduleIndexOfCurrentsmurf = sia[lisi].sched.length - sia[lisi].sIndx;
                            if (codedScheduleIndexOfCurrentsmurf<sia[lisi].sched.length-1){
                                errMsg += sia[lisi].sched[ codedScheduleIndexOfCurrentsmurf+1 ].planedPosition + "/" + (sia[lisi].sIndx-2);
                            }
                            errMsg += "[" + sia[lisi].sched[ codedScheduleIndexOfCurrentsmurf ].planedPosition + "/" + (sia[lisi].sIndx-1) + "]";
                            if (0<codedScheduleIndexOfCurrentsmurf){
                                errMsg += sia[lisi].sched[ codedScheduleIndexOfCurrentsmurf-1 ].planedPosition + "/" + (sia[lisi].sIndx-0);
                            }
                            errMsg += ")";
                            */
                        }//for
                        System.err.printf(
                            "%s   [%s]___{@%s}\n" ,
                            errMsg,
                            ev.et,
                            Tool.prettyTime( ev.time )
                        );
                        System.err.flush();
                        return false;
                    }//if
                    
                    wia[iwi].state = 1;                                         // "1st action" of WOE (W1MO1)
                    wia[iwi].time  = ev.time;                                   // Zeitpunkt des aktuellen Events
                    break;
                    
                    
                    
                case W2MO2:
                    iwi = ev.woeIdInternal;                                     // Internal WOE ID
                    
                    // general WOE checks
                    if (! subGeneralWoeChk( ev, wia, iwi, 1, 2 ) )  return false;
                    
                    wia[iwi].state = 2;                                         // "2nd action" of WOE (W2MO2)
                    wia[iwi].time  = ev.time;                                   // Zeitpunkt des aktuellen Events
                    break;
                    
                    
                    
                case W3AAP:
                    iwi = ev.woeIdInternal;                                     // Internal WOE ID
                    ili = ev.woePositionParameter;                                                                      // 130521
                    
                    // general WOE checks
                    if (! subGeneralWoeChk( ev, wia, iwi, 2, 3 ) )  return false;
                    
                    //##########################################################
                    //vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
                    //
                    // JETZT KOMMEN DIE WIRKLICH WICHTIGEN WOE-GETRIGGERTEN CHECKS !!!
                    // ===============================================================
                    //
                    // Anzahl WOE in Location ok?                                                                       // 130521
                    if ( lia[ili].waitingWOEsCollection.size() >= expectedNumberOfWOEs ){                               //
                        System.err.printf(                                                                              //
                            "ERROR: too many WOEs in location:  location:%d, cnt:%d+1(>%d)   [%s]___{@%s}\n",           //
                            ili,                                                                                        //
                            lia[ili].waitingWOEsCollection.size(),                                                      //
                            expectedNumberOfWOEs,                                                                       //
                            ev.et,                                                                                      //
                            Tool.prettyTime( ev.time )                                                                  //
                        );                                                                                              //
                        System.err.flush();                                                                             //
                        return false;                                                                                   //
                    }//if                                                                                               //
                    //
                    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                    //##########################################################
                    
                    // bereits an Haltestelle gemeldet?                                                                             // 130521
                    // kann eigentlich nicht sein, da WOE Activity-Cycle ok (bereits 2x geprüft)  130530                            //
                    if ( lia[ili].waitingWOEsCollection.contains( new Integer(iwi) ) ){   // "new Integer(iwi)" is constant         //
                        System.err.printf(                                                                                          //
                            "ERROR: WOE is already at location:  WOE %d   is already at location %d = parameter   [%s]___{@%s}\n",  //
                            ev.woeIdUser,                                                                                           //
                            ili,                                                                                                    //
                            ev.et,                                                                                                  //
                            Tool.prettyTime( ev.time )                                                                              //
                        );                                                                                                          //
                        System.err.flush();                                                                                         //
                        return false;                                                                                               //
                    }//if                                                                                                           //
                    //
                    lia[ili].waitingWOEsCollection.add( new Integer(iwi) );     // "new Integer(iwi)" is constant                   //
                    
                    wia[iwi].posi  = ev.woePositionParameter;                   // __???__<130525>
                    wia[iwi].state = 3;                                         // "3rd action" of WOE (W3AAP)
                    wia[iwi].time  = ev.time;                                   // Zeitpunkt des aktuellen Events
                    break;
                    
                    
                    
                case W4AEN:
                    iwi = ev.woeIdInternal;                                     // Internal WOE ID
                    
                    // general WOE checks
                    if (! subGeneralWoeChk( ev, wia, iwi, 3, 4 ) )  return false;
                    
                    wia[iwi].state = 4;                                         // "4th action" of WOE (W4AEN)
                    wia[iwi].time  = ev.time;                                   // Zeitpunkt des aktuellen Events
                    break;
                    
                    
                    
                case W5WP1:
                    iwi = ev.woeIdInternal;                                     // Internal WOE ID
                                        
                    // general WOE checks
                    if (! subGeneralWoeChk( ev, wia, iwi, 4, 5 ) )  return false;
                    
                    // Haltestelle unverändert                                  // __???___<130522> Hier noch mal ? <-> chkWoeBehaviorDuringStopOver()
                    if ( wia[iwi].state != -1 ){
                        if ( wia[iwi].posi != -1 ){                             // __???___<130522> macht das alles noch Sinn?  - u.U. hier zum ersten Mal definiert
                            if ( wia[iwi].posi != ev.woePositionParameter ){
                                System.err.printf(
                                    "ERROR: WOE has changed location:  WOE:%d   last = %d != %d = parameter   [%s]___{@%s}\n",
                                    ev.woeIdUser,
                                    wia[iwi].posi,
                                    ev.woePositionParameter,
                                    ev.et,
                                    Tool.prettyTime(ev.time )
                                );
                                System.err.flush();
                                return false;
                            }//if
                        }else{                                                  // __???___<130522> macht das alles noch Sinn?  - u.U. hier zum ersten Mal definiert
                            wia[iwi].posi = ev.woePositionParameter;            // __???___<130522> macht das alles noch Sinn?  - u.U. hier zum ersten Mal definiert
                        }//if
                    }else{
                        wia[iwi].posi = ev.woePositionParameter;
                    }//if
                    
                    wia[iwi].state = 5;                                         // "5th action" of WOE (W5WP1)
                    wia[iwi].time  = ev.time;                                   // Zeitpunkt des aktuellen Events
                    break;
                    
                    
                    
                case W6WP2:
                    iwi = ev.woeIdInternal;                                     // Internal WOE ID
                                        
                    // general WOE checks
                    if (! subGeneralWoeChk( ev, wia, iwi, 5, 6 ) )  return false;
                    
                    wia[iwi].state = 6;                                         // "6th action" of WOE (W6WP2)
                    wia[iwi].time  = ev.time;                                   // Zeitpunkt des aktuellen Events
                    break;
                    
                    
                    
                case W7FEN:
                    iwi = ev.woeIdInternal;                                     // Internal WOE ID
                                        
                    // general WOE checks
                    if (! subGeneralWoeChk( ev, wia, iwi, 6, 7 ) )  return false;
                    
                    //##########################################################
                    //vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
                    //
                    // JETZT KOMMEN DIE WIRKLICH WICHTIGEN WOE-GETRIGGERTEN CHECKS !!!
                    // ===============================================================
                    //
                    // Aus Symmetrie hier kein check, ob Schlümpfe ausgestigen sind
                    // Einsteigen und Aussteigen könnte von speziellen Anforderungen abhängen
                    // Checks werden z.B. in
                    // -> chkLineBasedStuff()
                    // -> chkCycleBasedStuff()
                    // durchgeführt
                    //
                    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                    //##########################################################
                    
                    wia[iwi].state = 7;                                         // "7th action" of WOE (W7FEN)
                    wia[iwi].time  = ev.time;                                   // Zeitpunkt des aktuellen Events
                    break;
                    
                    
                    
                case W8VFP:
                    iwi = ev.woeIdInternal;                                     // Internal WOE ID
                    ili = ev.woePositionParameter;                                                                              // 130521
                    
                    // general WOE checks
                    if (! subGeneralWoeChk( ev, wia, iwi, 7, 8 ) )  return false;
                    
                    // Haltestelle unverändert
                    if ( wia[iwi].posi != ili ){                                                                                // 130521
                        System.err.printf(
                            "ERROR: WOE starts from wrong location:  WOE%d   last = %d != %d = parameter   [%s]___{@%s}\n",
                            ev.woeIdUser,
                            wia[iwi].posi,
                            ili,
                            ev.et,
                            Tool.prettyTime( ev.time )
                        );
                        System.err.flush();
                        return false;
                    }//if
                    //
                    // an Haltestelle gemeldet?                                                                                 // 130521
                    if ( ! lia[ili].waitingWOEsCollection.remove( new Integer(iwi) ) ){   // "new Integer(iwi)" is constant               //
                        System.err.printf(                                                                                      //
                            "ERROR: WOE was NOT at location:  WOE %d   was NOT at location %d = parameter   [%s]___{@%s}\n",    //
                            ev.woeIdUser,                                                                                       //
                            ili,                                                                                                //
                            ev.et,                                                                                              //
                            Tool.prettyTime( ev.time )                                                                          //
                        );                                                                                                      //
                        System.err.flush();                                                                                     //
                        return false;                                                                                           //
                    }//if                                                                                                       //
                    
                    // Delta Time - wurde WOE irgendwie auf- oder fest-gehalten?
                    final long tmpDeltaEW2VP = ev.time - wia[iwi].time;
                    if ( tmpDeltaEW2VP > 4_000_000 ){                           // delta >  4[ms]         => ERROR
                        System.err.printf(
                            "ERROR: WOE:%d  starts from location:%d  %s[ns] too late (start-after-finished-stopping-dealy>4[ms])   [%s]___{@%s}\n",
                            ev.woeIdUser,
                            ev.woePositionParameter,
                            Tool.prettyTime( tmpDeltaEW2VP ),
                            ev.et,
                            Tool.prettyTime( ev.time )
                        );
                        System.err.flush();
                    } else if ( tmpDeltaEW2VP > maxDeltaEW2VP ){
                        maxDeltaEW2VP = tmpDeltaEW2VP;
                    }//if
                    sumDeltaEW2VP += tmpDeltaEW2VP;
                    cntDeltaEW2VP++;
                    
                    wia[iwi].state = 8;                                         // "7th action" of WOE (W8VFP)
                    wia[iwi].time  = ev.time;                                   // Zeitpunkt des aktuellen Events
                    break;
                    
                    
                    
                case WxxLD:
                    iwi = ev.woeIdInternal;                                     // Internal WOE ID
                    
                    // general WOE checks
                    if (! subGeneralWoeChk( ev, wia, iwi, -1, Integer.MAX_VALUE ) )  return false;
                    
                    //
                    if ( wia[iwi].passengerCollection.size() > 0 ){
                        System.err.printf(
                            "ERROR: WOE:%d behaves faulty - still %d passengers   [%s]___{@%s}\n",
                            ev.woeIdUser,
                            wia[iwi].passengerCollection.size(),
                            ev.et,
                            Tool.prettyTime(ev.time )
                        );
                        System.err.flush();
                        return false;
                    }//if
                    
                    wia[iwi].state = Integer.MIN_VALUE;                         // invalid on purpose - it's over ;-)
                    wia[iwi].posi  = Integer.MIN_VALUE;                         // invalid on purpose - it's over ;-)
                    wia[iwi].time  = ev.time;                                   // Zeitpunkt des aktuellen Events
                    break;
                    
                
                default :                                                       // nothing(!) to be done
                  //assert false : "who will end up here?";
                    break;
               
                    
            }//switch
        }//for
        
        System.err.printf( "\n\n" );
        System.err.printf( "NOTE:  max. WOE start-after-finished-stopping-delay : %s\n", Tool.prettyTime( maxDeltaEW2VP ) );
        System.err.printf( "NOTE:  avg. WOE start-after-finished-stopping-delay : %s\n", Tool.prettyTime( sumDeltaEW2VP/cntDeltaEW2VP ) );
        System.err.printf( "\n\n\n" );
        System.err.flush();
                
        generalBehaviorUnchecked = false;
        return true;
    }//chkGeneralBehavior()
    //
    //
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    private boolean subGeneralSmurfChk( final Event ev, final SmurfInfo[] sia, final int isi, final int expectedState, final int nextExpectedState ){
        // smurf activity cycle ok?
        if ( sia[isi].state != expectedState ){
            errHandler.reportError( FAULTY_BEHAVIOR_SMURF, ev, sia[isi].state, expectedState, nextExpectedState );
            return false;
        }//if
        
        /*                                                                      was soll hier hin ???
        // check location consistency                                           // __???__<130515>_tbd
        if ( ev.smurfPositionLocated != ev.smurfPositionParameter  &&  ev.smurfPositionLocated != null  &&  ev.smurfPositionParameter != null ){
            errHandler.reportError( INCONSISTENCY_LOCATION_SMURF, ev );
            return false;
        }//if
        */
        
        return true;
    }//subGeneralSmurfChk()
    //
    //
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    private boolean subGeneralWoeChk( final Event ev, final WoeInfo[] wia, final int iwi, final int expectedState, final int nextExpectedState ){
        // WOE activity cycle ok?
        if ( wia[iwi].state != expectedState  &&  wia[iwi].state != -1  &&  expectedState != -1 ){
            errHandler.reportError( FAULTY_BEHAVIOR_WOE, ev, wia[iwi].state, expectedState, nextExpectedState );
            return false;
        }//if
        
        /*                                                                      was soll hier hin ???
        // check location consistency
        if ( null!=ev.woePositionLocated  &&  ev.woePositionLocated != ev.woePositionParameter  &&  ev.woePositionLocated != -1  &&  ev.woePositionParameter != -1 ){
            errHandler.reportError( INCONSISTENCY_LOCATION_WOE, ev );
            return false;
        }//if
        */
        
        return true;
    }//subGeneralWoeChk()
    
    
    
    
    
    
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" )
    final boolean chkLineBasedStuff( final int[][] route ){
        // chkCycleBasedStuff modified afterwards - check there for improvements
        if ( smurfIdValuesUnchecked )                throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if values of smurf IDs are valid" );
        if ( numberOfSmurfsUnchecked )               throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if number of found smurfs valid" );
        if ( smurfIdConsistencyUnchecked )           throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if found smurf IDs are concistent" );
        if ( smurfActivityCycleUnchecked )           throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if smurf activity cycle valid" );
        if ( smurfBehaviorDuringStopOverUnchecked )  throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if smurf behavior valid" );
        if ( smurfScheduleFollowedUnchecked )        throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if smurf follows schedule" );
        if ( woeIdValuesUnchecked )                  throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if values of WOE IDs are valid" );
        if ( numberOfWOEsUnchecked )                 throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if number of found WOEs valid" );
        if ( woeIdConsistencyUnchecked )             throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if found WOE IDs are concistent" );
        if ( woeActivityCycleUnchecked )             throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if WOE activity cycle valid" );
        if ( woeBehaviorDuringStopOverUnchecked )    throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if WOE behavior valid" );
        if ( woeRouteUnchecked )                     throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if WOE follows route" );
        if ( numberOfLocationsUnchecked )            throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if number of locations valid" );
        if ( generalBehaviorUnchecked )              throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if general behavior valid" );
        //
        if ( route == null )                         throw new IllegalStateException( "UUUPPS - This might be an INTERNAL ERROR - parameter invalid" );
        
        
        
        class EWoeInfo extends WoeInfo{
            Stack<Integer> todo;
            Stack<Integer> done;
            EWoeInfo(){
                todo  = new Stack<Integer>();
                done  = new Stack<Integer>();
            }//EWoeInfo
        }//EWoeInfo
        
        
        final Global global = Global.getData();
        final int expectedNumberOfSmurfs     =  global.fnos;
        final int expectedNumberOfWOEs       =  global.fnow;
        final int expectedNumberOfLocations  =  global.fnol;
        final int maxNumberOfSmurfPerWOE     =  global.fmnspw;
      //final int maxNumberOfWoePerLocation  =  global.fmnwpl;
        
        
        // compute traces ( reused "check for cycles" code from "chkWoeRoute()" )
        final Map<Integer,List<Integer>> trace = new HashMap<Integer,List<Integer>>();
        for ( RawWOE woe : Global.av_woeList ){
            
            // generate trace of WOE
            List<Integer> curTrace = new ArrayList<Integer>();
            for ( Event ev : woe.eventList ){
                if ( ev.et == EventType.W5WP1 )  curTrace.add( ev.woePositionParameter );
            }//for
            
            // check trace for cycles
            boolean cycleFound = false;                                         // NO cycle found, yet
            int clc;                                                            // CycleLengthCandidates
            final int clcBorderValue = curTrace.size() >> 1;
            chkCLCloop:
            for ( clc=1; clc<=clcBorderValue; clc++ ){                          // test CycleLengthCandidates
                for ( int i=curTrace.size()-1-clc; --i>0; ){                    // "!=", since Integer values are constants
                    if (! curTrace.get(i).equals( curTrace.get(i+clc) )){
                        // candidate failed
                        //
                        continue chkCLCloop;                                    // try next (CycleLength)Candidate
                    }//if
                }//for
                // candidate passed test => (smallest) cycle found
                
                cycleFound = true;
                break chkCLCloop;
            }//chkCLCloop-for
            
            if ( cycleFound ){
                trace.put( woe.internalWoeID, curTrace );
                //
                for ( int i=curTrace.size(); --i>=clc; )  curTrace.remove(i);   // remove all entries after 1st cycle
            }else{
                throw new IllegalStateException( "UUUPPS - this might be an INTERNAL ERROR - it was NOT expected to end here" );
            }//if
        }//for
        
        
        
        SmurfInfo[] sia = new SmurfInfo[expectedNumberOfSmurfs];
        for ( int isi=expectedNumberOfSmurfs; --isi>=0; ){
            loop:
            for ( RawSmurf smurf : Global.av_smurfList ){
                sia[isi] = new SmurfInfo();
                if ( smurf.internalSmurfID == isi ){
                    sia[isi].smurf = smurf;
                    sia[isi].sched = smurf.schedule.scheduleData;
                    sia[isi].sIndx = sia[isi].sched.length-1;
                    break loop;
                }//if
            }//for
        }//for
        
        EWoeInfo[] wia = new EWoeInfo[expectedNumberOfWOEs];
        for ( int iwi=expectedNumberOfWOEs; --iwi>=0; ){
            wia[iwi] = new EWoeInfo();
            final List<Integer> cRoute = trace.get( iwi );
            for ( int i=(cRoute.size()>>1), j=i; i>0; i--,j++ )  if ( cRoute.get(i)!=cRoute.get(j) )  throw new IllegalStateException( "UUUPPS - this might be an INTERNAL ERROR - this was NOT expected to happen" );
            for ( int i=(cRoute.size()>>1); i>=0; i-- )  wia[iwi].todo.push( cRoute.get( i ) );
        }//for
        
        LocationInfo[] lia = new LocationInfo[expectedNumberOfLocations];
        for ( int ili=expectedNumberOfLocations; --ili>=0; ){
            lia[ili] = new LocationInfo();
        }//for
        
        
        
        long maxDawdleAwayDelta = Long.MIN_VALUE;      int mdadLoc = Integer.MIN_VALUE;   long mdadTime = Long.MIN_VALUE;   int mdadLeftSmurfCnt = Integer.MIN_VALUE;
        int  maxWaitingSmurfCnt = Integer.MIN_VALUE;   int mwscLoc = Integer.MIN_VALUE;   long mwscTime = Long.MIN_VALUE;
        for ( Event ev : Global.av_eventList ){
            final Integer isi = ev.smurfIdInternal;                             // Internal Smurf ID - null possible if not used
            final Integer iwi = ev.woeIdInternal;                               // Internal WOE ID   - null possible if not used
            switch ( ev.et ){
                
                case S5LVW:
                    if (    ( 0 > --(sia[isi].sIndx) )
                        ||  ( ! wia[iwi].passengerCollection.remove( new Integer(isi) ) )
                    ){
                        throw new IllegalStateException( "UUUPPS - this might be an INTERNAL ERROR - this was NOT expected to happen" );
                    }//if
                    break;
                    
                case S2EDS:
                    sia[isi].time = ev.time;
                    sia[isi].posi = ev.smurfPositionParameter;
                    lia[ sia[isi].posi ].waitingSmurfsCollection.add( isi );
                    if ( maxWaitingSmurfCnt < lia[ sia[isi].posi ].waitingSmurfsCollection.size() ){
                        maxWaitingSmurfCnt = lia[ sia[isi].posi ].waitingSmurfsCollection.size();
                        mwscTime = ev.time;
                        mwscLoc  = ev.smurfPositionParameter;
                    }//if
                    break;
                    
                case S3ENW:
                    if ( ! lia[ sia[isi].posi ].waitingSmurfsCollection.remove( isi ) )  throw new IllegalStateException( "UUUPPS - this might be an INTERNAL ERROR - this was NOT expected to happen" );
                    wia[iwi].passengerCollection.add( new Integer(isi) );
                    break;
                    
                    
                case W5WP1:
                    wia[iwi].posi = ev.woePositionParameter;
                    if ( wia[iwi].todo.peek() != wia[iwi].posi )  throw new IllegalStateException( "UUUPPS - this might be an INTERNAL ERROR - this was NOT expected to happen" );
                    wia[iwi].done.push( wia[iwi].todo.pop() );
                    if ( wia[iwi].todo.empty() ){
                        Stack<Integer> tmp = wia[iwi].todo;
                        wia[iwi].todo = wia[iwi].done;
                        wia[iwi].done = tmp;
                        wia[iwi].done.push( wia[iwi].todo.pop() );
                    }//if
                    break;
                    
                case W6WP2:
                    if ( wia[iwi].posi != ev.woePositionParameter ){
                        throw new IllegalStateException( "WOE has moved while waiting" );
                    }//if
                    break;
                    
                case W8VFP:
                    if ( wia[iwi].passengerCollection.size() < maxNumberOfSmurfPerWOE ){
                        final Set<Integer> maybe = new HashSet<Integer>();
                        final Set<Integer> error = new HashSet<Integer>();
                        for ( Integer cisi : lia[ wia[iwi].posi ].waitingSmurfsCollection ){
                            if ( sia[cisi].sIndx > 0 ){
                                if ( wia[iwi].todo.contains( sia[cisi].sched[ sia[cisi].sIndx-1 ].planedPosition ) ){
                                    long delta = ev.time - sia[cisi].time;
                                    if ( delta > 4_000_000 ){                   // delta >  4[ms]             => ERROR
                                        error.add( cisi );
                                    }else if ( delta >= 500_000 ){              // 4[ms] >= delta >= 500[us]  => MAYBE error
                                        maybe.add( cisi );
                                    }//if
                                    if ( delta > maxDawdleAwayDelta ){
                                        maxDawdleAwayDelta = delta;
                                        mdadTime           = ev.time;
                                        mdadLoc            = ev.woePositionParameter;
                                        mdadLeftSmurfCnt   = lia[ wia[iwi].posi ].waitingSmurfsCollection.size();
                                    }//if
                                }//if
                            }//if
                        }//for
                        if ( ! maybe.isEmpty() ){
                            System.err.printf(
                                "ATTENTION: WOE:%d with %d passenger(s) and capacity of %d   at position:%d (with %d remaining smurf(s) and going to next position:%d)   was NOT taken by %d smurf(s)   {@%s}\n",
                                ev.woeIdUser,
                                wia[iwi].passengerCollection.size(),
                                maxNumberOfSmurfPerWOE,
                                ev.woePositionParameter,
                                lia[ wia[iwi].posi ].waitingSmurfsCollection.size(),
                                wia[iwi].todo.peek(),
                                maybe.size(),
                                Tool.prettyTime( ev.time )
                            );
                            for ( Integer cisi : maybe ){
                                System.err.printf(
                                    "  |~~>  smurf:%d   (claims to be) at position:%d heading to position:%d   (delta:%s)\n",     /* "hopefully"="pretty sure", since otherwise there should have been an alarm before*/
                                    sia[cisi].smurf.identify(),
                                    sia[cisi].sched[ sia[cisi].sIndx ].planedPosition,
                                    sia[cisi].sched[ sia[cisi].sIndx-1 ].planedPosition,
                                    Tool.prettyTime( ev.time - sia[cisi].time )
                                );
                            }//for
                            System.err.printf( "  \\~~>  delta(s) < 4[ms]   maybe acceptable\n\n" );
                            System.err.flush();
                        }//if
                        if ( ! error.isEmpty() ){
                            System.err.printf(
                                "\n\nERROR: WOE:%d with %d passenger(s) and capacity of %d   at position:%d (with %d remaining smurf(s) and going to next position:%d)   was NOT taken by %d smurf(s)   {@%s}\n",
                                ev.woeIdUser,
                                wia[iwi].passengerCollection.size(),
                                maxNumberOfSmurfPerWOE,
                                ev.woePositionParameter,
                                lia[ wia[iwi].posi ].waitingSmurfsCollection.size(),
                                wia[iwi].todo.peek(),
                                error.size(),
                                Tool.prettyTime( ev.time )
                            );
                            for ( Integer cisi : error ){
                                System.err.printf(
                                    "  |~~>  smurf:%d   (claims to be) at position:%d heading to position:%d   (delta:%s)\n",     /* "hopefully"="pretty sure", since otherwise there should have been an alarm before*/
                                    sia[cisi].smurf.identify(),
                                    sia[cisi].sched[ sia[cisi].sIndx ].planedPosition,
                                    sia[cisi].sched[ sia[cisi].sIndx-1 ].planedPosition,
                                    Tool.prettyTime( ev.time - sia[cisi].time )
                                );
                            }//for
                            System.err.printf(
                                "  \\~~>  [%s]   {@%s}\n\n",
                                ev.et,
                                Tool.prettyTime( ev.time )
                            );
                            System.err.flush();
                            return false;
                        }//if
                    }//if
                    break;
                    
                    
                default:                                                        // keep compiler happy ;-)
                  //assert false : String.format( "Wie komm ich hier her? ->%s", ev.et );
                    break;
            }//switch
        }//for
        if ( maxDawdleAwayDelta >= 0 ){
            System.err.printf(
                "max. acceptable delta   occured at location:%d   {@%s}      --->>>      number of smurfs:%d,     delta:%s\n",
                mdadLoc,
                Tool.prettyTime( mdadTime ),
                mdadLeftSmurfCnt,
                Tool.prettyTime( maxDawdleAwayDelta )
            );
            System.err.printf(
                "max. waiting smurf cnt  occured at location:%d   {@%s}      --->>>      number of smurfs:%d\n\n\n",
                mwscLoc,
                Tool.prettyTime( mwscTime ),
                maxWaitingSmurfCnt
            );
        }//if
        
        lineBasedStuffUnchecked = false;
        return true;
    }//chkLineBasedStuff()
    
    
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" )
    final boolean chkCycleBasedStuff( final int[][] route ){
        if ( smurfIdValuesUnchecked )                throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if values of smurf IDs are valid" );
        if ( numberOfSmurfsUnchecked )               throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if number of found smurfs valid" );
        if ( smurfIdConsistencyUnchecked )           throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if found smurf IDs are concistent" );
        if ( smurfActivityCycleUnchecked )           throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if smurf activity cycle valid" );
        if ( smurfBehaviorDuringStopOverUnchecked )  throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if smurf behavior valid" );
        if ( smurfScheduleFollowedUnchecked )        throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if smurf follows schedule" );
        if ( woeIdValuesUnchecked )                  throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if values of WOE IDs are valid" );
        if ( numberOfWOEsUnchecked )                 throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if number of found WOEs valid" );
        if ( woeIdConsistencyUnchecked )             throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if found WOE IDs are concistent" );
        if ( woeActivityCycleUnchecked )             throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if WOE activity cycle valid" );
        if ( woeBehaviorDuringStopOverUnchecked )    throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if WOE behavior valid" );
        if ( woeRouteUnchecked )                     throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if WOE follows route" );
        if ( numberOfLocationsUnchecked )            throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if number of locations valid" );
        if ( generalBehaviorUnchecked )              throw new IllegalStateException( "INTERNAL CHECKING ERROR: still unchecked if general behavior valid" );
        //
        if ( route == null )                         throw new IllegalStateException( "UUUPPS - This might be an INTERNAL ERROR - parameter invalid" );
        
        
        
        class EWoeInfo extends WoeInfo{
            Queue<Integer> half1st;                                             // Queue, since cylce
            Queue<Integer> half2nd;                                             // Queue, since cycle
          //Boolean ascending;                                                  // WOE route ascending ?
            EWoeInfo(){
                half1st  = new LinkedList<Integer>();                           // LinkedList, since removed at front and added at end all the time
                half2nd  = new LinkedList<Integer>();                           // LinkedList, since removed at front and added at end all the time
            }//EWoeInfo
        }//EWoeInfo
        
        
        
        final Global global = Global.getData();
        final int expectedNumberOfSmurfs     =  global.fnos;
        final int expectedNumberOfWOEs       =  global.fnow;
        final int expectedNumberOfLocations  =  global.fnol;
        final int maxNumberOfSmurfPerWOE     =  global.fmnspw;
      //final int maxNumberOfWoePerLocation  =  global.fmnwpl;
        
        
        // compute traces ( reused "check for cycles" code from "chkWoeRoute()" )
        final Map<Integer,List<Integer>> trace = new HashMap<Integer,List<Integer>>();
        for ( RawWOE woe : Global.av_woeList ){                                 // loop over each WOE
            
            // generate trace of WOE
            List<Integer> curTrace = new ArrayList<Integer>();                  // CURrent TRACE of WOE
            for ( Event ev : woe.eventList ){
                if ( ev.et == EventType.W5WP1 )  curTrace.add( ev.woePositionParameter );   // W5WP1 determines stop position
            }//for
            // now, curTrace contains trace of WOE
            
            // check trace for cycles
            boolean cycleFound = false;                                         // NO cycle found, yet
            int clc;                                                            // CycleLengthCandidates
            final int clcBorderValue = curTrace.size() >> 1;                    // to detect cycle, trace must contain cycle at least twice
            chkCLCloop:
            for ( clc=1; clc<=clcBorderValue; clc++ ){                          // lop over CycleLengthCandidates from smallest to largest
                for ( int i=curTrace.size()-1-clc; --i>0; ){                    // now, test the very CycleLengthCandidate
                    if (! curTrace.get(i).equals( curTrace.get(i+clc) )){       // since Integer values are constants, "!=" would be sufficient
                        // candidate failed
                        //
                        continue chkCLCloop;                                    // try next (CycleLength)Candidate
                    }//if
                }//for
                // candidate passed test => (smallest) cycle found
                
                cycleFound = true;                                              // mark cycle found
                break chkCLCloop;                                               // break further search
            }//chkCLCloop-for
            
            if ( cycleFound ){
                trace.put( woe.internalWoeID, curTrace );                       // store (trace-) cycle for individual WOE
                //
                for ( int i=curTrace.size(); --i>=clc; )  curTrace.remove(i);   // remove all entries after 1st cycle
            }else{
                throw new IllegalStateException( "UUUPPS - this might be an INTERNAL ERROR - it was NOT expected to end here" );
            }//if
        }//for
        
        
        
        SmurfInfo[] sia = new SmurfInfo[expectedNumberOfSmurfs];                // Smurf Info Array
        for ( int isi=expectedNumberOfSmurfs; --isi>=0; ){                      // loop over each Internal Smurf-Id
            sia[isi] = new SmurfInfo();                                         // initialize entry
        }//for
        for ( RawSmurf smurf : Global.av_smurfList ){                           // loop over each smurf and define sia entries
            sia[smurf.internalSmurfID].smurf = smurf;
            sia[smurf.internalSmurfID].sched = smurf.schedule.scheduleData;
            sia[smurf.internalSmurfID].sIndx = sia[smurf.internalSmurfID].sched.length-1;
        }//for
        
        EWoeInfo[] wia = new EWoeInfo[expectedNumberOfWOEs];                    // Woe Info Array
        for ( int iwi=expectedNumberOfWOEs; --iwi>=0; ){                        // loop over each Internal Woe Id
            wia[iwi] = new EWoeInfo();                                          //   and define wia entries
            final List<Integer> cRoute = trace.get( iwi );
            int i;
            for ( i=0; i<((1+cRoute.size())>>1); i++ )  wia[iwi].half1st.add( cRoute.get(i) );
            for (    ; i<    cRoute.size()     ; i++ )  wia[iwi].half2nd.add( cRoute.get(i) );
            
            /*
            // compute "ascending" and check for valid cycle (again)            // dadurch dass geforderte cycle erf�llt werden ist Kreis bereits sichergestellt
            final int cRouteLength = cRoute.size();
            int ascCnt = 0;
            int dscCnt = 0;
            int s=0;                                                            // "Start" position index of WOE
            while ( s < cRouteLength ){
                int d = (cRouteLength<=s+1) ? 0 : s+1;                          // "Destination" position index of WOE
                if (       cRoute.get(i) < cRoute.get(d) ){ ascCnt++;
                }else if ( cRoute.get(i) > cRoute.get(d) ){ dscCnt++;
                }else{
                    throw new IllegalStateException( 
                        String.format(
                            "ERROR->WOE-cycle : Why wasN'T this detected in chkGeneralBehavior() before ?; WOE#%d : %d->%d",
                            iwi,
                            cRoute.get(i),
                            cRoute.get(d)
                        )
                    );
                }//if
                s = d;                                                          // old "Destination" becomes new "Start"
            }//while
            //
            if (       dscCnt==1 ){ wia[iwi].ascending = true;                  // once descending and ALL others ASCENDING
            }else if ( ascCnt==1 ){ wia[iwi].ascending = false;                 // once ascending  and ALL others DESCENDING
            }else{
                throw new IllegalStateException( 
                    String.format(
                        "ERROR->WOE-cycle : Why wasN'T this detected in chkGeneralBehavior() before ?; WOE#%d : asc=%d!=1<-?->1!=%d=dsc",
                        iwi,
                        ascCnt,
                        dscCnt
                    )
                );
            }//if
            */
        }//for
        
        LocationInfo[] lia = new LocationInfo[expectedNumberOfLocations];       // Location Info Array
        for ( int ili=expectedNumberOfLocations; --ili>=0; ){                   // loop over each Internal Location Id
            lia[ili] = new LocationInfo();                                      //   and initialize/define lia entries
        }//for
        
        
        /*
        __???__<130531> todo: Zeiten bestimmen
        long minDawdleAwayDelta4SEW = Long.MAX_VALUE;   long maxDawdleAwayDelta4SEW = Long.MIN_VALUE;
        long minDawdleAwayDelta4SLW = Long.MAX_VALUE;   long maxDawdleAwayDelta4SLW = Long.MIN_VALUE;
        long minDawdleAwayDelta4WAP = Long.MAX_VALUE;   long maxDawdleAwayDelta4WAP = Long.MIN_VALUE;
        long minDawdleAwayDelta4WVP = Long.MAX_VALUE;   long mxaDawdleAwayDelta4WVP = Long.MIN_VALUE;
        */
        
        long maxDawdleAwayDelta = Long.MIN_VALUE;      int mdadLoc = Integer.MIN_VALUE;   long mdadTime = Long.MIN_VALUE;   int mdadLeftSmurfCnt = Integer.MIN_VALUE;
        int  maxWaitingSmurfCnt = Integer.MIN_VALUE;   int mwscLoc = Integer.MIN_VALUE;   long mwscTime = Long.MIN_VALUE;
        for ( Event ev : Global.av_eventList ){
            final Integer isi = ev.smurfIdInternal;                             // Internal Smurf ID - null possible if not used
            final Integer iwi = ev.woeIdInternal;                               // Internal WOE ID   - null possible if not used
            switch ( ev.et ){
                    
                case S2EDS:
                    final int smurfPosiS2EDS = ev.smurfPositionParameter;       // current Smurf POSItion
                    sia[isi].time = ev.time;
                    sia[isi].posi = smurfPosiS2EDS;
                    lia[smurfPosiS2EDS].waitingSmurfsCollection.add( isi );
                    if ( maxWaitingSmurfCnt < lia[smurfPosiS2EDS].waitingSmurfsCollection.size() ){
                        // new MAX. (waiting smurf cnt) - store infos about this
                        maxWaitingSmurfCnt = lia[smurfPosiS2EDS].waitingSmurfsCollection.size();    // Max Waiting Smurf CouNT
                        mwscTime = ev.time;                                                         // Max Waiting Smurf Count time    - time of this very moment/event
                        mwscLoc  = smurfPosiS2EDS;                                                  // Max Waiting Smurf Cont LOCation - location of this very moment/event
                    }//if
                    break;
                    
                case S3ENW:
                    final int smurfPosiS3ENW = sia[isi].posi;                   // current Smurf POSItion
                    final int woePosiS3ENW   = wia[iwi].posi;                   // current WOE POSItion
                    //
                    // ist Einsteigen �berhaupt m�glich ? Sind Smurf und WOE an gleicher Position ? (-> Test geh�rt zu chkGeneralBehavior()-S3ENW )
                    if ( smurfPosiS3ENW != woePosiS3ENW ){
                        // Dieser Fehler darf HIER nicht mehr auftreten, da bereits darauf gepr�ft wurde -> Test geh�rt zu chkGeneralBehavior()-S3ENW
                        throw new IllegalStateException(
                            String.format(
                                "\n\nERROR->S3ENW : Why wasN'T this detected in chkGeneralBehavior() before ? Smurf:%d->%d != %d<-WOE:%d   {@%s}\n",
                                ev.smurfIdUser,
                                smurfPosiS3ENW,
                                woePosiS3ENW,
                                ev.woeIdUser,
                                Tool.prettyTime( ev.time )
                            )
                        );
                    }
                    // stimmt die Richtung des WOE ?
                    final Integer smurfTargetS3ENW = sia[isi].sched[ sia[isi].sIndx-1 ].planedPosition;
                    if ( ! wia[iwi].half1st.contains( smurfTargetS3ENW ) ){
                        System.err.printf(
                            "\n\nERROR: Smurf:%d enters WOE:%d  even so WOE goes to wrong direction   {@%s}\n",
                            ev.smurfIdUser,
                            ev.woeIdUser,
                            Tool.prettyTime( ev.time )
                        );
                        System.err.flush();
                        return false;
                    }//if
                    //
                    // smurf an location abmelden
                    if ( ! lia[smurfPosiS3ENW].waitingSmurfsCollection.remove( isi ) )  throw new IllegalStateException( "UUUPPS - this might be an INTERNAL ERROR - this was NOT expected to happen" );
                    //
                    // smurf als Passagier von WOE anmelden
                    wia[iwi].passengerCollection.add( new Integer(isi) );
                    break;
                    
                case S5LVW:
                    if (    ( 0 > --(sia[isi].sIndx) )
                        ||  ( ! wia[iwi].passengerCollection.remove( new Integer(isi) ) )
                    ){
                        throw new IllegalStateException( "UUUPPS - this might be an INTERNAL ERROR - this was NOT expected to happen" );
                    }//if
                    break;
                    
                    
                case W3AAP:
                    wia[iwi].posi = ev.woePositionParameter;
                    if ( wia[iwi].half1st.peek() != wia[iwi].posi )  throw new IllegalStateException( "UUUPPS - this might be an INTERNAL ERROR - this was NOT expected to happen" );
                    wia[iwi].half2nd.add( wia[iwi].half1st.remove() );
                    wia[iwi].half1st.add( wia[iwi].half2nd.remove() );
                    break;
                    
                case W8VFP:
                    if ( wia[iwi].passengerCollection.size() < maxNumberOfSmurfPerWOE ){
                        final Set<Integer> maybe = new HashSet<Integer>();
                        final Set<Integer> error = new HashSet<Integer>();
                        for ( Integer cisi : lia[ wia[iwi].posi ].waitingSmurfsCollection ){
                            if ( sia[cisi].sIndx > 0 ){
                                if ( wia[iwi].half1st.contains( sia[cisi].sched[ sia[cisi].sIndx-1 ].planedPosition ) ){
                                    long delta = ev.time - sia[cisi].time;
                                    if ( delta > DawdleThreshold4Error ){                   // delta >  4[ms]             => ERROR
                                        error.add( cisi );
                                    }else if ( delta >= DawdleThreshold4Warning ){          // 4[ms] >= delta >= 500[us]  => MAYBE error
                                        maybe.add( cisi );
                                    }//if
                                    if ( delta > maxDawdleAwayDelta ){
                                        maxDawdleAwayDelta = delta;
                                        mdadTime           = ev.time;
                                        mdadLoc            = ev.woePositionParameter;
                                        mdadLeftSmurfCnt   = lia[ wia[iwi].posi ].waitingSmurfsCollection.size();
                                    }//if
                                }//if
                            }//if
                        }//for
                        if ( ! maybe.isEmpty() ){
                            System.err.printf(
                                "ATTENTION: WOE:%d with %d passenger(s) and capacity of %d   at position:%d (with %d remaining smurf(s)) heading to position:%d)   was NOT taken by %d smurf(s)   {@%s}\n",
                                ev.woeIdUser,
                                wia[iwi].passengerCollection.size(),
                                maxNumberOfSmurfPerWOE,
                                ev.woePositionParameter,
                                lia[ wia[iwi].posi ].waitingSmurfsCollection.size(),
                                wia[iwi].half1st.peek(),
                                maybe.size(),
                                Tool.prettyTime( ev.time )
                            );
                            for ( Integer cisi : maybe ){
                                System.err.printf(
                                    "  |~~>  smurf:%d   (claims to be) at position:%d heading to position:%d   (delta:%s)\n",     /* "hopefully"="pretty sure", since otherwise there should have been an alarm before*/
                                    sia[cisi].smurf.identify(),
                                    sia[cisi].sched[ sia[cisi].sIndx ].planedPosition,
                                    sia[cisi].sched[ sia[cisi].sIndx-1 ].planedPosition,
                                    Tool.prettyTime( ev.time - sia[cisi].time )
                                );
                            }//for
                            System.err.println( "  delta(s) < 4[ms]   maybe acceptable\n\n" );
                            System.err.flush();
                        }//if
                        if ( ! error.isEmpty() ){
                            System.err.printf(
                                "\n\nERROR: WOE:%d with %d:passenger(s) and capacity of %d   at position:%d (with %d remaining smurf(s)) heading to position:%d)   was NOT taken by %d smurf(s)   {@%s}\n",
                                ev.woeIdUser,
                                wia[iwi].passengerCollection.size(),
                                maxNumberOfSmurfPerWOE,
                                ev.woePositionParameter,
                                lia[ wia[iwi].posi ].waitingSmurfsCollection.size(),
                                wia[iwi].half1st.peek(),
                                error.size(),
                                Tool.prettyTime( ev.time )
                            );
                            for ( Integer cisi : error ){
                                System.err.printf(
                                    "  |~~>  smurf:%d   (claims to be) at position:%d heading to position:%d   (delta:%s)\n",     /* "hopefully"="pretty sure", since otherwise there should have been an alarm before*/
                                    sia[cisi].smurf.identify(),
                                    sia[cisi].sched[ sia[cisi].sIndx ].planedPosition,
                                    sia[cisi].sched[ sia[cisi].sIndx-1 ].planedPosition,
                                    Tool.prettyTime( ev.time - sia[cisi].time )
                                );
                            }//for
                            System.err.printf(
                                "  [%s]   {@%s}\n\n",
                                ev.et,
                                Tool.prettyTime( ev.time )
                            );
                            System.err.flush();
                            if ( global.heavyTest ){
                                System.err.printf( "-BREAK-BREAK-BREAK-BREAK-BREAK-BREAK-BREAK-BREAK-BREAK-BREAK-BREAK-BREAK-BREAK-BREAK-BREAK-BREAK-BREAK-BREAK-BREAK-BREAK-BREAK-BREAK-BREAK-\n" );    // _HERE_120519
                                System.err.printf( "-^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^-\n" );    // _HERE_120519
                                System.err.printf( "-#########################################################################################################################################-\n\n" );  // _HERE_120519
                                System.err.flush();
                            }else{
                                return false;
                            }//if
                        }//if
                    }//if
                    break;
                    
                    
                default:                                                        // keep compiler happy ;-)
                  //assert false : String.format( "Wie komm ich hier her? ->%s", ev.et );
                    break;
            }//switch
        }//for
        if ( maxDawdleAwayDelta >= 0 ){
            System.err.printf(
                "max. acceptable delta   occured at location:%d   {@%s}      --->>>      number of smurfs:%d,     delta:%s\n",
                mdadLoc,
                Tool.prettyTime( mdadTime ),
                mdadLeftSmurfCnt,
                Tool.prettyTime( maxDawdleAwayDelta )
            );
            System.err.printf(
                "max. waiting smurf cnt  occured at location:%d   {@%s}      --->>>      number of smurfs:%d\n\n\n",
                mwscLoc,
                Tool.prettyTime( mwscTime ),
                maxWaitingSmurfCnt
            );
        }//if
        
        cycleBasedStuffUnchecked = false;
        return true;
    }//chkCycleBasedStuff()
    
    
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" )
    @SuppressWarnings("unused")
    final boolean chkLineRequestBasedStuff(){
        if ( true )  throw new IllegalStateException( "NOT supported yet" );
        return true;
    }//chkLineRequestBasedStuff()
    
    
    final boolean chkNumberOfWoePerLocation(){
        
        class WaitingWoeList{
            Set<Integer> wl;                                                    // waiting Woe List
            WaitingWoeList(){ wl = new HashSet<Integer>(); }
        }//WaitingWoeList
        
        
        final Global global = Global.getData();
        final int expectedNumberOfLocations  =  global.fnol;
        final int maxNumberOfWoePerLocation  =  global.fmnwpl;        
        
        WaitingWoeList[] la = new WaitingWoeList[expectedNumberOfLocations];         // Location Array
        for ( int i=la.length; --i>=0; ) la[i] = new WaitingWoeList();
        
        for ( Event ev : Global.av_eventList ){
            final Integer iwi = ev.woeIdInternal;                               // Internal WOE ID - null possible if not used
            switch ( ev.et ){
                
                case W3AAP:
                    la[ev.woePositionParameter].wl.add( iwi );
                    if ( la[ev.woePositionParameter].wl.size() > maxNumberOfWoePerLocation ){
                        System.err.printf(
                            "ERROR: too many WOE at location %d ; #WOE:%d > %d   [%s]___{@%s}\n",
                            ev.woePositionParameter,
                            la[ev.woePositionParameter].wl.size(),
                            maxNumberOfWoePerLocation,
                            ev.et,
                            Tool.prettyTime( ev.time )
                        );
                        System.err.print( "\\--> WOEs:" );
                        for ( Integer i : la[ev.woePositionParameter].wl ){
                            System.err.printf( "  %d", i );
                        }//for
                        System.err.println();
                        System.err.flush();
                        return false;
                    }//if
                    break;
                    
                case W8VFP:
                    la[ev.woePositionParameter].wl.remove( iwi );
                    break;
                    
                    
                default:                                                        // keep compiler happy ;-)
                  //assert false : String.format( "Wie komm ich hier her? ->%s", ev.et );
                    break;
            }//switch
        }//for
        
        
        return true;
    }//chkNumberOfWoePerLocation()
    
}//class Analyzer

// Leben alle Schiffe nach dem letzten SmurfLastDeed noch?