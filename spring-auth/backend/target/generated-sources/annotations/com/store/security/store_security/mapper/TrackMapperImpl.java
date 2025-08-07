package com.store.security.store_security.mapper;

import com.store.security.store_security.dto.TrackDto;
import com.store.security.store_security.entity.TrackEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-07T10:36:43+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class TrackMapperImpl implements TrackMapper {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public TrackDto toDto(TrackEntity trackEntity) {
        if ( trackEntity == null ) {
            return null;
        }

        TrackDto.TrackDtoBuilder trackDto = TrackDto.builder();

        trackDto.id( trackEntity.getId() );
        trackDto.order( orderMapper.toDto( trackEntity.getOrder() ) );
        trackDto.status( trackEntity.getStatus() );

        return trackDto.build();
    }

    @Override
    public TrackEntity toEntity(TrackDto trackDto) {
        if ( trackDto == null ) {
            return null;
        }

        TrackEntity.TrackEntityBuilder trackEntity = TrackEntity.builder();

        trackEntity.id( trackDto.getId() );
        trackEntity.order( orderMapper.toEntity( trackDto.getOrder() ) );
        trackEntity.status( trackDto.getStatus() );

        return trackEntity.build();
    }
}
