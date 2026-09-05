package com.example.portfolio.Repository;

import com.example.portfolio.Model.Cert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertRepo extends JpaRepository<Cert, Long> {
}
