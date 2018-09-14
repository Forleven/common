package com.forleven.common.fn;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import io.vavr.control.Either;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;

import com.forleven.common.exception.HttpException;
import com.forleven.common.web.Resources;

public class Lambda {

    public static final Supplier<ResponseEntity<HttpException>> TO_ACCEPTED = () -> ResponseEntity.accepted().build();

    private Lambda() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static <T extends List<?>> Optional<T> toOptional(T spec) {
        return Optional.of(spec).filter(promisedList -> !promisedList.isEmpty());
    }

    public static <T extends List<?>> Optional<T> toOptionalSpec(T spec) {
        return Optional.of(spec).filter(promisedList -> !promisedList.isEmpty());
    }

    public static <T extends Page<?>> Optional<T> toOptionalSpec(T spec) {
        return Optional.of(spec).filter(Slice::hasContent);
    }

    public static <T> ResponseEntity<T> toResponse(T body) {
        return ResponseEntity.ok(body);
    }

    public static <T> ResponseEntity toResponse(Either<HttpException, T> body) {
        return body.fold(
                Lambda::errorToResponse,
                Lambda::toResponse
        );
    }

    public static <T> ResponseEntity<Resources<T>> toResponse(Page<T> body) {
        return ResponseEntity.ok(new Resources<>(body));
    }

    /**
     * Here we throw your HttpException.
     */
    public static <T extends HttpException> ResponseEntity<T> errorToResponse(T e) {
        throw e;
    }
}