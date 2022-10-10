package it.itresources.springtut.springtutorial.mapper;

import java.time.LocalDate;

public class DateMapper {
    public static LocalDate stringToDate(String dateString){
        String[] dateValues=dateString.split("-");
        return LocalDate.of(Integer.parseInt(dateValues[2]),Integer.parseInt(dateValues[1]),Integer.parseInt(dateValues[0]));
    }
    public static String dateToString(LocalDate date)
    {
        String stringDate=date.toString();
        return stringDate;
    }

}
