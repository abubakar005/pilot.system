package io.pilot.system.repository;

import io.pilot.system.model.TedTalk;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TedTalkRepository extends JpaRepository<TedTalk, Long>, JpaSpecificationExecutor<TedTalk> {

    List<TedTalk> findByTitleOrLink(String title, String link);
    List<TedTalk> findAll(Specification<TedTalk> spec);
}
