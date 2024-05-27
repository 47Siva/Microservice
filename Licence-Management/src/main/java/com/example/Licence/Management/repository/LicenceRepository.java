package com.example.Licence.Management.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Licence.Management.entity.Licence;

@Repository
public interface LicenceRepository extends JpaRepository<Licence, UUID> {

	Optional<Licence> findByLicenceKey(String licenceKey);

	@Query(value = "select * from licence where mail_id = email")
	Optional<Licence> findByMailId(String email);
}
