package de.lumiliaro.symptothermapp.helper;

import java.util.Calendar;
import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CalenderHelper {
  private Calendar calender;
  private final int month;
  private final int year;

  private int maxDayOfMonth;
  private Date startDate;
  private Date endDate;

  public CalenderHelper(int month, int year) {
    this.month = month;
    this.year = year;

    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MONTH, month - 1);
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    startDate = calendar.getTime();
    calendar.set(Calendar.DAY_OF_MONTH, maxDayOfMonth);
    endDate = calendar.getTime();
  }
}
