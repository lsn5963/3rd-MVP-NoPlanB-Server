package com.noplanb.domain.item_image.domain.repository;

import com.noplanb.domain.item.domain.Item;
import com.noplanb.domain.item_image.domain.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemImageRepository extends JpaRepository<ItemImage,Long> {
    String findItemImageByItem(Item item);
}
