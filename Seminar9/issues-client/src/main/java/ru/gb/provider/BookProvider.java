package ru.gb.provider;

import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookProvider {
    private final WebClient webClient;

    public BookProvider(ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction) {
        webClient = WebClient.builder()
                .filter(loadBalancerExchangeFilterFunction)
                .build();
    }

//    public List<Book> getAllBooks() {
//        List<Book> bookList = webClient.get()
//                .uri("http://books-client/books")
//                .retrieve()
//                .bodyToFlux(Book.class).collectList().block();
//
//        return bookList;
//    }
    public Optional<Book> getBookById(Long id) {
        Book book = webClient.get()
                .uri("http://books-client/books/{id}", id)
                .retrieve()
                .bodyToMono(Book.class)
                .block();

        return Optional.ofNullable(book);
    }
}
