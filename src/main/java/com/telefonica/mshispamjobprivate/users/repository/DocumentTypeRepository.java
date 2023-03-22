package com.telefonica.mshispamjobprivate.users.repository;

import com.telefonica.mshispamjobprivate.users.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Integer> {
}
