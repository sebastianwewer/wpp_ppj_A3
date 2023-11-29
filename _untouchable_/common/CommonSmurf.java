package _untouchable_.common;


/**
 * This class is for internal use only.<br />
 * Do <strong><u>NOT(!)</u></strong> use this class (in your code directly).
 */
@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2013/05/31",
    currentRevision = "0.93",
    lastModified    = "2013/05/31",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
abstract public class CommonSmurf extends RawSmurf {
    
    /**
     * Der Aufruf von {@link #waitUntilNextArrival()} modelliert die Zeit,
     * die verstreicht bis der n&auml;chste Schlumpf eintrifft.
     * <br />
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
     * <br />
     * @throws InterruptedException wenn Methode beim Warten unterbrochen wird.
     */
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    public final static void waitUntilNextArrival() throws InterruptedException {     // models arrival rate
        RawSmurf.waitUntilNextArrival();
    }//waitUntilNextArrival
    
    
    
    
    
    /**
     * This constructor {@link #CommonSmurf_A( CommonScheduleFactory, boolean )} is for internal use only.<br />
     * Do <strong><u>NOT(!)</u></strong> use this constructor.
     */
    @ChunkPreamble ( lastModified="2012/07/20", lastModifiedBy="Michael Schäfers" )
    public CommonSmurf( final CommonScheduleFactory factory ){
        // checks are done in super class
        //
        // construct
        super( factory );
        this.schedule = super.schedule;
    }//CommonSmurf()
    
    
    
    
    
    /**
     * {@link #identify()} liefert eine Identifikationsnummer f&uuml;r den jeweiligen Schlumpf,
     * die den Schlumpf eindeutig identifiziert bzw. von anderen Schl&uuml;mpfen unterscheidet.
     * @return die ID des jeweiligen Schlumpfs.
     */
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    @Override
    abstract public int identify();
    
    /*                                                                          removing locate()
     * {@link #locate()} liefert die aktuelle Position des jeweiligen Schlumpfs.
     * @return die aktuelle Position des jeweiligen Schlumpfs.
     *
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    @Override
    abstract public int locate();
    */
    
    /**
     * {@link #getDebugState()} liefert den &quot;Debug Status&quot;.
     * @return <code>true</code> f&uuml;r Debug-Ausgaben enabled und <code>false</code> f&uuml;r Debug-Ausgaben disabled.
     */
    @ChunkPreamble ( lastModified="2012/07/20", lastModifiedBy="Michael Schäfers" )
    @Override
    abstract public boolean getDebugState();
    
    
    
    
    
    /**
     * Im {@link #schedule} sind die Schedule-Eintr&auml;ge ({@link SSI}) organisiert.
     * {@link #schedule} implementiert das {@link java.util.Iterator}<{@link SSI}>-Interface.
     */
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    public final CommonSchedule schedule;
    
    
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * Der Aufruf von {@link #takeTimeForDoingStuffAtCurrentPosition(int,CommonSSI)} modelliert die Zeit,
     * die der Schlumpf am jeweiligem Ort verweilt.
     * Diese Aktivit&auml;t (bzw. der Aufruf dieser Methode) muss <strong><u>zwingend</u></strong>
     * vom Schlunpf-Thread selbst ausgef&uuml;hrt werden.
     * @param position an der sich der Schlumpf gerade aufh&auml;lt.
     * @param ssi die aktuelle <strong>S</strong>cheduled <strong>S</strong>top <strong>I</strong>nfo
     * @throws InterruptedException wenn Methode beim Warten unterbrochen wird.
     */
    @ChunkPreamble ( lastModified="2013/06/01", lastModifiedBy="Michael Schäfers" )
    public final void takeTimeForDoingStuffAtCurrentPosition( final int position, final CommonSSI ssi ) throws InterruptedException {
        super.takeTimeForDoingStuffAtCurrentPosition( getDebugState(), position, ssi );
    }//takeTimeForDoingStuffAtCurrentPosition()
    
    
    /**
     * This method {@link #enter(CommonWOE,String)} is for internal use only.<br />
     * Do <strong><u>NOT(!)</u></strong> use this method.
     * @param woe for internal usage ;-)
     * @param woeText for internal usage ;-)
     */
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    public void enter( final CommonWOE woe,  final String woeText ){
        super.enter( getDebugState(), woe, woeText );
    }//enter()
    
    /**
     * This method {@link #beThere(CommonWOE,String)} is for internal use only.<br />
     * Do <strong><u>NOT(!)</u></strong> use this method.
     * @param thread for internal usage ;-)
     */
    @ChunkPreamble ( lastModified="2013/05/13", lastModifiedBy="Michael Schäfers" )
    public final void beThere( final CommonWOE woe,  final String woeText ){
        super.beThere( getDebugState(), woe, woeText );
    }//beThere()
    
    /**
     * This method {@link #leave(CommonWOE,String)} is for internal use only.<br />
     * Do <strong><u>NOT(!)</u></strong> use this method.
     * @param woe for internal usage ;-)
     * @param woeText for internal usage ;-)
     */
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    public final void leave( final CommonWOE woe,  final String woeText ){
        super.leave( getDebugState(), woe, woeText );
    }//leave()
    
    
    /**
     * Der Aufruf von {@link #lastDeed()} muss die <strong><u>letzte(!)</u></strong> Aktion des jeweiligen Schlumpfs sein.<br />
     * (Insbesondere bedeutet dies, dass nach dem Aufruf von {@link #lastDeed()} der jeweilige (Schlumpf-)Thread terminieren muss.)
     */
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    public final void lastDeed(){
        super.lastDeed( getDebugState() );
    }//lastDeed()
    
    
    
    
    
    // internalSmurfID is unique and clone() is forbidden
    //
    @ChunkPreamble ( lastModified="2013/05/23", lastModifiedBy="Michael Schäfers" )
    @Override public boolean equals( Object other ){ return super.equals( other ); }
    //
    @ChunkPreamble ( lastModified="2013/05/23", lastModifiedBy="Michael Schäfers" )
    @Override public int hashCode(){ return super.hashCode(); }
    
}//class CommonSmurf
