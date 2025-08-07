package com.store.security.store_security.mapper;

import com.store.security.store_security.dto.StockArticleDto;
import com.store.security.store_security.dto.StockDto;
import com.store.security.store_security.entity.StockArticleEntity;
import com.store.security.store_security.entity.StockEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-07T10:36:43+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class StockMapperImpl implements StockMapper {

    @Autowired
    private StockArticleMapper stockArticleMapper;

    @Override
    public StockEntity toEntity(StockDto stockDto) {
        if ( stockDto == null ) {
            return null;
        }

        StockEntity.StockEntityBuilder stockEntity = StockEntity.builder();

        stockEntity.id( stockDto.getId() );
        stockEntity.stockArticles( stockArticleDtoListToStockArticleEntityList( stockDto.getStockArticles() ) );

        return stockEntity.build();
    }

    @Override
    public StockDto toDto(StockEntity stockEntity) {
        if ( stockEntity == null ) {
            return null;
        }

        StockDto.StockDtoBuilder stockDto = StockDto.builder();

        stockDto.id( stockEntity.getId() );
        stockDto.stockArticles( stockArticleEntityListToStockArticleDtoList( stockEntity.getStockArticles() ) );

        return stockDto.build();
    }

    protected List<StockArticleEntity> stockArticleDtoListToStockArticleEntityList(List<StockArticleDto> list) {
        if ( list == null ) {
            return null;
        }

        List<StockArticleEntity> list1 = new ArrayList<StockArticleEntity>( list.size() );
        for ( StockArticleDto stockArticleDto : list ) {
            list1.add( stockArticleMapper.toEntity( stockArticleDto ) );
        }

        return list1;
    }

    protected List<StockArticleDto> stockArticleEntityListToStockArticleDtoList(List<StockArticleEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<StockArticleDto> list1 = new ArrayList<StockArticleDto>( list.size() );
        for ( StockArticleEntity stockArticleEntity : list ) {
            list1.add( stockArticleMapper.toDto( stockArticleEntity ) );
        }

        return list1;
    }
}
