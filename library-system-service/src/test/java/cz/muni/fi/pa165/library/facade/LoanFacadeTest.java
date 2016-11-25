package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.config.ServiceConfiguration;
import cz.muni.fi.pa165.library.dto.LoanDTO;
import cz.muni.fi.pa165.library.dto.LoanNewDTO;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.entity.User;
import cz.muni.fi.pa165.library.enums.BookState;
import cz.muni.fi.pa165.library.enums.UserRole;
import cz.muni.fi.pa165.library.mapping.BeanMappingService;
import cz.muni.fi.pa165.library.mapping.BeanMappingServiceImpl;
import cz.muni.fi.pa165.library.service.BookCopyService;
import cz.muni.fi.pa165.library.service.LoanService;
import cz.muni.fi.pa165.library.service.UserService;
import org.mockito.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;


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
    private BookCopyService bookCopyService;

    @Spy
    @Inject
    private final BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    @InjectMocks
    private LoanFacade loanFacade = new LoanFacadeImpl();

    @Captor
    private ArgumentCaptor<Loan> loanArgumentCaptor;

    private LoanNewDTO loanNewDTO;
    private User user;
    private User user2;
    private BookCopy bookCopy;
    private BookCopy bookCopy2;
    private Book book;
    private Loan loan;
    private Loan loan2;

    @BeforeClass
    public void initMockito() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void initEntities() {
        user = new User("Jan", "Novak", "jan@lala.com", "Brno", UserRole.MEMBER);
        user.setId(1L);
        book = new Book("Pride and Prejudice", "Jane Austen", "28392883829");
        book.setId(1L);
        bookCopy = new BookCopy(book, BookState.NEW);
        bookCopy.setId(1L);
        loan = new Loan(user, bookCopy, new Date());
        loan.setId(1L);
        loan.setBookState(BookState.LIGHT_DAMAGE);
        loan.setReturnDate(new Date());

        user2 = new User("Ondrej", "Velky", "ondrej@abc.com", "Praha", UserRole.ADMIN);
        user2.setId(2L);
        bookCopy2 = new BookCopy(book, BookState.MEDIUM_DAMAGE);
        bookCopy2.setId(2L);
        loan2 = new Loan(user2, bookCopy2, new Date());
    }

    @BeforeMethod(dependsOnMethods = "initEntities")
    public void initMocksBehaviour() {
        // findById
        when(loanService.findById(1L)).thenReturn(loan);
        when(bookCopyService.findById(1L)).thenReturn(bookCopy);
        when(userService.findById(1L)).thenReturn(user);

        // findByUser
        when(loanService.findByUser(user)).thenReturn(Arrays.asList(loan));

        // findAll
        when(loanService.findAll()).thenReturn(Arrays.asList(loan, loan2));

        // findNotReturnedUserLoans
        when(loanService.findNotReturnedUserLoans(user)).thenReturn(Arrays.asList(loan, loan2));

        // findReturnedUserLoans
        when(loanService.findReturnedUserLoans(user)).thenReturn(Arrays.asList(loan, loan2));
    }

    @Test
    public void testCreate() {
        loanNewDTO = new LoanNewDTO(user.getId(), Arrays.asList(bookCopy.getId()), new Date());
        loanFacade.create(loanNewDTO);

        verify(loanService).create(loanArgumentCaptor.capture());

        Loan entity = loanArgumentCaptor.getValue();

        assertNull(entity.getId());
        assertSame(user, entity.getUser());
        assertSame(bookCopy, entity.getBookCopy());
    }

    @Test
    public void testUpdate() {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setId(loan.getId());
        loanDTO.setBookState(BookState.LIGHT_DAMAGE);
      //  loanDTO.setBookCopy(loan.getBookCopy());
        loanDTO.setLoanDate(loan.getLoanDate());
      //  loanDTO.setUser(loan.getUser());
        loanDTO.setReturnDate(loan.getReturnDate());
        verify(loanService).update(loanArgumentCaptor.capture());

        //TODO:
    }

    @Test
    public void testDelete() {
        loanFacade.delete(1L);
        verify(loanService).delete(loan);
    }

    @Test
    public void testFindById() {
        LoanDTO dto = loanFacade.findById(1L);
        assertNotNull(dto);
        assertEqualsLoanAndLoanDTO(loan, dto);
    }

    @Test
    public void testFindByUser() {
        List<LoanDTO> result = loanFacade.findByUser(1L);
        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertEqualsLoanAndLoanDTO(loan, result.get(0));
    }

    @Test
    public void testFindAll() {
        List<LoanDTO> loanDTOs = loanFacade.findAll();
        assertNotNull(loanDTOs);
        assertEquals(loanDTOs.size(), 2);
        assertEqualsLoanAndLoanDTO(loan, loanDTOs.get(0));
        assertEqualsLoanAndLoanDTO(loan2, loanDTOs.get(1));
    }

    @Test
    public void testFindNotReturnedUserLoans() {
        List<LoanDTO> result = loanFacade.findNotReturnedUserLoans(user.getId());
        assertNotNull(result);
        assertEquals(result.size(), 2);
        assertEqualsLoanAndLoanDTO(loan, result.get(0));
        assertEqualsLoanAndLoanDTO(loan2, result.get(1));
    }

    @Test
    public void testFindReturnedUserLoans() {
        List<LoanDTO> result = loanFacade.findReturnedUserLoans(user.getId());
        assertNotNull(result);
        assertEquals(result.size(), 2);
        assertEqualsLoanAndLoanDTO(loan, result.get(0));
        assertEqualsLoanAndLoanDTO(loan2, result.get(1));
    }

    private void assertEqualsLoanAndLoanDTO(Loan loan, LoanDTO dto) {
        assertEquals(loan.getUser().getId(), dto.getUser().getId());
        assertEquals(loan.getBookCopy().getId(), dto.getBookCopy().getId());
        assertEquals(loan.getLoanDate(), dto.getLoanDate());
        assertEquals(loan.getBookState(), dto.getBookState());
        assertEquals(loan.getReturnDate(), dto.getReturnDate());
    }

}
