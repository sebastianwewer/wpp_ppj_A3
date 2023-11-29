package _untouchable_.common;


//import java.lang.annotation.Documented;


//@Documented
public @interface ChunkPreamble {
    String lastModified() default "yyyy/mm/dd";
    String lastModifiedBy() default "N/A";
}//ChunkPreamble
