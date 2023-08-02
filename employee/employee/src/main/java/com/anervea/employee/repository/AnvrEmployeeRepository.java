package com.anervea.employee.repository;


import com.anervea.employee.model.AnvrEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;


public interface AnvrEmployeeRepository extends JpaRepository<AnvrEmployee,Long> {

    AnvrEmployee findByEmailIdIgnoreCaseAndIsDeletedAndIsActive(String s, boolean b, boolean b1);

    AnvrEmployee findByMobileNumber(String s);

    boolean existsByEmailIdIgnoreCaseAndIsDeletedAndIsActive(String username, boolean b, boolean b1);

    @Query(value = "select concat(ce.first_name,' ',ce.last_name) as employeeName from anvr_employees as ce  where email_id=:username and is_deleted='false' and is_active='true' ", nativeQuery = true)
    String getEmployeeNameByUserName(String username);

    @Modifying
    @Transactional
    @Query(value ="update anvr_employees set fcm_token=:fcmToken where id=:employeeId", nativeQuery = true)
    void updateUserFcmToken(Long employeeId, String fcmToken);

    boolean existsByMobileNumberAndIsDeletedAndIsActive(String mobileNumber, boolean b, boolean b1);

    @Modifying
    @Transactional
    @Query(value ="update anvr_employees set fcm_token=null where id=:employeeId", nativeQuery = true)
    void setDoctorFcmToken(Long employeeId);
}
