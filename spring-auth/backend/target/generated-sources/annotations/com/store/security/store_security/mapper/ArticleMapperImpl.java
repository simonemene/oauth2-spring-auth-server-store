package com.store.security.store_security.mapper;

import com.store.security.store_security.dto.ArticleDto;
import com.store.security.store_security.entity.ArticleEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-07T10:36:43+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class ArticleMapperImpl implements ArticleMapper {

    @Override
    public ArticleEntity toEntity(ArticleDto articleDto) {
        if ( articleDto == null ) {
            return null;
        }

        ArticleEntity.ArticleEntityBuilder articleEntity = ArticleEntity.builder();

        articleEntity.id( articleDto.getId() );
        articleEntity.name( articleDto.getName() );
        articleEntity.description( articleDto.getDescription() );
        articleEntity.price( articleDto.getPrice() );
        articleEntity.tmstInsert( articleDto.getTmstInsert() );

        return articleEntity.build();
    }

    @Override
    public ArticleDto toDto(ArticleEntity articleEntity) {
        if ( articleEntity == null ) {
            return null;
        }

        ArticleDto.ArticleDtoBuilder articleDto = ArticleDto.builder();

        articleDto.id( articleEntity.getId() );
        articleDto.name( articleEntity.getName() );
        articleDto.description( articleEntity.getDescription() );
        articleDto.price( articleEntity.getPrice() );
        articleDto.tmstInsert( articleEntity.getTmstInsert() );

        return articleDto.build();
    }
}
