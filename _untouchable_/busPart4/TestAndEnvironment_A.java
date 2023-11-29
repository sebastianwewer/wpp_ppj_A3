package _untouchable_.busPart4;


import _untouchable_.common.*;

import static _untouchable_.common.WoeType.*;
import static _untouchable_.common.MovementStyle.*;


/**
 * Die abstrakte Klasse {@link TestAndEnvironment_A} ist Basisklasse f&uuml;r die von Ihnen zu implementierende Klasse TestFrame. 
 * Diese Klasse stellt Ihnen verschiedene Methoden zur Verf&uuml;gung<br />
 * &bull; die Sie unterst&uuml;tzen und<br />
 * &bull; die Sie nutzen m&uuml;ssen, damit die automatisierten Pr&uuml;fungen fehlerfrei funktionieren <br />
 * <br />
 * Die Methoden: <br />
 * &bull; {@link #doTest( int )} und <br />
 * &bull; {@link #letThereBeLife()} <br />
 * stellen Zugriffspunkte f&uuml;r die automatisierte &Uuml;berpr&uuml;fungen sicher. <br />
 * <br />
 */
@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2013/05/17",
    currentRevision = "0.91",
    lastModified    = "2013/05/17",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
public abstract class TestAndEnvironment_A extends CommonTestAndEnvironment {
    
    // abstract Integer getWantedNumberOfSmurfs();  wird in in super class CommonTestAndEnvironment eingefordert
    
    /**
     * {@link #getWantedNumberOfBusses()} liefert die Anzahl der Busse,
     * die Sie sich f&uuml;r Ihren Testlauf &quot;w&uuml;nschen&quot;.
     * Die Anzahl der Busse, die (beim Test) bei der Abnahme verwendet wird,
     * kann hiervon abweichen.
     * @return die von Ihnen &quot;gew&uuml;nschte&quot; Anzahl der Busse f&uuml;r Ihren Testlauf.
     */
    @ChunkPreamble ( lastModified="2013/05/18", lastModifiedBy="Michael Schäfers" )
    public abstract Integer getWantedNumberOfBusses();
    
    /**
     * {@link #getWantedNumberOfBusStops()} liefert die Anzahl der Bushaltestellen,
     * die Sie sich f&uuml;r Ihren Testlauf &quot;w&uuml;nschen&quot;.
     * Die Anzahl der Bushaltestellen, die (beim Test) bei der Abnahme verwendet wird,
     * kann hiervon abweichen.
     * @return die von Ihnen &quot;gew&uuml;nschte&quot; Anzahl der Bushaltestellen f&uuml;r Ihren Testlauf.
     */
    @ChunkPreamble ( lastModified="2013/05/18", lastModifiedBy="Michael Schäfers" )
    public abstract Integer getWantedNumberOfBusStops();
    
    /**
     * {@link #getWantedMaximumNumberOfSmurfsPerBus()} liefert die von Ihnen &quot;gew&uuml;nschte&quot; maximale Anzahl
     * von Schl&uuml;mpfen die sich gleichzeitig in einem Bus aufhalten d&uuml;rfen.
     * Die entsprechende Anzahl, die (beim Test) bei der Abnahme verwendet wird, kann hiervon abweichen.
     * @return die (f&uuml;r Ihren Testlauf) von Ihnen &quot;gew&uuml;nschte&quot; maximale Anzahl von Schl&uuml;mpfen,
     *         die sich gleichzeitig in einem Bus aufhalten d&uuml;rfen.
     */
    @ChunkPreamble ( lastModified="2013/05/18", lastModifiedBy="Michael Schäfers" )
    public abstract Integer getWantedMaximumNumberOfSmurfsPerBus();
    
    /**
     * {@link #getWantedMaximumNumberOfBussesPerBusStop()} liefert die von Ihnen &quot;gew&uuml;nschte&quot;
     * maximale Anzahl von Bussen, die sich gleichzeitig in einer Bushaltestelle stehen d&uuml;rfen.
     * Die entsprechende Anzahl, die (beim Test) bei der Abnahme verwendet wird, kann hiervon abweichen.
     * @return die (f&uuml;r Ihren Testlauf) von Ihnen &quot;gew&uuml;nschte&quot; maximale Anzahl von Bussen,
     *         die gleichzeitig an einer Bushaltestelle stehen d&uuml;rfen.
     */
    @ChunkPreamble ( lastModified="2013/05/18", lastModifiedBy="Michael Schäfers" )
    public abstract Integer getWantedMaximumNumberOfBussesPerBusStop();
    
    
    
    /**
     * {@link #doTest(Integer,Integer,Integer,Integer,Integer)} ist die Rahmentestmethode in welcher der eigentliche Test abgewickelt wird.
     * Diese Methode wird (in den &quot;Tiefen&quot; des Testsystems letztlich) von {@link #letThereBeLife()} aufgerufen.<br />
     * <br />
     * Sie k&ouml;nnen sich die Implementierung von {@link #letThereBeLife()} etwa wie folgt vorstellen:<br />
     * <code>
     * void letThereBeLife(){  <br />
     * &nbsp; &nbsp &hellip;   <br />
     * &nbsp; &nbsp doTest(    <br />
     * &nbsp; &nbsp &nbsp; &nbsp {@link #getWantedNumberOfSmurfs()},                <br />
     * &nbsp; &nbsp &nbsp; &nbsp {@link #getWantedNumberOfBusses()},                <br />
     * &nbsp; &nbsp &nbsp; &nbsp {@link #getWantedNumberOfBusStops()},              <br />
     * &nbsp; &nbsp &nbsp; &nbsp {@link #getWantedMaximumNumberOfSmurfsPerBus()},   <br />
     * &nbsp; &nbsp &nbsp; &nbsp {@link #getWantedMaximumNumberOfBussesPerBusStop()}<br />
     * &nbsp; &nbsp );         <br />
     * &nbsp; &nbsp &hellip;   <br />
     * }                       <br />
     * </code>
     * Dies bedeutet,
     * dass die an die Methode {@link #doTest(Integer,Integer,Integer,Integer,Integer)}
     * als aktuelle Parameter &uuml;bergebenen Werte (f&uuml;r die formalen Parameter)
     * bei der &quot;Auswertung&quot; &uuml;ber die konkreten Werte entscheiden.<br />
     * <br />
     * <strong><u>ACHTUNG!:</u></strong><br />
     * Ihr W&uuml;nsche bzw. die gew&uuml;nschten Werte ( getWanted&hellip;() ) m&uuml;ssen <strong><u>nicht</u></strong> zwingend erf&uuml;llt/verwendet werden,<br />
     * bei der Abnahme &nbsp bzw. &nbsp beim Test &nbsp bzw. &nbsp bei der Bewertung &nbsp bzw. &nbsp bei der Korrektur<br />
     * k&ouml;nnen <strong><u>andere</u></strong> Werte auftreten.<br />
     * Es w&auml;re also auch so &quot;etwas&quot; wie:<br />
     * <code>
     * void letThereBeLife(){  <br />
     * &nbsp; &nbsp &hellip;   <br />
     * &nbsp; &nbsp doTest(    <br />
     * &nbsp; &nbsp &nbsp; &nbsp requestedValueForNumberOfSmurfs,                   <br />
     * &nbsp; &nbsp &nbsp; &nbsp requestedValueForNumberOfBusses,                   <br />
     * &nbsp; &nbsp &nbsp; &nbsp requestedValueForNumberOfBusStops,                 <br />
     * &nbsp; &nbsp &nbsp; &nbsp requestedValueForMaximumNumberOfSmurfsPerBus,      <br />
     * &nbsp; &nbsp &nbsp; &nbsp requestedValueForMaximumNumberOfBussesPerBusStop   <br />
     * &nbsp; &nbsp );         <br />
     * &nbsp; &nbsp &hellip;   <br />
     * }                       <br />
     * </code>
     * denkbar.<br />
     * <br />
     * @param requestedNumberOfSmurfs bestimmt die Anzahl der Schl&uuml;mpfe f&uuml;r den zugeh&ouml;rigen Testlauf.
     * @param requestedNumberOfBusses bestimmt die Anzahl der Schiffe f&uuml;r den zugeh&ouml;rigen Testlauf.
     * @param requestedNumberOfBusStops bestimmt die Anzahl der Anlegestellen f&uuml;r den zugeh&ouml;rigen Testlauf.
     * @param requestedMaximumNumberOfSmurfsPerBus bestimmt die maximale Anzahl von Schl&uuml;pfen,
     *                                             die sich gleichzeitig in einem Bus aufhalten d&uuml;fen.
     * @param requestedMaximumNumberOfBussesPerBusStop bestimmt die maximale Anzahl von Bussen,
     *                                                 die gleichzeitig an einer Haltestelle halten d&uuml;rfen.
     */
    @ChunkPreamble ( lastModified="2016/04/27", lastModifiedBy="Michael Schäfers" )
    public abstract void doTest(
        final Integer requestedNumberOfSmurfs,
        final Integer requestedNumberOfBusses,
        final Integer requestedNumberOfBusStops,
        final Integer requestedMaximumNumberOfSmurfsPerBus,
        final Integer requestedMaximumNumberOfBussesPerBusStop
    );//method()
    
    /**
     * This method {@link #doTheTest(Integer,Integer,Integer,Integer,Integer)} is for internal use only.<br />
     * Do <strong><u>NOT(!)</u></strong> use this method.
     * @param nos for internal usage ;-)
     * @param now for internal usage ;-)
     * @param nol for internal usage ;-)
     * @param mnospw for internal usage ;-)
     * @param mnowpl for internal usage ;-)
     */
    @ChunkPreamble ( lastModified="2013/05/19", lastModifiedBy="Michael Schäfers" )
    @Override
    public final void doTheTest(
        final Integer nos,
        final Integer now,
        final Integer nol,
        final Integer mnospw,
        final Integer mnowpl
    ){
        doTest( nos, now, nol, mnospw, mnowpl );
    }//method()
    
    
    /**
     * {@link #letThereBeLife()} startet den eigentlich Test an (durch Aufruf von {@link #doTest(Integer,Integer,Integer,Integer,Integer)}),
     * setzt interne Variablen (die Sie <u>nicht</u> interessieren m&uuml;ssen)
     * und macht einige zus&auml;tzliche Ausgaben.
     */
    @ChunkPreamble ( lastModified="2016/04/27", lastModifiedBy="Michael Schäfers" )
    public final void letThereBeLife(){
        super.letThereBeLife(
            //------------------------------------------------------------------
            new Integer[]{
            
                4,                                                  // part Id
                
                getWantedNumberOfSmurfs(),                  null,   // number of smurfs
                getWantedNumberOfBusses(),                  2,      // number of WOE / busses
                getWantedNumberOfBusStops(),                5,      // number of WOE stops / bus stops
                
                getWantedMaximumNumberOfSmurfsPerBus(),     17,     // max number of smurfs per WOE   ;   null <=> wildcard
                getWantedMaximumNumberOfBussesPerBusStop(), null,   // max number of WOE per location ;   null <=> wildcard
                
                7,  23,                                             // min/max number of stops
                
                0,  31,                                             // min/max smurf arrival time (in units)
                3,  37,                                             // min/max smurf dwell (time for doing stuff in time units)
                
                13, 29,                                             // min/max bus ride time (in units)
                11, 19,                                             // min/max bus dwell (boarding time in units)
                
                10                                                  // (single) time unit size in milli seconds
                
            },
            // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
            new int[][]{
                { 0,1,2,3,4,3,2,1 },                                // 1st bus route
                { 4,3,2,1,0,1,2,3 }                                 // 2nd bus route
            },
            //------------------------------------------------------------------
            LineBusStyle,
            //------------------------------------------------------------------
            BUS,
            //------------------------------------------------------------------
            true
        );  // just prime numbers (to be used as test values): 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127
    };//method()

}//class
