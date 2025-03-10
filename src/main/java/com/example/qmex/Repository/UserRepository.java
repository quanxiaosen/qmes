package com.example.qmex.Repository;



import com.example.qmex.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Page<User> findByNameContaining(String name, Pageable pageable);
}
