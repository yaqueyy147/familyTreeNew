package com.witkey.familyTree.dao.fronts;

import com.witkey.familyTree.domain.TUserFront;
import com.witkey.familyTree.hibernate.EntityHibernateDao;
import org.springframework.stereotype.Repository;

/**
 * Created by suyx on 2016/12/18.
 */
@Repository("tUserFrontDao")
public class TUserFrontDao extends EntityHibernateDao<TUserFront> {
}
