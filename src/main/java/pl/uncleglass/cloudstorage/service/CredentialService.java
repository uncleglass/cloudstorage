package pl.uncleglass.cloudstorage.service;

import org.springframework.stereotype.Service;
import pl.uncleglass.cloudstorage.mapper.CredentialMapper;
import pl.uncleglass.cloudstorage.model.Credential;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int addCredential(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        credential.setKey(encodedKey);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setPassword(encryptedPassword);
        if (credential.getCredentialId() != null) {
            return credentialMapper.update(credential);
        }
        return credentialMapper.insert(credential);
    }

    public List<Credential> getCredentials(int userId) {
        return credentialMapper.selectCredentialsByUserId(userId);
    }

    public String getDecryptedPassword(int credentialId) {
        Credential credential = credentialMapper.selectCredentialById(credentialId);
        return encryptionService.decryptValue(credential.getPassword(), credential.getKey());
    }
}
