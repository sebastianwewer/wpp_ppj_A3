package _untouchable_.common;


@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2013/05/20",
    currentRevision = "0.93",
    lastModified    = "2012/07/19",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
class Tool {                                                                    // friendly on purpose
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    static long nanoTime(){                                                     // friendly on purpose ;  time in [ns]
        return System.nanoTime();                                               // just a wrapper function
    }//nanoTime()
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    static long nanoElapsedTime(){                                              // friendly on purpose ;  elapsed time since start measured in [ns]
        // "hidden" side effect:
        // the way things are: Global.av_startTime is set at system start (with Global creation)
        return nanoTime() - Global.av_startTime;
    }//nanoDeltaTime()
    
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    static String prettyTime( final long nsTime ){                              // friendly on purpose
        if (       nsTime < 1000000000000L ){
            return String.format(
                "%3d__%03d_%03d_%03d[ns]",
                   nsTime / 1000000000L,
                 ( nsTime /    1000000L ) % 1000,
                 ( nsTime /       1000L ) % 1000,
                   nsTime                 % 1000
            );//return
        }else if ( nsTime < 1000000000000000L ){
            return String.format(
                "%3d_%03d__%03d_%03d_%03d[ns]",
                   nsTime / 1000000000000L ,
                (  nsTime /    1000000000L ) % 1000,
                (  nsTime /       1000000L ) % 1000,
                (  nsTime /          1000L ) % 1000,
                   nsTime                    % 1000
            );//return
        }else if ( nsTime < 1000000000000000000L ){
            return String.format(
                "%3d_%03d_%03d__%03d_%03d_%03d[ns]",
                   nsTime / 1000000000000000L ,
                (  nsTime /    1000000000000L ) % 1000,
                (  nsTime /       1000000000L ) % 1000,
                (  nsTime /          1000000L ) % 1000,
                (  nsTime /             1000L ) % 1000,
                   nsTime                       % 1000
            );//return
        }else{
            return String.format(
                "%3d_%03d_%03d_%03d__%03d_%03d_%03d[ns]",
                   nsTime / 1000000000000000000L ,
                (  nsTime /    1000000000000000L ) % 1000,
                (  nsTime /       1000000000000L ) % 1000,
                (  nsTime /          1000000000L ) % 1000,
                (  nsTime /             1000000L ) % 1000,
                (  nsTime /                1000L ) % 1000,
                   nsTime                          % 1000
            );//return 
        }//if
    }//prettyTime()    
    
}//Tool
