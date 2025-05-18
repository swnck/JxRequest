package io.github.swnck.util;

import lombok.Getter;

@Getter
public enum SimulationAgent {
    CHROME("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36"),
    FIREFOX("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0"),
    SAFARI("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.4 Safari/605.1.15"),
    EDGE("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36 Edg/136.0.0.0");

    private final String userAgent;

    SimulationAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public static String CUSTOM(String userAgent) {
        return userAgent;
    }
}
