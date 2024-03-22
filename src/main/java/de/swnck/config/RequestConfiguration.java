package de.swnck.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RequestConfiguration {
    private boolean returnTransfer;
    private boolean followLocation;
    private int timeout;
    private int maxRedirects;
}
