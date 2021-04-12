package com.uos.vcommcerce.activity.oder

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uos.vcommcerce.R
import com.uos.vcommcerce.base.BaseActivity
import com.uos.vcommcerce.databinding.ActivityOderBinding
import com.uos.vcommcerce.databinding.ItemPaymentboxBinding


data class PaymentBtnData(
    var btnName: String
)

private var webView: WebView? = null
private var resultView: EditText? = null

private var handler = Handler()

class OrderActivity : BaseActivity<ActivityOderBinding>(
    layoutId = R.layout.activity_oder
) {
    val paymentBtnDataList = listOf(
        PaymentBtnData("신용카드"),
        PaymentBtnData("가상계좌"),
        PaymentBtnData("계좌이체")
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activityorder = this@OrderActivity

        webView = binding.orderZipcodeWebView
        resultView = binding.adressResultView

        val paymentAdapter = PaymentBtnAdapter(this)
        binding.recyclerPaymentboxView.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerPaymentboxView.adapter = paymentAdapter
        paymentAdapter.data = paymentBtnDataList

        //뒤로가기
        binding.activityOrderImagebuttonClose.setOnClickListener {
            finish()
        }

        //주소검색
        binding.addressSearchBtn.setOnClickListener {
            init_webView()
        }

        //결제하기
        binding.activityOrderButtonPay.setOnClickListener {
            startActivity(Intent(binding.root.context, OrderCompleteActivitiy::class.java))
        }
    }

    inner class PaymentBtnViewHolder(val binding: ItemPaymentboxBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun onBind(data: PaymentBtnData) {
            binding.paymentbtndata = data
        }
    }


    inner class PaymentBtnAdapter(val context: Context) :
        RecyclerView.Adapter<PaymentBtnViewHolder>() {

        var data = listOf<PaymentBtnData>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentBtnViewHolder {
            val binding = ItemPaymentboxBinding.inflate(
                LayoutInflater.from(context), parent, false
            )

            return PaymentBtnViewHolder(binding)
        }

        override fun getItemCount() = data.size

        override fun onBindViewHolder(holder: PaymentBtnViewHolder, position: Int) {
            holder.onBind(data[position])

            //그리드 버튼 클릭시
            holder.itemView.setOnClickListener {
                Toast.makeText(context, data[position].btnName, Toast.LENGTH_SHORT).show()

                //.val vedioIntent = Intent(requireActivity(), TestExoplayerActivity::class.java )
                //vedioIntent.putExtra("btnName", data[position].btnName)
                //startActivity(vedioIntent)
            }
        }
    }


    inner class AndroidBridge {
        @RequiresApi(Build.VERSION_CODES.O)
        fun setAddress(arg1: String?, arg2: String?, arg3: String?) {

            // 주소 전달
            val intent = Intent()
            intent.putExtra("address_arg1", arg1)
            intent.putExtra("address_arg2", arg2)
            intent.putExtra("address_arg3", arg3)
            setResult(Activity.RESULT_OK, intent)

            //WebView를 초기화 하지 않으면 재사용할 수 없음
            init_webView()
            finish()
        }
    }

    @SuppressLint("JavascriptInterface")
    @RequiresApi(Build.VERSION_CODES.O)
    fun init_webView() {
        var client: WebViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }
        }
        webView!!.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(true)
            }

            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.setSupportMultipleWindows(true)
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            webViewClient = client

            webView!!.addJavascriptInterface(AndroidBridge(), "TestApp")
            webView!!.loadUrl("https://project-new-windy.web.app")
        }

    }
}
