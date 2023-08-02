package com.anervea.company.repository;

import com.anervea.company.model.AnvrCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnvrCompanyRepository extends JpaRepository<AnvrCompany,Long> {
    @Query(value="select m.* from anvr_company_accesses as a inner join anvr_company as m on " +
            " a.company_id=m.company_id where lower(a.username)=:username and m.is_deleted=:b", nativeQuery=true)
    AnvrCompany getByCorporateByUsername(String username, boolean b);

    boolean existsByMobileNumberAndIsDeletedAndIsActive(String mobileNumber, boolean b, boolean b1);
}
