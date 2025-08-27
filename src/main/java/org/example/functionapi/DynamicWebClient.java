package org.example.functionapi;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.util.Map;

public class DynamicWebClient {
    private final WebClient webClient;

    public DynamicWebClient(String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public <T> Mono<T> execute(String path,
                               HttpMethod method,
                               Map<String, String> params,
                               Object body,
                               Class<T> responseType) {

        WebClient.RequestBodySpec requestSpec = webClient.method(method)
                .uri(uriBuilder -> {
                    UriBuilder builder = uriBuilder.path(path);
                    if (params != null) {
                        params.forEach(builder::queryParam);
                    }
                    return builder.build();
                });

        if (requiresBody(method) && body != null) {
            requestSpec = (WebClient.RequestBodySpec) requestSpec.bodyValue(body);
        }

        return requestSpec
                .retrieve()
                .bodyToMono(responseType);
    }

    private boolean requiresBody(HttpMethod method) {
        return method == HttpMethod.POST ||
                method == HttpMethod.PUT ||
                method == HttpMethod.PATCH;
    }
}