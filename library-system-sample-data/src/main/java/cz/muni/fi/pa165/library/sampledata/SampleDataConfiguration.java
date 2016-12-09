package cz.muni.fi.pa165.library.sampledata;

import cz.muni.fi.pa165.library.config.ServiceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Dávid Lupták
 * @version 9.12.2016
 */
@Configuration
@Import(ServiceConfiguration.class)
@ComponentScan(basePackageClasses = SampleDataLoadingFacade.class)
public class SampleDataConfiguration {

    final static Logger LOGGER = LoggerFactory.getLogger(SampleDataConfiguration.class);

    @Inject
    private SampleDataLoadingFacade sampleDataLoadingFacade;

    @PostConstruct
    public void dataLoading() {
        LOGGER.debug("dataLoading()");
        sampleDataLoadingFacade.loadData();
    }
}
