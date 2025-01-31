package com.vidcentral.api.domain.viewHistory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.viewHistory.entity.ViewHistory;

@Repository
public interface ViewHistoryRepository extends JpaRepository<ViewHistory, Long> {

	List<ViewHistory> findViewHistoriesByMember(Member member);

	List<ViewHistory> findAllByMember(Member member);
}
