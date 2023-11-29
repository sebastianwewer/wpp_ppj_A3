package _untouchable_.shipPart5;


import _untouchable_.common.*;


//JavaDOC                                                                       // adapt _HERE_
/**
 * Die abstrakte Klasse {@link Ship_A} ist Basisklasse f&uuml;r die von Ihnen zu implementierende Klasse Ship.
 * Diese Klasse stellt Ihnen verschiedene Methoden zur Verf&uuml;gung<br />
 * &bull; die Sie unterst&uuml;tzen und<br />
 * &bull; die Sie nutzen m&uuml;ssen,<br 7>
 * damit die automatisierten Pr&uuml;fungen fehlerfrei funktionieren <br />
 * <br />
 * Die Methoden: <br />
 * &bull; {@link #castOff(int)}, <br />
 * &bull; {@link #dockAt(int)}, <br />
 * &bull; {@link #takeTimeForBoardingAt(int)} und <br />
 * &bull; {@link #takeTimeForSailingTo(int)} <br />
 * stellen Zugriffspunkte f&uuml;r die automatisierte &Uuml;berpr&uuml;fungen sicher. <br />
 * <br />
 * Die Methode(n): <br />
 * &bull; {@link #identify()} <br />
 * m&uuml;ssen zu jedem Zeitpunkt die aktuellen tats&auml;chlichen Werte liefern.<br />
 * <br />
 * &quot;Ganz am Anfang&quot; befindet sich das Schiff bereits im demjenigen Hafen,
 * der der Schiffs-ID entspricht. Hier startet das Schiff auch.
 * Als &quot;erste Aktion&quot; muss es den Schl&uuml;pfen den Einstieg erlauben.<br />
 * <br />
 * In einigen Ausgaben werden Schiffe (Ship) aus internen Gr&uuml;nden auch als
 * &quot;<strong>WOE</strong>&quot; bezeichnet.
 */
@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2013/05/20",
    currentRevision = "0.93",
    lastModified    = "2013/06/01",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
abstract public class Ship_A extends CommonWOE {
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * Der Konstruktor erzeugt einen abstrakten {@link Ship_A}.
     * Es werden dabei interne Variablen gesetzt und interne Konsistenz-Checks vorgenommen.
     * @param debugState bestimmt ob Ship-spezifische Debug-Informationsmeldungen ausgegeben werden.
     */
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    public Ship_A(){
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
                "Sie handeln nicht gemaess Aufgabenstellung - pruefen Sie Ihre Package-Struktur -> "
                + objHome
            );
        }//if
        //
        if (! locHere.matches( "^_untouchable_." + Local.cmnPath + Global.getData().partId + "$" )){
            throw new IllegalStateException(
                "Sie handeln nicht gemaess Aufgabenstellung - pruefen Sie Ihre Package-Struktur -> "
                + locHere
            );
        }//if
        //
        // check if ship class is named "Ship"
        if (! getClass().getName().matches( "^" + wantedPath + "(\\..*)?\\.Ship$" ) ){
            throw new IllegalStateException(
                "Sie handeln nicht gemaess Aufgabenstellung -> pruefen Sie Ihren Klassennamen -> "
                + getClass().getName()
            );
        }//if
*/
    }//contructor()
    
    
    
    
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * Mit dem Aufruf von {@link #dockAt(int)} definiert ein Schiff (bzw. Ship) den Zeitpunkt
     * zu dem dieses Schiff (an der als Parameter &uuml;bergebenen Anlegestelle) festmacht
     * und seine Gangway herabl&auml;sst.
     * @param landing bestimmt die (Identifikations-Nr. der) Anlegestelle an der das Schiff gerade festmacht.
     */
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    public final void dockAt( final int landing ){                              // adapt _HERE_
        appear( landing, Local.woeText, "docks in" );                           // adapt _HERE_
    }//method()
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * Mit dem Aufruf von {@link #castOff(int)} definiert ein Schiff (bzw. Ship) den Zeitpunkt
     * zu dem dieses Schiff seine Gangway hochzieht und (von der als Parameter &uuml;bergebenen Anlegestelle)
     * ablegt.
     * @param landing bestimmt die (Identifikations-Nr. der) Anlegestelle von welcher das Schiff gerade ablegt.
     */
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    public final void castOff( final int landing ){                             // adapt _HERE_
        vanish( landing, Local.woeText, "casts off from" );                     // adapt _HERE_
    }//method()
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * Mit dem Aufruf von {@link #takeTimeForSailingTo(int)} wird die Zeit modelliert,
     * die dasSchiff f&uuml;r die Fahrt zu der (als Parameter &uuml;bergebenen) Anlegestelle ben&ouml;tigt.
     * Diese Aktivit&auml;t (bzw. der Aufruf dieser Methode) muss <strong><u>zwingend</u></strong>
     * vom Schiff-Thread selbst ausgef&uuml;hrt werden.
     * @param landing bestimmt das Ziel der Fahrt (also die Identifikations-Nr. der n&auml;chsten Anlegestelle).
     * @throws InterruptedException wenn Methode beim Warten unterbrochen wird.
     */
    @ChunkPreamble ( lastModified="2013/06/10", lastModifiedBy="Michael Schäfers" )
    public final void takeTimeForSailingTo( final int landing )  throws InterruptedException {  // adapt _HERE_
        takeTimeForMotionTo( landing, Local.woeText, "sails to", "is going to reach" );         // adapt _HERE_
    }//method()
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * Mit dem Aufruf von {@link #takeTimeForBoardingAt(int)} wird die Zeit modelliert,
     * die das Schiff an der jeweiligen Anlegestelle (mit herabgelassener Gangway) verweilt.
     * Diese Aktivit&auml;t (bzw. der Aufruf dieser Methode) muss <strong><u>zwingend</u></strong>
     * vom Schiff-Thread selbst ausgef&uuml;hrt werden.
     * @param landing bestimmt die (Identifikations-Nr. der) aktuelle(n) Anlegestelle an der das Schiff gerade h&auml;lt.
     * @throws InterruptedException wenn Methode beim Warten unterbrochen wird.
     */
    @ChunkPreamble ( lastModified="2013/06/01", lastModifiedBy="Michael Schäfers" )
    public final void takeTimeForBoardingAt( final int landing ) throws InterruptedException {  // adapt _HERE_
        takeTimeForStopover( landing );
    }//method()
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * Der Aufruf von {@link #lastDeed()} muss die <strong><u>letzte(!)</u></strong> Aktion des jeweiligen Schiffes sein.<br />
     * (D.h. insbesondere nach dem Aufruf von {@link #lastDeed()} muss der jeweilige (Schiff-)Thread terminieren.)
     */
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    public final void lastDeed(){
        lastDeed( Local.woeText );
    }//method()
    
    
    
    
    
    // internalWoeID is unique and clone() is forbidden
    //
    @ChunkPreamble ( lastModified="2013/05/23", lastModifiedBy="Michael Schäfers" )
    @Override public boolean equals( Object other ){ return super.equals( other ); }
    //
    @ChunkPreamble ( lastModified="2013/05/23", lastModifiedBy="Michael Schäfers" )
    @Override public int hashCode(){ return super.hashCode(); }
    
}//class
