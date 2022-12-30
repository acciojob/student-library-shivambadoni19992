package com.example.library.studentlibrary.services;

import com.example.library.studentlibrary.models.*;
import com.example.library.studentlibrary.repositories.BookRepository;
import com.example.library.studentlibrary.repositories.CardRepository;
import com.example.library.studentlibrary.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TransactionService {

    @Autowired
    BookRepository bookRepository5;

    @Autowired
    CardRepository cardRepository5;

    @Autowired
    TransactionRepository transactionRepository5;

    @Value("${books.max_allowed}")
    int max_allowed_books;

    @Value("${books.max_allowed_days}")
    int getMax_allowed_days;

    @Value("${books.fine.per_day}")
    int fine_per_day;

    public String issueBook(int cardId, int bookId) throws Exception {
        // check whether bookId and cardId already exist
        // conditions required for successful transaction of issue book:
        // 1. book is present and available
        // If it fails: throw new Exception("Book is either unavailable or not
        // present");
        // 2. card is present and activated
        // If it fails: throw new Exception("Card is invalid");
        // 3. number of books issued against the card is strictly less than
        // max_allowed_books
        // If it fails: throw new Exception("Book limit has reached for this card");
        // If the transaction is successful, save the transaction to the list of
        // transactions and return the id

        // Note that the error message should match exactly in all cases
        Book temp = bookRepository5.findById(bookId).get();
        Card card = cardRepository5.findById(cardId).get();
        CardStatus status = CardStatus.valueOf("ACCEPTED");
        List<Book> book = card.getBooks();
        String id ;
        if (bookRepository5.findById(bookId) == null || temp.isAvailable() == false) {
            throw new Exception("Book is either unavailable or not present");
        } else if (cardRepository5.findById(cardId) == null || card.getCardStatus().equals(status)) {
            throw new Exception("Card is invalid");
        } else if (book.size() >= 3) {
            throw new Exception("Book limit has reached for this card");
        } else {
            Transaction t = new Transaction();
            t.setBook(temp);
            t.setCard(card);
            Transaction transaction = transactionRepository5.save(t);
            id=transaction.getTransactionId();
        }

        return id; // return transactionId instead
    }

    public Transaction returnBook(int cardId, int bookId) throws Exception {

        List<Transaction> transactions = transactionRepository5.find(cardId, bookId, TransactionStatus.SUCCESSFUL,
                true);
        Transaction transaction = transactions.get(transactions.size() - 1);
        
        transaction.setFineAmount(100);
        Book b=bookRepository5.findById(bookId).get();
        b.setAvailable(true);
        Card card=cardRepository5.findById(cardId).get();
        


        // for the given transaction calculate the fine amount considering the book has
        // been returned exactly when this function is called
        // make the book available for other users
        // make a new transaction for return book which contains the fine amount as well

        Transaction returnBookTransaction = new Transaction();
        returnBookTransaction.setBook(b);
        returnBookTransaction.setFineAmount(100);
        returnBookTransaction.setCard(card);
        return returnBookTransaction; // return the transaction after updating all details
    }
}