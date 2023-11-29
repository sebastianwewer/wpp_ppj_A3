package _untouchable_.common;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import _untouchable_.GivenCodeVersion;


/**
 * This class is for internal use only.<br />
 * Do <strong><u>NOT(!)</u></strong> use this class (in your code).
 */
@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2013/06/07",
    currentRevision = "0.94",
    lastModified    = "2013/06/27",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
public abstract class CommonTestAndEnvironment {
    
    /**
     * This constructor {@link #CommonTestAndEnvironment_A()} is for internal use only.<br />
     * Do <strong><u>NOT(!)</u></strong> use this constructor.
     */
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    public CommonTestAndEnvironment(){
        // NO Functionality - just check for crazy "stuff" or malpractice of environment
        final String here = new Object(){}.getClass().getPackage().getName();
        if (! here.matches( "_untouchable_.common$" )){
            System.err.printf( "Sie handeln nicht gemaess Aufgabenstellung - Pfade/package path fehlerhaft\n" );
            System.err.printf( "-> Stimmmt die Package.Struktur?\n" );
            System.err.printf( "-> Stimmen die Import-Listen?\n" );
            System.err.flush();
            throw new IllegalStateException( "unexpected package path -> " + here );
        }//if
    }//contructor()
    
    
    
    
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * {@link #getAuthor()} liefert den Namen des Autors bzw. die Namen der Autoren.
     */
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    public abstract String getAuthor();
    
    
    
    //JavaDOC                                                                   // adapt _HERE_
    /**
     * {@link #getWantedNumberOfSmurfs()} liefert die Anzahl der Schl&uuml;mpfe,
     * die Sie sich f&uuml;r Ihren Testlauf &quot;w&uuml;nschen&quot;.
     * Die Anzahl der Schl&uuml;mpfe, die (beim Test) bei der Abnahme verwendet wird,
     * kann hiervon abweichen.
     * <br />
     * <br />
     * <u>Bemerkung:</u>
     * <br />
     * Der in den &quot;Tiefen&quot; des &quot;Testsystems&quot; an
     * die gem&auml;&szlig; Aufgabenstellung in Ihrem TestFrame befindliche Methode
     * &quot;<code>public void doTest(<strong>Integer</strong>,Integer,Integer,Integer,Integer)</code>&quot;
     * &uuml;bergebene Wert
     * entscheidet &uuml;ber die tats&auml;chliche Anzahl
     * der bei der Testlaufauswertung erwarteten Schl&uuml;mpfe
     * - und damit <strong><u>NICHT</u></strong> zwingend
     * der von {@link #getWantedNumberOfSmurfs()} abgelieferte Wert.
     * <br />
     * <br />
     * @return die von Ihnen &quot;gew&uuml;nschte&quot; Anzahl der Schl&uuml;mpfe f&uuml;r Ihren Testlauf.
     */
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    public abstract Integer getWantedNumberOfSmurfs();
    
    
    
    /**
     * This method {@link #doTheTest(Integer,Integer,Integer,Integer,Integer)} is for internal use only.<br />
     * Do <strong><u>NOT(!)</u></strong> use this method.
     * @param rnos for internal usage ;-)
     * @param rnow for internal usage ;-)
     * @param rnol for internal usage ;-)
     * @param rmnspw for internal usage ;-)
     * @param rmnwpl for internal usage ;-)
     */
    @ChunkPreamble ( lastModified="2013/05/19", lastModifiedBy="Michael Schäfers" )
    public abstract void doTheTest(
       final Integer rnos,
       final Integer rnow,
       final Integer rnol,
       final Integer rmnspw,
       final Integer rmnwpl
    );//method()
    
    
    
    
    
    /**
     * This method {@link #doTheTest(Integer[],int[][],MovementStyle)} is for internal use only.<br />
     * Do <strong><u>NOT(!)</u></strong> use this method.
     * @param par for internal usage ;-)
     * @param woeRoutes for internal usage ;-)
     * @param movementStyle for internal usage ;-)
     * @param woeType for internal usage ;-)
     */
    @ChunkPreamble ( lastModified="2016/04/25", lastModifiedBy="Michael Schäfers" )
    public final void letThereBeLife( final Integer[] par,  final int[][] woeRoutes,  final MovementStyle movementStyle,  final WoeType woeType,  final boolean grantWishes ){
        
        Global.createSingleInstanceOfGlobal( par, woeType, grantWishes );
        final Global global = Global.getData();
        
        
        //
        //
        // check if parameter (wanted values) harmonize with programmed checks
        //
        
        boolean requirementsForCheckOk = true;
        
        // wanted number of smurfs ok ?
        if ( null!=global.rnos  &&  ! global.wnos.equals(global.rnos) ){                                          // constants only exists once
            requirementsForCheckOk = false;
            System.err.println( String.format( "Illegal test configuration : (wanted #smurf =) %d != %d (= requested #smurf)", global.wnos, global.rnos ) );
        }//if
        //
        // wanted number of WOE ok ?
        if ( null!=global.rnow  &&  ! global.wnow.equals(global.rnow) ){                                          // constants only exists once
            requirementsForCheckOk = false;
            System.err.println( String.format( "Illegal test configuration : (wanted #woe =) %d != %d (= requested #woe)", global.wnow, global.rnow ) );
        }//if
        //
        // wanted number of locations/stops ok ?
        if ( null!=global.rnol  &&  ! global.wnol.equals(global.rnol) ){                                          // constants only exists once
            requirementsForCheckOk = false;
            System.err.println( String.format( "Illegal test configuration : (wanted #stop =) %d != %d (= requested #stop)", global.wnol, global.rnol ) );
        }//if
        //
        if ( null!=global.rmnspw  &&  ! global.wmnspw.equals(global.rmnspw) ){                                    // constants only exists once
            requirementsForCheckOk = false;
            System.err.println( String.format( "Illegal test configuration : (wanted s/w =) %d != %d (= requested s/w)", global.wmnspw, global.rmnspw ) );
        }//if
        //
        if ( null!=global.rmnwpl  &&  ! global.wmnwpl.equals(global.rmnwpl) ){                                    // constants only exists once
            requirementsForCheckOk = false;
            System.err.println( String.format( "Illegal test configuration : (wanted w/l =) %d != %d (= requested w/l)", global.wmnwpl, global.rmnwpl ) );
        }//if
        
        // is there a reference route for each WOE ?
        if ( woeRoutes.length != global.wnow ){
            requirementsForCheckOk = false;
            System.err.println( String.format( "Illegal test configuration : (#woe =) %d != %d (= #routes)", woeRoutes.length, global.wnow ) );
        }//if
        //
        // is the number of stops (in trace cycle) for each WOE route ok ?
        int traceCycleLength = Integer.MIN_VALUE;
        if ( movementStyle == MovementStyle.LineBusStyle ){
            traceCycleLength = 2*global.wnol - 2;                     // both "ends" are listed only once
        }else{
            traceCycleLength = global.wnol;
        }//if
        for ( int i=woeRoutes.length; --i>=0; ){
            if ( woeRoutes[i].length != traceCycleLength ){
                requirementsForCheckOk = false;
                System.err.println( String.format( "Illegal test configuration : (route[%d] =) %d != %d (= #stops)", i, woeRoutes[i].length, traceCycleLength ) );
            }//if
        }//for
        //
        if ( grantWishes ) requirementsForCheckOk = true;
        
        
        final long requiredNumberOfSuccessfullTestExecutions = 1;               // currently NOT supported
        magicTestLoop: while(true){
            
            //
            //
            // start action
            //
            
            startEverything( "WOE", "location" );
            try{
                TimeUnit.SECONDS.sleep( 2 );
            }catch( InterruptedException ex ){ ex.printStackTrace(); }
            System.out.printf( "_____ACTION_________________________________________\n\n\n" );
            
            
            doTheTest( global.fnos, global.fnow, global.fnol, global.fmnspw, global.fmnwpl );           // 160425
            
            try{
                TimeUnit.SECONDS.sleep( 1 );
            }catch( InterruptedException ex ){ ex.printStackTrace(); }
            announceTheEnd();
            
            
            
            // _HERE_ : Start of Verification --------------------------------------
            if ( requirementsForCheckOk ){
                Investigation.investigate( woeRoutes,  movementStyle );
            }else{
                System.err.println( String.format( "Illegal test configuration : NO checks will be done" ) );
            }//if
            
            
            
            if ( requiredNumberOfSuccessfullTestExecutions <= global.testExecutionLoopCount.getAndIncrement() ) break magicTestLoop;
            assert false : "currently NOT supported";
            
            System.err.flush();
            System.out.flush();
            System.out.printf( "\n\n\n" );
            System.out.printf( "################################################################################\n" );
            System.out.printf( "###\n" );
            System.out.printf( "### run# %d\n",  global.testExecutionLoopCount.get() );
            System.out.printf( "###\n\n" );
            
        }//while
    }//method()
    //
    //
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //
    //
    @ChunkPreamble ( lastModified="2016/04/27", lastModifiedBy="Michael Schäfers" )
    private final void startEverything( final String woeText, final String locText ){
        System.out.printf( "Informationen zum Environment:\n" );
        System.out.printf( "==============================\n" );
        System.out.printf( "\n" );
        System.out.printf( "Version:  %s  former:0.98c\n", GivenCodeVersion.getDecodedVersion() );
        System.out.printf( "\n" );
        System.out.printf( "Java:     %s bzw. %s\n",       System.getProperty( "java.specification.version" ), System.getProperty( "java.version" ) );
        System.out.printf( "O.-P.:    %s\n",               new Object().getClass().getPackage() );
        System.out.printf( "Class:    %s\n",               getClass().getCanonicalName() );
        System.out.printf( "WOE ::=   %s\n",               Global.getData().woeType );
        System.out.printf( "Author:   %s\n",               getAuthor() );
        System.out.printf( "\n" );
        System.out.printf( "There will be %5d [smurf]\n",  Global.getData().wnos );
        System.out.printf( "There will be %5d [%s]\n",     Global.getData().wnow, woeText ); // WhatsOEver :  bus,      elevator, ship
        System.out.printf( "There will be %5d [%s]\n",     Global.getData().wnol, locText ); // LOCation   :  bus stop, level,    landing
        System.out.printf( "\n" );
        if ( Global.getData().wmnspw != null )  System.out.printf( "Maximum number of smurf(s) per WOE:   %4d\n",  Global.getData().wmnspw );
        if ( Global.getData().wmnwpl != null )  System.out.printf( "Maximum number of WOE(s) per location:%4d\n",  Global.getData().wmnwpl );
        System.out.printf( "\n\n\n" );
        System.out.printf( "The Action starts now (respectively at: %s system time)\n", new SimpleDateFormat( "HH:mm:ss.SSS", new Locale("de","DE") ).format( new Date().getTime() ) );
        System.out.printf( "####################################################\n" );
        System.out.printf( "vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv\n" );
        System.out.flush();
    }//method()
    //
    //
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" )
    private final void announceTheEnd(){
        System.out.printf( "\n\n" );
        System.out.printf( "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n" );
        System.out.printf( "####################################################\n" );
        System.out.printf( "All smurfs have settled their schedule (hopefully;-)\n" );
        System.out.printf( "THE END after %s\n",  Tool.prettyTime( Tool.nanoElapsedTime() ) );
        System.out.flush();
    }//method()
    
}//class