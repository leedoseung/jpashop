package jpabook.jpashop;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.DeliveryStatus;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        public void dbInit() {
            Member member = createMember("userA", "서울", "1", "11111");

            em.persist(member);

            Book book1 = createBook("JPA1 Book", 10000, 20);

            Book book2 = createBook("JPA2 Book", 20000, 30);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 =OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(member);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Delivery createDelivery(Member member) {
            return Delivery.builder()
                    .address(member.getAddress())
                    .status(DeliveryStatus.READY)
                    .build();
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book1 = Book.builder()
                    .name(name)
                    .price(price)
                    .stockQuantity(stockQuantity)
                    .build();
            em.persist(book1);
            return book1;
        }

        private Member createMember(String name, String city, String zipcode, String street) {
            return Member.builder()
                    .name(name)
                    .address(new Address(city, zipcode, street))
                    .build();
        }

        public void dbInit2() {
            Member member = createMember("userB", "부산", "2", "22222");

            em.persist(member);

            Book book1 = createBook("Spring1 Book", 10000, 100);

            Book book2 = createBook("Spring2 Book", 20000, 50);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 =OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(member);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }



    }

}


