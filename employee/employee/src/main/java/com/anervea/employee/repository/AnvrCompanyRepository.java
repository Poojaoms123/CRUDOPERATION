package com.anervea.employee.repository;


import com.anervea.employee.model.AnvrCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface AnvrCompanyRepository extends JpaRepository<AnvrCompany,Long> {
    @Query(value="select secret_key from anvr_company where company_id=:companyId ", nativeQuery=true)
    String getCorporateSecretKey(Long companyId);

    @Modifying
    @Transactional
    @Query(value ="update anvr_employees set is_logged_in='true' where id=:id", nativeQuery = true)
    void updateIsLoggedIn(Long id);
}
