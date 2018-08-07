package com.idata365.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * Created by summer on 2016/11/25.
 */
@Configuration
@MapperScan(basePackages = "com.idata365.mapper.statistics", sqlSessionTemplateRef  = "statisticsSqlSessionTemplate")
public class DataSource2Config {

    @Bean(name = "statisticsDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.statistics")
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "statisticsSqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("statisticsDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/statistics/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "statisticsTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("statisticsDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "statisticsSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("statisticsSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
