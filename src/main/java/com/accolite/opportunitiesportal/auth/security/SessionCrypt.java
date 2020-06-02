package com.accolite.opportunitiesportal.auth.security;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.CheckForNull;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SerializationUtils;

public class SessionCrypt {

	private String key = "WGwR_gQidWKx_5Vu";
	private static final Logger log=LoggerFactory.getLogger(SessionCrypt.class);
    public String encrypt(String userId){
    	try {
            Cipher aes = createChiper(Cipher.ENCRYPT_MODE);
            byte[] bytes = SerializationUtils.serialize(userId);
            String encryptedSession = DatatypeConverter.printHexBinary(aes.doFinal(bytes));
            String signature = calculateSignature(bytes).toUpperCase();
            return encryptedSession + signature;
        } catch (NullPointerException e) {
        	log.error(e.toString());
		}
    	catch (Exception e) {
            log.error("Can't encrypt userID : ", e);
        }
        return null;
    }
    
    public String decrypt(String session){
    	if(session != null && session.length() >= 40) {
	        try {
	            String signature = session.substring(session.length() - 40);
	            String encryptedSession = session.substring(0,session.length() - 40);
	            Cipher aes = createChiper(Cipher.DECRYPT_MODE);
	            byte[] bytes = aes.doFinal(DatatypeConverter.parseHexBinary(encryptedSession));
	            if (!signature.equalsIgnoreCase(calculateSignature(bytes))) {
	                log.error("Session has been tampered with");
	                return null;
	            }
	            return (String) SerializationUtils.deserialize(bytes);
	        } catch (Exception e) {
	            log.error("Can't decrypt session", e);
	        }
    	}
        return null;
    }

    private Cipher createChiper(int mode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aes.init(mode, new SecretKeySpec(key.getBytes(), "AES"), new IvParameterSpec(new byte[16]));
        return aes;
    }

    private String calculateSignature(byte[] serialisedSession) {
        try {
            MessageDigest cript = MessageDigest.getInstance("SHA-1");
            cript.reset();
            cript.update(serialisedSession);
            return String.format("%1$40s", new BigInteger(1, cript.digest()).toString(16));
        } catch (Exception e) {
            log.error("Can't calculate signature", e);
        }
        return null;
    }
}

