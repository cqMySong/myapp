package com.myapp.core.base.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.myapp.core.base.dao.IAbstractBaseDao;
import com.myapp.core.base.dao.impl.AbstractBaseDao;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.base.service.IAbstractBaseService;
import com.myapp.core.enums.BillState;
import com.myapp.core.exception.db.AddNewException;
import com.myapp.core.exception.db.DeleteException;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.ReadException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.PageModel;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年3月27日 
 * @system:
 *-----------MySong---------------
 */
@SuppressWarnings("unchecked")
public abstract class AbstractBaseService implements IAbstractBaseService {
	private static final Logger log = LogManager.getLogger(AbstractBaseDao.class);
	
	protected abstract Class getEntityClass();
	
	@Resource
	public IAbstractBaseDao baseDao;
	
	protected IAbstractBaseDao getBaseDao() {
		return this.baseDao;
	}
	
	public <T> T loadEntity(String id) {
		return (T) loadEntity(getEntityClass(), id);
	}
	
	public <T> T loadEntity(Class<T> c, String id) {
		try {
			return (T) getBaseDao().loadEntity(c, id);
		} catch (ReadException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}
	
	public Object getEntity(String id) {
		return getEntity(getEntityClass(),id);
	}
	
	public <T> T getEntity(Class<T> c, String id) {
		try {
			return (T) getBaseDao().getEntity(c, id);
		} catch (ReadException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}
	
	public <T> T getEntity(Class<T> c,String hql,Object[] params){
		List datas = findByHQL(hql, params);
		if(datas!=null&&datas.size()>0) {
			return (T) datas.get(0);
		}
		return null;
	}
	 
	
	public <T> T getEntity(String hql,Object[] params){
		return (T) getEntity(getEntityClass(),hql,params);
	}
	
	public <E> List<E> getEntityCollection() {
		return getEntityCollection(getEntityClass());
	}
	
	public List getEntityCollection(Class claz) {
		try {
			return getBaseDao().getEntityCollection(claz);
		} catch (ReadException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}
	
	public Object saveEntity(Object entity) {
		try {
			if(entity!=null&&entity instanceof CoreBaseBillInfo){
				CoreBaseBillInfo cbbInfo = (CoreBaseBillInfo) entity;
				BillState bs = cbbInfo.getBillState();
				if(bs==null||BillState.ADDNEW.equals(bs))
					cbbInfo.setBillState(BillState.SAVE);
			}
			return getBaseDao().saveEntity(entity);
		} catch (SaveException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}
	
	public Object submitEntity(Object entity) {
		try {
			if(entity!=null&&entity instanceof CoreBaseBillInfo){
				CoreBaseBillInfo cbbInfo = (CoreBaseBillInfo) entity;
				cbbInfo.setBillState(BillState.SUBMIT);
			}
			return getBaseDao().saveEntity(entity);
		} catch (SaveException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}
	
	public void deleteEntity(String id) {
		deleteEntity(getEntityClass(), id);
	}
	
	public void deleteEntity(Class claz, String id) {
		try {
			getBaseDao().deleteEntity(claz, id);
		} catch (DeleteException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	public void deleteEntity(Object entity) {
		 try {
			getBaseDao().deleteEntity(entity);
		} catch (DeleteException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	
	public List findByHQL(String hql, Object[] params) {
		try {
			return getBaseDao().findByHQL(hql, params);
		} catch (QueryException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}
	public Serializable addNewEntity(Object entity) {
		try {
			return getBaseDao().addNewEntity(entity);
		} catch (AddNewException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}
	public PageModel toPageQuery(Integer curPage, Integer pageSize, String hql,
			Object[] params) {
		try {
			return getBaseDao().toPageQuery(curPage, pageSize, hql, params);
		} catch (QueryException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public <T> T queryEntity(String hql, Object[] params){
		return (T) queryEntity(getEntityClass(),hql,params);
	}
	
	public <T> T queryEntity(Class<T> c, String hql, Object[] params){
		try {
			return (T) getBaseDao().queryEntity(c, hql, params);
		} catch (ReadException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public <T> T findByDetachedCriteria(Class<T> c,DetachedCriteria dca)
			throws QueryException {
		Criteria criteria = getBaseDao().findByDetachedCriteria(c,dca);
		Object objs = criteria.list();
		
		return (T) criteria.list();
	}
	
	public <T> T findByDetachedCriteria(DetachedCriteria dca)
			throws QueryException {
		return (T) findByDetachedCriteria(getEntityClass(),dca);
	}
	
	public List executeSQLQuery(String sql, Object[] params)
			throws QueryException {
		return getBaseDao().executeSQLQuery(sql, params);
	}
	
	public Criteria initQueryCriteria(Class claz) throws QueryException {
		return getBaseDao().initQueryCriteria(claz);
	}
	
	public Criteria initQueryCriteria() throws QueryException {
		return getBaseDao().initQueryCriteria(getEntityClass());
	}
	
	public Criteria initQueryCriteria(String entityName) throws QueryException {
		return getBaseDao().initQueryCriteria(entityName);
	}
	
	public Map queryEntity(Criteria query,String billId){
		if(query==null||BaseUtil.isEmpty(billId)) return null;
		query.add(Restrictions.eq("id", billId));
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List data = query.list();
		if(data!=null&&data.size()>0) return (Map) data.get(0);
		return null;
	}
	
	public PageModel toPageQuery(Criteria query,ProjectionList pList,Integer curPage, Integer pageSize){
		if(query==null) return null;
		long rowCount = ((Long)query.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		PageModel pm = new PageModel(curPage, pageSize, rowCount);
		if(pList!=null){
			query.setProjection(pList);
		}else{
			query.setProjection(null);
		}
		
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setFirstResult(pm.getStartNum());
		query.setMaxResults(pageSize);
		pm.setDatas(query.list());
		return pm;
	}
	
	public void executeUpdata(String hql,Object[] params){
		getBaseDao().executeUpdata(hql, params);
	}
	
	public void executeDeleteEntitys(List idparams){
		if(BaseUtil.isEmpty(idparams)||idparams.size()<=0) return;
		String delHql = "from "+getEntityClass().getSimpleName()+" where id in(?)";
		List newParams = new ArrayList();
		newParams.add(idparams.toArray());
		executeUpdata(delHql,newParams.toArray());
	}
	
	
}
