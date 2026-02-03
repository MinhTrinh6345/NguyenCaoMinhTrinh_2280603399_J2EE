package com.example.Bai2.controller;

import com.example.Bai2.model.Book;
import com.example.Bai2.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // Lấy danh sách tất cả sách
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // Lấy sách theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Book book = bookService.getBookById(id);
        return book != null ? 
               ResponseEntity.ok(book) : 
               ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Thêm sách mới
    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body("Book added successfully!");
    }

    // Cập nhật sách
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(
            @PathVariable int id, 
            @RequestBody Book updatedBook) {
        bookService.updateBook(id, updatedBook);
        return ResponseEntity.ok("Book updated successfully!");
    }

    // Xóa sách
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully!");
    }
}