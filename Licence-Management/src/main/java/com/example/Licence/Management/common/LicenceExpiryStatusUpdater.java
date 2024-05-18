package com.example.Licence.Management.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Scheduled(fixedRate = 1000) // Run every 24 hours (86400000 milliseconds)
    public void updateExpiredStatus() {
        List<Licence> licences = licenceRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (Licence licence : licences) {
            LocalDateTime expiryDate = LocalDateTime.parse(licence.getExpiryDate());
            LocalDateTime gracePeriodEndDate = LocalDateTime.parse(licence.getGracePeriod());

			if (now.isAfter(expiryDate)/* && now.isBefore(gracePeriodEndDate) */) {
//                // Update the expired status to "Expiring Soon" if the expiry date has passed
                licence.setExpiredStatus(ExpiredStatus.EXPIREDSOON);
//                licence.setGracePeriod(now.toString());
                licenceRepository.save(licence);
            	
            	 // Update the expired status to "Expiring Soon" if the expiry date has passed but grace period not ended
//                if (licence.getExpiredStatus() != ExpiredStatus.EXPIREDSOON) {
//                    licence.setExpiredStatus(ExpiredStatus.EXPIREDSOON);
//                    licenceRepository.save(licence);
//                }
            }
            else if (now.isAfter(gracePeriodEndDate)) {
//                // Update the status or perform any necessary action if the grace period has ended
                licence.setExpiredStatus(ExpiredStatus.EXPIRED);
                licence.setStatus(Status.INACTIVE);
            	licence.setActiveationDate(null);
            	licence.setExpiryDate(null);
            	licence.setGracePeriod(null);
                licenceRepository.save(licence);
            	
            	// Update the status to "Expired" if the grace period has ended
//                if (licence.getExpiredStatus() != ExpiredStatus.EXPIRED) {
//                    licence.setExpiredStatus(ExpiredStatus.EXPIRED);
//                    licence.setStatus(Status.INACTIVE);
//                    licence.setActiveationDate(null);
//                    licence.setExpiryDate(null);
//                    licenceRepository.save(licence);
//                }
            }
        }
    }
}

