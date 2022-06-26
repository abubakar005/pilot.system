package io.pilot.system.specification;

import io.pilot.system.dto.TedTalkSearchCriteriaDto;
import io.pilot.system.model.TedTalk;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class TedTalkSpecification {

    public Specification<TedTalk> getTedTalks(final TedTalkSearchCriteriaDto searchCriteria) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (searchCriteria.getAuthor() != null && !searchCriteria.getAuthor().isEmpty())
                predicates.add(criteriaBuilder.equal(root.get("author"), searchCriteria.getAuthor()));

            if (searchCriteria.getTitle() != null && !searchCriteria.getTitle().isEmpty())
                predicates.add(criteriaBuilder.equal(root.get("title"), searchCriteria.getTitle()));

            if (searchCriteria.getViews() != null && searchCriteria.getViews() != 0)
                predicates.add(criteriaBuilder.equal(root.get("views"), searchCriteria.getViews()));

            if (searchCriteria.getLikes() != null && searchCriteria.getLikes() != 0)
                predicates.add(criteriaBuilder.equal(root.get("likes"), searchCriteria.getLikes()));

            query.orderBy(criteriaBuilder.asc(root.get("id")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
