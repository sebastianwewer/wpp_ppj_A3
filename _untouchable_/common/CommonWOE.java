package _untouchable_.common;


/**
 * This class is for internal use only.<br />
 * Do <strong><u>NOT(!)</u></strong> use this class (in your code directly).
 */
@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2013/05/20",
    currentRevision = "0.93",
    lastModified    = "2013/05/27",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
public abstract class CommonWOE extends RawWOE {
    
    /**
     * {@link #identify()} liefert eine Identifikationsnummer f&uuml;r das jeweilige WOE,
     * die das WOE eindeutig identifiziert bzw. von anderen WOE unterscheidet.
     * @return die ID des jeweiligen WOE.
     */
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    @Override
    abstract public int identify();
    
    /**
     * {@link #getDebugState()} liefert den &quot;Debug Status&quot;.
     * @return <code>true</code> f&uuml;r Debug-Ausgaben enabled und <code>false</code> f&uuml;r Debug-Ausgaben disabled.
     */
    @ChunkPreamble ( lastModified="2012/07/20", lastModifiedBy="Michael Schäfers" )
    @Override
    abstract public boolean getDebugState();
    
    /**
     * {@link #terminate()} terminiert
     * (im sinnvollen Rahmen so schnell wie m&ouml;glich)
     * den zugeh&ouml;rigen Thread.
     */
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    @Override
    abstract public void terminate();
    
    
    
    
    
    /**
     * This constructor {@link #CommonWOE_A( boolean )} is for internal use only.<br />
     * Do <strong><u>NOT(!)</u></strong> use this constructor.
     */
    @ChunkPreamble ( lastModified="2012/07/20", lastModifiedBy="Michael Schäfers" )
    public CommonWOE(){
        // checks are done in super class
        //
        // construct
        super();
    }//constructor()
    
    
    
    
    
    /**
     * This method {@link #appear(int,String,String)} is for internal use only.<br />
     * Do <strong><u>NOT(!)</u></strong> use this method.
     * @param location for internal usage ;-)
     * @param woeText for internal usage ;-)
     * @param actionText for internal usage ;-)
     */
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    public final void appear( final int location,  final String woeText,  final String actionText ){
        super.appear( getDebugState(), location, woeText, actionText );
        super.allowEntrance();
    }//method()
    
    
    
    /**
     * This method {@link #vanish(int,String,String)} is for internal use only.<br />
     * Do <strong><u>NOT(!)</u></strong> use this method.
     * @param location for internal usage ;-)
     * @param woeText for internal usage ;-)
     * @param actionText for internal usage ;-)
     */
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    public final void vanish( final int location,  final String woeText,  final String actionText ){
        super.forbidEntrance();
        super.vanish( getDebugState(), location, woeText, actionText );
    }//method()
    
    
    /**
     * This method {@link #takeTimeForMotionTo(int,String,String,String)} is for internal use only.<br />
     * Do <strong><u>NOT(!)</u></strong> use this method.
     * @param destinationPosition for internal usage ;-)
     * @param woeText for internal usage ;-)
     * @param actionText1 for internal usage ;-)
     * @param actionText2 for internal usage ;-)
     * @throws InterruptedException wenn die Methode (beim &quot;internen&quot; Warten) unterbrochen wird ;-)
     */
    @ChunkPreamble ( lastModified="2016/03/27", lastModifiedBy="Michael Schäfers" )
    public final void takeTimeForMotionTo(
            final int destinationPosition,
            final String woeText,
            final String actionText1,
            final String actionText2
    ) throws InterruptedException {
        super.takeTimeForMotionTo( getDebugState(), destinationPosition, woeText, actionText1, actionText2 );
    }//method()
    
    
    /**
     * This method {@link #takeTimeForStopover(int)} is for internal use only.<br />
     * Do <strong><u>NOT(!)</u></strong> use this method.
     * @param location for internal usage ;-)
     * @throws InterruptedException wenn die Methode (beim &quot;internen&quot; Warten) unterbrochen wird ;-)
     */
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    public final void takeTimeForStopover( final int location ) throws InterruptedException {
        super.takeTimeForStopover( getDebugState(), location );
    }//method()
    
    
    /**
     * This method {@link #lastDeed(String)} is for internal use only.<br />
     * Do <strong><u>NOT(!)</u></strong> use this method.
     * @param woeText for internal usage ;-)
     */
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    public final void lastDeed( final String woeText ){
        super.lastDeed( getDebugState(), woeText );
    }//method()
    
    
    
    
    
    // internalWoeID is unique and clone() is forbidden
    //
    @ChunkPreamble ( lastModified="2013/05/23", lastModifiedBy="Michael Schäfers" )
    @Override public boolean equals( Object other ){ return super.equals( other ); }
    //
    @ChunkPreamble ( lastModified="2013/05/23", lastModifiedBy="Michael Schäfers" )
    @Override public int hashCode(){ return super.hashCode(); }
    
}//class
