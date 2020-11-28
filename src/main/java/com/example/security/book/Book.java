package com.example.security.book;

import com.example.security.account.Account;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Book {

    @Id @GeneratedValue
    private Integer id;

    private String title;

    @ManyToOne
    private Account author;

}
