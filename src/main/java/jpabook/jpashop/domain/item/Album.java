package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Entity
@DiscriminatorValue("A")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Album extends Item{

    private String artist;
    private String etc;


    @Builder
    private Album(Long id, String artist, String etc, String name, int price, int stockQuantity ) {
        super(id, name, price, stockQuantity);
        this.artist = artist;
        this.etc = etc;
    }
}
