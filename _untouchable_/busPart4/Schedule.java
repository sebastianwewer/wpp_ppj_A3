package _untouchable_.busPart4;


import _untouchable_.common.*;


/**
 * In der Klasse {@link Schedule} sind die Schedule-Eintr&auml;ge ({@link SSI}) organisiert.
 * {@link Schedule} implementiert das {@link java.util.Iterator}<{@link SSI}>-Interface.
 */
@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2013/05/20",
    currentRevision = "0.91",
    lastModified    = "2012/07/23",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
public class Schedule extends CommonSchedule {
    
    
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    Schedule(){                                                                 // friendly on purpose
        super ( new SSIFactory(), null, null );
        
        
        //
        //
        // check for crazy "stuff" or malpractice of environment
        //
        if ( Global.getData() == null )  throw new IllegalStateException();
        //
        final String locHere = new Object(){}.getClass().getPackage().getName();
        if (! locHere.matches( "^_untouchable_." + Local.cmnPath + Global.getData().partId + "$" )){
            throw new IllegalStateException(
                "Sie handeln nicht gemaess Aufgabenstellung - pruefen Sie Ihre Package-Struktur -> "
                + locHere
            );
        }//if
    }//constructor()
    
    
    
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    @Override
    public SSI next(){ return (SSI)( super.next() ); }
    
}//class
