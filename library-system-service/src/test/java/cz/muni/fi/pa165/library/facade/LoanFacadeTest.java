package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.config.ServiceConfiguration;
import cz.muni.fi.pa165.library.mapping.BeanMappingService;
import cz.muni.fi.pa165.library.service.LoanService;
import cz.muni.fi.pa165.library.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author Lenka (433591)
 * @version 19.11.2016
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class LoanFacadeTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private LoanService loanService;

    @Mock
    private UserService userService;

    @Mock
    private BeanMappingService beanMappingService;

    @Autowired
    @InjectMocks
    private LoanFacade loanFacade;

    @BeforeClass
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() throws Exception {
    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testFindById() throws Exception {

    }

    @Test
    public void testFindByUser() throws Exception {

    }

    @Test
    public void testFindAll() throws Exception {

    }

}