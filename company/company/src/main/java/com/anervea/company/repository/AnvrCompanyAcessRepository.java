package com.anervea.company.repository;

import com.anervea.company.model.AnvrCompanyAcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface AnvrCompanyAcessRepository extends JpaRepository<AnvrCompanyAcess,Long> {
    AnvrCompanyAcess findByUserName(String userName);

    AnvrCompanyAcess findByUserNameIgnoreCase(String email);

    @Query(value="select a.* from anvr_company_accesses as a inner join anvr_company as m on " +
            " a.company_id=m.company_id where lower(a.username)=:username and m.is_deleted=:b", nativeQuery=true)
    AnvrCompanyAcess getByCorporateByUsername(String username, boolean b);

    boolean existsByCompanyId(Long companyId);

    AnvrCompanyAcess findByCompanyId(Long companyId);


    boolean existsByUserNameIgnoreCase(String userName);

    @Query(value = "select cc.company_name from anvr_company as cc inner join anvr_company_accesses as ca on\n" +
            "cc.company_id=ca.company_id where lower(ca.username)=:userName", nativeQuery = true)
    String getCorporateNameByUserName(String userName);
}
