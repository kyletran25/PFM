package com.finace.management.mapper;

import com.finace.management.dto.response.ItemResDto;
import com.finace.management.entity.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemResDto toItemResDto(Item item);
}
