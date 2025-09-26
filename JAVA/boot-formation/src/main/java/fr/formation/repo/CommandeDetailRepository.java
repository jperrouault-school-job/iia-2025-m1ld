package fr.formation.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.model.CommandeDetail;

public interface CommandeDetailRepository extends JpaRepository<CommandeDetail, String> {
    
}
