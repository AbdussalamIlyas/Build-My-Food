package de.htwberlin.webtech.api.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface FileRepository extends JpaRepository<File,String> {

    @Transactional
    File findFileByRecipeId(Long recipeId);
}
