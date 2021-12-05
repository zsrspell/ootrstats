package com.ootrstats.ootrstats.speedrunner.specifications;

import com.ootrstats.ootrstats.speedrunner.Speedrunner;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class SpeedrunnerNameSpecification implements Specification<Speedrunner> {
    private final String name;

    public SpeedrunnerNameSpecification(String name) {
        this.name = name;
    }

    @Override
    public Predicate toPredicate(Root<Speedrunner> root, @NonNull CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.like(builder.upper(root.get("name")), "%" + name.toUpperCase() + "%");
    }
}
