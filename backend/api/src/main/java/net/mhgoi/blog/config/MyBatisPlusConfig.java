package net.mhgoi.blog.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

/**
 * @author 少年
 */
@EnableTransactionManagement
@Configuration
public class MyBatisPlusConfig {

//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
//        // paginationInterceptor.setOverflow(false);
//        // 设置最大单页限制数量，默认 500 条，-1 不受限制
//        // paginationInterceptor.setLimit(500);
//        // 开启 count 的 join 优化,只针对部分 left join
//        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
//        return paginationInterceptor;
//    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(@Value("${spring.datasource.url}") String url) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();


        if (StringUtils.contains(url, "postgresql")) {
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));
        } else {
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        }


        return interceptor;
    }

//    @Bean
//    public MybatisPlusPropertiesCustomizer plusPropertiesCustomizer() {
//        return plusProperties -> plusProperties.getGlobalConfig().setIdentifierGenerator(new CustomIdGenerator());
//    }

    @Bean
    public DatabaseIdProvider databaseIdProvider() {
        VendorDatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties properties = new Properties();
        properties.put("MySQL", "mysql");
        properties.put("PostgreSQL", "postgresql");
        databaseIdProvider.setProperties(properties);
        return databaseIdProvider;
    }
}
