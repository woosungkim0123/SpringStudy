package book.shop.domain.item;

import book.shop.service.UpdateItemDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter @Setter
@DiscriminatorValue("B")
public class Book extends Item {
    private String author;
    private String isbn;

    @Override
    public void changeItem(UpdateItemDto updateItemDto) {
        super.changeItem(updateItemDto);
        this.author = updateItemDto.getAuthor();
        this.isbn = updateItemDto.getIsbn();
    }
}
