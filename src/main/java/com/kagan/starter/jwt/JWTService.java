package com.kagan.starter.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service  // Spring'e bu sınıfın bir servis olduğunu belirtir, bu sınıf iş mantığını içerir ve Spring tarafından yönetilir.
public class JWTService {

    // SECRET_KEY, JWT token'larını imzalarken kullanılacak olan gizli anahtar.
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    // Kullanıcı bilgilerini alarak bir JWT token'ı oluşturur.
    public String generateToken(UserDetails userDetails)
    {
        // JWT token'ı oluşturuluyor.
        return Jwts.builder()
                .setSubject(userDetails.getUsername())  // Token'ın subject kısmına kullanıcı adını ekler.
                .setIssuedAt(new Date())  // Token'ın oluşturulma tarihini ekler.
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60))  // Token'ın geçerlilik süresini (1 saat) ayarlar.
                .signWith(getKey(), SignatureAlgorithm.HS256)  // Token'ı gizli anahtar ile imzalar (HS256 algoritması kullanarak).
                .compact();  // Token'ı oluşturur ve geri döndürür.
    }

    // Token'dan belirli bir bilgi (claim) çıkartır.
    public <T> T exportToken(String token, Function<Claims,T> claimsTFunction)
    {
        Claims claims = getClaims(token);  // Token'dan claims (iddaa edilen bilgiler) alınır.
        return claimsTFunction.apply(claims);  // İstenilen claim'i çıkarır ve geri döndürür.
    }

    // Token'dan kullanıcı adını çıkarır.
    public String getUserNameByToken(String token)
    {
        return exportToken(token, Claims::getSubject);  // Token'dan subject (kullanıcı adı) bilgisini alır.
    }

    // Token'ın geçerliliğini kontrol eder.
    public boolean isTokenValid(String token)
    {
        // Token'ın süresi dolmuş mu kontrol etmek için expiration (son kullanma tarihi) bilgisi alınır.
        Date expireDate = exportToken(token, Claims::getExpiration);
        return new Date().before(expireDate);  // Eğer bugünün tarihi expiration tarihinden önceyse, token geçerlidir.
    }

    // Token'dan claims bilgilerini çıkartır (payload kısmını).
    public Claims getClaims(String token)
    {
        // Token'ı analiz eder ve body kısmındaki claims verisini çıkarır.
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())  // Token'ı doğrulamak için kullanılan gizli anahtar.
                .build()
                .parseClaimsJws(token).getBody();  // Token'ı çözerek claims'i elde eder.
        return claims;  // Claims bilgisi döndürülür.
    }

    // Gizli anahtar (SECRET_KEY) kullanılarak bir Key nesnesi döndürülür.
    public Key getKey()
    {
        // SECRET_KEY BASE64 formatında çözülür (decode edilir) ve bir byte array'e dönüştürülür.
        byte[] decode = Decoders.BASE64.decode(SECRET_KEY);
        // Decode edilen byte array kullanılarak bir HMAC-SHA key oluşturulur ve döndürülür.
        return Keys.hmacShaKeyFor(decode);  // HMAC algoritması ile kullanılacak anahtar döndürülür.
    }
}