package _untouchable_;


import java.io.Serializable;


/**
 * The single purpose of this class is to hold the &quot;project version&quot;.
 * Since the code/project is an exercise, it's named {@link GivenCodeVersion}.
 * It's not expected or wanted that there are different release branches.
 * There has to be only one single release branch (for the "given code")!!!
 * Hence, there is a single/central project version
 * that is stored in this class.<br />
 * <br />
 * <code>
 * Coding/format of (Given) Code Version<br />
 * &nbsp;&nbsp;c: coding format<br />
 * &nbsp;&nbsp;m: main version<br />
 * &nbsp;&nbsp;s: sub version<br />
 * &nbsp;&nbsp;Y: year<br />
 * &nbsp;&nbsp;M: month<br />
 * &nbsp;&nbsp;D: day<br />
 * &nbsp;&nbsp;d: version of day<br />
 * <br />
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; c___mmmm_sss___YYYY_MM_DD__ddd<br />
 * &nbsp;&nbsp;e.g.&nbsp;&nbsp;                     1___0001_014___2021_11_20__001<br />
 * <br />
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; c___mmmmm_sss___YYYY_MM_DD__dd<br />
 * &nbsp;&nbsp;e.g.&nbsp;&nbsp;                     2___00001_014___2021_11_20__01
 * <code />
 * 
 * @version {@value #version}
 * @author  Michael Schaefers ([UTF-8]:"Michael Schäfers");  Px@Hamburg-UAS.eu 
 */
public class GivenCodeVersion implements Serializable {
    
    //  VERSION:                        #---vvvvvvvvv---vvvv-vv-vv--vv
    //  ========                        #___~version~___YYYY_MM_DD__dd_
    final static private long version = 2___00001_008___2023_11_28__01L;
    //                                  #---^^^^^-^^^---^^^^-^^-^^--^^
    static {
        final int leadingDigit = (int)( version / 1__000_000__000_000__000_000L );
        //@SuppressWarnings( "all" )                                            // __???__<211120>
        assert 1<=leadingDigit && leadingDigit<=2 : "setup error : faulty version number coded";  // we are all humans - check that "leading one" has NOT get lost
    }//static block resp. "static initializer" ~ "class-constructor()"
    
    private static final long serialVersionUID = version;
    
    
    
    /**
     * The method {@link #getVersionNumber()} delivers the project version.
     * 
     * @return version
     */
    static public long getVersionNumber(){
        return version;
    }//method()
    
    /**
     * The method {@link #getDecodedVersion()} delivers the given project version as reground/readable String.
     * 
     * @return version as decoded/readable String.
     */
    static public String getDecodedVersion(){
        return decodeVersionNumber( version );
    }//method()
    
    
    
    /**
     * The method {@link #decodeVersionNumber( long )} delivers the given coded version as reground/readable String.
     * 
     * @param version  the version to decode.
     * @return         version as readable String.
     */
    static public String decodeVersionNumber( final long versionNumber ){
        final int leadingDigit = (int)( versionNumber / 1__000_000__000_000__000_000L );
        if( leadingDigit<1 || 2<leadingDigit ) throw new IllegalArgumentException( String.format( "Illegal coded version number -> Format: %d",  leadingDigit ));
        
        int mainVersion = 0;
        int subVersion = 0;
        int year = 0;
        int month = 0;
        int day = 0;
        int dailyVersion = 0;
        //
        long tmp = versionNumber;
        switch( leadingDigit ){
            //  _1___mmmm_sss___YYYY_MM_DD__ddd
            case 1:
                dailyVersion = (int)( tmp %   1_000 );
                tmp /=   1_000;
                day =          (int)( tmp %     100 );
                tmp /=     100;
                month =        (int)( tmp %     100 );
                tmp /=     100;
                year =         (int)( tmp %  10_000 );
                tmp /=  10_000;
                subVersion =   (int)( tmp %   1_000 );
                tmp /=   1_000;
                mainVersion =  (int)( tmp %  10_000 );
                tmp /=  10_000;
                assert 1 == tmp : "Uuuupppss : internal error - there (should) have been checks before";
            break;
            //
            //  _2___mmmmm_sss___YYYY_MM_DD__dd
            case 2:
                dailyVersion = (int)( tmp %     100 );
                tmp /=     100;
                day =          (int)( tmp %     100 );
                tmp /=     100;
                month =        (int)( tmp %     100 );
                tmp /=     100;
                year =         (int)( tmp %  10_000 );
                tmp /=  10_000;
                subVersion =   (int)( tmp %   1_000 );
                tmp /=   1_000;
                mainVersion =  (int)( tmp % 100_000 );
                tmp /= 100_000;
                assert 2 == tmp : "Uuuupppss : internal error - there (should) have been checks before";
            break;
            //
            //  undefined
            default:
                assert false : "Uuuupppss : internal error - there (should) have been checks before";
        }//switch
        //
        final StringBuffer sb = new StringBuffer( "" );
        sb.append( Long.toString( mainVersion ));
        sb.append( "." );
        sb.append( String.format( "%03d", subVersion ));
        sb.append( "   ( " );
        sb.append( Long.toString( year ));
        sb.append( "/" );
        sb.append( Long.toString( month ));
        sb.append( "/" );
        sb.append( Long.toString( day ));
        sb.append( " [#" );
        sb.append( Long.toString( dailyVersion ));
        sb.append( "] )" );
        return sb.toString();
    }//method()
    
}//class
