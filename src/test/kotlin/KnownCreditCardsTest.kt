import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class KnownCreditCardsTest {

    @Test
    fun `test valid Visa credit card numbers`() {
        val (issuer, isValid) = KnownCreditCards.validateCard(CardNumber("4111-1111-1111-1111"))

        assertEquals("Visa", issuer.value)
        assertTrue(isValid)
    }

    @Test
    fun `test valid MasterCard credit card numbers`() {
        val (issuer, isValid) = KnownCreditCards.validateCard(CardNumber("5431 1111 1111 1111"))

        assertEquals("MasterCard", issuer.value)
        assertTrue(isValid)
    }

    @Test
    fun `test valid Amex credit card numbers`() {
        val (issuer, isValid) = KnownCreditCards.validateCard(CardNumber("3400-0000-0000-009"))

        assertEquals("Amex", issuer.value)
        assertTrue(isValid)
    }

    @Test
    fun `test unknown credit card issuer`() {
        val (issuer, isValid) = KnownCreditCards.validateCard(CardNumber("6022-1111-1111-1117"))
        assertEquals("Unknown", issuer.value)
        assertFalse(isValid)
    }

    @Test
    fun `test invalid credit card number`() {
        val (issuer, isValid) = KnownCreditCards.validateCard(CardNumber("4111-1111-1111-1112"))

        assertEquals("Visa", issuer.value)
        assertFalse(isValid)
    }
}