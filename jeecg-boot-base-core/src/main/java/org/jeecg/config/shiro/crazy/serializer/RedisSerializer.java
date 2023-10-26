package org.jeecg.config.shiro.crazy.serializer;

import org.jeecg.config.shiro.crazy.exception.SerializationException;

public interface RedisSerializer<T> {

    byte[] serialize(T t) throws SerializationException;

    T deserialize(byte[] bytes) throws SerializationException;
}
