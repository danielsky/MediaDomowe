package com.dskimina;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

import javax.sql.DataSource;
import java.util.Locale;
import java.util.Properties;

/**
 * Created by daniel on 09.07.16.
 */
@Configuration
public class Config extends WebMvcConfigurerAdapter{


    @Value("${ds.url}")
    private String jdbcUrl;

    @Value("${ds.driver-class-name}")
    private String jdbcDriverClass;

    @Value("${hibernate.dialect}")
    private String HIBERNATE_DIALECT;

    @Value("${hibernate.show_sql}")
    private String HIBERNATE_SHOW_SQL;

    @Value("${hibernate.hbm2ddl.auto}")
    private String HIBERNATE_HBM2DDL_AUTO;

    @Value("${hibernate.schema}")
    private String HIBERNATE_SCHEMA;

    @Value("${entitymanager.packagesToScan}")
    private String ENTITYMANAGER_PACKAGES_TO_SCAN;

    @Bean
    public ViewResolver getViewResolver(){
        VelocityViewResolver vr = new VelocityViewResolver();
        vr.setPrefix("/templates/");
        vr.setSuffix(".vm");
        vr.setContentType("text/html;charset=UTF-8");
        return vr;
    }

    /*@Bean
    public VelocityConfigurer getViewConfigurer(){
        return new VelocityConfigurer();
    }*/

    /*@Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }*/

    /*@Bean
    public MessageSource getMessages(){
        ReloadableResourceBundleMessageSource msg = new ReloadableResourceBundleMessageSource();
        msg.setBasename("/i18n/messages");
        msg.setDefaultEncoding("UTF-8");
        return msg;
    }*/

    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(new Locale("pl"));
        return resolver;
    }

    /*@Bean
    public DataSource getDataSource() throws Exception{
        SQLiteJDBCLoader.initialize();
        SQLiteDataSource dataSource = new SQLiteDataSource();
        //dataSource.setUrl("jdbc:sqlite:/home/users.sqlite");
        dataSource.setUrl("jdbc:sqlite:"+path);

        return dataSource;
    }*/

    public DataSource getDataSource() throws Exception{
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setDriverClassName(jdbcDriverClass);
        dataSource.setUsername("daniel");
        dataSource.setPassword("daniel");
        return dataSource;
    }


    @Bean
    public LocalSessionFactoryBean sessionFactory() throws Exception {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(getDataSource());
        sessionFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
        hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
        hibernateProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
        hibernateProperties.put("hibernate.default_schema", HIBERNATE_SCHEMA);
        sessionFactoryBean.setHibernateProperties(hibernateProperties);

        return sessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager transactionManager() throws Exception{
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }





    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("/img/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");
        registry.addResourceHandler("/view/**").addResourceLocations("/view/");
    }
}
