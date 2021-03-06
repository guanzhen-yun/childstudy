package com.inke.childstudy.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userInfoEntityDaoConfig;

    private final UserInfoEntityDao userInfoEntityDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userInfoEntityDaoConfig = daoConfigMap.get(UserInfoEntityDao.class).clone();
        userInfoEntityDaoConfig.initIdentityScope(type);

        userInfoEntityDao = new UserInfoEntityDao(userInfoEntityDaoConfig, this);

        registerDao(UserInfoEntity.class, userInfoEntityDao);
    }
    
    public void clear() {
        userInfoEntityDaoConfig.clearIdentityScope();
    }

    public UserInfoEntityDao getUserInfoEntityDao() {
        return userInfoEntityDao;
    }

}
