package com.example.portfolio.Repository;

import com.example.portfolio.DTO.CertResponse;
import com.example.portfolio.DTO.ProjectResponse;
import com.example.portfolio.Model.Cert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertRepo extends JpaRepository<Cert, Long> {

    @Query("""
  SELECT new com.example.portfolio.DTO.CertResponse(
   c.certId,
   c.certName,
   c.certThumbnail
) 
FROM Cert c
ORDER BY c.certId ASC
""")
    List<CertResponse> findAllCerts();


}
