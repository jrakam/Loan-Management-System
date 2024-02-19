package com.lms.repository;


  import com.lms.entity.LmsCustomer;
  import org.springframework.data.jpa.repository.JpaRepository;

  import java.util.Optional;

public interface LmsCustomerRepository extends JpaRepository<LmsCustomer, Long> {
  Optional<LmsCustomer> findByUserName(String userName);
}
