package com.example.Licence.Management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Licence.Management.entity.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Integer> {

	Optional<Otp> findByOtpCodeAndEmail(String otpCode, String email);

	Optional<Otp> findByEmail(String email);
	
}
