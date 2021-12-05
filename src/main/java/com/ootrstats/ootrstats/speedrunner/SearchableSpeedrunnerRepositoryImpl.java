package com.ootrstats.ootrstats.speedrunner;

import com.ootrstats.ootrstats.race.Entrant_;
import com.ootrstats.ootrstats.race.Race_;
import com.ootrstats.ootrstats.speedrunner.projections.SpeedrunnerInfo;
import com.ootrstats.ootrstats.speedrunner.specifications.SpeedrunnerNameSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

@Repository
public class SearchableSpeedrunnerRepositoryImpl implements SearchableSpeedrunnerRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<SpeedrunnerInfo> findAll(SpeedrunnerSearchCriteria criteria, Pageable pageable) {
        // Initialize our query builder, query and root.
        CriteriaBuilder cb = em.getCriteriaBuilder();
        var query = cb.createQuery(SpeedrunnerInfo.class);
        var root = query.from(Speedrunner.class);

        // JOIN entities.
        var entries = root.join(Speedrunner_.entries);
        var race = entries.join(Entrant_.race);

        // SELECT parameters and project onto SpeedrunnerInfo
        query.select(cb.construct(
                SpeedrunnerInfo.class,
                root.get(Speedrunner_.name),
                root.get(Speedrunner_.countryCode),
                cb.count(race),
                cb.max(race.get(Race_.STARTED_AT))
        ));

        // ORDER BY, GROUP BY and WHERE.
        var predicate = new SpeedrunnerNameSpecification(criteria.getName()).toPredicate(root, query, cb);
        query.where(predicate);
        query.orderBy(QueryUtils.toOrders(pageable.getSort(), root, cb));
        query.groupBy(root.get(Speedrunner_.name), root.get(Speedrunner_.countryCode));

        // COUNT query for pagination only.
        var countQuery = cb.createQuery(Long.class);
        var countRoot = countQuery.from(Speedrunner.class);
        countQuery.select(cb.count(countRoot));
        countQuery.where(new SpeedrunnerNameSpecification(criteria.getName()).toPredicate(countRoot, countQuery, cb));
        var count = em.createQuery(countQuery).getSingleResult();

        // Set pagination parameters.
        var q = em.createQuery(query);
        q.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        q.setMaxResults(pageable.getPageSize());
        return new PageImpl<>(q.getResultList(), pageable, count);
    }
}
