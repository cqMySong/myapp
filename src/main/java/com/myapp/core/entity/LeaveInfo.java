package com.myapp.core.entity;

import com.myapp.core.base.entity.CoreBaseInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 包路径：com.myapp.core.entity
 * 功能说明：测试用
 * 创建人： ly
 * 创建时间: 2017-07-23 22:49
 */
@Entity
@Table(name="t_leave")
public class LeaveInfo extends CoreBaseInfo {

    private String auditState;
    private String auditor;
    private Date auditTime;
    private String days;
    private String reason;
    private Date start;
    private Date end;
    @Column(name="auditState",length=1)
    public String getAuditState() {
        return auditState;
    }

    public void setAuditState(String auditState) {
        this.auditState = auditState;
    }
    @Column(name="auditor",length=20)
    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }
    @Column(name="auditTime")
    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }
    @Column(name="days",length = 3,precision=1)
    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
    @Column(name="reason",length = 255)
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    @Column(name="start")
    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }
    @Column(name="end")
    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
