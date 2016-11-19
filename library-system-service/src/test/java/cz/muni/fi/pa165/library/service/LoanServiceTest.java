package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.config.ServiceConfiguration;
import cz.muni.fi.pa165.library.dao.LoanDao;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author Lenka (433591)
 * @version 19.11.2016
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class LoanServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private LoanDao loanDao;

    @Autowired
    @InjectMocks
    private LoanService loanService;

    @BeforeMethod
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