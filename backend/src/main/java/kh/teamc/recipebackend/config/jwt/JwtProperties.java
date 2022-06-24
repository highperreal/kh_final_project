package kh.teamc.recipebackend.config.jwt;

public interface JwtProperties {
    String SECRET = "TESTPASSWORDTESTPASSWORDTESTPASSWORDTESTPASSWORD";
    int EXPIRATION_TIME = 60000*10; //10일
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
