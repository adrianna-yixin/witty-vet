package net.yixin.witty_vet.service.pet;

import net.yixin.witty_vet.model.Pet;

import java.util.List;

public interface PetService {
    List<Pet> savePetsForAppointment(List<Pet> pets);
    Pet getPetById(Long petId);
    Pet updatePet(Long petId, Pet pet);
    void deletePet(Long petId);
}
