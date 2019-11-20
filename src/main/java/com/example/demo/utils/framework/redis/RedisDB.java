package com.example.demo.utils.framework.redis;

public enum RedisDB {
    //Default,User,iShop,eMall,Product,Cart,Platform,BaseData,Global,BST,BSTM,ATM,UNIPAY
    Default(0), Web(1), App(2), Session(3), Key(4), User(5), Queue(6), BaseData(7),
    //nice
    Activity(8), AdvertIndustryIdData(9), AdvertOrderShow(10), AdvertRoundShow(11), OrderCreative(12), AreaSearch(13),
    //storm相关
    AdvertUV(14), AdvertOrderDayBudget(15), AdvertAccount(16), AdvertOrderStatus(17),
    //MQ相关
    AliyunMQStatus(18);

    private int dbIndex;

    private RedisDB(int value) {
        this.dbIndex = value;
    }

    /**
     * 获取redis数据库索引
     *
     * @return
     */
    public int getDBIndex() {
        return this.dbIndex;
    }

    /**
     * 将值解析为redisdb
     *
     * @param dbIndex
     * @return
     */
    public static RedisDB parse(int dbIndex) {
        RedisDB redisDb = RedisDB.Default;
        for (RedisDB db : RedisDB.values()) {
            if (db.getDBIndex() == dbIndex) {
                redisDb = db;
                break;
            }
        }

        return redisDb;
    }
}
