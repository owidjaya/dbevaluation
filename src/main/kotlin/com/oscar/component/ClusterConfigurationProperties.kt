package com.oscar.component

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component


@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
class ClusterConfigurationProperties {
    /**
     * Get initial collection of known cluster nodes in format `host:port`.
     *
     * @return
     */
    /*
     * spring.redis.cluster.nodes[0] = 127.0.0.1:7379
     * spring.redis.cluster.nodes[1] = 127.0.0.1:7380
     * ...
     */
    var nodes = mutableListOf<String>()
}

