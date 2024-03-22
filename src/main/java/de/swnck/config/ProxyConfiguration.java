package de.swnck.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProxyConfiguration {
    private String host;
    private int port;
}
