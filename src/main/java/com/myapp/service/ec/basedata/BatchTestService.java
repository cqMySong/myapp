package com.myapp.service.ec.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.myapp.core.enums.OrgTypeEnum;
import com.myapp.core.exception.db.QueryException;
import org.springframework.stereotype.Service;

import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.BatchTestInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月24日 
 * @system:
 *-----------MySong---------------
 */
@Service("batchTestService")
public class BatchTestService extends BaseInterfaceService<BatchTestInfo> {
	
	
	public BatchTestInfo getBatchTestByBaseWBS(String baseWbsId,Boolean isEnabled){
		if(BaseUtil.isEmpty(baseWbsId)) return null;
		String hql = " from BatchTestInfo where proBaseWbs.id=?";
		List params = new ArrayList();
		params.add(baseWbsId);
		if(isEnabled!=null){
			hql +=" and enabled=?";
			params.add(isEnabled);
		}
		return getEntity(hql, params.toArray());
	}

	public List getTreeData(Map params) throws QueryException {
		StringBuffer sql = new StringBuffer();
		List queryParams = new ArrayList();

		sql.append(" select fid as id,fnumber as number,fname as name,fprentid as parentId,flongnumber as longNumber,'sunentry' as type");
		sql.append(" from t_ec_probasewbs ");
		sql.append(" union all ");
		sql.append("select fid as id,fnumber as number,fname as name,fprowbsId as parentId,'' as longNumber,'batch' as type from t_ec_bathchtest ");
		return executeSQLQuery(sql.toString(), queryParams.toArray());
	}
}
