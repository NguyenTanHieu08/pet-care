package com.hieusrpingboot.petcare.service;

import com.hieusrpingboot.petcare.entity.Pet;

import java.util.List;

public interface IPetService {
    List<Pet> savePetsForAppointment(List<Pet> pets);

    List<Pet> savePetForAppointment(List<Pet> pets);
    Pet updatePet(Pet pet,Long id );
    void deletePet(Long id);
    Pet getPetById(Long id);
}
