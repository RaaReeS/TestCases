import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import test.Usuario;

/**
 * Tests de caja negra para la clase Usuario.
 * Incluye:
 * 1. Pairwise de Clases de Equivalencia (CE)
 * 2. Pairwise de Valores Límite (VL)
 */
public class UsuarioTest {

    // =========================================================
    // 1. PAIRWISE: CLASES DE EQUIVALENCIA (CE)
    // =========================================================

    @Test
    @DisplayName("CE-TC1: Camino Feliz - Todo Válido")
    void ce_tc1_valido() {
        // CE1 (abc), CE4 (18), CE7 (u@g.com), CE20 (Abc1)
        // Se usa "Abc1" porque cumple: Mayúscula, Minúscula y Número
        Usuario u = new Usuario("abc", "Abc1", 18, "usuario@gmail.com");
        assertTrue(u.validarRegistro(), "Debe ser válido con datos correctos");
    }

    @Test
    @DisplayName("CE-TC2 al TC5: Errores en Usuario y Edad")
    void ce_tc2_tc5_usuario_edad() {
        // TC2: Usuario corto (CE2)
        assertFalse(new Usuario("us", "Abc1", 18, "u@g.com").validarRegistro(), "Error en Usuario corto");
        // TC3: Usuario largo (CE3)
        assertFalse(new Usuario("usuario", "Abc1", 18, "u@g.com").validarRegistro(), "Error en Usuario largo");
        // TC4: Edad menor (CE5)
        assertFalse(new Usuario("abc", "Abc1", 15, "u@g.com").validarRegistro(), "Error en Edad < 18");
        // TC5: Edad negativa (CE6)
        assertFalse(new Usuario("abc", "Abc1", -2, "u@g.com").validarRegistro(), "Error en Edad negativa");
    }

    @Test
    @DisplayName("CE-TC6 al TC17: Errores en Correo (CE8-CE19)")
    void ce_tc6_tc17_correo() {
        // Formatos de correo inválidos según las CE
        assertFalse(new Usuario("abc", "Abc1", 18, "usuariogmail.com").validarRegistro(), "Falta @");
        assertFalse(new Usuario("abc", "Abc1", 18, "@gmail.com").validarRegistro(), "Falta nombre");
        assertFalse(new Usuario("abc", "Abc1", 18, "usuario@gmail.").validarRegistro(), "Termina en punto");
    }

    @Test
    @DisplayName("CE-TC18 al TC21: Errores en Contraseña (CE21-CE24)")
    void ce_tc18_tc21_password() {
        // TC18: Pass larga (CE21) -> "Abcde12" tiene 7 caracteres
        assertFalse(new Usuario("abc", "Abcde12", 18, "u@g.com").validarRegistro(), "Pass > 5");
        // TC19: Pass corta (CE22) -> "a" tiene 1 caracter
        assertFalse(new Usuario("abc", "a", 18, "u@g.com").validarRegistro(), "Pass < 3");
        // TC20: Carácter prohibido (CE23)
        assertFalse(new Usuario("abc", "Ab_1", 18, "u@g.com").validarRegistro(), "Símbolo prohibido _");
        // TC21: Sin mayúscula (CE24)
        assertFalse(new Usuario("abc", "abc1", 18, "u@g.com").validarRegistro(), "Falta Mayúscula");
    }

    // =========================================================
    // 2. PAIRWISE: VALORES LÍMITE (VL)
    // =========================================================

    @Test
    @DisplayName("VL-TC1: Límites Válidos")
    void vl_tc1_valido() {
        // mikey (5 chars - VL4), 18 (VL7), abcde (5 chars - VL11)
        // Se ajusta pass a "Abc1" para cumplir requisitos de la clase Usuario
        Usuario u = new Usuario("mikey", "Abc1", 18, "u@t.com");
        assertTrue(u.validarRegistro(), "Límites válidos deben pasar");
    }

    @Test
    @DisplayName("VL-TC2 y TC4: Límites Inválidos Usuario")
    void vl_tc2_tc4_usuario() {
        // VL1: mi (longitud 2)
        assertFalse(new Usuario("mi", "Abc1", 18, "u@t.com").validarRegistro(), "Usuario corto VL1");
        // VL5: minkey (longitud 6)
        assertFalse(new Usuario("minkey", "Abc1", 18, "u@t.com").validarRegistro(), "Usuario largo VL5");
    }

    @Test
    @DisplayName("VL-TC5: Edad Límite Inválido")
    void vl_tc5_edad() {
        // VL6: 17 años
        assertFalse(new Usuario("mikey", "Abc1", 17, "u@t.com").validarRegistro(), "Edad 17 debe fallar");
    }

    @Test
@DisplayName("VL-TC7 y TC9: Límites Inválidos Contraseña")
void vl_tc7_tc9_password() {
    // VL12: Contraseña de longitud 2 (Debe ser falsa)
    // Usamos "A1" (longitud 2) para que el fallo sea solo por longitud
    assertFalse(new Usuario("mikey", "A1", 18, "u@t.com").validarRegistro(), "Pass corta VL12 (longitud 2)");

    // VL13: Contraseña de longitud 6 (Debe ser falsa)
    // Usamos "Abcde1" (longitud 6)
    assertFalse(new Usuario("mikey", "Abcde1", 18, "u@t.com").validarRegistro(), "Pass larga VL13 (longitud 6)");
}

    @Test
    @DisplayName("VL-TC3, TC6, TC8: Otros Límites Válidos")
    void vl_validos_frontera() {
        // VL2: mik (3 chars)
        assertTrue(new Usuario("mik", "Abc1", 18, "u@t.com").validarRegistro(), "VL2: mik");
        // VL8: 19 años
        assertTrue(new Usuario("mikey", "Abc1", 19, "u@t.com").validarRegistro(), "VL8: 19 años");
        // VL9: abc (3 chars) -> "Ab1"
        assertTrue(new Usuario("mikey", "Ab1", 18, "u@t.com").validarRegistro(), "VL9: pass 3 chars");
    }
}