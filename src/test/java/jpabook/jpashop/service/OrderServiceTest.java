package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    
    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();

        Book book = createBook("시골 JPA", 10000, 10);

        int orderCount =2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //then

        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getOrderStatus());
        assertEquals("주문한 상품 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        assertEquals("주문한 가격은 가격 * 수량이다.", 20000, getOrder.getTotalPrice());
        assertEquals("상품 주문시 재고는", 8, book.getStockQuantity());


    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();

        Book book = createBook("시골 JPA", 10000, 10);

        int orderCount =12;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //then
        fail("재고 수향 부족 예외가 발생해야 한다!!!");

    }
    
    @Test
    public void 주문취소 () throws Exception {
        //given
        Member member = createMember();

        Book book = createBook("aws 배우기",20000,6);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        Order getOrder = orderRepository.findOne(orderId);


        //then
        assertEquals("주문 취소시 상태는 CANCEL 이다", OrderStatus.CANCEL, getOrder.getOrderStatus());
        assertEquals("상품 취소 시 재고수량이 증가한다", 6, book.getStockQuantity());
    
    }



    private Book createBook(String name, int price, int stockQuantity) {
        Book book = Book.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();

        em.persist(book);

        return book;
    }

    private Member createMember() {

        Member member = Member.builder()
                .name("회원1")
                .address(new Address("서울", "강남", "123-123"))
                .build();

        em.persist(member);

        return member;
    }

}