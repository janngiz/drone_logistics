package com.musala.drones.services;

import com.musala.drones.entities.Medication;
import com.musala.drones.exceptions.NotFoundException;
import com.musala.drones.exceptions.ValidationException;
import com.musala.drones.repo.MedicationRepository;
import com.musala.drones.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class MedicationService {
    private static final String UPLOAD_DIRECTORY = "uploads";
    @Autowired
    private MedicationRepository medicationRepository;


    public Medication saveMedication(String name, String weight, String code, MultipartFile file) {
        try {
            createUploadDirectoryIfNotExists();

            if (file != null && !file.isEmpty() && !isJPEG(file)) {
                throw new ValidationException("Only JPEG images are allowed.");
            }

            if (!isWeightValid(weight)) {
                throw new ValidationException("Weight is not number.");

            }
            double weightDouble = Double.parseDouble(weight);

            if (!isValidCode(code)) {
                throw new ValidationException("Code is not valid.");
            }

            String imageUrl = null;
            if (file != null) {
                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path filePath = Path.of(UPLOAD_DIRECTORY, fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                imageUrl = fileName;
            }

            Medication medication = new Medication(UUID.randomUUID().toString(), name, weightDouble, code, imageUrl);
            return medicationRepository.save(medication);
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }

    }

    public Medication getMedicationById(String medicationId) {
        if (StringUtils.isNullOrEmpty(medicationId)) {
            throw new ValidationException("Medication id cannot be null or empty");
        }
        return medicationRepository.findById(medicationId).orElse(null);
    }

    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }


    public Medication updateMedication(String id, String name, String weight, String code, MultipartFile file) {
        try {
            if (StringUtils.isNullOrEmpty(id)) {
                throw new ValidationException("Medication id cannot be null or empty");
            }

            Medication medication = medicationRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Medication not found with id: " + id));


            if (file != null && !file.isEmpty() && !isJPEG(file)) {
                throw new ValidationException("Only JPEG images are allowed.");
            }

            if (!isWeightValid(weight)) {
                throw new ValidationException("Weight is not number.");

            }
            double weightDouble = Double.parseDouble(weight);

            if (!isValidCode(code)) {
                throw new ValidationException("Code is not valid.");
            }

            medication.setName(name);
            medication.setWeight(weightDouble);
            medication.setCode(code);

            // Update the image if provided
            String imageUrl;
            if (file != null) {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path filePath = Path.of(UPLOAD_DIRECTORY, fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                imageUrl = fileName;
                medication.setImage(imageUrl);
            }

            return medicationRepository.save(medication);
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
    }


    private void createUploadDirectoryIfNotExists() throws IOException, IOException {
        Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
    }

    private boolean isJPEG(MultipartFile file) {
        return file.getContentType() != null && file.getContentType().equalsIgnoreCase("image/jpeg");
    }

    public boolean isValidCode(String code) {
        String pattern = "[A-Z_0-9]+";
        return code.matches(pattern);
    }

    public boolean isWeightValid(String weight) {
        String pattern = "-?\\d+(\\.\\d+)?";
        return weight.matches(pattern);
    }

}
