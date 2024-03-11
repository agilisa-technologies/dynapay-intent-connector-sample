package com.far.dynapaycallerexample.model

import java.util.Date

enum class TransactionType(val transaction:String) {
    SALE("TRANSACTION.SALE"),
    AUTH("TRANSACTION.AUTH"),
    CAPTURE("TRANSACTION.CAPTURE"),
    REFUND("TRANSACTION.REFUND"),
    VOID("TRANSACTION.VOID")
}

data class TransactionRequest (
    var TransactionId: String? = null, //Unique transaction Id from the POS
    var Invoice: String? = null, //Unique invoice number from POS System
    var Amount: Double = 0.0, // Authorization amount
    var CashBackAmount: Double = 0.0, //Optional: Authorization Cashbackamount
    var TipAmount: Double = 0.0, // Optional: Authorization tip amount
    var Taxes: List<TaxData?>? = null, //Optional: Tax amount
    var PreAuth: Boolean = false, //Sends Auth message to host Transaction is not included on batch until capture
    var AutoPrint: Boolean = false, //Imprimir automaticamente despues de hacer una transacion
    var Signature:Boolean = false
)

data class RefundRequest(
    var TransactionId: String? = null, //Unique transaction Id from the POS
    var Invoice: String? = null, //Unique invoice number from POS System
    var Amount: Double = 0.0, // Authorization amount
    var Taxes: List<TaxData?>? = null, //Optional: Tax amount
    var AutoPrint: Boolean = false, //Imprimir automaticamente despues de hacer una transacion
    var Signature:Boolean = false
)
data class VoidRequest(
    var TransactionId: String? = null, //Unique transaction Id from the POS
)


data class TaxData(
    var Name: String? = null,
    var Amount: Double = 0.0
)

