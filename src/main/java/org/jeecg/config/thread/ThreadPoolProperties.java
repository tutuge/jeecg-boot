package org.jeecg.config.thread;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 线程池 配置属性
 *
 * @author none
 */
@Data
@Component
@ConfigurationProperties(prefix = "thread-pool")
public class ThreadPoolProperties {

    /**
     * 是否开启线程池
     */
    private boolean enabled;
    /**
     * 核心线程数
     */
    private Integer corePoolSize;
    /**
     * 最大线程数
     */
    private Integer maxPoolSize;

    /**
     * 队列最大长度
     */
    private int queueCapacity;

    /**
     * 线程池维护线程所允许的空闲时间
     */
    private int keepAliveSeconds;

}
