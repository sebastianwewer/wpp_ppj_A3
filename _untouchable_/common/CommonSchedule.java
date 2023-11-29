package _untouchable_.common;


import java.util.*;


@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2013/05/20",
    currentRevision = "0.93",
    lastModified    = "2012/07/20",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
public abstract class CommonSchedule implements Iterator<CommonSSI> {
    
    @ChunkPreamble ( lastModified="2012/07/20", lastModifiedBy="Michael Schäfers" )
    public CommonSchedule( final CommonSSIFactory factory,  final Integer startPos,  final Integer finalPos ){
        // check for crazy "stuff" or malpractice of environment
        if ( Global.getData() == null ){
            System.err.printf( "Sie handeln nicht gemaess Aufgabenstellung - die interne Konfiguration ist unterblieben!\n" );
            System.err.printf( "\\--> Wurde letThereBeLife() aufgerufen?\n" );
            System.err.printf( "\\--> Sind alle Initialisierungen innerhalb von dotest(), die hinein gehoeren?\n" );
            System.err.flush();
            throw new IllegalStateException( "missing configuration" );
        }//if
        if (! new Object(){}.getClass().getPackage().getName().matches( "_untouchable_.common$" )){
            System.err.printf( "Sie handeln nicht gemaess Aufgabenstellung - Pfade/package path fehlerhaft\n" );
            System.err.printf( "\\--> Stimmmt die Package.Struktur?\n" );
            System.err.printf( "\\--> Stimmen die Import-Listen?\n" );
            System.err.flush();
            throw new IllegalStateException( "unexpected package path -> " + new Object(){}.getClass().getPackage().getName() );
        }//if
        
        // construct
        scheduleData      =  constructScheduleData( factory, startPos, finalPos );
        currentStopIndex  =  scheduleData.length;
    }//CommonSchedule()
    //
    @ChunkPreamble ( lastModified="2016/05/03", lastModifiedBy="Michael Schäfers" ) // 160503: Thanks to Florian Arfert detecting bug concerning computation of startposition
    private CommonSSI[] constructScheduleData( final CommonSSIFactory factory,  final Integer startPos,  final Integer finalPos ){
        final Global global  =  Global.getData();
        int minDuration      =  global.minSmurfDwell;
        int maxDuration      =  global.maxSmurfDwell;
        int numberOfTargets  =  global.wnol;
        int numberOfStops    =  global.minNumberOfStops   +   (int)( (global.maxNumberOfStops-global.minNumberOfStops) * Math.random() );
        if ( startPos!=null  && finalPos!=null  && startPos==finalPos ){
            if ( numberOfStops < 3 )  throw new IllegalStateException( String.format( "INTERNAL CONFIGURATION ERROR: start:%d, final:%d, #stop(s):%d(<3)", startPos, finalPos, numberOfStops ) );
        }else{
            if ( numberOfStops < 2 )  throw new IllegalStateException( String.format( "INTERNAL CONFIGURATION ERROR: #stop(s):%d(<2)", numberOfStops ) );
        }//if    
        int startPosition    =  ( startPos == null )  ?  (int)( numberOfTargets*Math.random() )  :  startPos;
        
        int factor = 1 + maxDuration - minDuration;                             // internal factor (for computation of duration time)
        
        
        CommonSSI[] resu = new CommonSSI[numberOfStops];
        int workIndex = numberOfStops-1;
        int stopPosition = startPosition;
        int durationTimeAtStop = minDuration + (int)( factor * Math.random() );
        resu[workIndex] = factory.createSSI( stopPosition, durationTimeAtStop );
        
        while ( workIndex > 0 ){
            stopPosition = ( stopPosition + 1 + (int)( (numberOfTargets-1) * Math.random() ) ) % numberOfTargets;
            durationTimeAtStop = minDuration + (int)( factor * Math.random() );            
            resu[--workIndex] = factory.createSSI( stopPosition, durationTimeAtStop );
        }//while
        
        if ( finalPos != null ){
            if ( resu[0].planedPosition != finalPos ){
                if ( resu[1].planedPosition == finalPos ){
                    if ( resu[2].planedPosition != resu[0].planedPosition ){
                        // resu[2].planedPosition!=resu[0].planedPosition==finalPos
                        // => resu[1]<-resu[0]<-finalPos
                        //
                        resu[1] = factory.createSSI( resu[0].planedPosition, resu[1].planedDwell );
                    }else{
                        // resu[2].planedPosition==resu[0].planedPosition!=finalPos==resu[1].planedPosition
                        // => resu[0], resu[1] must be modified
                        //
                        int low;
                        int hgh;
                        if ( finalPos < resu[2].planedPosition ){
                            low = finalPos;
                            hgh = resu[2].planedPosition;
                        }else{
                            low = resu[2].planedPosition;
                            hgh = finalPos;
                        }//if
                        int hlp = low + 1 + (int)( (numberOfTargets-2) * Math.random() );
                        if ( hlp >= hgh  )  hlp++;
                        resu[1] = factory.createSSI( hlp%numberOfTargets , resu[1].planedDwell );
                    }//if
                }//if
                // resu[0/1].planedPosition!=finalPos
                //
                resu[0] = factory.createSSI( finalPos, resu[0].planedDwell );
            }//if
        }//if
        
        return resu;
    }//constructScheduleData()
    
    
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    final CommonSSI[] scheduleData;                                             // the schedule
    //
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    int currentStopIndex;                                                       // Current Stop Index (in schedule)
    
    
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    @Override
    public boolean hasNext(){ return currentStopIndex > 0; }
    
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    @Override
    public CommonSSI next(){
        if ( this.hasNext() ){
            return scheduleData[ --currentStopIndex ];
        }else{
            throw new NoSuchElementException();
        }//if
    }//next()
    
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    @Override
    public void remove(){ throw new UnsupportedOperationException(); }
    
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    @Override
    public String toString(){
        StringBuilder resu = new StringBuilder( "[<Schedule>: csi:" );
        resu.append( currentStopIndex );
        resu.append( " core_schedule:( " );
        for ( CommonSSI ssi : this.scheduleData ){
            resu.append( ssi.toString() );
            resu.append( " " ); 
        }//for
        resu.append( ")]" );
        return resu.toString();
    }//toString()
    
}//CommonSchedule
