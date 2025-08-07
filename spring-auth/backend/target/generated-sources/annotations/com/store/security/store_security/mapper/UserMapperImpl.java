package com.store.security.store_security.mapper;

import com.store.security.store_security.dto.UserDto;
import com.store.security.store_security.entity.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-07T10:36:43+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.authoritiesList( UserMapper.mapAuthoritiesToStrings( userEntity.getAuthoritiesList() ) );
        userDto.id( userEntity.getId() );
        userDto.username( userEntity.getUsername() );
        userDto.password( userEntity.getPassword() );
        userDto.age( userEntity.getAge() );
        userDto.tmstInsert( userEntity.getTmstInsert() );

        return userDto.build();
    }

    @Override
    public UserEntity toEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.id( userDto.getId() );
        userEntity.username( userDto.getUsername() );
        userEntity.password( userDto.getPassword() );
        userEntity.age( userDto.getAge() );
        userEntity.tmstInsert( userDto.getTmstInsert() );

        return userEntity.build();
    }
}
