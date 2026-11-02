package com.ecommerce.main.refreshToken.repository;

import com.ecommerce.main.refreshToken.entity.RefreshToken;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @EntityGraph(attributePaths = "user")
    Optional<RefreshToken> findByToken(String token);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE RefreshToken r SET r.isRevoked=true WHERE r.familyId=?1")
    int updateByFamilyId(UUID familyId);
    List<RefreshToken> findByUserId(Long userId);


    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete RefreshToken r where r.user.id=?1")
    int clearAllRefreshToken(Long userid);

}