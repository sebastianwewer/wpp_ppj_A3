package _untouchable_.busPart4;


import _untouchable_.common.*;


/**
 * Die Schedule-Eintr&auml;ge bzw. Objekte der Klasse
 * {@link #SSI} (<strong>S</strong>cheduled <strong>S</strong>top <strong>I</strong>nfo)
 * definieren, was als n&auml;chstes zu tun ist.
 * Sie k&ouml;nnen diese Klasse als &quot;Block Box&quot; betrachten.
 * Sie ben&ouml;tigen Objekte dieser Klasse als Parameter f&uuml;r &quot;Methoden&quot;.
 * Ansonsten sollten Sie sich <strong><u>nicht</u></strong> weiter mit dieser Klasse besch&auml;ftigen.
 */
@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2013/05/20",
    currentRevision = "0.91",
    lastModified    = "2012/07/23",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
public final class SSI extends CommonSSI {
    
    @ChunkPreamble ( lastModified="2012/07/23", lastModifiedBy="Michael Schäfers" )
    public SSI( final Integer position, final Integer dwell ){
        super( position, dwell );
        
        
        //
        //
        // check for crazy "stuff" or malpractice of environment
        //
        if ( Global.getData() == null )  throw new IllegalStateException();
        //
        final String locHere = new Object(){}.getClass().getPackage().getName();
        if (! locHere.matches( "^_untouchable_." + Local.cmnPath + Global.getData().partId + "$" )){
            throw new IllegalStateException(
                "Sie handeln nicht gemaess Aufgabenstellung - pruefen Sie Ihre Package-Struktur -> "
                + locHere
            );
        }//if
    }//constructor()
    
}//class
