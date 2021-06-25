package net.manik.springboot.crudrestfulwebservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.manik.springboot.crudrestfulwebservices.model.Vegetable;

@Repository
public interface VegetableRepository extends JpaRepository<Vegetable,Long> {

}
