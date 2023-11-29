package _untouchable_.common;


//import java.lang.annotation.*;


//@Documented
public @interface ClassPreamble {
    String author() default "Michael Schäfers";
    String organization() default "Dept.Informatik; HAW Hamburg";
    String date() default "yyyy/mm/dd";                                         // yyyy/mm/dd
    String currentRevision() default "1.0";
    String lastModified() default "yyyy/mm/dd";                                 // yyyy/mm/dd
    String lastModifiedBy() default "N/A";
    String reviewed() default "N/A";                                            // yyyy/mm/dd
    String[] reviewers() default { "N/A" };
}//annotation
