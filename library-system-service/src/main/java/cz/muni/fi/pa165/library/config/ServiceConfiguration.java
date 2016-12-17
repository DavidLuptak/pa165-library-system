package cz.muni.fi.pa165.library.config;

import cz.muni.fi.pa165.library.LibraryApplicationContext;
import cz.muni.fi.pa165.library.facade.CategoryFacade;
import cz.muni.fi.pa165.library.mapping.BeanMappingService;
import cz.muni.fi.pa165.library.service.CategoryService;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lenka (433591)
 * @version 12.11.2016
 */
@Configuration
@Import(LibraryApplicationContext.class)
@ComponentScan(basePackageClasses = {CategoryFacade.class, CategoryService.class, BeanMappingService.class})
public class ServiceConfiguration {

    @Bean
    public Mapper dozer() {
        // this is needed to support Java 8 time api with Dozer
        List<String> mappingFiles = new ArrayList<>();
        mappingFiles.add("dozerJdk8Converters.xml");

        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.setMappingFiles(mappingFiles);
        return dozer;
    }

//    /**
//     * Custom config for Dozer
//     */
//    public class DozerCustomConfig extends BeanMappingBuilder {
//        @Override
//        protected void configure() {
//        }
//    }
}
