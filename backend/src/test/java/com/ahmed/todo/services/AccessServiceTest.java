package com.ahmed.todo.services;

import com.ahmed.todo.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

public class AccessServiceTest {
  @Mock
  private UserRepository userRepository;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private JwtService jwtService;

  @Mock
  private PasswordEncoder passwordEncoder;


  @InjectMocks
  private AccessService accessService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void isLoggedTestCookiesNull() {
    // Arrange
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getCookies()).thenReturn(null);
    // Act
    boolean result = accessService.isLogged(request);
    // Assert
    Assertions.assertFalse(result);
    verify(request, times(1)).getCookies();
  }

  @Test
  public void isLoggedTestCookiesDoNotHaveAccessTokenCookie() {
    // Arrange
    HttpServletRequest request = mock(HttpServletRequest.class);
    Cookie[] cookies = new Cookie[] {new Cookie("name", "value")};
    when(request.getCookies()).thenReturn(cookies);
    // Act
    boolean result = accessService.isLogged(request);
    // Assert
    Assertions.assertFalse(result);
    verify(request, atLeast(1)).getCookies();
  }

  @Test
  public void isLoggedTestAccessTokenIsEmpty() {
    // Arrange
    String accessTokenCookieName = AccessService.ACCESS_TOKEN_COOKIE_NAME;
    HttpServletRequest request = mock(HttpServletRequest.class);
    Cookie[] cookies = new Cookie[] {new Cookie(accessTokenCookieName, "")};
    when(request.getCookies()).thenReturn(cookies);
    // Act
    boolean result = accessService.isLogged(request);
    // Assert
    Assertions.assertFalse(result);
    verify(request, atLeast(1)).getCookies();
  }

  @Test
  public void isLoggedTestAccessTokenIsNotValid() {
    // Arrange
    String accessTokenCookieName = AccessService.ACCESS_TOKEN_COOKIE_NAME;
    String accessTokenCookieValue = "not_valid_token";
    HttpServletRequest request = mock(HttpServletRequest.class);
    Cookie[] cookies = new Cookie[] {new Cookie(accessTokenCookieName, accessTokenCookieValue)};
    when(request.getCookies()).thenReturn(cookies);
    when(jwtService.verifyToken(accessTokenCookieValue)).thenReturn("");
    // Act
    boolean result = accessService.isLogged(request);
    // Assert
    Assertions.assertFalse(result);
    verify(request, atLeast(1)).getCookies();
    verify(jwtService, atLeast(1)).verifyToken(accessTokenCookieValue);
  }

  @Test
  public void isLoggedTestAccessTokenIsValid() {
    // Arrange
    String accessTokenCookieName = AccessService.ACCESS_TOKEN_COOKIE_NAME;
    String accessTokenCookieValue = "valid_token";
    HttpServletRequest request = mock(HttpServletRequest.class);
    Cookie[] cookies = new Cookie[] {new Cookie(accessTokenCookieName, accessTokenCookieValue)};
    when(request.getCookies()).thenReturn(cookies);
    when(jwtService.verifyToken(accessTokenCookieValue)).thenReturn("user_email");
    // Act
    boolean result = accessService.isLogged(request);
    // Assert
    Assertions.assertTrue(result);
    verify(request, atLeast(1)).getCookies();
    verify(jwtService, atLeast(1)).verifyToken(accessTokenCookieValue);
  }

  @Test
  public void testLogin() {
    // Arrange

    // Act

    // Assert/
  }

  @Test
  public void testRegister() {
    // Arrange

    // Act

    // Assert
  }

  @Test
  public void testProfile() {
    // Arrange

    // Act

    // Assert/
  }
}
