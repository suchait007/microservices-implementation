package com.retail.api.gateway.filter;

import brave.Span;
import brave.propagation.TraceContext;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Component
public class TraceIdFilter implements GlobalFilter, Ordered {

    private static final String TRACE_ID_HEADER = "X-Trace-ID";

    private final Tracer tracer;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String traceId = tracer.currentSpan().context().traceId();
        exchange.getResponse().getHeaders().add(TRACE_ID_HEADER, traceId);
        MDC.put(TRACE_ID_HEADER, traceId);

        return chain.filter(exchange).doFinally(signalType -> {
            MDC.remove(TRACE_ID_HEADER);
        });
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

}
