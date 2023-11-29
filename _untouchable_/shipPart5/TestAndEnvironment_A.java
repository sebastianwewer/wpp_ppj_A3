package _untouchable_.shipPart5;


import _untouchable_.common.*;

import static _untouchable_.common.WoeType.*;
import static _untouchable_.common.MovementStyle.*;


//JavaDOC                                                                       // adapt _HERE_
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
    date            = "2013/06/07",
    currentRevision = "0.94",
    lastModified    = "2013/06/07",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
public abstract class TestAndEnvironment_A extends CommonTestAndEnvironment {
    
    // public abstract Integer getWantedNumberOfSmurfs();
    //        wird in in super class CommonTestAndEnvironment eingefordert.
    //        Siehe auch dort bezueglich JavaDoc.
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * {@link #getWantedNumberOfShips()} liefert die Anzahl der Schiffe,
     * die Sie sich f&uuml;r Ihren Testlauf &quot;w&uuml;nschen&quot;.
     * Die Anzahl der Schiffe, die (beim Test) bei der Abnahme verwendet wird,
     * kann hiervon abweichen.
     * <br />
     * <br />
     * <u>Bemerkung:</u>
     * <br />
     * Der in den &quot;Tiefen&quot; des &quot;Testsystems&quot; an
     * die gem&auml;&szlig; Aufgabenstellung in Ihrem TestFrame befindliche Methode
     * &quot;<code>public void doTest(Integer,<strong>Integer</strong>,Integer,Integer,Integer)</code>&quot;
     * &uuml;bergebene Wert
     * entscheidet &uuml;ber die tats&auml;chliche Anzahl
     * der bei der Testlaufauswertung erwarteten Schliffe
     * - und damit <strong><u>NICHT</u></strong> zwingend
     * der von {@link #getWantedNumberOfShips()} abgelieferte Wert.
     * <br />
     * <br />
     * @return die von Ihnen &quot;gew&uuml;nschte&quot; Anzahl der Schiffe f&uuml;r Ihren Testlauf.
     */
    @ChunkPreamble ( lastModified="2013/05/31", lastModifiedBy="Michael Schäfers" )
    public abstract Integer getWantedNumberOfShips();
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * {@link #getWantedNumberOfLandings()} liefert die Anzahl der Anlegestellen,
     * die Sie sich f&uuml;r Ihren Testlauf &quot;w&uuml;nschen&quot;.
     * Die Anzahl der Anlegestellen, die (beim Test) bei der Abnahme verwendet wird,
     * kann hiervon abweichen.
     * <br />
     * <br />
     * <u>Bemerkung:</u>
     * <br />
     * Der in den &quot;Tiefen&quot; des &quot;Testsystems&quot; an
     * die gem&auml;&szlig; Aufgabenstellung in Ihrem TestFrame befindliche Methode
     * &quot;<code>public void doTest(Integer,Integer,<strong>Integer</strong>,Integer,Integer)</code>&quot;
     * &uuml;bergebene Wert
     * entscheidet &uuml;ber die tats&auml;chliche Anzahl
     * der bei der Testlaufauswertung erwarteten Anlegestelen
     * - und damit <strong><u>NICHT</u></strong> zwingend
     * der von {@link #getWantedNumberOfLandings()} abgelieferte Wert.
     * <br />
     * <br />
     * @return die von Ihnen &quot;gew&uuml;nschte&quot; Anzahl der Anlegestellen f&uuml;r Ihren Testlauf.
     */
    @ChunkPreamble ( lastModified="2013/05/31", lastModifiedBy="Michael Schäfers" )
    public abstract Integer getWantedNumberOfLandings();
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * {@link #getWantedMaximumNumberOfSmurfsPerShip()} liefert die von Ihnen f&uuml;r Ihren Testlauf &quot;gew&uuml;nschte&quot;
     * maximale Anzahl von Schl&uuml;mpfen die sich gleichzeitig auf einem Schiff aufhalten d&uuml;rfen.
     * Die entsprechende Anzahl, die (beim Test) bei der Abnahme verwendet wird, kann hiervon abweichen.
     * <br />
     * <br />
     * <u>Bemerkung:</u>
     * <br />
     * Der in den &quot;Tiefen&quot; des &quot;Testsystems&quot; an
     * die gem&auml;&szlig; Aufgabenstellung in Ihrem TestFrame befindliche Methode
     * &quot;<code>public void doTest(Integer,Integer,Integer,<strong>Integer</strong>,Integer)</code>&quot;
     * &uuml;bergebene Wert
     * entscheidet &uuml;ber die tats&auml;chliche Anzahl
     * der bei der Testlaufauswertung erwarteten maximale Anzahl von Schl&uuml;mpfen pro Schiff
     * - und damit <strong><u>NICHT</u></strong> zwingend
     * der von {@link #getWantedMaximumNumberOfSmurfsPerShip()} abgelieferte Wert.
     * <br />
     * <br />
     * @return die (f&uuml;r Ihren Testlauf) von Ihnen &quot;gew&uuml;nschte&quot; maximale Anzahl von Schl&uuml;mpfen,
     *         die sich gleichzeitig auf einem Schiff aufhalten d&uuml;rfen.
     */
    @ChunkPreamble ( lastModified="2013/05/31", lastModifiedBy="Michael Schäfers" )
    public abstract Integer getWantedMaximumNumberOfSmurfsPerShip();
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * {@link #getWantedMaximumNumberOfShipsPerLanding()} liefert die von Ihnen f&uuml;r Ihren Testlauf &quot;gew&uuml;nschte&quot;
     * maximale Anzahl von Schiffen, die gleichzeitig an einer Anlegestelle anlegen d&uuml;rfen.
     * Die entsprechende Anzahl, die (beim Test) bei der Abnahme verwendet wird, kann hiervon abweichen.
     * <br />
     * <br />
     * <u>Bemerkung:</u>
     * <br />
     * Der in den &quot;Tiefen&quot; des &quot;Testsystems&quot; an
     * die gem&auml;&szlig; Aufgabenstellung in Ihrem TestFrame befindliche Methode
     * &quot;<code>public void doTest(Integer,Integer,Integer,Integer,<strong>Integer</strong>)</code>&quot;
     * &uuml;bergebene Wert
     * entscheidet &uuml;ber die tats&auml;chliche Anzahl
     * der bei der Testlaufauswertung erwarteten maximale Anzahl von Schl&uuml;mpfen pro Schiff
     * - und damit <strong><u>NICHT</u></strong> zwingend
     * der von {@link #getWantedMaximumNumberOfShipsPerLanding()} abgelieferte Wert.
     * <br />
     * <br />
     * @return die (f&uuml;r Ihren Testlauf) von Ihnen &quot;gew&uuml;nschte&quot; maximale Anzahl von Schiffen,
     *         die gleichzeitig an einer Anlegestelle anlegen d&uuml;rfen.
     */
    @ChunkPreamble ( lastModified="2013/05/31", lastModifiedBy="Michael Schäfers" )
    public abstract Integer getWantedMaximumNumberOfShipsPerLanding();
    
    
    
    //JavaDOC                                                                   // adapt _HERE_
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
     * &nbsp; &nbsp &nbsp; &nbsp {@link #getWantedNumberOfShips()},                 <br />
     * &nbsp; &nbsp &nbsp; &nbsp {@link #getWantedNumberOfLandings()},              <br />
     * &nbsp; &nbsp &nbsp; &nbsp {@link #getWantedMaximumNumberOfSmurfsPerShip()},  <br />
     * &nbsp; &nbsp &nbsp; &nbsp {@link #getWantedMaximumNumberOfShipsPerLanding()} <br />
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
     * &nbsp; &nbsp &nbsp; &nbsp requestedValueForNumberOfShips,                    <br />
     * &nbsp; &nbsp &nbsp; &nbsp requestedValueForNumberOfLandings,                 <br />
     * &nbsp; &nbsp &nbsp; &nbsp requestedValueForMaximumNumberOfSmurfsPerShip,     <br />
     * &nbsp; &nbsp &nbsp; &nbsp requestedValueForMaximumNumberOfShipsPerLanding    <br />
     * &nbsp; &nbsp );         <br />
     * &nbsp; &nbsp &hellip;   <br />
     * }                       <br />
     * </code>
     * denkbar.<br />
     * <br />
     * @param requestedNumberOfSmurfs bestimmt die Anzahl der Schl&uuml;mpfe f&uuml;r den zugeh&ouml;rigen Testlauf.
     * @param requestedNumberOfShips bestimmt die Anzahl der Schiffe f&uuml;r den zugeh&ouml;rigen Testlauf.
     * @param requestedNumberOfLandings bestimmt die Anzahl der Anlegestellen f&uuml;r den zugeh&ouml;rigen Testlauf.
     * @param requestedMaximumNumberOfSmurfsPerShip bestimmt die maximale Anzahl von Schl&uuml;pfen,
     *                                              die sich gleichzeitig auf einem Schiff aufhalten d&uuml;fen.
     * @param requestedMaximumNumberOfShipsPerLanding bestimmt die maximale Anzahl von Schiffen,
     *                                                die gleichzeitig an einer Anlegestellen festmachen d&uuml;rfen.
     */
    @ChunkPreamble ( lastModified="2013/05/19", lastModifiedBy="Michael Schäfers" )
    public abstract void doTest(
        final Integer requestedNumberOfSmurfs,
        final Integer requestedNumberOfShips,
        final Integer requestedNumberOfLandings,
        final Integer requestedMaximumNumberOfSmurfsPerShip,
        final Integer requestedMaximumNumberOfShipsPerLanding
    );//method()
    
    //JavaDOC                                                                   // adapt _HERE_
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
    
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * {@link #letThereBeLife()} startet den eigentlich Test an (durch Aufruf von {@link #doTest(Integer,Integer,Integer,Integer,Integer)}),
     * setzt interne Variablen (die Sie <u>nicht</u> interessieren m&uuml;ssen)
     * und macht einige zus&auml;tzliche Ausgaben.
     */
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" )
    public final void letThereBeLife(){
        super.letThereBeLife(
            //------------------------------------------------------------------
            new Integer[]{
                
                5,                                                  // part Id
                
                getWantedNumberOfSmurfs(),                 null,    // number of smurfs
                getWantedNumberOfShips(),                     6,    // number of WOE / ships
                getWantedNumberOfLandings(),                  6,    // number of WOE stops / (ship) landings
                
                getWantedMaximumNumberOfSmurfsPerShip(),    157,    // max number of smurfs per WOE   ;   null <=> egal
                getWantedMaximumNumberOfShipsPerLanding(),    2,    // max number of WOE per location ;   null <=> egal
               
                7,  59,                                             // min/max number of stops
                
                0,  31,                                             // min/max smurf arrival time (in units)
                3,  37,                                             // min/max smurf dwell (time for doing stuff in units)
                
                19, 29,                                             // min/max ship/WOE in-motion time (in units)
                17, 23,                                             // min/max ship dwell (boarding time in units)
                
                10                                                  // one unit equals <number> milli seconds
            },
            //------------------------------------------------------------------
            new int[][]{
                { 0,1,2,3,4,5 },
                { 1,0,5,4,3,2 },
                { 2,3,4,5,0,1 },
                { 3,2,1,0,5,4 },
                { 4,5,0,1,2,3 },
                { 5,4,3,2,1,0 }
            },
            // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
            CircleShipStyle,
            //------------------------------------------------------------------
            SHIP,
            //------------------------------------------------------------------
            true
        );  // just prime numbers (to be used as test values): 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127
    };//method()
    
}//class
