package com.example.library.studentlibrary.controller;


import com.example.library.studentlibrary.models.Transaction;
import com.example.library.studentlibrary.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Add required annotations
@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction/issueBook")
    public ResponseEntity<String> issueBook(@RequestParam("cardId") int cardId, @RequestParam("bookId") int bookId) throws Exception{
        String id=transactionService.issueBook(cardId, bookId);
       return new ResponseEntity<>(id, HttpStatus.ACCEPTED);
    }

    @PostMapping("/transaction/returnBook")
    public ResponseEntity<Transaction> returnBook(@RequestParam("cardId") int cardId, @RequestParam("bookId") int bookId) throws Exception{
        Transaction transaction=transactionService.returnBook(cardId, bookId);
        return new ResponseEntity<Transaction>(transaction, HttpStatus.ACCEPTED);
    }
}
