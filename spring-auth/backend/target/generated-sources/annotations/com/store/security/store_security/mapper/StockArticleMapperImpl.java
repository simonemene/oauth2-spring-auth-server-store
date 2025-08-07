package com.store.security.store_security.mapper;

import com.store.security.store_security.dto.StockArticleDto;
import com.store.security.store_security.entity.StockArticleEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-07T10:36:43+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class StockArticleMapperImpl implements StockArticleMapper {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public StockArticleEntity toEntity(StockArticleDto stockDto) {
        if ( stockDto == null ) {
            return null;
        }

        StockArticleEntity.StockArticleEntityBuilder stockArticleEntity = StockArticleEntity.builder();

        stockArticleEntity.id( stockDto.getId() );
        stockArticleEntity.article( articleMapper.toEntity( stockDto.getArticle() ) );
        if ( stockDto.getQuantity() != null ) {
            stockArticleEntity.quantity( stockDto.getQuantity() );
        }

        return stockArticleEntity.build();
    }

    @Override
    public StockArticleDto toDto(StockArticleEntity stockEntity) {
        if ( stockEntity == null ) {
            return null;
        }

        StockArticleDto.StockArticleDtoBuilder stockArticleDto = StockArticleDto.builder();

        stockArticleDto.id( stockEntity.getId() );
        stockArticleDto.article( articleMapper.toDto( stockEntity.getArticle() ) );
        stockArticleDto.quantity( stockEntity.getQuantity() );

        return stockArticleDto.build();
    }
}
