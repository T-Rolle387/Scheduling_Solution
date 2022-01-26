package Utility;

import java.sql.Timestamp;
import java.time.*;
import java.time.chrono.ChronoPeriod;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

public class TimeConversion {


    public static String convertLDTToString(LocalDateTime time){

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
         String apptTime = time.format(formatter);

         return apptTime;
     }




}
//LocalDate localDate, LocalTime localTime