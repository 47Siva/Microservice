package com.example.Licence.Management.common;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

//	@Scheduled(fixedRate = 1000)
//	public void updateExpiredStatus() {
//		List<Licence> licences = licenceRepository.findAll();
//		LocalDateTime now = LocalDateTime.now();
//		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
//
//		List<Licence> updatedLicences = new ArrayList<>();
//
//		for (Licence licence : licences) {
//			if (licence.getExpiryDate() != null && licence.getGracePeriod() != null) {
//				LocalDateTime expiryDate = LocalDateTime.parse(licence.getExpiryDate(), formatter);
//				LocalDateTime gracePeriodEndDate = LocalDateTime.parse(licence.getGracePeriod(), formatter);
//
//				if (now.isAfter(expiryDate) && now.isBefore(gracePeriodEndDate)  ) {
//					
////					// Decrement the grace period end date by one second
////	                gracePeriodEndDate = gracePeriodEndDate.minusSeconds(1);
////	                licence.setGracePeriod(gracePeriodEndDate.format(formatter));
//
//					// Update the expired status to "Expiring Soon" if the expiry date has passed
//					licence.setExpiredStatus(ExpiredStatus.EXPIRED_SOON);
//					updatedLicences.add(licence);
//					
//				}
//				
//				if (now.isAfter(gracePeriodEndDate)) {
//					// Update the status or perform any necessary action if the grace period has
//					// ended
//					licence.setExpiredStatus(ExpiredStatus.EXPIRED);
//					licence.setStatus(Status.INACTIVE);
//					licence.setActiveationDate(null);
//					licence.setExpiryDate(null);
//					licence.setGracePeriod(null);
//					updatedLicences.add(licence);
//				}
//				
//			}
//		}
//
//		if (!updatedLicences.isEmpty()) {
//			licenceRepository.saveAll(updatedLicences);
//		}
//	}

	
	@Scheduled(fixedRate = 1000) // Schedule to run every second
	public void updateExpiredStatus() {
	    List<Licence> licences = licenceRepository.findAll();
	    LocalDateTime now = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	    List<Licence> updatedLicences = new ArrayList<>();

	    for (Licence licence : licences) {
	        if (licence.getExpiryDate() != null && licence.getGracePeriod() != null) {
	            LocalDateTime expiryDate = LocalDateTime.parse(licence.getExpiryDate(), formatter);
	            LocalDateTime gracePeriodEndDate = LocalDateTime.parse(licence.getGracePeriod(), formatter);

	            // Calculate remaining seconds until expiry date
	            long remainingSeconds = java.time.Duration.between(now, expiryDate).getSeconds();

	            // If the remaining time is greater than zero, update the grace period end date
	            if (remainingSeconds >= 0) {
	            	
	            	String gracepriod = remainingSeconds + " ";
	                licence.setGracePeriod(gracepriod);

	                // Update the expired status to "Expiring Soon"
	                licence.setExpiredStatus(ExpiredStatus.EXPIRED_SOON);
	                updatedLicences.add(licence);
	            } else {
	                // If the remaining time is zero or negative, set the license as expired
	                licence.setExpiredStatus(ExpiredStatus.EXPIRED);
	                licence.setStatus(Status.INACTIVE);
	                licence.setActiveationDate(null);
	                licence.setExpiryDate(null);
	                licence.setGracePeriod(null);
	                updatedLicences.add(licence);
	            }
	        }
	    }

	    if (!updatedLicences.isEmpty()) {
	        licenceRepository.saveAll(updatedLicences);
	    }
	}

}
