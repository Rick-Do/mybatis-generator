package com.dx.generator.service.impl;

import com.dx.generator.entity.Memory;
import com.dx.generator.mapper.MemoryMapper;
import com.dx.generator.service.LocalMemoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author dongy
 * @version 1.0
 * @date 2023/4/3 08:31:39
 * @since jdk1.8_202
 */
@Service
public class LocalMemoryServiceImpl implements LocalMemoryService {

    @Resource
    private MemoryMapper memoryMapper;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void setValue(String key, String value, Long expire, TimeUnit timeUnit) {
        Assert.notNull(key, "key不能为空");
        String id = DigestUtils.md5DigestAsHex(key.getBytes(StandardCharsets.UTF_8));
        Memory memory = memoryMapper.selectById(id);
        if (Objects.nonNull(memory)){
            memoryMapper.deleteById(memory.getId());
        }
        memoryMapper.insert(new Memory()
                .setId(id)
                .setMemValue(value)
                .setMemKey(key)
                .setMemType("redis")
                .setSaveTime(System.nanoTime())
                .setTimeUnit(timeUnit)
                .setExpire(expire));

    }

    @Override
    public void setValue(String key, String value) {
        setValue(key, value, -1L, null);
    }

    @Override
    public String getValue(String key) {
        String id = DigestUtils.md5DigestAsHex(key.getBytes(StandardCharsets.UTF_8));
        Memory memory = memoryMapper.selectById(id);
        if (Objects.isNull(memory)){
            return null;
        }
        if (Objects.equals(-1L, memory.getExpire()) || Objects.isNull(memory.getTimeUnit())){
            return memory.getMemValue();
        }
        TimeUnit timeUnit = memory.getTimeUnit();
        Long expire = memory.getExpire();
        long nanos = timeUnit.toNanos(expire);
        return (System.nanoTime() - memory.getSaveTime()) > nanos
                ? null
                : memory.getMemValue();
    }
}
