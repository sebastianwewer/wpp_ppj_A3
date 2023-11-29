package _untouchable_.common;


/**
 * Die Schedule-Eintr&auml;ge bzw. &quot;Objekte&quot; der (abstrakten) Klasse
 * {@link #CommonSSI} (<strong>S</strong>cheduled <strong>S</strong>top <strong>I</strong>nfo)
 * definieren, was als n&auml;chstes zu tun ist.
 * Sie k&ouml;nnen diese Klasse als &quot;Block Box&quot; betrachten.
 * Sie ben&ouml;tigen Objekte dieser Klasse als Parameter f&uuml;r &quot;Methoden&quot;.
 * Ansonsten sollten Sie sich <strong><u>nicht</u></strong> weiter mit dieser Klasse besch&auml;ftigen.
 */
@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2013/05/20",
    currentRevision = "0.93",
    lastModified    = "2012/07/20",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
public abstract class CommonSSI {
    
    @ChunkPreamble ( lastModified="2012/07/20", lastModifiedBy="Michael Schäfers" )
    public CommonSSI( final Integer position,  final Integer dwell ){
        // check for crazy "stuff" or malpractice of environment
        if ( Global.getData() == null ){
            System.err.printf( "Sie handeln nicht gemaess Aufgabenstellung - die interne Konfiguration ist unterblieben!\n" );
            System.err.printf( "-> Wurde letThereBeLife() aufgerufen?\n" );
            System.err.printf( "-> Sind alle Initialisierungen innerhalb von dotest(), die hinein gehoeren?\n" );
            System.err.flush();
            throw new IllegalStateException( "missing configuration" );
        }//if
        if (! new Object(){}.getClass().getPackage().getName().matches( "_untouchable_.common$" )){
            System.err.printf( "Sie handeln nicht gemaess Aufgabenstellung - Pfade/package path fehlerhaft\n" );
            System.err.printf( "-> Stimmmt die Package.Struktur?\n" );
            System.err.printf( "-> Stimmen die Import-Listen?\n" );
            System.err.flush();
            throw new IllegalStateException( "unexpected package path -> " + new Object(){}.getClass().getPackage().getName() );
        }//if
        
        // construct
        planedPosition  =  position;
        planedDwell     =  dwell;
    }//CommonSSI()
    
    
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    final Integer planedPosition;   public int getPlanedPosition(){ return planedPosition; }
    //
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    final Integer planedDwell;      public int getDwell()         { return planedDwell; }    
    
    
    
    @ChunkPreamble ( lastModified="2012/07/20", lastModifiedBy="Michael Schäfers" )
    @Override
    public String toString(){ return String.format( "[<CommonSSI> time:%d@posi:%d]", planedDwell, planedPosition ); }
    
}//class CommonSSI
