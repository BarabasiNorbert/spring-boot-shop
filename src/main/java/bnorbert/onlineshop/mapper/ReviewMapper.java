package bnorbert.onlineshop.mapper;

import bnorbert.onlineshop.domain.Product;
import bnorbert.onlineshop.domain.Review;
import bnorbert.onlineshop.domain.User;
import bnorbert.onlineshop.transfer.review.ReviewDto;
import bnorbert.onlineshop.transfer.review.ReviewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ReviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "intent", source = "reviewDto.intent")
    @Mapping(target = "content", source = "reviewDto.content")
    @Mapping(target = "rating", source = "reviewDto.rating")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "user", source = "user")
    public abstract Review map(ReviewDto reviewDto, Product product, User user);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "intent", source = "review.intent")
    @Mapping(target = "content", source = "review.content")
    @Mapping(target = "rating", source = "review.rating")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "createdDate", source = "review.createdDate")
    public abstract ReviewResponse mapToDto(Review review);

    public abstract List<ReviewResponse> entitiesToEntityDTOs(List<Review> reviews);
}
