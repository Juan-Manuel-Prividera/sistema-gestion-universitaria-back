package com.api.materias;

import com.api.materias.service.EmailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EmailSenderTest {

  private EmailSender emailSender;
  private JavaMailSender mailSender;

  @BeforeEach
  public void setUp() {
    mailSender = mock(JavaMailSender.class);
    emailSender = new EmailSender(mailSender);
  }

  @Test
  public void testEnviarCorreo() {
    String para = "test@example.com";
    String asunto = "Asunto de prueba";
    String mensaje = "Mensaje de prueba";

    emailSender.enviarCorreo(para, asunto, mensaje);

    ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
    verify(mailSender, times(1)).send(captor.capture());

    SimpleMailMessage emailEnviado = captor.getValue();
    assertEquals(para, emailEnviado.getTo()[0]);
    assertEquals(asunto, emailEnviado.getSubject());
    assertEquals(mensaje, emailEnviado.getText());
  }
}
