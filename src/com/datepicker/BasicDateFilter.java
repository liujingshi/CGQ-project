package com.datepicker;

import java.util.Date;

public class BasicDateFilter implements DateFilter {

    /*
     * (non-Javadoc)
     * 
     * @see com.datepicker.DateFilter#filter(java.util.Date)
     */
    public boolean filter(Date date) {
        return false;
    }

}
