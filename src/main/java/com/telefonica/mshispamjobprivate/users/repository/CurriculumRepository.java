package com.telefonica.mshispamjobprivate.users.repository;

import com.telefonica.mshispamjobprivate.users.entity.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
}
