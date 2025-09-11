package com.hieusrpingboot.petcare.repository;

import com.hieusrpingboot.petcare.entity.PetPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetPhotoRepository extends JpaRepository<PetPhoto, Long> {
    List<PetPhoto> findByPetIdOrderByCreatedAtDesc(Long petId);
}


