package _untouchable_.common;


@ClassPreamble (
    author          = "Michael Schäfers",
    organization    = "Dept.Informatik; HAW Hamburg",
    date            = "2012/07/19",
    currentRevision = "0.93",
    lastModified    = "2012/07/20",
    lastModifiedBy  = "Michael Schäfers",
    reviewers       = ( "none" )
)
public abstract class CommonScheduleFactory {
    
    @ChunkPreamble ( lastModified="2012/07/20", lastModifiedBy="Michael Schäfers" )
    public abstract CommonSchedule createSchedule();

}//class ScheduleFactory_A
