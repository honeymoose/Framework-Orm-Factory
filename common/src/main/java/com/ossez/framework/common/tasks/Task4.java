package com.ukg.framework.common.tasks;

import org.apache.commons.lang3.ObjectUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.time.DateTimeException;
import java.util.Date;
import java.util.Objects;


public class Task4 {


    static class TimeRange implements Serializable {
        private static final long serialVersionUID = 6474765081240948885L;
        /*
         * Design a class that will represent a time range: start time to end time
         * One possible use for such a class is in scheduling or booking application.
         * Example: Conference room will be used from Monday 10am to 12pm
         * Create any code constructors, variables and methods that you feel are necessary.
         * One such method should test for intersection or conflict
         * in times between 2 instances of TimeRange
         *
         */
        private Date startDateTime;
        private Date endDateTime;

        public TimeRange(Date startDateTime, Date endDateTime) {
            this.startDateTime = startDateTime;
            this.endDateTime = endDateTime;
        }

        public Date getStartDateTime() {
            return startDateTime;
        }

        public void setStartDateTime(Date startDateTime) {
            this.startDateTime = startDateTime;
        }

        public Date getEndDateTime() {
            return endDateTime;
        }

        public void setEndDateTime(Date endDateTime) {
            this.endDateTime = endDateTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TimeRange timeRange = (TimeRange) o;
            return Objects.equals(startDateTime, timeRange.startDateTime) && Objects.equals(endDateTime, timeRange.endDateTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDateTime, endDateTime);
        }

        public Boolean isConflict(TimeRange tr2) throws DateTimeException {

            if (this.isValidRange() && tr2.isValidRange()) {
                if (tr2.startDateTime.before(this.endDateTime))
                    return true;
                else
                    return false;
            } else {
                throw new DateTimeException("Range Error ");
            }
        }

        /**
         * @return
         */
        public Boolean isValidRange() throws DateTimeException {

            if (ObjectUtils.isNotEmpty(startDateTime) && ObjectUtils.isNotEmpty(endDateTime) && startDateTime.before(endDateTime)) {
                return true;
            } else {
                throw new DateTimeException("Time Range Error ");
            }
        }
    }

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");


        TimeRange tr1 = new TimeRange(DateTime.parse("2021-12-09 08:00:01", formatter).toDate(), DateTime.parse("2021-12-09 10:00:01", formatter).toDate());
        TimeRange tr2 = new TimeRange(DateTime.parse("2021-12-09 08:00:01", formatter).toDate(), DateTime.parse("2021-12-09 08:30:01", formatter).toDate());
        try {
            System.out.println(tr1.isConflict(tr2));
        } catch (DateTimeException e) {
            e.printStackTrace();
        }
    }

}
