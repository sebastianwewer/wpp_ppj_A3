package _untouchable_.busPart4;


import _untouchable_.common.*;


//JavaDOC                                                                       // adapt _HERE_
/**
 * Die abstrakte Klasse {@link Bus_A} ist Basisklasse f&uuml;r die von Ihnen zu implementierende Klasse Bus.
 * Diese Klasse stellt Ihnen verschiedene Methoden zur Verf&uuml;gung<br />
 * &bull; die Sie unterst&uuml;tzen und<br />
 * &bull; die Sie nutzen m&uuml;ssen,<br />
 * damit die automatisierten Pr&uuml;fungen fehlerfrei funktionieren. <br />
 * <br />
 * Die Methoden: <br />
 * &bull; {@link #startFrom(int)}, <br />
 * &bull; {@link #stopAt(int)}, <br />
 * &bull; {@link #takeTimeForStopoverAt(int)} und <br />
 * &bull; {@link #takeTimeForBusRideTo(int)} <br />
 * stellen Zugriffspunkte f&uuml;r die automatisierte &Uuml;berpr&uuml;fungen sicher. <br />
 * <br />
 * Die Methoden: <br />
 * &bull; {@link #identify()} und <br />
 * &bull; {@link #locate()} <br />
 * m&uuml;ssen zu jedem Zeitpunkt die aktuellen tats&auml;chlichen Werte liefern.
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
abstract public class Bus_A extends CommonWOE {
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * Der Konstruktor erzeugt einen abstrakten {@link Bus_A}.
     * Es werden dabei interne Variablen gesetzt und interne Konsistenz-Checks vorgenommen.
     * @param debugState bestimmt ob Bus-spezifische Debug-Informationsmeldungen ausgegeben werden.
     */
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    public Bus_A(){
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
        // check if bus class is named "Bus"
        if (! getClass().getName().matches( "^" + wantedPath + "(\\..*)?\\.Bus$" ) ){
            throw new IllegalStateException(
                "Sie handeln nicht gemaess Aufgabenstellung -> pruefen Sie Ihren Klassennamen -> "
                + getClass().getName()
            );
        }//if
*/
    }//constructor()
    
    
    
    
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * Mit dem Aufruf von {@link #stopAt(int)} definiert ein Bus den Zeitpunkt
     * zu dem dieser Bus (an der als Parameter &uuml;bergebenen Bushaltestelle) h&auml;lt
     * und seine T&uuml;ren &ouml;ffnet.
     * @param location bestimmt die Haltestelle an welcher der Bus gerade h&auml;lt.
     */
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    public final void stopAt( final int location ){                             // adapt _HERE_
        appear( location, Local.woeText, "stops at" );                          // adapt _HERE_
    }//method()
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * Mit dem Aufruf von {@link #startFrom(int)} definiert ein Bus den Zeitpunkt
     * zu dem dieser Bus (von der als Parameter &uuml;bergebenen Bushaltestelle) abf&auml;hrt
     * und seine T&uuml;ren schlie&szlig;t. Dies hat so schnell wie m&ouml;glich
     * nach Ablauf der Haltezeit zu passieren. D.h. nach {@link #takeTimeForStopoverAt(int)}
     * darf der Bus <strong><u>nicht</u></strong> erst noch einmal zum Einsteigen auffordern.
     * @param location bestimmt die Haltestelle von welcher der Bus gerade abf&auml;hrt.
     */
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    public final void startFrom( final int location ){                          // adapt _HERE_
        vanish( location, Local.woeText, "starts from" );                       // adapt _HERE_
    }//method()
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * Mit dem Aufruf von {@link #takeTimeForBusRideTo(int)} wird die Zeit modelliert,
     * die der Bus f&uuml;r die Fahrt zu der (als Parameter &uuml;bergebenen) Bushaltestelle ben&ouml;tigt.
     * @param destinationPosition bestimmt das Ziel der Fahrt (also die n&auml;chste Haltestelle).
     * @throws InterruptedException wenn Methode beim Warten unterbrochen wird.
     */
    @ChunkPreamble ( lastModified="2016/03/27", lastModifiedBy="Michael Schäfers" )
    public final void takeTimeForBusRideTo( final int destinationPosition )  throws InterruptedException {  // adapt _HERE_
        takeTimeForMotionTo( destinationPosition, Local.woeText, "drives to", "is going to reach" );    // adapt _HERE_
    }//method()
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * Mit dem Aufruf von {@link #takeTimeForStopoverAt(int)} wird die Zeit modelliert,
     * die der Bus an der jeweiligen Haltestelle (mit ge&ouml;ffneten T&uuml;ren) verweilt.
     * Nach dem Verstreichen der Zeit f&auml;hrt der Bus so schnell wie m&ouml;glich ab.
     * Der Bus darf nach dem Ablaufen der Zeit <strong><u>nicht</u></strong> erst noch
     * einmal zum Einsteigen auffordern.
     * @param location bestimmt die aktuelle Haltestelle an der der Bus gerade h&auml;lt.
     * @throws InterruptedException wenn Methode beim Warten unterbrochen wird.
     */
    @ChunkPreamble ( lastModified="2016/04/27", lastModifiedBy="Michael Schäfers" )
    public final void takeTimeForStopoverAt( final int location ) throws InterruptedException { // adapt _HERE_
        takeTimeForStopover( location );
    }//method()
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * Der Aufruf von {@link #lastDeed()} muss die <strong><u>letzte(!)</u></strong> Aktion des jeweiligen Busses sein.<br />
     * (D.h. insbesondere nach dem Aufruf von {@link #lastDeed()} muss der jeweilige (Bus-)Thread terminieren.)
     */
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    public final void lastDeed(){
        lastDeed( Local.woeText );
    }//method()
    
}//class
