package io.paioneer.nain.security.service;

import io.paioneer.nain.security.model.entity.RefreshToken;
import io.paioneer.nain.security.repository.RefreshRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RefreshService {
    private final RefreshRepository refreshRepository;



    public RefreshService(RefreshRepository refreshRepository) {
        this.refreshRepository = refreshRepository;
    }

    public void save(RefreshToken refreshToken) {
        refreshRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByTokenValue(String token) {
        return refreshRepository.findByTokenValue(token);
    }

    public Boolean existsByRefresh(String tokenValue) {
        return refreshRepository.existsByTokenValue(tokenValue);
    }

    public void deleteByRefresh(String tokenValue) {
        refreshRepository.deleteByTokenValue(tokenValue);
    }

    public List<RefreshToken> findMemberNo(Long id) {
        return refreshRepository.findMemberNo(id);
    }
}
