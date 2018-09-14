package com.forleven.common.specification;

import org.springframework.data.jpa.domain.Specification;

public class GeneralSpecification {

    private GeneralSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> Specification<T> hasStatusActive() {
        return (root, query, builder) ->
                builder.equal(root.get("status"), true);
    }
}
