# Dynapay Android Intent connector Sample

Sample to interconnect PAX applications to Dynapay 

This sample application demostrate how to connect to Dynapay Pax application via Intent sending the same Transaction request used for remote control integration (POS).

This allows Dynapay to take care of the interaction process with BroadPOS and integration with reporting environments and other customer channels (ecommerce, payment link, REST API, Virtual Terminal, Recurrences)

![Payment selection!](/Seleccion_400.png "Payment selection")

![Payment selection!](/BroadPOS.png "Payment selection")

## How to use 

Initialize the package name to Dynapay app

```
    const val DYNAPAY_PACKAGE="com.agilisa.posvisanet"
    const val DYNAPAY_INTENT_RECEIVER="com.agilisa.posvisanet.ExternalPaymentIntent"
```

Sample transaction request:

```
    val transaction = TransactionRequest(
        TransactionId = UUID.randomUUID().toString(),
        Invoice = "12344554",
        Amount = 22.55,
        CashBackAmount = 0.0,
        TipAmount = 1.0,
        PreAuth = false,
        AutoPrint = true,
        Taxes = listOf(
            TaxData(Name = "STATETAX", Amount = 1.0),
            TaxData(Name = "CITYTAX", Amount = 1.0),
            TaxData(Name = "REDUCEDSTATETAX", Amount = 1.0)
        )

```

To call the intent send the TransactionRequest 

```
    startTransaction(TransactionType.SALE, transaction)
    
    private fun startTransaction(action: TransactionType, transaction:Any){
            val i = Intent().apply {
                component = ComponentName(Constants.DYNAPAY_PACKAGE,Constants.DYNAPAY_INTENT_RECEIVER)
                this.action = action.transaction
                putExtra(Constants.EXTRA_DATA,toJson(transaction))
            }
            startActivityForResult(i,1)
        }
   
```

## POS integration documentation: 

* https://agilisa.atlassian.net/wiki/x/EIBQAg

