package com.xb.blog.article.config.mybatis.typeHandler;

import cn.hutool.core.util.StrUtil;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Mybatis类型处理器：将以逗号分割的字符串转化为List,使用场景：
 * 1、Mybatis-Plus实体类中，标注了@TableField注解的字段，设置typeHandler属性的值
 * 2、xml文件中，定义resultMap，在需要转换的字段映射中，设置typeHandler属性的值
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes({List.class})
public class ConvertCommaSeparatedStrToListTypeHandler implements TypeHandler<List<String>> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, List<String> strings, JdbcType jdbcType) throws SQLException {
        String items = StrUtil.join(",", strings);
        try {
            preparedStatement.setString(i, items);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getResult(ResultSet resultSet, String s) throws SQLException {
        if (StrUtil.isNotBlank(resultSet.getString(s))) {
            return new ArrayList<>(Arrays.asList(resultSet.getString(s).split(",")));
        }
        return null;
    }

    @Override
    public List<String> getResult(ResultSet resultSet, int i) throws SQLException {
        if (StrUtil.isNotBlank(resultSet.getString(i))) {
            return new ArrayList<>(Arrays.asList(resultSet.getString(i).split(",")));
        }
        return null;
    }

    @Override
    public List<String> getResult(CallableStatement callableStatement, int i) throws SQLException {
        String items = callableStatement.getString(i);
        return StrUtil.isNotBlank(items) ? new ArrayList<>(Arrays.asList(items.split(","))) : null;
    }
}
