package _untouchable_.shipPart5;


import _untouchable_.common.ChunkPreamble;
import _untouchable_.common.ClassPreamble;
import _untouchable_.common.WoeType;

import static _untouchable_.common.WoeType.*;


@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2013/05/20",
    currentRevision = "0.93",
    lastModified    = "2013/05/17",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
//the WHOLE CLASS and ALL of its ELEMENTS are friendly on purpose (!)
class Local {
    @ChunkPreamble ( lastModified="2013/05/17", lastModifiedBy="Michael Schäfers" ) static final WoeType woeType = SHIP;                    // adapt _HERE_
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" ) static final String  woeText = "ship";                  // adapt _HERE_
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" ) static final String  cmnPath = "shipPart";              // adapt _HERE_
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" ) static final String  reqPath = "solution4SE";           // adapt _HERE_
    @ChunkPreamble ( lastModified="2012/07/19", lastModifiedBy="Michael Schäfers" ) static final boolean partSeparationRequired = false;    // adapt _HERE_
}//class
