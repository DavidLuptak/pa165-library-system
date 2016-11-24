package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.config.ServiceConfiguration;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;

/**
 * @author Martin
 * @version 24.11.2016.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BookCopyFacadeTest extends AbstractTransactionalTestNGSpringContextTests {

    @BeforeClass
    public void initMockito() {
        MockitoAnnotations.initMocks(this);
    }


}
