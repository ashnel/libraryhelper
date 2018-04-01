package com.ashley.booksystem.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ashley.booksystem.models.Book;
import com.ashley.booksystem.models.BookInCheckOutHistory;
import com.ashley.booksystem.models.CheckedOutBook;
import com.ashley.booksystem.models.User;
import com.ashley.booksystem.services.BookService;
import com.ashley.booksystem.services.CheckedBookService;
import com.ashley.booksystem.services.PrevBookService;
import com.ashley.booksystem.services.UserService;
import com.ashley.booksystem.validator.UserValidator;

@Controller
public class UserController {
	private UserService userService;
	private BookService bookService;
	private CheckedBookService checkedBookService;
	private PrevBookService prevBookService;
	private UserValidator userValidator;
    
    public UserController (UserService userService, BookService bookService, CheckedBookService checkedBookService, PrevBookService prevBookService, UserValidator userValidator) {
        this.userService = userService;
        this.bookService = bookService;
        this.checkedBookService = checkedBookService;
        this.prevBookService = prevBookService;
        this.userValidator = userValidator;
    }
    
	@RequestMapping("/login")
    public String loginOrRegister (@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model, @ModelAttribute("user") User user) {
		if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        return "loginOrRegister.jsp";
    }
	
	@RequestMapping("/logo")
    public String logout (Principal principal, Model model, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("logoutMessage", "Logged out.");
        return "redirect:/login";
    }
	
	@PostMapping("/registration")
	public String registerUser (@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "loginOrRegister.jsp";
        }
        userService.saveWithUserRole(user);
        return "redirect:/";
    }
	
	@PostMapping("/checkout/{id}")
	public String checkOutBook (@Valid @ModelAttribute("checkedoutbook") CheckedOutBook checkedoutbook, BindingResult result, Model model, @PathVariable("id") Long id) {
		Book book = bookService.findBookById(id);
		book.setNumberOfBooksAvailable(book.getNumberOfBooksAvailable() - 1);
		bookService.updatedBook(book);
		checkedBookService.saveCheckedOutBook(checkedoutbook);
		return "redirect:/";
	}
	
	@PostMapping("/checkin/{id}")
	public String checkInBook (@Valid @ModelAttribute("bookincheckouthistory") BookInCheckOutHistory bookincheckouthistory, BindingResult result, Model model, @PathVariable("id") Long id, Principal principal) {
//		model.addAttribute("bookincheckouthistory", new BookInCheckOutHistory());
		String username = principal.getName();
        User user = userService.findByUsername(username);
		
		Book book = bookService.findBookById(id);
		book.setNumberOfBooksAvailable(book.getNumberOfBooksAvailable() + 1);
		bookService.updatedBook(book);
		prevBookService.saveCheckedOutBook(bookincheckouthistory);
		checkedBookService.removeFromUsersCheckedOutBooks(book.getId(), user.getId());
		return "redirect:/";
	}
	
	@RequestMapping("/search")
	public String search (@Valid @ModelAttribute("checkedoutbook") CheckedOutBook checkedoutbook, BindingResult result, Model model, @RequestParam("searchtext") String searchtext, Principal principal) {
		if (searchtext != null) {
			List<Book> foundBooks = bookService.searchByTextInput(searchtext);
			model.addAttribute("foundBooks", foundBooks);
		}
		String username = principal.getName();
		User user = userService.findByUsername(username);
        model.addAttribute("currentUser", userService.findByUsername(username));
        
        List<CheckedOutBook> checkedBooks = checkedBookService.findByUserId(user.getId());
        model.addAttribute("checkedBooks", checkedBooks);
		
		return "searchResults.jsp";
	}
	
	@RequestMapping("/account")
	public String accountOverview(Model model, Principal principal) {
		String username = principal.getName();
        User user = userService.findByUsername(username);
        
        model.addAttribute("currentUser", userService.findByUsername(username));
        List<CheckedOutBook> checkedBooks = checkedBookService.findByUserId(user.getId());
        model.addAttribute("checkedBooks", checkedBooks);
		
		return "accountOverview.jsp";
	}
	
	@RequestMapping("/admin")
    public String adminPage(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("currentUser", userService.findByUsername(username));
        return "adminPage.jsp";
    }
	
	@RequestMapping(value = {"/", "/dashboard"})
    public String home(@Valid @ModelAttribute("checkedoutbook") CheckedOutBook checkedoutbook, @Valid @ModelAttribute("bookincheckouthistory") BookInCheckOutHistory bookincheckouthistory, BindingResult result, Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        
        model.addAttribute("currentUser", userService.findByUsername(username));
        
        List<Book> books = bookService.findAllBooks();
        model.addAttribute("books", books);
        
        List<CheckedOutBook> checkedBooks = checkedBookService.findByUserId(user.getId());
        model.addAttribute("checkedBooks", checkedBooks);
        
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        Calendar checkOutDate = Calendar.getInstance();
        checkOutDate.setTime(new Date());
        model.addAttribute("checkOutDate", sdf.format(checkOutDate.getTime()));
        checkOutDate.add(Calendar.DATE, 7);
        model.addAttribute("returnDate", sdf.format(checkOutDate.getTime()));

        return "dashboard.jsp";
    }
}
