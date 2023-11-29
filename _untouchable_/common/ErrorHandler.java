package _untouchable_.common;


import  java.util.*;


@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2013/05/20",
    currentRevision = "0.93",
    lastModified    = "2013/05/28",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
//the WHOLE CLASS and ALL of its ELEMENTS are friendly on purpose (!)
class ErrorHandler {
    
    @ChunkPreamble ( lastModified="2013/05/28", lastModifiedBy="Michael Schäfers" )
    void reportError( final ErrorType errTyp ){
        System.out.flush();
        switch (errTyp){
            
            case UNEXPECTED_NUMBER_OF_SMURF :
                final int expectedNumberOfSmurfs  =  (null!=Global.getData().rnos)  ?  Global.getData().rnos  :  Global.getData().wnos;
                System.err.printf(
                    "ERROR : unexpected number of smurfs -> found:%d != %d(=expected)\n",
                    CommonSmurf.smurfCnt.get(),
                    expectedNumberOfSmurfs
                );
            break;
            //
            case UNEXPECTED_NUMBER_OF_WOE :
                final int expectedNumberOfWOEs  =  (null!=Global.getData().rnow)  ?  Global.getData().rnow  :  Global.getData().wnow;
                System.err.printf(
                    "ERROR : unexpected number of WOEs -> found:%d != %d(=expected)\n",
                    CommonWOE.woeCnt.get(),
                    expectedNumberOfWOEs
                );
            break;
            //
            default :
                System.err.printf(
                    "internal error : unexpected call of this method with parameter  %s\n",
                    errTyp
                );
            throw new IllegalStateException( "internal error" );
            
        }//switch
        System.err.flush();
    }//method()
    
    
    
    @ChunkPreamble ( lastModified="2013/05/28", lastModifiedBy="Michael Schäfers" )
    void reportError( final ErrorType errTyp,  final Set<Integer> set ){
        System.out.flush();
        switch (errTyp){
            
            case UNEXPECTED_NUMBER_OF_ID_SMURF :
                final int expectedNumberOfSmurfs  =  (null!=Global.getData().rnos)  ?  Global.getData().rnos  :  Global.getData().wnos;
                System.err.printf(
                    "ERROR : unexpected number of smurf IDs -> found:%d != %d(=expected)\n",
                    set.size(),
                    expectedNumberOfSmurfs
                );
                if ( set.size() < 100 ){
                    System.err.printf( "\\--> found smurf IDs:" );
                    for ( Integer sid : set ){
                        System.err.printf( "  %d",  sid );
                    }//for
                    System.err.printf( "\n" );
                }//if
            break;
            //
            case UNEXPECTED_NUMBER_OF_ID_WOE :
                final int expectedNumberOfWOEs  =  (null!=Global.getData().rnow)  ?  Global.getData().rnow  :  Global.getData().wnow;
                System.err.printf(
                    "ERROR : unexpected number of WOE IDs -> found:%d != %d(=expected)\n",
                    set.size(),
                    expectedNumberOfWOEs
                );
                if ( set.size() < 100 ){
                    System.err.printf( "\\--> found WOE IDs:" );
                    for ( Integer wid : set ){
                        System.err.printf( "  %d",  wid );
                    }//for
                    System.err.printf( "\n" );
                }//if
            break;
            //
            case UNEXPECTED_NUMBER_OF_LOCATION :
                final int expectedNumberOfLocations  =  (null!=Global.getData().rnol)  ?  Global.getData().rnol  :  Global.getData().wnol;
                System.err.printf(
                    "ERROR : unexpected number of locations -> found:%d != %d(=expected)\n",
                    set.size(),
                    expectedNumberOfLocations
                );
                if ( set.size() < 100 ){
                    System.err.printf( "\\--> found locations:" );
                    for ( Integer location : set ){
                        System.err.printf( "  %d",  location );
                    }//for
                    System.err.printf( "\n" );
                }//if
            break;
            //
            default :
                System.err.printf(
                    "internal error : unexpected call of this method with parameter  %s\n",
                    errTyp
                );
            throw new IllegalStateException( "internal error" );
            
        }//switch
        System.err.flush();
    }//method()
    
    
    
    @ChunkPreamble ( lastModified="2013/05/28", lastModifiedBy="Michael Schäfers" )
    void reportError( final ErrorType errTyp,  final Event ev ){
        System.out.flush();
        switch (errTyp){
            
            case INCONSISTENCY_LOCATION_SMURF :
                System.err.printf(
                    "ERROR : location inconsistency: smurf:%d   parameter = %d != %d = smurf.locate()   [%s]   {@%s}\n",
                    ev.smurfIdUser,
                    ev.smurfPositionParameter,
                    ev.smurfPositionLocated,
                    ev.et,
                    Tool.prettyTime( ev.time )
                );
            break;
            //
            case INCONSISTENCY_LOCATION_WOE :
                System.err.printf(
                    "ERROR : location inconsistency: WOE:%d   parameter = %d != %d = WOE.locate()   [%s]___{@%s}\n",
                    ev.woeIdUser,
                    ev.woePositionParameter,
                    ev.woePositionLocated,
                    ev.et,
                    Tool.prettyTime( ev.time )
                );
            break;
            
            
            case UNEXPECTED_ID_SMURF :
                System.err.printf(
                    "ERROR : smurf ID has unexpected value:%d   {@%s}\n",
                    ev.smurfIdUser,
                    Tool.prettyTime( ev.time )
                );
            break;
            //
            case UNEXPECTED_ID_WOE :
                System.err.printf(
                    "ERROR : WOE ID has unexpected value:%d   {@%s}\n",
                    ev.woeIdUser,
                    Tool.prettyTime( ev.time )
                );
            break;
            
            
            /*
            case UNEXPECTED_POSITION_SMURF :                                    // locate() removed => obsolete
                System.err.printf(
                    "ERROR : smurf.locate() has unexpected value:%d   {@%s}\n",
                    ev.smurfPositionLocated,
                    Tool.prettyTime( ev.time )
                );
            break;
            //
            case UNEXPECTED_POSITION_WOE :                                      // locate() removed => obsolete
                System.err.printf(
                    "ERROR : (user defined -> WOE.locate() ) location ID has unexpected value:%d   {@%s}\n",
                    ev.woePositionLocated,
                    Tool.prettyTime( ev.time )
                );
            break;
            */
            
            case UNEXPECTED_POSITION_PARAMETER_SMURF :
                System.err.printf(
                    "ERROR : smurf position parameter has unexpected value:%d   {@%s}\n",
                    ev.smurfPositionParameter,
                    Tool.prettyTime( ev.time )
                );
            break;
            //
            case UNEXPECTED_POSITION_PARAMETER_WOE :
                System.err.printf(
                    "ERROR : WOE position parameter has unexpected value:%d   {@%s}\n",
                    ev.woePositionParameter,
                    Tool.prettyTime( ev.time )
                );
            break;
            
            
            case FAULTY_BEHAVIOR_SMURF :
                System.err.printf(
                    "ERROR : smurf:%d behaves faulty   {@%s}\n",
                    ev.smurfIdUser,
                    Tool.prettyTime( ev.time )
                );
                System.err.printf(
                    "\\--> unexpected 1st action of smurf -> [%s]   {@%s}\n",
                    ev.et,
                    Tool.prettyTime( ev.time )
                );
            break;
            //
            case FAULTY_BEHAVIOR_WOE :
                System.err.printf(
                    "ERROR : WOE:%d behaves faulty   {@%s}\n",
                    ev.woeIdUser,
                    Tool.prettyTime( ev.time )
                );
                System.err.printf(
                    "\\--> unexpected 1st action of WOE -> [%s]   {@%s}\n",
                    ev.et,
                    Tool.prettyTime( ev.time )
                );
            break;
            
            
            default :
                System.err.printf(
                    "internal error : unexpected call of this method with parameter  %s\n",
                    errTyp
                );
            throw new IllegalStateException( "internal error" );
            
        }//switch
        System.err.flush();
    }//method()
    
    
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    void reportError( final ErrorType errTyp,  final Event ev,  final Event[] eva ){
        System.out.flush();
        switch (errTyp){
            
            case FAULTY_BEHAVIOR_SMURF :
                if ( eva[ev.smurfIdInternal] == null ){                         // __???__<130616> wirklich hier? UNSCH�N
                    reportError( errTyp, ev );                                  //
                }else{
                    System.err.printf(
                        "ERROR : smurf:%d behaves faulty - activity cycle suspicious   {@%s}\n",
                        ev.smurfIdUser,
                        Tool.prettyTime( ev.time )
                    );
                    System.err.printf(
                        "\\--> [%s]___{@%s}     and last [%s]___{@%s}\n",
                        ev.et,
                        Tool.prettyTime( ev.time ),
                        eva[ev.smurfIdInternal].et,
                        Tool.prettyTime( eva[ev.smurfIdInternal].time )
                    );
                }//if
            break;
            //
            case FAULTY_BEHAVIOR_WOE :
                if ( eva[ev.woeIdInternal] == null ){                           // __???__<130616> wirklich hier? UNSCH�N
                    reportError( errTyp, ev );                                  //
                }else{
                    System.err.printf(
                        "ERROR : WOE:%d behaves faulty - activity cycle suspicious   {@%s}\n",
                        ev.woeIdUser,
                        Tool.prettyTime( ev.time )
                    );
                    System.err.printf(
                        "\\--> [%s]___{@%s}     and last [%s]___{@%s}\n",
                        ev.et,
                        Tool.prettyTime( ev.time ),
                        eva[ev.woeIdInternal].et,
                        Tool.prettyTime( eva[ev.woeIdInternal].time )
                    );
                }//if
            break;
            //
            default :
                System.err.printf(
                    "internal error : unexpected call of this method with parameter  %s\n",
                    errTyp
                );
            throw new IllegalStateException( "internal error" );
            
        }//switch
        System.err.flush();
    }//method()            
    
    
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    void reportError( final ErrorType errTyp,  final Event ev,  final EvTab_IDxTime[] da ){
        System.out.flush();
        switch (errTyp){
            
            case ACTIVE_AFTER_LASTDEED_SMURF :
                System.err.printf(
                    "ERROR : smurf did his last deed before ->   smurf ID:%d   {@%s  (before %s)}\n",
                    ev.smurfIdUser,
                    Tool.prettyTime( ev.time ),
                    Tool.prettyTime( da[ev.smurfIdInternal].time )
                );
            break;
            //
            case ACTIVE_AFTER_LASTDEED_WOE :
                System.err.printf(
                    "ERROR : WOE did his last deed before ->   WOE ID:%d   {@%s  (before %s)}\n",
                    ev.woeIdUser,
                    Tool.prettyTime( ev.time ),
                    Tool.prettyTime( da[ev.woeIdInternal].time )
                );
            break;
            
            
            case FAULTY_VISIBILITY_SMURF :
                System.err.printf(
                    "ERROR : visibility problem -> smurf ID  %d != %d (=smurf ID from before)   {@%s  (before %s)}\n",
                    ev.smurfIdUser,
                    da[ev.smurfIdInternal].id,
                    Tool.prettyTime( ev.time ),
                    Tool.prettyTime( da[ev.smurfIdInternal].time )
                );
            break;
            //
            case FAULTY_VISIBILITY_WOE :
                System.err.printf(
                    "ERROR : visibility problem -> WOE ID  %d != %d (=WOE ID from before)   {@%s  (before %s)}\n",
                    ev.smurfIdUser,
                    da[ev.woeIdInternal].id,
                    Tool.prettyTime( ev.time ),
                    Tool.prettyTime( da[ev.woeIdInternal].time )
                );
            break;
            //
            default :
                System.err.printf(
                    "internal error : unexpected call of this method with parameter  %s\n",
                    errTyp
                );
            throw new IllegalStateException( "internal error" );
            
        }//switch
        System.err.flush();            
    }//method()
    
    
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    void reportError( final ErrorType errTyp,  final Event ev,  final int iState,  final int expectedState,  final int nextExpectedState ) {
        System.out.flush();
        switch (errTyp){
            
            case FAULTY_BEHAVIOR_SMURF :
                System.err.printf(
                    "ERROR : smurf:%d behaves faulty   [%s]___{@%s}   (%d#%d~%d)\n",
                    ev.smurfIdUser,
                    ev.et,
                    Tool.prettyTime(ev.time ),
                    iState, expectedState, nextExpectedState
                );
            break;
            //
            case FAULTY_BEHAVIOR_WOE :
                System.err.printf(
                    "ERROR : WOE:%d behaves faulty   [%s]___{@%s}   (%d#%d~%d)\n",
                    ev.woeIdUser,
                    ev.et,
                    Tool.prettyTime(ev.time ),
                    iState, expectedState, nextExpectedState
                );
            break;
            //
            default :
                System.err.printf(
                    "internal error : unexpected call of this method with parameter  %s\n",
                    errTyp
                );
            throw new IllegalStateException( "internal error" );
            
        }//switch
        System.err.flush();
    }//method()
    
    
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    void reportError( final ErrorType errTyp,  final Event ev,  final int posi ) {
        System.out.flush();
        switch (errTyp){
            
            case FAULTY_BEHAVIOR_SMURF_SCHEDULE :                               // posi <=> planedPosition
                System.err.printf(
                    "ERROR: smurf:%d does NOT follow his schedule   {@%s}\n",
                    ev.smurfIdUser,
                    Tool.prettyTime( ev.time )
                );
                System.err.printf(
                    "\\--> smurf.locate():%d != %d   {@%s}\n",
                    ev.smurfPositionLocated,
                    posi,
                    Tool.prettyTime( ev.time )
                );
            break;
            
            
            case INCONSISTENCY_LOCATION_SMURF_S2EDS :                           // posi <=> position smurf left WOE
                System.err.printf(
                    "ERROR: position of smurf%d changed ->   last = %d != %d = current   [%s]___{@%s}\n\n",     // __???__<130529> check message
                    ev.smurfIdUser,
                    posi,
                    ev.smurfPositionParameter,
                    ev.et,
                    Tool.prettyTime( ev.time )
                );
            break;
            
            
            case INCONSISTENCY_LOCATION_WOE_W1MO1 :                             // posi <=> position WOE appeared at "location"
            case INCONSISTENCY_LOCATION_WOE_W2MO2 :                             // posi <=> position WOE appeared at "location"
            case INCONSISTENCY_LOCATION_WOE_W3AAP :                             // posi <=> position WOE appeared at "location"
            case INCONSISTENCY_LOCATION_WOE_W5WP1 :                             // posi <=> position WOE appeared at "location"
            case INCONSISTENCY_LOCATION_WOE_W6WP2 :                             // posi <=> position WOE appeared at "location"
            case INCONSISTENCY_LOCATION_WOE_W8VFP :                             // posi <=> position WOE appeared at "location"
                System.err.printf(
                    "ERROR: position of WOE:%d changed ->   last = %d != %d = current   [%s]___{@%s}\n\n",     // __???__<130529> check message
                    ev.woeIdUser,
                    posi,
                    ev.woePositionParameter,
                    ev.et,
                    Tool.prettyTime( ev.time )
                );
            break;
            
            
            
            default :
                System.err.printf(
                    "internal error : unexpected call of this method with parameter  %s\n",
                    errTyp
                );
            throw new IllegalStateException( "internal error" );
            
        }//switch
        System.err.flush();
    }//method()
    
}//class
