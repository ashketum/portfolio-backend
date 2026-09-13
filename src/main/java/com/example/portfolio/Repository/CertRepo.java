package com.example.portfolio.Repository;

import com.example.portfolio.DTO.CertResponse;
import com.example.portfolio.Model.Cert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertRepo extends JpaRepository<Cert, Long> {

    @Query("SELECT c.certThumbnail.imgId FROM Cert c WHERE c.certId = :certId")
    Optional<Long> findThumbnailIdByCertId(Long certId);

    @Modifying
    @Query("""
    UPDATE Cert c
    SET c.displayOrder = c.displayOrder + 1
    WHERE c.displayOrder >= :position
""")
    void incrementDisplayOrderFrom(Long position);

    @Query("""
  SELECT new com.example.portfolio.DTO.CertResponse(
   c.certId,
   c.certName
  )
  FROM Cert c
  ORDER BY c.displayOrder ASC
""")
    List<CertResponse> findAllByDisplayOrder();

    @Modifying
    @Query("""
    UPDATE Cert c
    SET c.displayOrder = c.displayOrder + 1
    WHERE c.displayOrder >= :newPosition
      AND c.displayOrder < :oldPosition
""")
    void shiftOrdersUp(
            @Param("newPosition") Long newPosition,
            @Param("oldPosition") Long oldPosition
    );

    @Modifying
    @Query("""
    UPDATE Cert c
    SET c.displayOrder = c.displayOrder - 1
    WHERE c.displayOrder > :oldPosition
      AND c.displayOrder <= :newPosition
""")
    void shiftOrdersDown(
            @Param("oldPosition") Long oldPosition,
            @Param("newPosition") Long newPosition
    );
}
