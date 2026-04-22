package gravit.code.global.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.http.HttpClient;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RestClientConfigTest {

    private final RestClient restClient = new RestClientConfig().restClient();

    @Nested
    @DisplayName("RestClient Bean이 생성될 때")
    class BeanCreationContext {

        @Test
        void JdkClientHttpRequestFactory를_사용한다() {
            // when
            Object factory = ReflectionTestUtils.getField(restClient, "clientRequestFactory");

            // then
            assertThat(factory).isInstanceOf(JdkClientHttpRequestFactory.class);
        }

        @Test
        void connectTimeout이_3초로_설정된다() {
            // given
            JdkClientHttpRequestFactory factory = (JdkClientHttpRequestFactory)
                    ReflectionTestUtils.getField(restClient, "clientRequestFactory");
            HttpClient httpClient = (HttpClient) ReflectionTestUtils.getField(factory, "httpClient");

            // when
            Optional<Duration> connectTimeout = httpClient.connectTimeout();

            // then
            assertThat(connectTimeout)
                    .isPresent()
                    .hasValue(Duration.ofSeconds(3));
        }

        @Test
        void readTimeout이_5초로_설정된다() {
            // given
            JdkClientHttpRequestFactory factory = (JdkClientHttpRequestFactory)
                    ReflectionTestUtils.getField(restClient, "clientRequestFactory");

            // when
            Duration readTimeout = (Duration) ReflectionTestUtils.getField(factory, "readTimeout");

            // then
            assertThat(readTimeout).isEqualTo(Duration.ofSeconds(5));
        }
    }

    @Nested
    @DisplayName("외부 서버 응답이 지연될 때")
    class ReadTimeoutBehaviorContext {

        @Test
        @Timeout(10)
        void readTimeout_초과시_ResourceAccessException이_발생한다() throws IOException {
            // given - 연결은 수락하지만 응답을 보내지 않는 서버 시뮬레이션
            try (ServerSocket serverSocket = new ServerSocket(0)) {
                int port = serverSocket.getLocalPort();

                Thread serverThread = new Thread(() -> {
                    try (Socket accepted = serverSocket.accept()) {
                        accepted.getInputStream().read(); // 클라이언트가 끊을 때까지 블로킹
                    } catch (IOException ignored) {}
                });
                serverThread.setDaemon(true);
                serverThread.start();

                Instant start = Instant.now();

                // when & then
                assertThatThrownBy(() ->
                        restClient.get()
                                .uri("http://localhost:" + port)
                                .retrieve()
                                .body(String.class)
                ).isInstanceOf(ResourceAccessException.class);

                Duration elapsed = Duration.between(start, Instant.now());
                assertThat(elapsed)
                        .as("readTimeout(5초) 이후 예외가 발생해야 한다")
                        .isGreaterThanOrEqualTo(Duration.ofSeconds(4))
                        .isLessThan(Duration.ofSeconds(8));
            }
        }
    }
}
