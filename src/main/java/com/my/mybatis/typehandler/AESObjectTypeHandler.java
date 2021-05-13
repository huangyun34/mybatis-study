package com.my.mybatis.typehandler;

import com.my.mybatis.typehandler.bo.AESObject;
import com.my.mybatis.utils.AESUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AESObjectTypeHandler extends BaseTypeHandler<AESObject> {

    public AESObjectTypeHandler() {
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, AESObject aesObject, JdbcType jdbcType) throws SQLException {
        String param = aesObject == null || aesObject.getValue() == null ? null : aesObject.getValue();
        if (null != param) {
            try {
                param = AESUtils.encrypt(param);
            } catch (Exception e) {
            }
        }
        preparedStatement.setString(i, param);
    }

    @Override
    public AESObject getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String resultSetString = resultSet.getString(s);
        if (null != resultSetString) {
            try {
                resultSetString = AESUtils.decrypt(resultSetString);
            } catch (Exception e) {
            }
        }
        return new AESObject(resultSetString);
    }

    @Override
    public AESObject getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String resultSetString = resultSet.getString(i);
        if (null != resultSetString) {
            try {
                resultSetString = AESUtils.decrypt(resultSetString);
            } catch (Exception e) {
            }
        }
        return new AESObject(resultSetString);
    }

    @Override
    public AESObject getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String resultSetString = callableStatement.getString(i);
        if (null != resultSetString) {
            try {
                resultSetString = AESUtils.decrypt(resultSetString);
            } catch (Exception e) {
            }
        }
        return new AESObject(resultSetString);
    }
}
