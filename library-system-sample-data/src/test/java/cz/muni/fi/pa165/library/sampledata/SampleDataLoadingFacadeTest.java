package cz.muni.fi.pa165.library.sampledata;

import cz.muni.fi.pa165.library.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;

/**
 * @author Dávid Lupták
 * @version 9.12.2016
 */
@ContextConfiguration(classes = {SampleDataConfiguration.class})
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class SampleDataLoadingFacadeTest extends AbstractTestNGSpringContextTests {

    final static Logger LOGGER = LoggerFactory.getLogger(SampleDataLoadingFacadeTest.class);

    @Inject
    public BookDao bookDao;
    @Inject
    public LoanDao loanDao;
    @Inject
    public UserDao userDao;
    @Inject
    public CategoryDao categoryDao;

    @Test
    public void createSampleData() {
        LOGGER.debug("Tests started.");

        Assert.assertTrue(bookDao.findAll().size() > 0, "No books.");
        Assert.assertTrue(loanDao.findAll().size() > 0, "No loans.");
        Assert.assertTrue(categoryDao.findAll().size() > 0, "No categories.");
        Assert.assertTrue(userDao.findAll().size() > 0, "No users.");

        LOGGER.debug("Tests finished.");
    }
}
