package com.android.styy.common.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "user".
*/
public class UserEntityDao extends AbstractDao<UserEntity, Long> {

    public static final String TABLENAME = "user";

    /**
     * Properties of entity UserEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Uid = new Property(0, long.class, "uid", true, "_id");
        public final static Property Id = new Property(1, String.class, "id", false, "ID");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property RegisterEmail = new Property(3, String.class, "registerEmail", false, "REGISTER_EMAIL");
        public final static Property AreaCode = new Property(4, String.class, "areaCode", false, "AREA_CODE");
        public final static Property AreaName = new Property(5, String.class, "areaName", false, "AREA_NAME");
        public final static Property Contacts = new Property(6, String.class, "contacts", false, "CONTACTS");
        public final static Property ContactsTel = new Property(7, String.class, "contactsTel", false, "CONTACTS_TEL");
        public final static Property ResponsePerson = new Property(8, String.class, "responsePerson", false, "RESPONSE_PERSON");
        public final static Property LeaderEmail = new Property(9, String.class, "leaderEmail", false, "LEADER_EMAIL");
        public final static Property OfficialWebsite = new Property(10, String.class, "officialWebsite", false, "OFFICIAL_WEBSITE");
        public final static Property Address = new Property(11, String.class, "address", false, "ADDRESS");
        public final static Property Introduce = new Property(12, String.class, "introduce", false, "INTRODUCE");
        public final static Property Level = new Property(13, String.class, "level", false, "LEVEL");
        public final static Property ComplaintTel = new Property(14, String.class, "complaintTel", false, "COMPLAINT_TEL");
        public final static Property ConsultTel = new Property(15, String.class, "consultTel", false, "CONSULT_TEL");
        public final static Property UserId = new Property(16, String.class, "userId", false, "USER_ID");
        public final static Property CreatedUserId = new Property(17, String.class, "createdUserId", false, "CREATED_USER_ID");
        public final static Property CreatedTime = new Property(18, String.class, "createdTime", false, "CREATED_TIME");
        public final static Property UpdateUserId = new Property(19, String.class, "updateUserId", false, "UPDATE_USER_ID");
        public final static Property UpdateTime = new Property(20, String.class, "updateTime", false, "UPDATE_TIME");
        public final static Property UserType = new Property(21, String.class, "userType", false, "USER_TYPE");
        public final static Property NickName = new Property(22, String.class, "nickName", false, "NICK_NAME");
        public final static Property Status = new Property(23, String.class, "status", false, "STATUS");
        public final static Property Username = new Property(24, String.class, "username", false, "USERNAME");
        public final static Property Logout = new Property(25, boolean.class, "logout", false, "LOGOUT");
    }


    public UserEntityDao(DaoConfig config) {
        super(config);
    }
    
    public UserEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"user\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: uid
                "\"ID\" TEXT," + // 1: id
                "\"NAME\" TEXT," + // 2: name
                "\"REGISTER_EMAIL\" TEXT," + // 3: registerEmail
                "\"AREA_CODE\" TEXT," + // 4: areaCode
                "\"AREA_NAME\" TEXT," + // 5: areaName
                "\"CONTACTS\" TEXT," + // 6: contacts
                "\"CONTACTS_TEL\" TEXT," + // 7: contactsTel
                "\"RESPONSE_PERSON\" TEXT," + // 8: responsePerson
                "\"LEADER_EMAIL\" TEXT," + // 9: leaderEmail
                "\"OFFICIAL_WEBSITE\" TEXT," + // 10: officialWebsite
                "\"ADDRESS\" TEXT," + // 11: address
                "\"INTRODUCE\" TEXT," + // 12: introduce
                "\"LEVEL\" TEXT," + // 13: level
                "\"COMPLAINT_TEL\" TEXT," + // 14: complaintTel
                "\"CONSULT_TEL\" TEXT," + // 15: consultTel
                "\"USER_ID\" TEXT," + // 16: userId
                "\"CREATED_USER_ID\" TEXT," + // 17: createdUserId
                "\"CREATED_TIME\" TEXT," + // 18: createdTime
                "\"UPDATE_USER_ID\" TEXT," + // 19: updateUserId
                "\"UPDATE_TIME\" TEXT," + // 20: updateTime
                "\"USER_TYPE\" TEXT," + // 21: userType
                "\"NICK_NAME\" TEXT," + // 22: nickName
                "\"STATUS\" TEXT," + // 23: status
                "\"USERNAME\" TEXT," + // 24: username
                "\"LOGOUT\" INTEGER NOT NULL );"); // 25: logout
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"user\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getUid());
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(2, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String registerEmail = entity.getRegisterEmail();
        if (registerEmail != null) {
            stmt.bindString(4, registerEmail);
        }
 
        String areaCode = entity.getAreaCode();
        if (areaCode != null) {
            stmt.bindString(5, areaCode);
        }
 
        String areaName = entity.getAreaName();
        if (areaName != null) {
            stmt.bindString(6, areaName);
        }
 
        String contacts = entity.getContacts();
        if (contacts != null) {
            stmt.bindString(7, contacts);
        }
 
        String contactsTel = entity.getContactsTel();
        if (contactsTel != null) {
            stmt.bindString(8, contactsTel);
        }
 
        String responsePerson = entity.getResponsePerson();
        if (responsePerson != null) {
            stmt.bindString(9, responsePerson);
        }
 
        String leaderEmail = entity.getLeaderEmail();
        if (leaderEmail != null) {
            stmt.bindString(10, leaderEmail);
        }
 
        String officialWebsite = entity.getOfficialWebsite();
        if (officialWebsite != null) {
            stmt.bindString(11, officialWebsite);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(12, address);
        }
 
        String introduce = entity.getIntroduce();
        if (introduce != null) {
            stmt.bindString(13, introduce);
        }
 
        String level = entity.getLevel();
        if (level != null) {
            stmt.bindString(14, level);
        }
 
        String complaintTel = entity.getComplaintTel();
        if (complaintTel != null) {
            stmt.bindString(15, complaintTel);
        }
 
        String consultTel = entity.getConsultTel();
        if (consultTel != null) {
            stmt.bindString(16, consultTel);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(17, userId);
        }
 
        String createdUserId = entity.getCreatedUserId();
        if (createdUserId != null) {
            stmt.bindString(18, createdUserId);
        }
 
        String createdTime = entity.getCreatedTime();
        if (createdTime != null) {
            stmt.bindString(19, createdTime);
        }
 
        String updateUserId = entity.getUpdateUserId();
        if (updateUserId != null) {
            stmt.bindString(20, updateUserId);
        }
 
        String updateTime = entity.getUpdateTime();
        if (updateTime != null) {
            stmt.bindString(21, updateTime);
        }
 
        String userType = entity.getUserType();
        if (userType != null) {
            stmt.bindString(22, userType);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(23, nickName);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(24, status);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(25, username);
        }
        stmt.bindLong(26, entity.getLogout() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getUid());
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(2, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String registerEmail = entity.getRegisterEmail();
        if (registerEmail != null) {
            stmt.bindString(4, registerEmail);
        }
 
        String areaCode = entity.getAreaCode();
        if (areaCode != null) {
            stmt.bindString(5, areaCode);
        }
 
        String areaName = entity.getAreaName();
        if (areaName != null) {
            stmt.bindString(6, areaName);
        }
 
        String contacts = entity.getContacts();
        if (contacts != null) {
            stmt.bindString(7, contacts);
        }
 
        String contactsTel = entity.getContactsTel();
        if (contactsTel != null) {
            stmt.bindString(8, contactsTel);
        }
 
        String responsePerson = entity.getResponsePerson();
        if (responsePerson != null) {
            stmt.bindString(9, responsePerson);
        }
 
        String leaderEmail = entity.getLeaderEmail();
        if (leaderEmail != null) {
            stmt.bindString(10, leaderEmail);
        }
 
        String officialWebsite = entity.getOfficialWebsite();
        if (officialWebsite != null) {
            stmt.bindString(11, officialWebsite);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(12, address);
        }
 
        String introduce = entity.getIntroduce();
        if (introduce != null) {
            stmt.bindString(13, introduce);
        }
 
        String level = entity.getLevel();
        if (level != null) {
            stmt.bindString(14, level);
        }
 
        String complaintTel = entity.getComplaintTel();
        if (complaintTel != null) {
            stmt.bindString(15, complaintTel);
        }
 
        String consultTel = entity.getConsultTel();
        if (consultTel != null) {
            stmt.bindString(16, consultTel);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(17, userId);
        }
 
        String createdUserId = entity.getCreatedUserId();
        if (createdUserId != null) {
            stmt.bindString(18, createdUserId);
        }
 
        String createdTime = entity.getCreatedTime();
        if (createdTime != null) {
            stmt.bindString(19, createdTime);
        }
 
        String updateUserId = entity.getUpdateUserId();
        if (updateUserId != null) {
            stmt.bindString(20, updateUserId);
        }
 
        String updateTime = entity.getUpdateTime();
        if (updateTime != null) {
            stmt.bindString(21, updateTime);
        }
 
        String userType = entity.getUserType();
        if (userType != null) {
            stmt.bindString(22, userType);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(23, nickName);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(24, status);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(25, username);
        }
        stmt.bindLong(26, entity.getLogout() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public UserEntity readEntity(Cursor cursor, int offset) {
        UserEntity entity = new UserEntity( //
            cursor.getLong(offset + 0), // uid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // registerEmail
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // areaCode
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // areaName
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // contacts
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // contactsTel
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // responsePerson
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // leaderEmail
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // officialWebsite
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // address
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // introduce
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // level
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // complaintTel
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // consultTel
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // userId
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // createdUserId
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // createdTime
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // updateUserId
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // updateTime
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // userType
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // nickName
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // status
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // username
            cursor.getShort(offset + 25) != 0 // logout
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserEntity entity, int offset) {
        entity.setUid(cursor.getLong(offset + 0));
        entity.setId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setRegisterEmail(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAreaCode(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setAreaName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setContacts(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setContactsTel(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setResponsePerson(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setLeaderEmail(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setOfficialWebsite(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setAddress(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setIntroduce(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setLevel(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setComplaintTel(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setConsultTel(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setUserId(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setCreatedUserId(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setCreatedTime(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setUpdateUserId(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setUpdateTime(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setUserType(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setNickName(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setStatus(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setUsername(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setLogout(cursor.getShort(offset + 25) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UserEntity entity, long rowId) {
        entity.setUid(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UserEntity entity) {
        if(entity != null) {
            return entity.getUid();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserEntity entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}