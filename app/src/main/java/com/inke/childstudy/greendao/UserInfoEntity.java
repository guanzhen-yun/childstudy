package com.inke.childstudy.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 本地存储的用户信息
 */
@Entity(nameInDb = "USERINFO")
public class UserInfoEntity extends DbBean {
    @Id(autoincrement = true)
    Long id;
    @Property(nameInDb = "token")
    String token;
    @Property(nameInDb = "headPath")
    String headPath;
    @Property(nameInDb = "nick")
    String nick;
    @Property(nameInDb = "sex")
    String sex;
    @Property(nameInDb = "age")
    int age;
    @Property(nameInDb = "objectId")
    String objectId;
    @Generated(hash = 1122944088)
    public UserInfoEntity(Long id, String token, String headPath, String nick,
            String sex, int age, String objectId) {
        this.id = id;
        this.token = token;
        this.headPath = headPath;
        this.nick = nick;
        this.sex = sex;
        this.age = age;
        this.objectId = objectId;
    }
    @Generated(hash = 2042969639)
    public UserInfoEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getHeadPath() {
        return this.headPath;
    }
    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }
    public String getNick() {
        return this.nick;
    }
    public void setNick(String nick) {
        this.nick = nick;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getObjectId() {
        return this.objectId;
    }
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
