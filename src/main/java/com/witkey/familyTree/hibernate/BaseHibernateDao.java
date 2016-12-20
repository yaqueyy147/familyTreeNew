package com.witkey.familyTree.hibernate;

import com.witkey.familyTree.hibernate.domain.Page;
import com.witkey.familyTree.util.BeanUtils;
import com.witkey.familyTree.util.GenericsUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

public class BaseHibernateDao<E> extends HibernateDaoSupport {
	protected final Logger log = Logger.getLogger(BaseHibernateDao.class);
	
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * Dao所管理的Entity类型.
	 */
	protected Class entityClass;

	/**
	 * 取得entityClass的函数.不支持泛型的子类可以抛开Class entityClass, 重新实现此函数达到相同效果。
	 */
	protected Class getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class clazz) {
		this.entityClass = clazz;
	}

	/**
	 * 通过构造函数对entityClass付值
	 */
	public BaseHibernateDao() {
		this.entityClass = GenericsUtils.getGenericClass(getClass());
	}

	/**
	 * 通过id获取实体实例
	 * 
	 * @param id
	 * @return entity
	 */
	@SuppressWarnings("unchecked")
	public E get(Serializable id) {
		return (E) getHibernateTemplate().get(entityClass, id);
	}

	/**
	 * 通过id获取实体实例,并对实体记录加锁
	 * 
	 * @param id
	 * @param mode
	 * @return entity
	 */
	@SuppressWarnings("unchecked")
	public E get(Serializable id, LockMode mode) {
		return (E) getHibernateTemplate().get(entityClass, id, mode);
	}

	/**
	 * 获取实体所有实例
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<E> list() {
		return getHibernateTemplate().loadAll(entityClass);
	}

	/**
	 * 新增一条实体实例
	 * 
	 * @param o
	 */
	public Serializable create(Object o) {
		return super.getHibernateTemplate().save(o);
	}

	/**
	 * 修改实体实例
	 * 
	 * @param o
	 */
	public void update(Object o) {
		super.getHibernateTemplate().update(o);
	}

	/**
	 * 新增或修改一条实体实例
	 * 
	 * @param o
	 */
	public void save(Object o) {
		getHibernateTemplate().merge(o);
	}

	/**
	 * 删除实体实例
	 * 
	 * @param o
	 */
	public void remove(Object o) {
		getHibernateTemplate().delete(o);
	}

	/**
	 * 通过id删除实体实例
	 * 
	 * @param id
	 */
	public void removeById(Serializable id) {
		remove(get(id));
	}

	/**
	 * 消除与 Session 的关联
	 * 
	 * @deprecated
	 * @param entity
	 */
	// 好像用要报错
	public void evict(Object entity) {
		getHibernateTemplate().evict(entity);
	}

	/**
	 * 同步数据
	 */
	public void flush() {
		getHibernateTemplate().flush();
	}

	/**
	 * 批量增加或修改记录
	 * 
	 * @param c
	 */
	public void saveAll(Collection c) {
		super.getHibernateTemplate().saveOrUpdate(c);
	}

	/**
	 * 批量删除记录
	 * 
	 * @param c
	 */
	public void removeAll(Collection c) {
		super.getHibernateTemplate().deleteAll(c);
	}

	/**
	 * 同步数据，清空缓存
	 */
	public void clear() {
		getHibernateTemplate().clear();
	}

	/**
	 * 通过HSQL进行统计
	 * 
	 * @param hsql
	 * @param values 可变参数 用户可以如下四种方式使用 dao.getCount(hql); dao.getCount(hql,arg0); dao.getCount(hql,arg0,arg1);
	 *            dao.getCount(hql,new Object[arg0,arg1,arg2])
	 * @return
	 */
	public int getCount(String hsql, Object... values) {
		Object c = getHibernateTemplate().find(hsql, values).get(0);

		if (c instanceof Integer) {
			return ((Integer) c).intValue();
		}

		return 0;
	}

	/**
	 * 通过标准API查询数量
	 */
	public int getCount(Criteria criteria) {
		return ((Integer) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}

	/**
	 * hql查询.
	 * 
	 * @param hql
	 * @param values 可变参数 用户可以如下四种方式使用 dao.find(hql); dao.find(hql,arg0); dao.find(hql,arg0,arg1); dao.find(hql,new
	 *            Object[arg0,arg1,arg2])
	 * @return
	 */
	public List find(String hql, Object... values) {
		return getHibernateTemplate().find(hql, values);
	}

	/**
	 * 直接通过hql查询，不带参数
	 */
	public List find(String hql) {
		return getHibernateTemplate().find(hql);
	}

	/**
	 * hql查询.
	 * 
	 * @param hql 使用 named query parameter as <tt>from Foo foo where foo.bar = :bar</tt>.
	 * @param param a java.util.Map
	 * @return
	 */
	public List find(String hql, Map param) {
		return currentSession().createQuery(hql).setProperties(param).list();
	}

	/**
	 * 根据Map中的条件的Criteria查询.
	 * 
	 * @param filter Map中仅包含条件名与条件值，默认全部相同, 可重载
	 */
	public List<E> find(Map filter) {
		Criteria criteria = createCriteria();
		return find(criteria, filter);
	}
	
	/**
	 * 根据Map中的条件的Criteria查询.
	 * 
	 * @param filter Map中仅包含条件名与条件值，默认全部相同, 可重载
	 */
	public List<E> find(Map filter, Map sort) {
		Criteria criteria = createCriteria();
		sortCriteria(criteria, sort);
		return find(criteria, filter);
	}

	/**
	 * 根据Map中的条件的Criteria查询.
	 * 
	 * @param filter Map中仅包含条件名与条件值,默认全部相同, 可重载
	 */
	@SuppressWarnings("unchecked")
	public List<E> find(Criteria criteria, Map filter) {
		Assert.notNull(criteria);
		// criteria.add(Restrictions.allEq(filter));
		filterCriteria(criteria, filter);
		return criteria.list();
	}

	/**
	 * 根据属性名和属性值查询对象.
	 * 
	 * @return 符合条件的对象列表
	 */
	@SuppressWarnings("unchecked")
	public List<E> findBy(String key, Object value) {
		Assert.hasText(key);
		Criteria criteria = currentSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq(key, value));
		return criteria.list();
	}

	/**
	 * 根据属性名和属性值以Like AnyWhere方式查询对象.
	 */
	@SuppressWarnings("unchecked")
	public List<E> findByLike(String key, String value) {
		Assert.hasText(key);
		Criteria criteria = currentSession().createCriteria(entityClass);
		criteria.add(Restrictions.like(key, value, MatchMode.ANYWHERE));
		return criteria.list();
	}

	/**
	 * 判断对象某些属性的值在数据库中是否唯一.
	 * 
	 * @param entity 在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
	 */
	public boolean isUnique(Object entity, String names) {
		Assert.hasText(names);
		Criteria criteria = currentSession().createCriteria(entityClass).setProjection(Projections.rowCount());
		String[] nameList = names.split(",");
		try {
			// 循环加入
			for (String name : nameList) {
				criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(entity, name)));
			}

			// 以下代码为了如果是update的情况,排除entity自身.
			String idName = getIdName(entityClass);

			// 取得entity的主键值
			Serializable id = getId(entityClass, entity);

			// 如果id!=null,说明对象已存在,该操作为update,加入排除自身的判断
			if (id != null)
				criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}

		return (Integer) criteria.uniqueResult() == 0;
	}

	/**
	 * 根据属性名和属性值查询唯一对象.
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public E getUniqueBy(String key, Object value) {
		Criteria criteria = currentSession().createCriteria(getEntityClass());
		criteria.add(Restrictions.eq(key, value));
		return (E) criteria.uniqueResult();
	}

	/**
	 * 根据属性名和属性值查询唯一对象.
	 * 
	 * @deprecated
	 * @param key
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public E getFirst(String key, Object value) {
		Criteria criteria = currentSession().createCriteria(getEntityClass());
		criteria.add(Restrictions.eq(key, value));
		return (E) criteria.setMaxResults(1).list();
	}

	/**
	 * 创建Query对象. 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置. 留意可以连续设置,如下：
	 * 
	 * <pre>
	 * dao.createQuery(hql).setMaxResult(100).setCacheable(true).list();
	 * </pre>
	 * 
	 * 调用方式如下：
	 * 
	 * <pre>
	 *        dao.createQuery(hql)
	 *        dao.createQuery(hql,arg0);
	 *        dao.createQuery(hql,arg0,arg1);
	 *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
	 * </pre>
	 * 
	 * @param values 可变参数.
	 */
	public Query createQuery(String hql, Object... values) {
		Assert.hasText(hql);
		Query query = currentSession().createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}

	/**
	 * 创建Criteria对象.
	 * 
	 * @param criterions 可变的Restrictions条件列表
	 */
	public Criteria createCriteria(Criterion... criterions) {
		Criteria criteria = currentSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 创建Criteria对象.
	 * 
	 * @param filterMap 可变的Restrictions条件列表
	 */
	public Criteria createCriteria(Map filterMap, Map likeMap, Map sortMap) {
		Criteria criteria = currentSession().createCriteria(entityClass);
		// criteria.add(Restrictions.allEq(filterMap));
		filterCriteria(criteria, filterMap);
		likeCriteria(criteria, likeMap);
		sortCriteria(criteria, sortMap);

		return criteria;
	}

	/**
	 * 生成方便通用的代条件分页查询
	 * 
	 * @param filterMap eq条件
	 * @param likeMap like条件
	 * @param sortMap sort排序条件
	 * @param pageNo 起始条件
	 * @param pageSize 页大小
	 * @return
	 */
	public Page pagedQuery(Map filterMap, Map likeMap, Map sortMap, int pageNo, int pageSize) {
		return pagedQuery(createCriteria(filterMap, likeMap, sortMap), pageNo, pageSize);
	}

	/**
	 * 分页查询函数，使用sql.
	 * 
	 * @param pageNo 页号,从1开始.
	 */
	public Page pagedSql(String sql, int pageNo, int pageSize) {
		return pagedSql(sql, pageNo, pageSize, -1);
	}

	/**
	 * 修正informix的first 错误,原方法起始值为0;
	 * 
	 * @param sql
	 * @param pageNo
	 * @param pageSize
	 * @param startIndex
	 * @return
	 */
	public Page pageSql(String sql, int pageNo, int pageSize, int startIndex) {
		return pageSql(sql, pageNo, pageSize, startIndex, -1);
	}

	/**
	 * 修正informix的first 错误,原方法起始值为0;
	 * 
	 * @param sql
	 * @param pageNo
	 * @param pageSize
	 * @param startIndex
	 * @param totalCount
	 * @return
	 */
	public Page pageSql(String sql, int pageNo, int pageSize, int startIndex, int totalCount) {
		Assert.hasText(sql);
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");

		// Count查询
		if (totalCount == -1) {
			String countQueryString = " select count (*) " + SqlUtils.removeSelect(SqlUtils.removeOrders(sql));
			List countlist = currentSession().createSQLQuery(countQueryString).list();
			totalCount = ((BigDecimal) countlist.get(0)).intValue();
		}

		// 实际查询返回分页对象
		Query query = currentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();

		return new Page(list, startIndex, totalCount, pageSize);
	}

	/**
	 * 分页查询函数，使用sql.
	 * 
	 * @param pageNo 页号,从1开始.
	 */
	public Page pagedSql(String sql, int pageNo, int pageSize, int totalCount) {
		Assert.hasText(sql);
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");

		// Count查询
		if (totalCount == -1) {
			String countQueryString = " select count (*) " + SqlUtils.removeSelect(SqlUtils.removeOrders(sql));
			List countlist = currentSession().createSQLQuery(countQueryString).list();
			totalCount = ((BigDecimal) countlist.get(0)).intValue();
		}

		// 实际查询返回分页对象
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		Query query = currentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();

		return new Page(list, startIndex, totalCount, pageSize);
	}

	/**
	 * 分页查询函数，使用hql
	 * 
	 * @param hql 使用JDBC-style query parameter as <tt>from Foo foo where foo.bar = ?</tt>.
	 * @param pageNo 页号,从0开始.
	 * @param pageSize
	 * @param values
	 * @return
	 */
	public Page pagedQuery(String hql, int pageNo, int pageSize, Object... values) {
		return pagedQuery(hql, pageNo, pageSize, -1, values);
	}

	/**
	 * 分页查询函数，使用hql
	 * 
	 * @param hql 使用JDBC-style query parameter as <tt>from Foo foo where foo.bar = ?</tt>.
	 * @param pageNo 页号,从0开始.
	 * @param pageSize
	 * @param totalCount
	 * @param values
	 * @return
	 */
	public Page pagedQuery(String hql, int pageNo, int pageSize, int totalCount, Object... values) {
		Assert.hasText(hql);
		if (totalCount == -1) {
			String countQueryString = " select count(*) " + SqlUtils.removeSelect(SqlUtils.removeOrders(hql));
			List countlist = getHibernateTemplate().find(countQueryString, values);
			totalCount = ((Long) countlist.get(0)).intValue();
		}

		// 返回分页对象
		if (totalCount < 1)
			return new Page();

		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		// 创建查询
		Query query = createQuery(hql, values);
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();

		return new Page(list, startIndex, totalCount, pageSize);
	}

	/**
	 * 分页查询函数，使用hql
	 * 
	 * @param hql 使用 named query parameter as <tt>from Foo foo where foo.bar = :bar</tt>.
	 * @param pageNo 页号,从0开始.
	 * @param pageSize 页大小
	 * @param param a java.util.Map
	 * @return
	 */
	public Page pagedQuery(String hql, int pageNo, int pageSize, Map param) {
		return pagedQuery(hql, pageNo, pageSize, -1, param);
	}

	/**
	 * 分页查询函数，使用hql
	 * 
	 * @param hql 使用 named query parameter as <tt>from Foo foo where foo.bar = :bar</tt>.
	 * @param pageNo 页号,从0开始.
	 * @param pageSize 页大小
	 * @param param a java.util.Map
	 * @return
	 */
	public Page pagedQuery(String hql, int pageNo, int pageSize, int totalCount, Map param) {
		Assert.hasText(hql);
		if (totalCount == -1) {
			String countQueryString = " select count(*) " + SqlUtils.removeSelect(SqlUtils.removeOrders(hql));
			List countlist = currentSession().createQuery(countQueryString).setProperties(param).list();
			totalCount = ((Long) countlist.get(0)).intValue();
		}

		// 返回分页对象
		if (totalCount < 1)
			return new Page();

		int startIndex = Page.getStartOfPage(pageNo, pageSize);

		// 创建查询
		Query query = currentSession().createQuery(hql).setProperties(param);
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();

		return new Page(list, startIndex, totalCount, pageSize);
	}

	/**
	 * 分页查询函数，使用Criteria
	 * 
	 * @param pageNo 页号,从0开始.
	 */
	@SuppressWarnings("unchecked")
	public Page pagedQuery(Criteria criteria, int pageNo, int pageSize) {
		Assert.notNull(criteria);
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
		CriteriaImpl impl = (CriteriaImpl) criteria;

		// 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
		Projection projection = impl.getProjection();
		List<CriteriaImpl.OrderEntry> orderEntries;
		try {
			orderEntries = (List) BeanUtils.forceGetProperty(impl, "orderEntries");
			BeanUtils.forceSetProperty(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility throw ");
		}

		int totalCount = 0;
		try {
			// 执行查询
			totalCount = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
		} catch (Exception e) {
			System.out.println("E:" + e.getMessage());
		}

		// 将之前的Projection和OrderBy条件重新设回去
		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		try {
			BeanUtils.forceSetProperty(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility throw ");
		}

		// 返回分页对象
		if (totalCount < 1)
			return new Page();

		int startIndex = Page.getStartOfPage(pageNo, pageSize);

		List list = criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();

		return new Page(list, startIndex, totalCount, pageSize);
	}

	/**
	 * 根据filter 构造criteria的过滤条件的回调函数. 默认将filter内的所有对象设为equal条件, 一般需要在子类进行重载
	 * 
	 * @param criteria Criteria实例
	 * @param filter 查询条件
	 */
	protected void filterCriteria(Criteria criteria, Map filter) {
		if (filter != null && !filter.isEmpty()) {
			HibernateQueryUtil.changeStringToClassFieldTypeOfMap(entityClass, filter, true);
			Conjunction conj = Restrictions.conjunction();
			Iterator iter = filter.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry me = (Map.Entry) iter.next();

				if (me.getKey().toString().indexOf(">=") > 0) {
					conj.add(Restrictions.ge(((String) me.getKey()).replace(">=", ""), me.getValue()));
					continue;
				}

				if (me.getKey().toString().indexOf("<=") > 0) {
					conj.add(Restrictions.le(((String) me.getKey()).replace("<=", ""), me.getValue()));
					continue;
				}

				if (me.getKey().toString().indexOf(">") > 0) {
					conj.add(Restrictions.gt(((String) me.getKey()).replace(">", ""), me.getValue()));
					continue;
				}

				if (me.getKey().toString().indexOf("<") > 0) {
					conj.add(Restrictions.lt(((String) me.getKey()).replace("<", ""), me.getValue()));
					continue;
				}

				if (me.getKey().toString().indexOf("!=") > 0) {
					conj.add(Restrictions.ne(((String) me.getKey()).replace("!=", ""), me.getValue()));
					continue;
				}

				conj.add(Restrictions.eq((String) me.getKey(), me.getValue()));
			}
			criteria.add(conj);
		}
	}

	/**
	 * 根据filter 构造criteria的过滤条件的回调函数. 默认将filter内的所有对象设为like条件, 一般需要在子类进行重载
	 * 
	 * @param criteria Criteria实例
	 * @param filter 查询条件
	 */
	protected void likeCriteria(Criteria criteria, Map filter) {
		if (filter != null && !filter.isEmpty()) {
			Set keys = filter.keySet();
			for (Object key : keys) {
				String value = (String) filter.get(key);
				if (StringUtils.isNotBlank(value))
					criteria.add(Restrictions.like((String) key, value, MatchMode.ANYWHERE));
			}
		}
	}

	/**
	 * 构造Criteria的排序条件。
	 * 
	 * @param sortMap 排序条件,map中key-字段名 value-排序方式(asc desc)
	 * @param criteria Criteria实例
	 */
	protected void sortCriteria(Criteria criteria, Map sortMap) {
		if (sortMap != null && !sortMap.isEmpty()) {
			// jdk1.5中的新的for循环方式
			for (Object o : sortMap.keySet()) {
				String fieldName = o.toString();
				String orderType = sortMap.get(fieldName).toString();

				// 处理嵌套属性如category.name
				if (fieldName.indexOf('.') != -1) {
					String alias = StringUtils.substringBefore(fieldName, ".");
					criteria.createAlias(alias, alias);
				}

				if ("asc".equalsIgnoreCase(orderType)) {
					criteria.addOrder(Order.asc(fieldName));
				} else {
					criteria.addOrder(Order.desc(fieldName));
				}
			}
		}
	}

	/**
	 * 取得对象的主键值,辅助函数.
	 */
	public Serializable getId(Class clazz, Object entity) throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		Assert.notNull(entity);
		Assert.notNull(clazz);
		return (Serializable) PropertyUtils.getProperty(entity, getIdName(clazz));
	}

	/**
	 * 取得对象的主键名,辅助函数.
	 */
	public String getIdName(Class clazz) {
		Assert.notNull(clazz);
		ClassMetadata meta = getSessionFactory().getClassMetadata(clazz);
		Assert.notNull(meta, "Class " + clazz + " not define in hibernate session factory.");
		String idName = meta.getIdentifierPropertyName();
		Assert.hasText(idName, clazz.getSimpleName() + " has no identifier property define.");
		return idName;
	}
}
