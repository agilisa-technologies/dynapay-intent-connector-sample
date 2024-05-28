package com.far.dynapaycallerexample.model

import java.util.UUID
import com.far.dynapaycallerexample.model.TransactionRequest
import com.far.dynapaycallerexample.model.TaxData

object TransactionUtil {
    fun generateSale()= TransactionRequest(
        TransactionId = UUID.randomUUID().toString(),
        Invoice = "",
        Amount = (5..100).random().toDouble(),
        CashBackAmount = 0.0,
        TipAmount = 0.0,
        PreAuth = false,
        AutoPrint = true,
        Taxes = listOf(
            TaxData(Name = "STATETAX", Amount = 1.0),
            TaxData(Name = "CITYTAX", Amount = 1.0),
            TaxData(Name = "REDUCEDSTATETAX", Amount = 1.0)
        )
    )

    fun generateAuth()= TransactionRequest(
        TransactionId = UUID.randomUUID().toString(),
        Invoice = "",
        Amount = (5..100).random().toDouble(),
        CashBackAmount = 0.0,
        TipAmount = 0.0,
        PreAuth = true,
        AutoPrint = true,
        Taxes = listOf(
            TaxData(Name = "STATETAX", Amount = 1.0),
            TaxData(Name = "CITYTAX", Amount = 1.0),
            TaxData(Name = "REDUCEDSTATETAX", Amount = 1.0)
        )
    )

    fun generateCapture(transactionId:String)= TransactionRequest(
        TransactionId = transactionId,
        Amount = (5..100).random().toDouble(),
        CashBackAmount = 0.0,
        TipAmount = 1.0,
        AutoPrint = true,
        Taxes = listOf(
            TaxData(Name = "STATETAX", Amount = 1.0),
            TaxData(Name = "CITYTAX", Amount = 1.0),
            TaxData(Name = "REDUCEDSTATETAX", Amount = 1.0)
        )
    )

    fun generateRefund()= RefundRequest(
        TransactionId = UUID.randomUUID().toString(),
        Invoice = "",
        Amount = (5..100).random().toDouble(),
        AutoPrint = true,
        Taxes = listOf(
            TaxData(Name = "STATETAX", Amount = 1.0),
            TaxData(Name = "CITYTAX", Amount = 1.0),
            TaxData(Name = "REDUCEDSTATETAX", Amount = 1.0)
        )
    )

    fun generateVoid(transactionId: String)= VoidRequest(
        TransactionId = transactionId
    )
    fun generateBatchClose() = CloseBatchRequest(AutoPrint = true)

    fun generatePrint(transactionId: String)= PrintRequest(
        TransactionId = transactionId
    )

}