package jpabook.jpashop.domain.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie extends Item{

    private String director;
    private String actor;

    private Movie(Long id, String director, String actor, String name, int price, int stockQuantity ) {
        super(id, name, price, stockQuantity);
        this.director = director;
        this.actor = actor;
    }
}
