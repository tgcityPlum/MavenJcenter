package com.tgcity.profession.network.callback;

import java.lang.reflect.Type;

/**
 * @author TGCity
 *
 * 获取类型接口
 */
public interface IType<T> {
    /**
     * get Type
     * @return Type
     */
    Type getType();
}
