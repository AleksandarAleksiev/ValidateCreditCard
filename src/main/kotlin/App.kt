fun main(args: Array<String>) {
    val (issuer, isValid) = KnownCreditCards.validateCard(CardNumber(args.getOrNull(0).orEmpty()))
    println("Card issuer ${issuer.value}. Card is valid $isValid")
}