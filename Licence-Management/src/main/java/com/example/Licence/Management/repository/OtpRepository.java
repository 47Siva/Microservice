package com.example.Licence.Management.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Licence.Management.entity.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Integer> {

	@Query(value = "select * from user where otp_code =:otpCode && email =:email")
	Optional<Otp> findByOtpCodeAndEmail(String otpCode, String email);

	@Query(value = "select * from user where email =:email")
	Optional<Otp> findByEmail(String email);
	
}
