package com.telefonica.mshispamjobprivate.users.repository;

import com.telefonica.mshispamjobprivate.users.entity.SalaryCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryCurrencyRepository extends JpaRepository<SalaryCurrency, Integer> {

}
