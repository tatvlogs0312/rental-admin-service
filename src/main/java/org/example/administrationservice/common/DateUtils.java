package org.example.administrationservice.common;

import lombok.extern.slf4j.Slf4j;
import org.example.administrationservice.exception.ApplicationException;
import org.example.administrationservice.exception.ExceptionEnums;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class DateUtils {
    public static final String F_DDMMYYYYHHMMSS = "dd/MM/yyyy HH:mm:ss";
    public static final String F_DDMMYYYY = "dd/MM/yyyy";
    public static final String DDMMYYYY = "ddMMyyyy";
    public static final String F_MMYYYY = "MM/yyyy";
    public static final String F_YYYY = "yyyy";
    public static final String DATE_TIME_FORMAT_VI = "dd-MM-yyyy HH:mm:ss";
    public static final String DATE_TIME_FORMAT_VI_OUTPUT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT_GENERAL = "EEE MMM dd HH:mm:ss zzz yyyy";
    public static final String YYYY = "yyyy";
    public static final String YYMMDD = "yyMMdd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String MM_YYYY = "MM/yyyy";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DD_MM_YYYY = "dd-MM-yyyy";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy/MM/dd HH:mm";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String HH_MM = "HH:mm";
    public static final String HHMMSS = "HHmmss";
    public static final String F_DDMMYYYYHHMM = "dd/MM/yyyy HH:mm";
    public static final String F_HHMMDDMMYYYY = "HH:mm dd/MM/yyyy";
    public static final String F_YYYY_MM_DD_T_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss";

    public static final String F_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
    public static final String F_MM_YY = "MM-YYYY";
    public static List<String> patterns = null;

    static {
        patterns = new ArrayList<>();
        patterns.add(DATE_TIME_FORMAT_VI);
        patterns.add(DATE_TIME_FORMAT_VI_OUTPUT);
        patterns.add(F_DDMMYYYYHHMMSS);
        patterns.add(DATE_TIME_FORMAT_GENERAL);
        patterns.add(F_DDMMYYYY);
        patterns.add(MM_YYYY);
    }

    public static String toStr(LocalDate date, String format) {
        try {
            if (Objects.nonNull(date)) {
                DateTimeFormatter formatters = DateTimeFormatter.ofPattern(format);
                return date.format(formatters);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public static String toStr(LocalTime time, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return time.format(dateTimeFormatter);
    }

    public static boolean isValid(String date, String format) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static LocalDate convertToLocalDate(String dateAsString, String format) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
            return LocalDate.parse(dateAsString, dateTimeFormatter);
        } catch (Exception e) {
            log.error("convertToLocalDate error: {} {}", dateAsString, format);
            throw new ApplicationException(ExceptionEnums.DATA_NOT_VALID);
        }
    }
}
