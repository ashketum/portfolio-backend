package com.example.portfolio.Repository;

import com.example.portfolio.DTO.CertResponse;
import com.example.portfolio.Model.Cert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertRepo extends JpaRepository<Cert, Long> {

    @Query("""
  SELECT new com.example.portfolio.DTO.CertResponse(
   c.certId,
   c.certName,
   c.certThumbnail.imgId
  )
  FROM Cert c
  ORDER BY c.certId ASC
""")
    List<CertResponse> findAllCerts();


}
