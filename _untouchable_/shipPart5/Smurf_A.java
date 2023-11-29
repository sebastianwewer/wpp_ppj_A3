package _untouchable_.shipPart5;


import _untouchable_.common.*;


//JavaDOC                                                                       // adapt _HERE_
/**
 * Die abstrakte Klasse {@link Smurf_A} ist Basisklasse f&uuml;r die von Ihnen zu implementierende Klasse Smurf. 
 * Diese Klasse stellt Ihnen verschiedene Methoden zur Verf&uuml;gung<br />
 * &bull; die Sie unterst&uuml;tzen und<br />
 * &bull; die Sie nutzen m&uuml;ssen,<br />
 * damit die automatisierten Pr&uuml;fungen fehlerfrei funktionieren <br />
 * <br />
 * Die Methoden: <br />
 * &bull; {@link #enter( Ship_A )}, <br />
 * &bull; {@link #beThere( Ship_A )}, <br />
 * &bull; {@link #leave( Ship_A )}, <br />
 * &bull; {@link #takeTimeForDoingStuffAtCurrentPosition( int, CommonSSI )} und <br />
 * &bull; {@link #lastDeed()} <br />
 * stellen Zugriffspunkte f&uuml;r die automatisierte &Uuml;berpr&uuml;fungen sicher. <br />
 * <br />
 * Die Methode(n): <br />
 * &bull; {@link #identify()} <br />
 * m&uuml;ssen zu jedem Zeitpunkt die aktuellen tats&auml;chlichen Werte liefern. <br />
 * <br />
 * <br />
 * Der Aufruf von {@link #waitUntilNextArrival()} modelliert die Zeit,
 * die verstreicht bis der n&auml;chste Schlumpf eintrifft. <br />
 * <br />
 * <u>Anwendungs-Beispiel:</u>
 * <br />
 * <code><i>
 *     wiederhole bis &quot;gew&uuml;nschte Anzahl&quot; von Schl&uuml;mpfen erreicht ist {<br />
 *     &nbsp; &nbsp;</i>waitUntilNextArrival();<i> &nbsp; &nbsp; // warten darauf, dass ein Schlumpf ankommt<br />
 *     &nbsp; &nbsp;Schlumpf-Thread starten        &nbsp; &nbsp; // den entsprechenden Schlumpf generieren und anstarten<br />
 *     }<br />
 * </i></code>
 * <br />
 */                                                                             // adapt _HERE_
@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2013/05/31",
    currentRevision = "0.93",
    lastModified    = "2016/03/23",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
abstract public class Smurf_A extends CommonSmurf {
    
    /**
     * Der Konstruktor erzeugt einen abstrakten {@link Smurf_A}.
     * Es werden dabei interne Variablen gesetzt und interne Konsistenz-Checks vorgenommen.
     * @param debugState bestimmt ob Smurf-spezifische Debug-Informationsmeldungen ausgegeben werden.
     */                                                                         // adapt _HERE_
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    public Smurf_A(){
        super( new ScheduleFactory() );
        
        
        //
        //
        // check for crazy "stuff" or malpractice of environment
        //
        if ( Global.getData() == null )  throw new IllegalStateException( "Sie handeln nicht gemaess Aufgabenstellung" );
        //
/*
        final String wantedPath  =  Local.partSeparationRequired  ?  Local.reqPath + Global.getData().partId  :  Local.reqPath;
        final String objHome = getClass().getPackage().getName();
        final String locHere = new Object(){}.getClass().getPackage().getName();
        //
        if (! objHome.matches( "^" + wantedPath + "(\\..*)?$" ) ){
            throw new IllegalStateException(
                "Sie handeln nicht gemaess Aufgabenstellung -> pruefen Sie Ihre package-Struktur -> "
                + objHome
            );
        }//if
        //
        if (! locHere.matches( "^_untouchable_." + Local.cmnPath + Global.getData().partId + "$" )){
            throw new IllegalStateException(
                "Sie handeln nicht gemaess Aufgabenstellung -> pruefen Sie Ihre package-Struktur -> "
                + locHere
            );
        }//if
        //
        // check if smurf class is named "Smurf"
        if (! this.getClass().getName().matches( "^" + wantedPath + "(\\..*)?\\.Smurf$" ) ){
            throw new IllegalStateException(
                "Sie handeln nicht gemaess Aufgabenstellung -> pruefen Sie Ihren Klassennamen -> "
                + this.getClass().getName()
            );
        }//if
 */
        
        // construct
        schedule = (Schedule)super.schedule;
    }//Smurf_A()
    
    
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * {@link #schedule} ist der {@link Schedule} des jeweiligen Schlumpfs.
     * Diesen {@link Schedule} muss der Schlumpf abarbeiten.
     * Der {@link Schedule} sagt dem Schlumpf wo er als &quot;n&auml;chstes&quot; hin soll und wie lange er dort verweilen soll.
     */                                                                         // adapt _HERE_
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    public final Schedule schedule;
    
    
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * Mit dem Aufruf von {@link #enter(Ship_A)} definiert ein Schlumpf den Zeitpunkt
     * zu dem dieser Schlumpf in das (als Parameter &uuml;bergebene) Schiff einsteigt.
     * @param ship bestimmt das Schiff, welches der Schlumpf betritt.
     */                                                                         // adapt _HERE_
    @ChunkPreamble ( lastModified="2020/12/07", lastModifiedBy="Michael Schäfers" )
    public final void enter( final Ship_A ship ){                               // adapt _HERE_
        if( null==ship )  throw new IllegalArgumentException( "null NOT supported as argument - an existent ship is required" );
        enter( ship, Local.woeText );
    }//method()
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * Mit dem Aufruf von {@link #beThere(Ship_A)} definiert ein Schlumpf den Zeitpunkt
     * zu dem dieser Schlumpf das Schiff betreten hat, einen Moment der Ruhe w&auml;hrend der Schifffahrt geniesst,
     * auf die See schaut und sich entspannt.
     * Diese Aktivit&auml;t (bzw. der Aufruf dieser Methode) muss <strong><u>zwingend</u></strong>
     * vom Schlunpf-Thread selbst ausgef&uuml;hrt werden.
     * Der Zeitbedarf f&uuml;r diese Aktivit&auml;t ist vernachl&auml;ssigbar.<br />
     * Das ein Schlumpf &quot;passiv eingestiegen oder ausgestiegen wird&quot;,
     * ist eine akzetable Synchronisation-Idee/Variante (von vielen anderen Alternativen),
     * aber der Schlumpf geniesst zwingend aktiv selbst die Schiffahrt!<br />
     * Die ganze Aufgabe ist eine Metapher. In einer &quot;sinnvollen&quot; Anwendung,
     * wird eine Resource belegt um etwas damit zu &quot;machen&quot;.
     * {@link #beThere(Ship_A)} ist die Metapher f&uuml;r die &quot;Aktion/Operation&quot; des (Smurf-)Threads
     * f&uuml;r die die Resource ({@link Ship_A}) ben&ouml;tigt wurde.
     * Die Methode {@link #beThere(Ship_A)} ist zwingend vom Schlumpf-Thread selbst auszuf&uuml;hren!
     * @param ship bestimmt das Schiff, auf dem sich der Schlumpf befindet.
     */                                                                         // adapt _HERE_
    @ChunkPreamble ( lastModified="2020/12/07", lastModifiedBy="Michael Schäfers" )
    public final void beThere( final Ship_A ship ){                             // adapt _HERE_
        if( null==ship )  throw new IllegalArgumentException( "null NOT supported as argument - an existent ship is required" );
        super.beThere( ship, Local.woeText );
    }//method()
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * Mit dem Aufruf von {@link #leave(Ship_A)} definiert ein Schlumpf den Zeitpunkt
     * zu dem dieser Schlumpf aus (dem als Parameter &uuml;bergebenen Schiff) aussteigt.
     * @param ship bestimmt das Schiff aus dem der Schlumpf aussteigt.
     */                                                                         // adapt _HERE_
    @ChunkPreamble ( lastModified="2020/12/07", lastModifiedBy="Michael Schäfers" )
    public final void leave( final Ship_A ship ){                               // adapt _HERE_
        if( null==ship )  throw new IllegalArgumentException( "null NOT supported as argument - an existent ship is required" );
        leave( ship, Local.woeText );
    }//method()
    
    
    
    
    
    // internalSmurfID is unique and clone() is forbidden
    //
    @ChunkPreamble ( lastModified="2013/05/23", lastModifiedBy="Michael Schäfers" )
    @Override public boolean equals( Object other ){ return super.equals( other ); }
    //
    @ChunkPreamble ( lastModified="2013/05/23", lastModifiedBy="Michael Schäfers" )
    @Override public int hashCode(){ return super.hashCode(); }
    
}//class
