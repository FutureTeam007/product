package com.ei.itop.common.dbentity;

import java.util.Date;

public class ScHoliday {
    /**
     * This field was generated by AILK AUTOMATIC TOOLS.
     * This field corresponds to the database column SC_HOLIDAY.SC_ORG_ID
     *
     * @abatorgenerated Wed Nov 26 21:55:28 CST 2014
     */
    private Long scOrgId;

    /**
     * This field was generated by AILK AUTOMATIC TOOLS.
     * This field corresponds to the database column SC_HOLIDAY.HOLIDAY
     *
     * @abatorgenerated Wed Nov 26 21:55:28 CST 2014
     */
    private Date holiday;

    /**
     * This method was generated by AILK AUTOMATIC TOOLS.
     * This method returns the value of the database column SC_HOLIDAY.SC_ORG_ID
     *
     * @return the value of SC_HOLIDAY.SC_ORG_ID
     *
     * @abatorgenerated Wed Nov 26 21:55:28 CST 2014
     */
    public Long getScOrgId() {
        return scOrgId;
    }

    /**
     * This method was generated by AILK AUTOMATIC TOOLS.
     * This method sets the value of the database column SC_HOLIDAY.SC_ORG_ID
     *
     * @param scOrgId the value for SC_HOLIDAY.SC_ORG_ID
     *
     * @abatorgenerated Wed Nov 26 21:55:28 CST 2014
     */
    public void setScOrgId(Long scOrgId) {
        this.scOrgId = scOrgId;
    }

    /**
     * This method was generated by AILK AUTOMATIC TOOLS.
     * This method returns the value of the database column SC_HOLIDAY.HOLIDAY
     *
     * @return the value of SC_HOLIDAY.HOLIDAY
     *
     * @abatorgenerated Wed Nov 26 21:55:28 CST 2014
     */
    public Date getHoliday() {
        return holiday;
    }

    /**
     * This method was generated by AILK AUTOMATIC TOOLS.
     * This method sets the value of the database column SC_HOLIDAY.HOLIDAY
     *
     * @param holiday the value for SC_HOLIDAY.HOLIDAY
     *
     * @abatorgenerated Wed Nov 26 21:55:28 CST 2014
     */
    public void setHoliday(Date holiday) {
        this.holiday = holiday;
    }
}