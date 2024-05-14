/**
 * +============+=============+===============+
 *     | Card Type  | Begins With | Number Length |
 *     +============+=============+===============+
 *     | AMEX       | 34 or 37    | 15            |
 *     +------------+-------------+---------------+
 *     | Discover   | 6011        | 16            |
 *     +------------+-------------+---------------+
 *     | MasterCard | 51-55       | 16            |
 *     +------------+-------------+---------------+
 *     | Visa       | 4           | 13 or 16      |
 *     +------------+-------------+---------------+
 */

@JvmInline
value class CardNumber(val value: String)
@JvmInline
value class CardIssuer(val value: String)
@JvmInline
value class CardNumberPattern(val value: String)

data object KnownCreditCards {

    private val unknownIssuer = CardIssuer("Unknown")
    private val knownCreditCards = mapOf(
        CardNumberPattern("^4[0-9]{12}(?:[0-9]{3})?$") to CardIssuer("Visa"),
        CardNumberPattern("^5[1-5][0-9]{14}$") to CardIssuer("MasterCard"),
        CardNumberPattern("^3[47][0-9]{13}$") to CardIssuer("Amex"),
        CardNumberPattern("^6(?:011|5[0-9]{2})[0-9]{12}$") to CardIssuer("Discover"),
    )

    private fun findCardIssuer(number: CardNumber): CardIssuer? {
        return knownCreditCards.entries.firstOrNull { (pattern, _) ->
            pattern.value.toRegex().matches(number.value)
        }?.value
    }

    private fun validateCardNumber(number: CardNumber): Boolean {
        var doubleTheDigit = false
        val sum = number.value.foldRight(0) { numberChar, acc ->
            var cardNumberDigit = numberChar.digitToIntOrNull() ?: 0
            if (doubleTheDigit) {
                cardNumberDigit *= 2
                if (cardNumberDigit >= 10) {
                    cardNumberDigit = cardNumberDigit / 10 + cardNumberDigit % 10
                }
            }
            doubleTheDigit = !doubleTheDigit
            acc + cardNumberDigit
        }

        return sum % 10 == 0
    }

    fun validateCard(number: CardNumber): Pair<CardIssuer, Boolean> {
        val digitsOnlyCardNumber = CardNumber(number.value.replace("\\D".toRegex(), ""))
        val issuer = findCardIssuer(digitsOnlyCardNumber)
        return if (issuer == null) {
            unknownIssuer to false
        } else {
            issuer to validateCardNumber(digitsOnlyCardNumber)
        }
    }
}