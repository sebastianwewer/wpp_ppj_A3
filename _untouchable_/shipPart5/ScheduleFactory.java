package _untouchable_.shipPart5;


import _untouchable_.common.*;


/**
 * This class is for internal use only.<br />
 * Do <strong><u>NOT(!)</u></strong> use this class (in your code directly).
 */
@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2013/05/20",
    currentRevision = "0.93",
    lastModified    = "2012/07/23",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
class ScheduleFactory extends CommonScheduleFactory {                           // friendly on purpose
    
    @ChunkPreamble ( lastModified="2013/05/17", lastModifiedBy="Michael Schäfers" )
    @Override
    public CommonSchedule createSchedule(){ return new Schedule(); }

}//class
