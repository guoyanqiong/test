package com.example.demo.utils.common.utils;

import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.util.StringUtil;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Administrator on 2018/3/29.
 */
public class MapperUtil {

    protected static MapperHelper mapperHelper;

    public MapperUtil(Class<?> mapperClass, MapperHelper mapperHelper) {
        this.mapperHelper = mapperHelper;
    }

    public static String tableName(Class<?> entityClass) {
        EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        String prefix = entityTable.getPrefix();
//        if(StringUtil.isEmpty(prefix)) {
//            prefix = this.mapperHelper.getConfig().getPrefix();
//        }

        return StringUtil.isNotEmpty(prefix)?prefix + "." + entityTable.getName():entityTable.getName();
    }

    public static Long getPKValue(Object obj) {
        try {
            Class<?> objClass = obj.getClass();
            Set columnSet = EntityHelper.getPKColumns(obj.getClass());
            Iterator var4 = columnSet.iterator();
            while (var4.hasNext()) {
                EntityColumn column = (EntityColumn) var4.next();
                String pkColumn = column.getColumn();
                PropertyDescriptor pd = new PropertyDescriptor(pkColumn, objClass);
                Method getMethod = pd.getReadMethod();
                Object objNew = getMethod.invoke(obj);
                return Long.parseLong(objNew.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }
}
