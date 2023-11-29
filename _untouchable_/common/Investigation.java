package _untouchable_.common;


import java.util.concurrent.TimeUnit;


@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2013/06/03",
    currentRevision = "0.93",
    lastModified    = "2013/06/03",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
    //
    // History:
    // ========
    // 2013/05/12: CommonTestAndEnvironment_A.av_startDelayedChecks() moved here to Investigator.investigate()
)
// the WHOLE CLASS and ALL of its ELEMENTS are friendly on purpose (!)
class Investigation {
    
    /*
     * #########################################################################
     * ###
     * ### "MAGIC NUMBERs" - das muss besser werden   __???__<130601>
     * ###
     */
    @ChunkPreamble ( lastModified="2013/06/01", lastModifiedBy="Michael Schäfers" )
    static final boolean EnableReportForUncriticalEventCollision = false;
    @ChunkPreamble ( lastModified="2013/06/01", lastModifiedBy="Michael Schäfers" )
    static final boolean EnableReportForCriticalEventCollision = true;
    
    
    
    
    
    @ChunkPreamble ( lastModified="2013/05/30", lastModifiedBy="Michael Schäfers" )
    static void investigate( final int[][] woeRoutes,  final MovementStyle chk ){
        
        try{
            TimeUnit.SECONDS.sleep( 5 );
            //
            System.err.printf( "\n\n\n################################################################################\n" );
            System.err.printf( "NOTE: VERIFICATION starts now : %s\n", Tool.prettyTime( Tool.nanoElapsedTime() ) );
            System.err.flush();
            
            Global.av_syncWithGlobalMutex.lock();                               // NEVER unlock - ALL actions are considered to have finished !
            
            boolean allSmurfHaveTerminated = true;
            chkSmurfTerminatedLoop:
            while ( Global.av_smurfList.size() < Global.getData().wnos ){
                if ( ! Global.av_condVar.await( 1, TimeUnit.SECONDS ) ){
                    allSmurfHaveTerminated = false;
                    break chkSmurfTerminatedLoop;
                }//if
            }//while-chkSmurfTerminatedLoop
            if ( ! allSmurfHaveTerminated ){
                System.err.printf( "ERROR: Either some smurfs have NOT terminated yet or they did NOT do their last deed -> %d\n",  Global.getData().wnos - Global.av_smurfList.size() );
                System.err.flush();
                throw new IllegalStateException( "Exactly the expected number of smurfs have to terminate!" );
            }//if
            //
            // all smurfs have finished their business
            
            boolean allWoeHaveTerminated = true;
            chkWoeTerminatedLoop:
            while ( Global.av_woeList.size() < Global.getData().wnow ){
                if ( ! Global.av_condVar.await( 10, TimeUnit.SECONDS ) ){
                    allWoeHaveTerminated = false;
                    break chkWoeTerminatedLoop;
                }//if
            }//while-chkWoeTerminatedLoop
            if ( ! allWoeHaveTerminated ){
                System.err.printf( "ERROR: Either some WOE have NOT terminated yet or they did NOT do their last deed -> %d\n",  Global.getData().wnow - Global.av_woeList.size() );
                System.err.flush();
                throw new IllegalStateException( "Exactly the expected number of WOE(s) have to terminate!" );
            }//if
            //
            // all WOE have terminated
            
            
            
            System.err.printf( "--------------------------------------------------------------------------------\n" );
            System.err.printf( "\n%s\n", Tool.prettyTime( Tool.nanoElapsedTime() ) );
            System.err.printf( "all actions from smurfs and WOEs are expected to be stopped\n" );
            System.err.flush();
            
            System.err.printf( "collectings lists\n" );
            
            
            System.err.printf( "sorting events...\n" );
            System.err.flush();
            {
                int merkIndex = Integer.MIN_VALUE;
                int[] listIndex = new int[Global.av_eventLLst.size()];
                int locWarnCnt = 0;
                int locNoteCnt = 0;
                long minDeltaTime = Long.MAX_VALUE;
                
                for ( int i=0; i<Global.av_eventLLst.size(); i++ )  listIndex[i]=0;
                //
                loop:while(true){
                    boolean finished = true;                                    // start with assumption "finished" and check it
                    Event ev = new Event(
                        EventType.OTHER,                                                    // et
                        Long.MAX_VALUE,                                                     // time
                        null,                                                               // lastEvent
                        null,                                                               // current thread id
                        //
                        null,                                                               // smurfIdInternal
                        null,                                                               // smurfIdUser
                        null,                                                               // smurfPositionLocated
                        null,                                                               // smurfPositionParameter
                        null,                                                               // smurfRequested
                        //
                        null,                                                               // woeIdInternal
                        null,                                                               // woeIdUser
                        null,                                                               // woePositionLocated
                        null                                                                // woePositionParameter
                    );
                    for ( int i=0; i<Global.av_eventLLst.size(); i++ ){
                        if ( listIndex[i] < Global.av_eventLLst.get( i ).size() ){
                            finished = false;                                   // mark that there is still something to do
                            
                            long deltaTime = ev.time - Global.av_eventLLst.get( i ).get( listIndex[i] ).time;
                            if ( deltaTime == 0 ){
                                
                                int order = ev.et.ordinal() - Global.av_eventLLst.get( i ).get( listIndex[i] ).et.ordinal();
                                if ( order == 0 ){
                                    // means 2 different threads with same event(type) -> uncritical
                                    if ( EnableReportForUncriticalEventCollision  &&  locNoteCnt < 10 ){
                                        System.err.printf(
                                            "INTERNAL NOTE: uncritical event collision -> 2 events with same time stamp -> %d = %d = %s\n",
                                            Global.av_eventLLst.get( i ).get( listIndex[i] ).time,
                                            ev.time,
                                            Tool.prettyTime( ev.time )
                                        );
                                    }//if
                                    locNoteCnt++;
                                }else if (
                                        ( ev.et != EventType.S5LVW  ||  Global.av_eventLLst.get( i ).get( listIndex[i] ).et != EventType.S3ENW  ||  ev.woeIdInternal != Global.av_eventLLst.get( i ).get( listIndex[i] ).woeIdInternal )
                                    &&  ( ev.et != EventType.S3ENW  ||  Global.av_eventLLst.get( i ).get( listIndex[i] ).et != EventType.S5LVW  ||  ev.woeIdInternal != Global.av_eventLLst.get( i ).get( listIndex[i] ).woeIdInternal )
                                    
                                    &&  ( ev.et != EventType.W3AAP  ||  Global.av_eventLLst.get( i ).get( listIndex[i] ).et != EventType.W8VFP  ||  ev.woePositionParameter != Global.av_eventLLst.get( i ).get( listIndex[i] ).woePositionParameter )
                                    &&  ( ev.et != EventType.W8VFP  ||  Global.av_eventLLst.get( i ).get( listIndex[i] ).et != EventType.W3AAP  ||  ev.woePositionParameter != Global.av_eventLLst.get( i ).get( listIndex[i] ).woePositionParameter )
                                    
                                    &&  ( ev.et != EventType.S5LVW  ||  Global.av_eventLLst.get( i ).get( listIndex[i] ).et != EventType.W3AAP  ||  ev.woeIdInternal != Global.av_eventLLst.get( i ).get( listIndex[i] ).woeIdInternal )
                                    &&  ( ev.et != EventType.W3AAP  ||  Global.av_eventLLst.get( i ).get( listIndex[i] ).et != EventType.S5LVW  ||  ev.woeIdInternal != Global.av_eventLLst.get( i ).get( listIndex[i] ).woeIdInternal )
                                    
                                    &&  ( ev.et != EventType.S3ENW  ||  Global.av_eventLLst.get( i ).get( listIndex[i] ).et != EventType.W8VFP  ||  ev.woeIdInternal != Global.av_eventLLst.get( i ).get( listIndex[i] ).woeIdInternal )
                                    &&  ( ev.et != EventType.W8VFP  ||  Global.av_eventLLst.get( i ).get( listIndex[i] ).et != EventType.S3ENW  ||  ev.woeIdInternal != Global.av_eventLLst.get( i ).get( listIndex[i] ).woeIdInternal )
                                ){
                                    // uncritical
                                    if ( EnableReportForUncriticalEventCollision  &&  locNoteCnt < 100 ){
                                        System.err.printf(
                                            "INTERNAL NOTE: uncritical event collision (if visibility correct) -> 2 events with same time stamp -> %d = %d = %s\n",
                                            Global.av_eventLLst.get( i ).get( listIndex[i] ).time,
                                            ev.time,
                                            Tool.prettyTime( ev.time )
                                        );
                                    }//if
                                    locNoteCnt++;
                                }else{
                                    // means 2 different Threads with different event(type) -> CRITICAL
                                    if ( EnableReportForCriticalEventCollision  &&  locWarnCnt < 100 ){
                                        System.err.printf(
                                            "INTERNAL PROBLEM: CRITICAL(!) event collision -> 2 with same time stamp -> %d = %d = %s  <<<---- INTERNAL PROBLEM\n",
                                            Global.av_eventLLst.get( i ).get( listIndex[i] ).time,
                                            ev.time,
                                            Tool.prettyTime( ev.time )
                                        );
                                        System.err.printf( "--> Automatic ordering of events:\n" );
                                        if ( order < 0 ){
                                            System.err.printf( "--> %s\n",  ev );
                                            System.err.printf( "--> %s\n",  Global.av_eventLLst.get( i ).get( listIndex[i] ) );
                                            minDeltaTime = 0;
                                        }else{
                                            System.err.printf( "--> %s\n",  Global.av_eventLLst.get( i ).get( listIndex[i] ) );
                                            System.err.printf( "--> %s\n",  ev );
                                            ev = Global.av_eventLLst.get( i ).get( listIndex[i] );
                                            merkIndex = i;
                                            minDeltaTime = 0;
                                        }//if
                                    }//if
                                    locWarnCnt++;
                                }//if
                            }else if ( deltaTime > 0 ){
                                ev = Global.av_eventLLst.get( i ).get( listIndex[i] );
                                merkIndex = i;
                                if ( deltaTime < minDeltaTime )  minDeltaTime = deltaTime;
                            }//if
                        }//if
                    }//for
                    //
                    if (finished)  break loop;
                    //
                    Global.av_eventList.add( ev );
                    listIndex[merkIndex]++;
                }//loop
                System.err.printf( "critical   event collisions :%7d ->%8.4f%%\n",  locWarnCnt, 100.0*locWarnCnt/Global.av_eventList.size() );
                System.err.printf( "uncritical event collisions :%7d ->%8.4f%%\n",  locNoteCnt, 100.0*locNoteCnt/Global.av_eventList.size() );
                System.err.printf( "number of events :           %7d\n", Global.av_eventList.size() );
                System.err.printf( "min. time delta =%23s\n",  Tool.prettyTime( minDeltaTime ) );
            }//scope
            System.err.printf( "events sorted / global event list generated\n" );
            System.err.flush();
            
            
            System.err.printf( "################################################################################\n" );
            System.err.printf( "%s\n", Tool.prettyTime( Tool.nanoElapsedTime() ) );
            System.err.flush();
            
            
            
            
            Analyzer analyzer = Analyzer.createSingleInstanceOfAnalyzer( Global.av_eventList );
            //
            if (! analyzer.chkIfUserSmurfIDsPositive() )      throw new IllegalStateException( "negative smurf ID found" );
            if (! analyzer.chkIfUserWoeIDsPositive() )        throw new IllegalStateException( "negative woe ID found" );
            if (! analyzer.chkIfUserLocationIDsPositive() )   throw new IllegalStateException( "negative location ID found" );
            //
            if (! analyzer.chkSmurfNo() )                     throw new IllegalStateException( "unexpected number of smurfs found" );
            if (! analyzer.chkWoeNo() )                       throw new IllegalStateException( "unexpected number of WOEs found" );
            if (! analyzer.chkLocationNo() )                  throw new IllegalStateException( "unexpected number of locations found" );
            //
            if (! analyzer.chkSmurfIdConsistency() )          throw new IllegalStateException( "smurf ID related visibilty problem? -> inconsistent smurf IDs" );
            if (! analyzer.chkWoeIdConsistency() )            throw new IllegalStateException( "WOE ID related visibilty problem? -> inconsistent WOE IDs" );
            //
            if (! analyzer.chkSmurfsLastDeed() )              throw new IllegalStateException( "unexpected smurf behavior -> problem with last deed of smuf" );
            if (! analyzer.chkWOEsLastDeed() )                throw new IllegalStateException( "unexpected WOE behavior -> problem with last deed of WOE" );
            //
            if (! analyzer.chkSmurfActivityCycle() )          throw new IllegalStateException( "unexpected smurf activity cycle" );
            if (! analyzer.chkWoeActivityCycle() )            throw new IllegalStateException( "unexpected WOE activity cycle" );
            //
            if (! analyzer.chkIfSmurfFollowsHisSchedule() )   throw new IllegalStateException( "smurf has NOT followed his schedule" );
            if (! analyzer.chkSmurfBehaviorDuringStopOver() ) throw new IllegalStateException( "smurf has changed position during stop over" );
            //
            if (! analyzer.chkWoeRoute( woeRoutes ) )         throw new IllegalStateException( "WOE has NOT followed requested route" );
            if (! analyzer.chkWoeBehaviorDuringStopOver() )   throw new IllegalStateException( "WOE has changed position during stop over" );
            //
            if (! analyzer.chkGeneralBehavior() )             throw new IllegalStateException( "General behavior faulty" );
            //
            if ( null!=Global.getData().rmnwpl  ||  null!=Global.getData().rmnwpl ){
                if (! analyzer.chkNumberOfWoePerLocation() )  throw new IllegalStateException( "too many WOE at location" );
            }//if
            //
            switch ( chk ){
                case LineBusStyle:
                    if (! analyzer.chkLineBasedStuff( woeRoutes ) )   throw new IllegalStateException( "smurf<->WOE interaction on line based WOE route faulty" );
                    break;
                case CircleShipStyle:
                    if (! analyzer.chkCycleBasedStuff( woeRoutes ) )  throw new IllegalStateException( "smurf<->WOE interaction on cycle based WOE route faulty" );
                    break;
                case RequestElevatorStyle:
                    if (! analyzer.chkLineRequestBasedStuff() )       System.err.println( "tbd" ); // throw new IllegalStateException( "tbd" );
                    break;
            }//switch
            //
            System.err.printf( "NOTE:  no errors found during random based test => code might be ok\nthis is THE END of the test after %s",  Tool.prettyTime( Tool.nanoElapsedTime() ) );
            System.err.flush();
            
            
        }catch( InterruptedException ex ){
            ex.printStackTrace();
        }finally{
            Global.av_syncWithGlobalMutex.unlock();
        }//try
        
    }//method()

}//class
