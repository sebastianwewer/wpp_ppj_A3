package _untouchable_.busPart4;


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
 * &bull; {@link #enter( Bus_A )}, <br />
 * &bull; {@link #beThere( Bus_A )}, <br />
 * &bull; {@link #leave( Bus_A )}, <br />
 * &bull; {@link #takeTimeForDoingStuffAtCurrentPosition( CommonSSI )} und <br />
 * &bull; {@link #lastDeed()} <br />
 * stellen Zugriffspunkte f&uuml;r die automatisierte &Uuml;berpr&uuml;fungen sicher. <br />
 * <br />
 * Die Methoden: <br />
 * &bull; {@link #getSmurfId()} und <br />
 * &bull; {@link #locate()} <br />
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
 *     &nbsp; &nbsp;Schlumpf-Thread starten        &nbsp; &nbsp; // den entsprechenden Schlumpf generieren<br />
 *     }<br />
 * </i></code>
 * <br />
 */
@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2013/05/20",
    currentRevision = "0.91",
    lastModified    = "2016/03/23",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
abstract public class Smurf_A extends CommonSmurf {
    
    /**
     * Der Konstruktor erzeugt einen abstrakten {@link Smurf_A}.
     * Es werden dabei interne Variablen gesetzt und interne Konsistenz-Checks vorgenommen.
     * @param debugState bestimmt ob Smurf-spezifische Debug-Informationsmeldungen ausgegeben werden.
     */
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
    }//constructor()
    
    
    
    /**
     * {@link #schedule} ist der {@link Schedule} des jeweiligen Schlumpfs.
     * Diesen {@link Schedule} muss der Schlumpf abarbeiten.
     * Der {@link Schedule} sagt dem Schlumpf wo er als &quot;n&auml;chstes&quot; hin soll und wie lange er dort verweilen soll.
     */
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    public final Schedule schedule;
    
    
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * Mit dem Aufruf von {@link #enter(Bus_A)} definiert ein Schlumpf den Zeitpunkt
     * zu dem dieser Schlumpf in den (als Parameter &uuml;bergebenen) Bus einsteigt.
     * @param bus bestimmt den Bus in dem der Schlumpf einsteigt.
     */                                                                         // adapt _HERE_
    @ChunkPreamble ( lastModified="2020/12/07", lastModifiedBy="Michael Schäfers" )
    public final void enter( Bus_A bus ){                                       // adapt _HERE_
        if( null==bus )  throw new IllegalArgumentException( "null NOT supported as argument - an existent bus is required" );
        enter( bus, Local.woeText );
    }//method()
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * Mit dem Aufruf von {@link #beThere(Bus_A)} definiert ein Schlumpf den Zeitpunkt
     * zu dem dieser Schlumpf in dem Bus sitzt, den Moment der Ruhe w&auml;hrend der Busfahrt geniesst,
     * aus dem Fenster schaut und sich entspannt.
     * Diese Aktivit&auml;t muss <u>zwingend</u> vom Schlunpf-Thread selbst ausgef&uuml;hrt werden.
     * Der Zeitbedarf f&uuml;r diese Aktivit&auml;t ist vernachl&auml;ssigbar.<br />
     * Noch einmal ganz deutlich f&uuml;r die &quot;Spezialisten&quot;:
     * Das ein Schlumpf &quot;eingestiegen oder ausgestiegen wird&quot;,
     * ist eine akzetable Synchronisation-Idee/Variante (von vielen anderen Alternativen),
     * aber der Schlumpf geniesst aktiv selbst die Busfahrt!
     * Das {@link #beThere(Bus_A)} ist zwingend vom Schlumpf-Thread auszuf&uuml;hren!
     */                                                                         // adapt _HERE_
    @ChunkPreamble ( lastModified="2020/12/07", lastModifiedBy="Michael Schäfers" )
    public final void beThere( Bus_A bus ){                                     // adapt _HERE_
        if( null==bus )  throw new IllegalArgumentException( "null NOT supported as argument - an existent bus is required" );
        super.beThere( bus, Local.woeText );
    }//method()
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * Mit dem Aufruf von {@link #leave(Bus_A)} definiert ein Schlumpf den Zeitpunkt
     * zu dem dieser Schlumpf aus dem (als Parameter &uuml;bergebenen Bus) aussteigt.
     * @param bus bestimmt den Bus aus dem der Schlumpf aussteigt.
     */                                                                         // adapt _HERE_
    @ChunkPreamble ( lastModified="2020/12/07", lastModifiedBy="Michael Schäfers" )
    public final void leave( Bus_A bus ){                                       // adapt _HERE_
        if( null==bus )  throw new IllegalArgumentException( "null NOT supported as argument - an existent bus is required" );
        leave( bus, Local.woeText );
    }//method()
    
}//class
