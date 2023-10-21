package ru.katok.tamctf.domain.util;

import lombok.experimental.UtilityClass;
import ru.katok.tamctf.domain.dto.UserDto;
import ru.katok.tamctf.domain.entity.UserEntity;

@UtilityClass
public class MappingUtil {

    public static UserDto mapToUserDto(UserEntity user) {
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .team(user.getTeam())
                .build();
    }

//    public static Product mapToProductFromRequest(CreateProductRequest request) {
//        return Product.builder()
//                .name(request.getName())
//                .count(request.getCount())
//                .price(request.getPrice())
//                .build();
//    }
}