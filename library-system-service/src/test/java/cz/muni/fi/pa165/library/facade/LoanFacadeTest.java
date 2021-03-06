package cz.muni.fi.pa165.library.facade;

import cz.muni.fi.pa165.library.config.ServiceConfiguration;
import cz.muni.fi.pa165.library.dto.BookCopyDTO;
import cz.muni.fi.pa165.library.dto.LoanDTO;
import cz.muni.fi.pa165.library.dto.LoanNewDTO;
import cz.muni.fi.pa165.library.dto.UserDTO;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.BookCopy;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.entity.User;
import cz.muni.fi.pa165.library.enums.BookState;
import cz.muni.fi.pa165.library.enums.UserRole;
import cz.muni.fi.pa165.library.mapping.BeanMappingService;
import cz.muni.fi.pa165.library.mapping.BeanMappingServiceImpl;
import cz.muni.fi.pa165.library.service.BookCopyService;
import cz.muni.fi.pa165.library.service.BookService;
import cz.muni.fi.pa165.library.service.LoanService;
import cz.muni.fi.pa165.library.service.UserService;
import org.mockito.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Arrays;
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

    @Mock
    private BookService bookService;

    @Spy
    @Inject
    private final BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    @InjectMocks
    private LoanFacade loanFacade = new LoanFacadeImpl();

    @Captor
    private ArgumentCaptor<Loan> loanArgumentCaptor;

    private LoanNewDTO loanNewDTO;
    private BookCopyDTO bookCopyDTO;
    private UserDTO userDTO;
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
        loan = new Loan(user, bookCopy, LocalDateTime.of(2016,10,10,10,10));
        loan.setId(1L);
        loan.setReturnBookState(BookState.LIGHT_DAMAGE);
        loan.setReturnDate(LocalDateTime.of(2016,10,10,10,10));

        user2 = new User("Ondrej", "Velky", "ondrej@abc.com", "Praha", UserRole.ADMIN);
        user2.setId(2L);
        bookCopy2 = new BookCopy(book, BookState.MEDIUM_DAMAGE);
        bookCopy2.setId(2L);
        loan2 = new Loan(user2, bookCopy2, LocalDateTime.of(2016,10,10,10,10));
    }

    @BeforeMethod(dependsOnMethods = "initEntities")
    public void initMocksBehaviour() {
        // findById
        when(loanService.findById(1L)).thenReturn(loan);
        when(bookCopyService.findById(1L)).thenReturn(bookCopy);
        when(bookCopyService.findLoanableByBook(book)).thenReturn(bookCopy);
        when(userService.findById(1L)).thenReturn(user);
        when(bookService.findById(1L)).thenReturn(book);

        // findByUser
        when(loanService.findByUser(user)).thenReturn(Arrays.asList(loan));

        // findAll
        when(loanService.findAll()).thenReturn(Arrays.asList(loan, loan2));

        // findNotReturnedUserLoans
        when(loanService.findNotReturnedUserLoans(user)).thenReturn(Arrays.asList(loan, loan2));

        // findReturnedUserLoans
        when(loanService.findReturnedUserLoans(user)).thenReturn(Arrays.asList(loan, loan2));

        // findAllReturned
        when(loanService.findAllReturned()).thenReturn(Arrays.asList(loan));

        // findAllNotReturned
        when(loanService.findAllNotReturned()).thenReturn(Arrays.asList(loan2));

    }

    @Test
    public void testCreate() {
        loanNewDTO = new LoanNewDTO(user.getId(), Arrays.asList(book.getId()), LocalDateTime.of(2016,10,10,10,10));
        loanFacade.create(loanNewDTO);

        verify(loanService).create(loanArgumentCaptor.capture());

        Loan entity = loanArgumentCaptor.getValue();

        assertNull(entity.getId());
        assertSame(user, entity.getUser());
        assertSame(bookCopy, entity.getBookCopy());
    }

    @Test
    public void testUpdate() {
        bookCopyDTO = new BookCopyDTO();
        bookCopyDTO.setId(1L);
        userDTO = new UserDTO();
        userDTO.setId(1L);

        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setId(loan.getId());
        loanDTO.setReturnBookState(BookState.HEAVY_DAMAGE);
        loanDTO.setBookCopy(bookCopyDTO);
        loanDTO.setLoanDate(LocalDateTime.of(2016,10,10,10,10));
        loanDTO.setUser(userDTO);
        loanDTO.setReturnDate(LocalDateTime.of(2016,10,10,10,10));

        loanFacade.update(loanDTO);

        verify(loanService).update(loanArgumentCaptor.capture());

        Loan entity = loanArgumentCaptor.getValue();
        assertEquals(entity.getId(), loan.getId());
        assertEquals(entity.getReturnBookState(), BookState.HEAVY_DAMAGE);
        assertEquals(entity.getLoanDate(), LocalDateTime.of(2016,10,10,10,10));
        assertEquals(entity.getReturnDate(), LocalDateTime.of(2016,10,10,10,10));
        assertEquals(entity.getUser().getId(), new Long(1));
        assertEquals(entity.getBookCopy().getId(), new Long(1));
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
    public void testFindAllNotReturned() {
        List<LoanDTO> loanDTOs = loanFacade.findAllNotReturned();
        assertNotNull(loanDTOs);
        assertEquals(loanDTOs.size(), 1);
        assertEqualsLoanAndLoanDTO(loan2, loanDTOs.get(0));
    }

    @Test
    public void testFindAllReturned() {
        List<LoanDTO> loanDTOs = loanFacade.findAllReturned();
        assertNotNull(loanDTOs);
        assertEquals(loanDTOs.size(), 1);
        assertEqualsLoanAndLoanDTO(loan, loanDTOs.get(0));
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

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testReturnLoan(){
        loanFacade.returnLoan(null);
    }



    private void assertEqualsLoanAndLoanDTO(Loan loan, LoanDTO dto) {
        assertEquals(loan.getUser().getId(), dto.getUser().getId());
        assertEquals(loan.getBookCopy().getId(), dto.getBookCopy().getId());
        assertEquals(loan.getLoanDate(), dto.getLoanDate());
        assertEquals(loan.getReturnBookState(), dto.getReturnBookState());
        assertEquals(loan.getReturnDate(), dto.getReturnDate());
    }

}
