package com.witkey.familyTree.dao.consoles;

import com.witkey.familyTree.domain.TLog;
import com.witkey.familyTree.domain.TRole;
import com.witkey.familyTree.hibernate.BaseHibernateDao;
import org.springframework.stereotype.Repository;

/**
 * Created by suyx on 2016/12/21 0021.
 */
@Repository("tLogDao")
public class TLogDao extends BaseHibernateDao<TLog> {
}
