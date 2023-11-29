package _untouchable_.common;


@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2013/05/31",
    currentRevision = "0.93",
    lastModified    = "2013/05/31",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
//the WHOLE CLASS and ALL of its ELEMENTS are friendly on purpose (!)
class ActivityReporter {                                                        // friendly on purpose
    
    final boolean enableReport;                                                 // ENable report
    final Event event;                                                          // EVent to be reported
    
    
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    ActivityReporter( final boolean enReport,  final Event ev ){
        enableReport = enReport;
        event = ev;
    }//ActivityReporter()

    
    
    @ChunkPreamble ( lastModified="2013/05/31", lastModifiedBy="Michael Schäfers" )
    void report( final String woeText, final String actionText ){
        if ( enableReport ){
            String outStr;
            switch ( event.et ){
                case S1BDS: 
                    outStr = String.format(
                        "smurf---------------  smurf[%04d] starts his stuff....................................@position[%02d] ->%24s\n",
                        event.smurfIdUser,
                        event.smurfPositionParameter,
                        Tool.prettyTime( event.time )
                    );
                break;
                
                case S2EDS:
                    outStr = String.format(
                        "smurf---------------  smurf[%04d] finishes stuff......................................@position[%02d] ->%24s\n",
                        event.smurfIdUser,
                        event.smurfPositionParameter,
                        Tool.prettyTime( event.time )
                    );
                break;
                
                case S3ENW: 
                    outStr = String.format(
                        "smurf>>>>>>>>>>>>>>>  smurf[%04d] enters >>>>>>>>>>>>  %s[%02d]%s............. ->%24s\n",
                        event.smurfIdUser,
                        woeText,
                        event.woeIdUser,
                        genString( '.',  28 - woeText.length() ),
                        Tool.prettyTime( event.time )
                    );
                break;
                
                case S5LVW:
                    outStr = String.format(
                        "smurf<<<<<<<<<<<<<<<  smurf[%04d] leaves <<<<<<<<<<<<  %s[%02d]%s............. ->%24s\n",
                        event.smurfIdUser,
                        woeText,
                        event.woeIdUser,
                        genString( '.',  28 - woeText.length() ),
                        Tool.prettyTime( event.time )
                    );
                break;
                
                case SxxLD:
                    outStr = String.format(
                        "Smurf###############  smurf[%04d] down ############################################################ ->%24s\n",
                        event.smurfIdUser,
                        Tool.prettyTime( event.time )
                    );
                break;
                
                
                case W1MO1:
                    outStr = String.format(
                        "~~~~~~%s%s  %s[%02d] %s%sposition[%02d] ->%24s\n",
                        woeText,
                        genString( '~',  48 - woeText.length() ),
                        woeText,
                        event.woeIdUser,
                        actionText,
                        genString( '.',  28 - ( woeText.length() + actionText.length() ) ),
                        event.woePositionParameter,                             // woePositionParameter/target since position might be marked as -1
                        Tool.prettyTime( event.time )
                    );
                break;
                
                case W2MO2:
                    outStr = String.format(
                        "~~~~~~%s%s  %s[%02d] %s%sposition[%02d] ->%24s\n",
                        woeText,
                        genString( '~',  48 - woeText.length() ),
                        woeText,
                        event.woeIdUser,
                        actionText,
                        genString( '.',  28 - ( woeText.length() + actionText.length() ) ),
                        event.woePositionParameter,                             // woePositionParameter/target since position might be marked as -1
                        Tool.prettyTime( event.time )
                    );
                break;
                
                case W3AAP:
                    outStr = String.format(
                        "~~~~~~%s%s%s  %s[%02d] %s%sposition[%02d] ->%24s\n",
                        woeText,
                        genString( '~',  17 - woeText.length() ),
                        genString( '<',  32 ),
                        woeText,
                        event.woeIdUser,
                        actionText,
                        genString( '.',  28 - ( woeText.length() + actionText.length() ) ),
                        event.woePositionParameter,                             // woePositionParameter/target since position might be marked as -1
                        Tool.prettyTime( event.time )
                    );
                break;
                
                case W8VFP:
                    outStr = String.format(
                        "~~~~~~%s%s%s  %s[%02d] %s%sposition[%02d] ->%24s\n",
                        woeText,
                        genString( '~',  17 - woeText.length() ),
                        genString( '>',  32 ),
                        woeText,
                        event.woeIdUser,
                        actionText,
                        genString( '.',  28 - ( woeText.length() + actionText.length() ) ),
                        event.woePositionParameter,                             // woePositionParameter/target since position might be marked as -1
                        Tool.prettyTime( event.time )
                    );
                break;
                
                case WxxLD:
                    outStr = String.format(
                        "%s%s%s%s[%02d] stops doing stuff........................................................... ->%24s\n",
                        woeText,
                        genString( '-',  10 - woeText.length() ),
                        genString( ' ',  10 - woeText.length() ),
                        woeText,
                        event.woeIdUser,
                        Tool.prettyTime( event.time )
                    );
                break;
                
                
                default:
                    outStr = "";
                break;
            }//switch
            //
            System.out.print( outStr );
        }//if
    }//report()
    //
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    void report( final String woeText ){ report( woeText, null ); }
    //
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    void report(){ report( null, null ); }
    
    
    
    
    
    //
    // "object specific methods ON PURPOSE"
    // ------------------------------------
    //
    // typically they would be static and they are (see Tool) - but no more words ;-)
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    private String genString( final char c,  final int cnt ){
        StringBuilder resu = new StringBuilder();
        for ( int i=cnt; --i>0; )  resu.append( c );
        return resu.toString();
    }//genString()
    
}//class ActivityReporter
