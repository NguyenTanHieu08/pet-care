package com.hieusrpingboot.petcare.repository;

import com.hieusrpingboot.petcare.entity.Feedback;
import com.hieusrpingboot.petcare.enums.FeedbackTargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByUserId(Long userId);
    List<Feedback> findByTargetTypeAndTargetId(FeedbackTargetType targetType, Long targetId);
    List<Feedback> findByRatingGreaterThanEqual(Integer minRating);
    List<Feedback> findByCreatedAtAfter(LocalDateTime after);
    List<Feedback> findByTargetType(FeedbackTargetType targetType);
}
