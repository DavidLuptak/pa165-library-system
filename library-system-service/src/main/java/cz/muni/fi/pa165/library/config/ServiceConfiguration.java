package cz.muni.fi.pa165.library.config;

import cz.muni.fi.pa165.library.LibraryApplicationContext;
import cz.muni.fi.pa165.library.facade.CategoryFacadeImpl;
import cz.muni.fi.pa165.library.service.CategoryServiceImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Lenka (433591)
 * @version 12.11.2016
 */
@Configuration
@Import(LibraryApplicationContext.class)
@ComponentScan(basePackageClasses = {CategoryFacadeImpl.class, CategoryServiceImpl.class})
public class ServiceConfiguration {

    @Bean
    public Mapper dozer() {
        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.addMapping(new DozerCustomConfig());
        return dozer;
    }

    /**
     * Custom config for Dozer
     */
    public class DozerCustomConfig extends BeanMappingBuilder {
        @Override
        protected void configure() {
            //TODO
        }
    }
}
