package com.telefonica.mshispamjobprivate.users.repository;

import com.telefonica.mshispamjobprivate.users.entity.RefreshToken;
import com.telefonica.mshispamjobprivate.users.entity.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {

    @Override
    Optional<UserDevice> findById(Long id);

    Optional<UserDevice> findByRefreshToken(RefreshToken refreshToken);

    Optional<UserDevice> findByUserIdAndDeviceId(Long userId, String userDeviceId);
}
