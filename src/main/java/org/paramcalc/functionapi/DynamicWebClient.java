package org.paramcalc.functionapi;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * A dynamic HTTP client for making requests to various APIs without requiring specific implementation for each endpoint.
 * Provides a flexible way to call different REST APIs using a unified interface.
 */
public class DynamicWebClient {
    private final WebClient webClient;

    /**
     * Constructs a DynamicWebClient with the specified base URL.
     *
     * @param baseUrl the base URL for all requests (e.g., "<a href="https://api.example.com">...</a>")
     */
    public DynamicWebClient(String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    /**
     * Executes a dynamic HTTP request to the specified endpoint.
     *
     * @param <T>          the expected response type
     * @param path         the endpoint path (e.g., "/users")
     * @param method       the HTTP method (GET, POST, PUT, DELETE, etc.)
     * @param params       query parameters (optional - can be null)
     * @param body         request body for POST/PUT/PATCH methods (optional)
     * @param responseType the expected response class
     * @return a Mono containing the response of the specified type
     */
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

    /**
     * Checks if the given HTTP method requires a request body.
     *
     * @param method the HTTP method to check
     * @return true if the method requires a body (POST, PUT, PATCH)
     */
    private boolean requiresBody(HttpMethod method) {
        return method == HttpMethod.POST ||
                method == HttpMethod.PUT ||
                method == HttpMethod.PATCH;
    }
}