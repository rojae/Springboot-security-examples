package com.example.security.common;


import com.example.security.book.Book;
import com.example.security.book.BookRepository;
import com.example.security.account.Account;
import com.example.security.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DefaultDataGenerator implements ApplicationRunner {

    @Autowired
    AccountService accountService;

    @Autowired
    BookRepository bookRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // rojae - spring
        // jae - hibernate

        Account rojae = createUser("rojae");
        Account jae = createUser("jae");

        createBook("spring", rojae);
        createBook("hibernate", jae);
    }

    private void createBook(String title, Account rojae){
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(rojae);
        bookRepository.save(book);
    }

    public Account createUser(String userName){
        Account account = new Account();
        account.setUsername(userName);
        account.setPassword("123");
        account.setRole("USER");
        return accountService.createNew(account);
    }
}
