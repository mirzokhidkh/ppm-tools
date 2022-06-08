package uz.mk.ppmtool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mk.ppmtool.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User getById(Long id);
}
