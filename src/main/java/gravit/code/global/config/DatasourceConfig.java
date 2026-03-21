package gravit.code.global.config;

import gravit.code.global.listener.QueryMetricsListener;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@RequiredArgsConstructor
@Configuration
public class DatasourceConfig {
    private final QueryMetricsListener queryMetricsListener;

    @Bean
    @Primary
    public DataSource proxyDataSource(DataSourceProperties props) {
        DataSource dataSource = props.initializeDataSourceBuilder().build();

        return ProxyDataSourceBuilder
                .create(dataSource)
                .listener(queryMetricsListener)
                .name("main")
                .build();
    }
}
