package com.store.security.store_security.mapper;

import com.store.security.store_security.dto.OrderDto;
import com.store.security.store_security.entity.OrderEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-07T10:36:43+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public OrderEntity toEntity(OrderDto orderDto) {
        if ( orderDto == null ) {
            return null;
        }

        OrderEntity.OrderEntityBuilder orderEntity = OrderEntity.builder();

        orderEntity.id( orderDto.getId() );
        orderEntity.user( userMapper.toEntity( orderDto.getUser() ) );
        orderEntity.tmstInsert( orderDto.getTmstInsert() );

        return orderEntity.build();
    }

    @Override
    public OrderDto toDto(OrderEntity orderEntity) {
        if ( orderEntity == null ) {
            return null;
        }

        OrderDto.OrderDtoBuilder orderDto = OrderDto.builder();

        orderDto.id( orderEntity.getId() );
        orderDto.user( userMapper.toDto( orderEntity.getUser() ) );
        orderDto.tmstInsert( orderEntity.getTmstInsert() );

        return orderDto.build();
    }
}
