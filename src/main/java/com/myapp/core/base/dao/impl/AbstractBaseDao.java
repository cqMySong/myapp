package com.myapp.core.base.dao.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.internal.ast.QueryTranslatorImpl;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.transform.Transformers;

import com.myapp.core.base.dao.IAbstractBaseDao;
import com.myapp.core.base.dao.MyResultTransFormer;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.base.entity.CoreBaseDataInfo;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.entity.CoreBaseTreeInfo;
import com.myapp.core.base.entity.CoreInfo;
import com.myapp.core.base.setting.SystemConstant;
import com.myapp.core.entity.SubsystemTreeInfo;
import com.myapp.core.enums.BillState;
import com.myapp.core.exception.db.DeleteException;
import com.myapp.core.exception.db.ReadException;
import com.myapp.core.model.PageModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.uuid.UuidUtils;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年3月27日 
 * @system:
 *
 *-----------MySong---------------
 */
public abstract class AbstractBaseDao implements IAbstractBaseDao {
	private static final Logger log = LogManager.getLogger(AbstractBaseDao.class);
	private Class claz;
	
	public abstract Session getCurrentSession() ;
	public abstract SessionFactory getSessionFactory();
	public Object loadEntity(Class claz, String id) {
		if(claz!=null||id!=null){
			return getCurrentSession().load(claz, id);
		}else{
			log.info("加载数据错误：class为空或者id为空");
		}
		return null;
	}
	
	public Object getEntity(Class claz, String id) {
		if(claz!=null||id!=null){
			return getCurrentSession().get(claz, id);
		}else{
			log.info("加载数据错误：class为空或者id为空");
		}
		return null;
	}
	
	public List<CoreInfo> getEntityCollection(Class claz) {
		if(claz!=null) return findByHQL("from "+claz.getSimpleName(),null);
		return null;
	}
	
	public Object saveEntity(Object entity) {
		if(entity!=null){
			Serializable pk = null;
			if(entity instanceof CoreInfo){
				pk = ((CoreInfo)entity).getId();
			}
			if(entity instanceof CoreBaseInfo){
				CoreBaseInfo cbInfo = (CoreBaseInfo) entity;
				cbInfo.setLastUpdateDate(new Date());
			}
			if(BaseUtil.isEmpty(pk)){
				pk = addNewEntity(entity);
			}else{
				Session sesion = getCurrentSession();
				sesion.merge(entity);
//				sesion.update(entity);
				sesion.flush();
			}
			if(pk!=null) {
				return entity;
			}
		}
		return null;
	}
	public void deleteEntity(Class claz, String id) throws DeleteException {
		if(claz!=null||id!=null){
			deleteEntity(loadEntity(claz,id));
		}
	}
	
	public void deleteEntity(Object entity) throws DeleteException {
		if(entity!=null){
			if(entity instanceof CoreBaseDataInfo){
				CoreBaseDataInfo cbInfo = (CoreBaseDataInfo) entity;
				if(cbInfo.getEnabled()){
					throw new DeleteException("已经启用的都基础数据无法完成删除操作!");
				}
			}else if(entity instanceof CoreBaseBillInfo){
				CoreBaseBillInfo cbInfo = (CoreBaseBillInfo) entity;
				BillState bs = cbInfo.getBillState();
				if(bs!=null&&bs.equals(BillState.AUDIT)){
					throw new DeleteException("已经审核的业务数据无法完成删除操作!");
				}
			}
			
			Session session = getCurrentSession();
			session.delete(entity);
			session.flush();
		}
	}
	
	private Query createQuery(String hql, Object[] params){
		return initHqlParams(getCurrentSession().createQuery(hql),params);
	}
	
	private Query initHqlParams(Query query,Object[] params){
		if(query!=null&&params!=null&&params.length>0){
			for(int i=0;i<params.length;i++){
				Object paramObj = params[i];
				if(paramObj!=null) query.setParameter(i, paramObj); 
			}
		}
		return query;
	}
	
	public List findByHQL(String hql, Object[] params) {
		Query query = createQuery(hql,params);  
        if(query!=null) return query.list();
		return null;
	}
	
	private void initTreeNewData(CoreBaseTreeInfo treeInfo){
		if(treeInfo==null) return;
		Date curDate = new Date();
		treeInfo.setCreateDate(curDate);
		treeInfo.setLastUpdateDate(curDate);
		Set<CoreBaseTreeInfo> children =  treeInfo.getChildren();
		if(children!=null&&children.size()>0){
			treeInfo.setLeaf(false);
			for(CoreBaseTreeInfo tInfo:children){
				initTreeNewData(tInfo);
			}
		}else{
			treeInfo.setLeaf(true);
		}
	}
	
	public Serializable addNewEntity(Object entity) {
		if(entity!=null){
			Session session = getCurrentSession();
			if(!(entity instanceof SubsystemTreeInfo)){
				Class claz = entity.getClass();
				String entityClaz = claz.getName();
				String hql = "select id from SubsystemTreeInfo where entityClaz=?";
				List datas = findByHQL(hql,new String[]{entityClaz});
				if(datas==null||datas.size()<=0){
					SubsystemTreeInfo subTree = new SubsystemTreeInfo();
					AbstractEntityPersister cmd = (AbstractEntityPersister) session.getSessionFactory().getClassMetadata(claz);
					subTree.setEntityClaz(entityClaz);
					String tableName = cmd.getTableName();
					subTree.setEntityTable(tableName);
					long seq = UuidUtils.getStringLong(entityClaz);
					subTree.setEntityObjectType(UuidUtils.getEntityType(entityClaz));
					subTree.setEntitySeq(new Date().getTime());
					subTree.setSeq(seq);
					session.save(subTree);
				}
			}
			if(entity instanceof CoreBaseTreeInfo){
				initTreeNewData((CoreBaseTreeInfo)entity);
			}else if(entity instanceof CoreBaseInfo){
				CoreBaseInfo cbinfo = (CoreBaseInfo) entity;
				Date curDate = new Date();
				cbinfo.setCreateDate(curDate);
				cbinfo.setLastUpdateDate(curDate);
			}
			Serializable pk = session.save(entity);
			session.merge(entity);
			session.flush();
			return pk;
		}
		return null;
	}
	
	public void executeUpdata(String hql,Object[] params){
		Query query = createQuery(hql,params);
		query.executeUpdate();
	}
	
	public List executeSQLQuery(String sql, Object[] params) {
		Query query = initHqlParams(getCurrentSession().createSQLQuery(sql),params);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	
	protected long getCount(Session session, String hql, Object[] params)
			throws QueryException {
		QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(hql, hql,
				Collections.EMPTY_MAP,
				(SessionFactoryImplementor) getSessionFactory());
		queryTranslator.compile(Collections.EMPTY_MAP, false);
		String sql = queryTranslator.getSQLString();
		String s = "select count(*) from (" + sql + ") t";
		Query query = session.createSQLQuery(s);
		return (long) ((Number)initHqlParams(query, params).uniqueResult()).longValue();
	}
	
	public PageModel toPageQuery(Integer curPage, Integer pageSize, String hql,
			Object[] params) {
		if(curPage==null) curPage = SystemConstant.DEF_PAGE_BEG;
		if(pageSize==null) pageSize = SystemConstant.DEF_PAGE_SIZE;
		if(!BaseUtil.isEmpty(hql)){
			Session session = getCurrentSession();
			long rows = getCount(session,hql,params);
			System.out.println("rows := "+rows);
			PageModel pm = new PageModel(curPage, pageSize, rows);
			Query pageQuery = initHqlParams(session.createQuery(hql),params);
			pageQuery.setFirstResult(pm.getStartNum());
			pageQuery.setMaxResults(pageSize);
			pageQuery.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
			pm.setDatas(pageQuery.list());
			return pm;
		}
		return null;
	}
	public Object queryEntity(Class claz, String hql, Object[] params)
			throws ReadException {
		Session session = getCurrentSession();
		Query entityQuery = initHqlParams(session.createQuery(hql),params);
		entityQuery.setResultTransformer(Transformers.aliasToBean(claz));
		return entityQuery.uniqueResult();
	}
	
	public Criteria findByDetachedCriteria(Class claz,DetachedCriteria dca)
			throws QueryException {
		dca.setResultTransformer(new MyResultTransFormer(claz));
		return dca.getExecutableCriteria(getCurrentSession());
	}
	
	/**
	 * 此方法有局限性 后期再考虑 怎么处理
	 * 查询级联对象的值 获取不成功
	 */
	
	@Deprecated 
	public PageModel toPageDetachedCriteria(Class claz,DetachedCriteria dca,ProjectionList pList,Integer curPage, Integer pageSize) throws QueryException{
		Criteria query = dca.getExecutableCriteria(getCurrentSession());
		long rowCount = ((Long)query.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		PageModel pm = new PageModel(curPage, pageSize, rowCount);
		if(pList!=null){
			query.setProjection(pList);
		}else{
			query.setProjection(null);
		}
		
		query.setResultTransformer(Transformers.aliasToBean(claz));
		query.setFirstResult(pm.getStartNum());
		query.setMaxResults(pageSize);
		pm.setDatas(query.list());
		return pm;
	}
	
	public Criteria initQueryCriteria(Class claz) throws QueryException {
		Criteria ctiteria = getCurrentSession().createCriteria(claz);
		return ctiteria;
	}
	
	public Criteria initQueryCriteria(String entityName) throws QueryException {
		return getCurrentSession().createCriteria(entityName);
	}
	
	
	
	
}
