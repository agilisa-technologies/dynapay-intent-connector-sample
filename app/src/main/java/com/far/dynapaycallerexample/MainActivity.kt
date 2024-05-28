package com.far.dynapaycallerexample

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.far.dynapaycallerexample.databinding.ActivityMainBinding
import com.far.dynapaycallerexample.model.Constants
import com.far.dynapaycallerexample.model.TransactionType
import com.far.dynapaycallerexample.model.TransactionUtil
import com.google.gson.Gson
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTransaction.setOnClickListener{
            binding.tilTransactionId.error = ""

            if(binding.spnTransaction.selectedItem as String == Constants.SALE){
                startTransaction(TransactionType.SALE,TransactionUtil.generateSale())
            }else if(binding.spnTransaction.selectedItem as String == Constants.AUTHORIZATION){
                startTransaction(TransactionType.AUTH,TransactionUtil.generateAuth())
            }else if(binding.spnTransaction.selectedItem as String == Constants.CAPTURE){
                if(validateTransactionId()) {
                    startTransaction(
                        TransactionType.CAPTURE,
                        TransactionUtil.generateCapture(binding.etTransactionId.text.toString())
                    )
                }
            }else if(binding.spnTransaction.selectedItem as String == Constants.REFUND){
                startTransaction(TransactionType.REFUND,TransactionUtil.generateRefund())
            }else if(binding.spnTransaction.selectedItem as String == Constants.VOID){
                if(validateTransactionId())
                    startTransaction(TransactionType.VOID,TransactionUtil.generateVoid(binding.etTransactionId.text.toString()))
            }else if(binding.spnTransaction.selectedItem as String == Constants.BATCH){
                startTransaction(TransactionType.BATCH,TransactionUtil.generateBatchClose())
            }else if(binding.spnTransaction.selectedItem as String == Constants.PRINT){
                if(validateTransactionId()) {
                    startTransaction(
                        TransactionType.PRINT,
                        TransactionUtil.generatePrint(binding.etTransactionId.text.toString())
                    )
                }
            }
        }
        val trx = resources.getStringArray(R.array.transactions).toMutableList()
        binding.spnTransaction.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,trx)
        binding.spnTransaction.onItemSelectedListener = object :OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = binding.spnTransaction.selectedItem as String
                val enableTransactionId = selectedItem in listOf(Constants.VOID,Constants.CAPTURE, Constants.PRINT)
                binding.etTransactionId.isEnabled = enableTransactionId
                binding.tilTransactionId.isEnabled = enableTransactionId
                if(!enableTransactionId)
                    binding.etTransactionId.text?.clear()

                if(enableTransactionId) {
                    binding.etTransactionId.requestFocus()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.etResponseTransactionId.setOnClickListener{
            copyToClipBoard(binding.etResponseTransactionId.text.toString())
        }
    }


    private fun startTransaction(action: TransactionType, transaction:Any){
        val i = Intent().apply {
            component = ComponentName(Constants.DYNAPAY_PACKAGE,Constants.DYNAPAY_INTENT_RECEIVER)
            this.action = action.transaction
            putExtra(Constants.EXTRA_DATA,toJson(transaction))
        }
        startActivityForResult(i,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 &&  resultCode == Activity.RESULT_OK){
            val result =  data?.getStringExtra(Constants.EXTRA_DATA)
            val jsonObject = if(result != null) JSONObject(result) else null
            val transactionId= if(jsonObject?.has("TransactionId") == true) jsonObject.getString("TransactionId") else ""

            binding.tvResponse.text = result
            binding.etResponseTransactionId.setText(transactionId)
        }
    }

    fun toJson(transaction: Any):String {
        val gson = Gson()
        return gson.toJson(transaction)
    }

    private fun copyToClipBoard(value:String){
        val clipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("text", value)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this,"Text copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    private fun validateTransactionId():Boolean {
        if(binding.etTransactionId.text.isNullOrBlank()){
            binding.tilTransactionId.error = getString(R.string.mandatory)
            return false
        }
        return true
    }

}