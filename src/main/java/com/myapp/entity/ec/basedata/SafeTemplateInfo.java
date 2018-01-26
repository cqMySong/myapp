package com.myapp.entity.ec.basedata;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseDataInfo;
import com.myapp.core.entity.PositionInfo;

import javax.persistence.*;
import java.util.Set;

/**
 * @path：com.myapp.entity.ec.basedata
 * @description：
 * @author： ly
 * @date: 2018-01-14 20:21
 */
@MyEntityAnn(name="安全样板")
@Entity
@Table(name="t_ec_safe_template")
public class SafeTemplateInfo extends CoreBaseDataInfo {
    /**
     * 分部
     */
    private ProBaseWbsInfo branchBaseWbs;
    /**
     * 分项
     */
    private ProBaseWbsInfo subentry;

    private Set<SafeTemplateDetailInfo> safeTemplateDetailInfos;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fBranchBaseWbsId")
    public ProBaseWbsInfo getBranchBaseWbs() {
        return branchBaseWbs;
    }

    public void setBranchBaseWbs(ProBaseWbsInfo branchBaseWbs) {
        this.branchBaseWbs = branchBaseWbs;
    }
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fSubentryId")
    public ProBaseWbsInfo getSubentry() {
        return subentry;
    }

    public void setSubentry(ProBaseWbsInfo subentry) {
        this.subentry = subentry;
    }

    @OneToMany(cascade={CascadeType.ALL},mappedBy="parent")
    @OrderBy("seq ASC")
    public Set<SafeTemplateDetailInfo> getSafeTemplateDetailInfos() {
        return safeTemplateDetailInfos;
    }

    public void setSafeTemplateDetailInfos(Set<SafeTemplateDetailInfo> safeTemplateDetailInfos) {
        this.safeTemplateDetailInfos = safeTemplateDetailInfos;
    }
}
