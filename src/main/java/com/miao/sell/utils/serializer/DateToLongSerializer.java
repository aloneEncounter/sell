package com.miao.sell.utils.serializer;
/*
 *Created by IntelliJ IDEA.
 *@author Miao
 *@date 2019/6/17 0:01
 * Date 时间的转换
 */

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/*
* 该注解将时间序列化成时间戳  以秒为单位
* */
public class DateToLongSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(date.getTime()/1000);
    }
}
