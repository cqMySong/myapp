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

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.dao.IAbstractBaseDao;
import com.myapp.core.base.dao.MyResultTransFormer;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.base.entity.CoreBaseDataInfo;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.entity.CoreBaseTreeInfo;
import com.myapp.core.base.entity.CoreInfo;
import com.myapp.core.base.setting.SystemConstant;
import com.myapp.core.entity.SubsystemTreeInfo;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.EntityTypeEnum;
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
	
	private boolean isEquals(Object obj1,Object obj2){
		if(obj1==null||obj2==null){
			return obj1==null&&obj2==null;
		}else{
			String eq1 = obj1.toString();
			String eq2 = obj2.toString();
			if(obj1 instanceof CoreInfo){
				eq1 = ((CoreInfo)obj1).getId();
			}
			if(obj2 instanceof CoreInfo){
				eq2 = ((CoreInfo)obj2).getId();
			}
			return eq1.equals(eq2);
		}
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
				boolean toUpTree = false;
				if(entity instanceof CoreBaseTreeInfo){
					CoreBaseTreeInfo curTreeInfo = (CoreBaseTreeInfo) entity;
					CoreBaseTreeInfo dbTreeInfo = (CoreBaseTreeInfo) sesion.get(entity.getClass(), pk);
					toUpTree = isEquals(curTreeInfo.getName(),dbTreeInfo.getName())
							||isEquals(curTreeInfo.getNumber(),dbTreeInfo.getNumber());
				}
				if(toUpTree){
					updateTreeData(sesion,(CoreBaseTreeInfo) entity);
				}else{
					sesion.merge(entity);	
				}
				sesion.flush();
				
			}
			if(pk!=null) {
				return entity;
			}
		}
		return null;
	}
	
	private void updateTreeData(Session sesion,CoreBaseTreeInfo treeInfo){
		if(treeInfo==null) return;
		String longNumber = treeInfo.getNumber();
		String displayName = treeInfo.getName();
		if(treeInfo.getParent()!=null){
			CoreBaseTreeInfo ptreeInfo = (CoreBaseTreeInfo)treeInfo.getParent();
			longNumber = ptreeInfo.getLongNumber();
			if(!BaseUtil.isEmpty(longNumber)){
				longNumber = longNumber+"!"+treeInfo.getNumber();
			}else{
				longNumber = treeInfo.getNumber();
			}
			displayName = ptreeInfo.getDisplayName();
			if(!BaseUtil.isEmpty(displayName)){
				displayName = displayName+"_"+treeInfo.getName();
			}else{
				displayName = treeInfo.getName();
			}
		}
		
		treeInfo.setLongNumber(longNumber);
		treeInfo.setDisplayName(displayName);
		sesion.merge(treeInfo);
		
		Set<CoreBaseTreeInfo> children =  treeInfo.getChildren();
		if(children!=null&&children.size()>0){
			for(CoreBaseTreeInfo tInfo:children){
				updateTreeData(sesion,tInfo);
			}
		}
	}
	
	public void deleteEntity(Class claz, String id) throws DeleteException {
		if(claz!=null||id!=null){
			deleteEntity(loadEntity(claz,id));
		}
	}
	
	public void deleteEntity(Object entity) throws DeleteException {
		if(entity!=null){
			if(entity instanceof CoreBaseTreeInfo){
				CoreBaseTreeInfo treeInfo = (CoreBaseTreeInfo) entity;
				Set treeChildren = treeInfo.getChildren();
				if(treeChildren!=null&&treeChildren.size()>0){
					throw new DeleteException("还存在下级节点无法完成删除操作!");
				}
			}
			if(entity instanceof CoreBaseDataInfo){
				CoreBaseDataInfo cbInfo = (CoreBaseDataInfo) entity;
				if(cbInfo.getEnabled()!=null&&cbInfo.getEnabled()){
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
			if(entity instanceof CoreBaseTreeInfo){
				CoreBaseTreeInfo treeInfo = (CoreBaseTreeInfo) entity;
				CoreBaseTreeInfo ptreeInfo = (CoreBaseTreeInfo) treeInfo.getParent();
				if(ptreeInfo!=null){
					Class clas = ptreeInfo.getClass();
					String hql = "select id from "+clas.getSimpleName()+" where id !=? and parent.id=?";
					Query query = initHqlParams(session.createQuery(hql),new String[]{treeInfo.getId(),ptreeInfo.getId()});
					if(query!=null){
						List datas = query.list();
						if(datas!=null&&datas.size()>0){
							ptreeInfo.setIsLeaf(true);
							session.merge(ptreeInfo);
						}
					}
				}
			}
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
		if(hql.indexOf("select")>=0){
			query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		}
        if(query!=null) return query.list();
		return null;
	}
	
	
	private EntityTypeEnum getEntityType(Object entity){
		EntityTypeEnum et = EntityTypeEnum.OTHER;
		if(entity!=null){
			if(entity instanceof CoreBaseBillInfo){
				et = EntityTypeEnum.BIZBILL;
			}else if(entity instanceof CoreBaseDataInfo){
				et = EntityTypeEnum.BASEDATA;
			}else if(entity instanceof CoreBaseEntryInfo){
				et = EntityTypeEnum.ENTRY;
			}
		}
		return et;
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
					subTree.setEntityType(getEntityType(entity));
					String entityName = claz.getSimpleName();
					MyEntityAnn myA = (MyEntityAnn) claz.getAnnotation(MyEntityAnn.class);
					if(myA!=null){
						entityName = myA.name();
					}
					subTree.setEntityName(entityName);
					session.save(subTree);
				}
			}
			if(entity instanceof CoreBaseTreeInfo){
				CoreBaseTreeInfo treeInfo = (CoreBaseTreeInfo)entity;
				Integer level = 1;
				String longNumber = treeInfo.getNumber();
				String displayName = treeInfo.getName();
				Boolean isLeaf = Boolean.TRUE;
				if(treeInfo.getParent()!=null){
					CoreBaseTreeInfo ptreeInfo = (CoreBaseTreeInfo)treeInfo.getParent();
					longNumber = ptreeInfo.getLongNumber();
					if(!BaseUtil.isEmpty(longNumber)){
						longNumber = longNumber+"!"+treeInfo.getNumber();
					}else{
						longNumber = treeInfo.getNumber();
					}
					displayName = ptreeInfo.getDisplayName();
					if(!BaseUtil.isEmpty(displayName)){
						displayName = displayName+"_"+treeInfo.getName();
					}else{
						displayName = treeInfo.getName();
					}
					level = ptreeInfo.getLevel();
					if(level!=null){
						level = level+1;
					}else{
						level = 1;
					}
				}
				treeInfo.setIsLeaf(isLeaf);
				treeInfo.setLevel(level);
				treeInfo.setLongNumber(longNumber);
				treeInfo.setDisplayName(displayName);
			}
			if(entity instanceof CoreBaseInfo){
				CoreBaseInfo cbinfo = (CoreBaseInfo) entity;
				Date curDate = new Date();
				cbinfo.setCreateDate(curDate);
				cbinfo.setLastUpdateDate(curDate);
			}
			Serializable pk = session.save(entity);
			session.merge(entity);
			if(entity instanceof CoreBaseTreeInfo){
				CoreBaseTreeInfo treeInfo = (CoreBaseTreeInfo)entity;
				Object pObj = treeInfo.getParent();
				if(pObj!=null&&pObj instanceof CoreBaseTreeInfo){
					CoreBaseTreeInfo pTreeInfo = (CoreBaseTreeInfo)pObj;
					pTreeInfo.setIsLeaf(false);
					session.merge(pTreeInfo);
				}
			}
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
		//entityQuery.setResultTransformer(Transformers.aliasToBean(claz));
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
