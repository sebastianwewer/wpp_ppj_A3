package _untouchable_.common;


@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2013/05/20",
    currentRevision = "0.93",
    lastModified    = "2012/07/19",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
//the WHOLE CLASS and ALL of its ELEMENTS are friendly on purpose (!)
class EvTab_IDxTime {
    
    final int  id;      // ID of event related smurf / WOE 
    long  time;         // time of event
    
    EvTab_IDxTime( final int id,  final long time ){ this.id=id; this.time=time; }
    EvTab_IDxTime(){ this( -1, -1 ); }
    
}//class
