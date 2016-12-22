package com.witkey.familyTree.dao.fronts;

import com.witkey.familyTree.domain.TFamily;
import com.witkey.familyTree.hibernate.BaseHibernateDao;
import org.springframework.stereotype.Repository;

/**
 * Created by suyx on 2016/12/21 0021.
 */
@Repository("tFamilyDao")
public class TFamilyDao extends BaseHibernateDao<TFamily> {
}
