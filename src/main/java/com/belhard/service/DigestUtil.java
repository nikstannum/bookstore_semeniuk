package com.belhard.service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class DigestUtil {

	public String hash(String password) {
		
		return password;
//		try {
//			MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
//			try {
//				messageDigest.update(password.getBytes("UTF-8"));
//			} catch (UnsupportedEncodingException e) {
//				throw new RuntimeException(e);
//			}
//			byte[] bytes = messageDigest.digest();
//			BigInteger bigInteger = new BigInteger(1, bytes);
//			return bigInteger.toString(16).toUpperCase();
//		} catch (NoSuchAlgorithmException e) {
//			throw new RuntimeException(e);
//		}
	}
}
