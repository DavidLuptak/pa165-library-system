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
import java.util.ArrayList;
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

    private LoanNewDTO newLoan;
    private User user;
    private BookCopy bookCopy;
    private Book book;
    private Loan loan;

    @BeforeClass
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
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
    }

    @Test
    public void testCreate() throws Exception {
        Date now = new Date();
        newLoan = new LoanNewDTO(user.getId(), bookCopy.getId(), new Date());

        when(userService.findById(1L)).thenReturn(user);
        when(bookCopyService.findById(1L)).thenReturn(bookCopy);
        ArgumentCaptor<Loan> captor = ArgumentCaptor.forClass(Loan.class);

        loanFacade.create(newLoan);
        verify(loanService).create(captor.capture());

        Loan entity = captor.getValue();
        assertNull(entity.getId());
        assertSame(user, entity.getUser());
        assertSame(bookCopy, entity.getBookCopy());
    }

    @Test
    public void testDelete() throws Exception {
        when(loanService.findById(1L)).thenReturn(loan);
        loanFacade.delete(1L);
        verify(loanService).delete(loan);
    }

    @Test
    public void testFindById() throws Exception {
        when(loanService.findById(1L)).thenReturn(loan);
        LoanDTO dto = loanFacade.findById(1L);
        assertNotNull(dto);
        assertEqualsLoanAndLoanDTO(loan, dto);
    }

    @Test
    public void testFindByUser() throws Exception {
        List<Loan> loans = new ArrayList<>();
        loans.add(loan);
        when(loanService.findByUser(user)).thenReturn(loans);
        when(loanService.findById(1L)).thenReturn(loan);
        List<LoanDTO> result = loanFacade.findByUser(1L);
        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertEqualsLoanAndLoanDTO(loan, result.get(0));
    }

    @Test
    public void testFindAll() {
        User user2 = new User("Ondrej", "Velky", "ondrej@abc.com", "Praha", UserRole.ADMIN);
        user2.setId(2L);
        BookCopy bookCopy2 = new BookCopy(book, BookState.MEDIUM_DAMAGE);
        bookCopy2.setId(2L);
        Loan loan2 = new Loan(user2, bookCopy2, new Date());
        List<Loan> loans = new ArrayList<>();
        loans.add(loan);
        loans.add(loan2);
        when(loanService.findAll()).thenReturn(loans);
        List<LoanDTO> dtos = loanFacade.findAll();
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEqualsLoanAndLoanDTO(loan, dtos.get(0));
        assertEqualsLoanAndLoanDTO(loan2, dtos.get(1));
    }

    private void assertEqualsLoanAndLoanDTO(Loan loan, LoanDTO dto) {
        assertEquals(loan.getUser().getId(), dto.getUser().getId());
        assertEquals(loan.getBookCopy().getId(), dto.getBookCopy().getId());
        assertEquals(loan.getLoanDate(), dto.getLoanDate());
        assertEquals(loan.getBookState(), dto.getBookState());
        assertEquals(loan.getReturnDate(), dto.getReturnDate());
    }

}