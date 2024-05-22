package com.example.Licence.Management.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.Licence.Management.entity.Licence;
import com.example.Licence.Management.enumuration.ExpiredStatus;
import com.example.Licence.Management.enumuration.Status;
import com.example.Licence.Management.repository.LicenceRepository;

@Service
@EnableScheduling
public class LicenceExpiryStatusUpdater {

	@Autowired
	private LicenceRepository licenceRepository;

	// Run every 24 hours (86400000 milliseconds)
	// Run every 1 second (1000 milliseconds)
	// Run every 1 minutes (1000 milliseconds)

	@Scheduled(fixedRate = 86400000) // Schedule to run every 24 hours (1 day)
	public void updateExpiredStatus() {
		List<Licence> licences = licenceRepository.findAll();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

		List<Licence> updatedLicences = new ArrayList<>();

		for (Licence licence : licences) {
			if (licence.getExpiryDate() != null && !licence.getExpiryDate().isEmpty()
					&& licence.getGracePeriod() != null && !licence.getGracePeriod().isEmpty()) {

				LocalDateTime expiryDate;
				LocalDateTime gracePeriodEndDate;
				try {
					expiryDate = LocalDateTime.parse(licence.getExpiryDate(), formatter);
					gracePeriodEndDate = LocalDateTime.parse(licence.getGracePeriod(), formatter);
				} catch (DateTimeParseException e) {
					// Log the error and skip this licence
					System.err.println("Error parsing dates for licence ID " + licence.getId() + ": " + e.getMessage());
					continue;
				}

				if (now.isAfter(expiryDate) && now.isBefore(gracePeriodEndDate)) {
					// Decrement the grace period end date by one day
					gracePeriodEndDate = gracePeriodEndDate.minusDays(1);
					licence.setGracePeriod(gracePeriodEndDate.format(formatter));

					// Update the expired status to "Expiring Soon" if the expiry date has passed
					licence.setExpiredStatus(ExpiredStatus.EXPIRED_SOON);
					updatedLicences.add(licence);

				} else if (now.isAfter(gracePeriodEndDate)) {
					// Update the status or perform any necessary action if the grace period has
					// ended
					licence.setExpiredStatus(ExpiredStatus.EXPIRED);
					licence.setStatus(Status.INACTIVE);
					licence.setActiveationDate(null);
					licence.setExpiryDate(null);
					licence.setGracePeriod(null);
					updatedLicences.add(licence);
				}
			} else {
				// Log a warning if either expiryDate or gracePeriod is invalid
				System.err.println("Licence ID " + licence.getId() + " has invalid date fields.");
			}
		}

		if (!updatedLicences.isEmpty()) {
			licenceRepository.saveAll(updatedLicences);
		}
	}

}
