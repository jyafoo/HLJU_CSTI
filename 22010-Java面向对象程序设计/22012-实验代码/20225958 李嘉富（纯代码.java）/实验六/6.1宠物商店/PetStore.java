package com.example.six.two;

import java.util.LinkedList;

public class PetStore {
    private LinkedList<Pet> pets;

    public PetStore() {
        pets = new LinkedList<Pet>();
    }

    public void addPet(Pet pet) {
        pets.add(pet);
    }

    public void removePet(Pet pet) {
        pets.remove(pet);
    }

    public void showPets() {
        System.out.println("Pets in the store:");
        for (Pet pet : pets) {
            System.out.println(pet.getName() + ", " + pet.getAge() + " years old");
        }
    }
}