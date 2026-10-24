package com.ecommerce.main.cart.service.impl;

import com.ecommerce.main.book.entity.Book;
import com.ecommerce.main.book.repository.BookRepository;
import com.ecommerce.main.cart.dto.AddCartItemRequest;
import com.ecommerce.main.cart.dto.CartResponse;
import com.ecommerce.main.cart.entity.Cart;
import com.ecommerce.main.cart.mapper.CartMapper;
import com.ecommerce.main.cart.repositroy.CartRepositroy;
import com.ecommerce.main.cart.service.CartService;
import com.ecommerce.main.cartItems.CartItems;
import com.ecommerce.main.common.exception.BusinessException;
import com.ecommerce.main.common.exception.ResourceNotFoundException;
import com.ecommerce.main.user.repository.UserRepository;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CartServiceImpl implements CartService {
    private final UserRepository userRepository;
    private final CartRepositroy cartRepositroy;
    private final BookRepository bookRepository;
    private final CartMapper cartMapper;

    @Override
    public CartResponse addItem(Integer id, AddCartItemRequest request) {
        Cart cart = cartRepositroy.findByUserId(id)
                .orElseGet(() -> createCard(id));
        Book findBook = bookRepository.findById(request.bookId()).orElseThrow(() ->
                new ResourceNotFoundException("کتاب موجود نیست"));
        CartItems cartItems = cart.getItems().stream().filter(b -> b.getBook().getId().equals(request.bookId())).findFirst().orElse(null);
        int quantity = cartItems == null ? request.quantity() : cartItems.getQuantity() + request.quantity();

        if (quantity > findBook.getStock()) {
            throw new BusinessException(
                    "موجودی کافی نیست. موجودی: " + findBook.getStock()
                            + " ، درخواستی: " + quantity
            );
        }
        if (cartItems == null) {
            cartItems = CartItems.builder().cart(cart).quantity(quantity).book(findBook).build();
            cart.getItems().add(cartItems);

        }

        cartItems.setQuantity(quantity);
        cartRepositroy.save(cart);
        return cartMapper.toCartResponse(cart);
    }

    @Override
    public CartResponse removeItem(Integer id, Long bookId) {
        Cart cart = cartRepositroy.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        boolean removed = cart.getItems()
                .removeIf(item -> item.getBook().getId().equals(bookId));

        if (!removed) {
            throw new ResourceNotFoundException("Book is not in the cart");
        }

        return cartMapper.toCartResponse(cart);}

    @Override
    public CartResponse clearCart(Integer id) {
        Cart cart = cartRepositroy.findByUserId(id).orElse(null);

        if (cart == null) {
            return cartMapper.empty();          // no cart yet, so there is nothing to clear
        }

        cart.getItems().clear();
        return cartMapper.toCartResponse(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCart(Integer id) {
        cartRepositroy.findByUserId(id).orElse(null);
        return cartMapper.empty();
    }

    //CREATE NEW CARD FOR USER IF HE IT IS HS HIS FIRST TIME TO BUT SOMETINHG///
    private Cart createCard(Integer id) {
        Cart cart = new Cart();
        cart.setUser(userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("کاربر یافت نشد")));
        return cartRepositroy.save(cart);


    }
}
