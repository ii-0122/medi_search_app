package com.example.test1

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import com.example.test1.databinding.ActivitySearchActivityBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class SearchActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivitySearchActivityBinding? = null
    // 매번 null 체크를 할 필요 없이 바인딩 변수 재선언
    private val binding get() = mBinding!!
    //val api = APIS.create()

    private var bottomNavigationView // 바텀 네비게이션 뷰
            : BottomNavigationView? = null

    private var frag1: Frag1? = null
    private var frag2: Frag2? = null
    private var frag3: Frag3? = null

    var itemName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 자동 생성된 뷰 바인딩 클래스에서의 inflate라는 메서드를 활용하여
        // 액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        mBinding = ActivitySearchActivityBinding.inflate(layoutInflater)

        // getRoot 메서드로 레이아웃 내부의 최상위 위치 뷰의 인스턴트를 활용해 생성된 뷰를 액티비티에 표시
        setContentView(binding.root)
        var intent2 = Intent(this, MainActivity::class.java)

        // 하단 탭이 눌렸을 때 화면을 전환하기 위해선 이벤트 처리하기 위해 BottomNavigationView 객체 생성
        //var bnv_main2 = findViewById(R.id.bottomNavi) as BottomNavigationView

        // 텍스트뷰에 스크롤 추가
        binding.textViewResult.movementMethod = ScrollingMovementMethod.getInstance()

        binding.textViewResult.text = ""
        binding.buttonSearch.setOnClickListener{
            itemName = "&itemName=" + binding.searchText.text

            binding.textViewResult.text = ""
            val thread = NetworkThread()
            thread.start()
            thread.join()
        }

        binding.button.setOnClickListener{
            startActivity(intent2)
        }
        // finish() // 자기 자신의 액티비티 파괴
    }

    inner class NetworkThread: Thread(){    // 여기 구현해야 함
        override fun run(){
            // 인증키 값
            val key = "C4ZFZEuQBYMardwrAg1zD4wxPrlne5OJjEPDEOzUOb73m8RLVW2z1sMjlHnFK4tzRciK19JK3BW%2FnHIMZDwNzw%3D%3D"
            val type ="&type=json"

            val site = "http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList?serviceKey="+key+itemName+type
            val url = URL(site)
            val conn = url.openConnection()
            val input = conn.getInputStream()
            val isr = InputStreamReader(input)
            val br = BufferedReader(isr)

            var str_search: String? = null
            val buf = StringBuffer()

            do{
                str_search = br.readLine()
                if(str_search != null){
                    buf.append(str_search)
                }
            }while(str_search != null)

            val root = JSONObject(buf.toString())
            val response = root.getJSONObject("body")
            val items = response.getJSONArray("items")

            runOnUiThread {     // 화면에 데이터 출력
                for(i in 0 until items.length()){
                    val jObject = items.getJSONObject(i)

                    binding.textViewResult.append("${i+1} )\n")
                    binding.textViewResult.append("1. 제품명: ${ JSON_Parse(jObject,"itemName")}")
                    binding.textViewResult.append("2. 업체명: ${ JSON_Parse(jObject,"entpName")}")
                    binding.textViewResult.append("3. 효능: ${ JSON_Parse(jObject,"efcyQesitm")}")
                    binding.textViewResult.append("4. 복용 방법: ${ JSON_Parse(jObject,"useMethodQesitm")}")
                    binding.textViewResult.append("5. 부작용: ${ JSON_Parse(jObject,"seQesitm")}")
                    binding.textViewResult.append("6. 보관법: ${ JSON_Parse(jObject,"depositMethodQesitm")}")
                    binding.textViewResult.append("7. 주의사항: ${ JSON_Parse(jObject,"atpnQesitm")}\n")
                }
            }
        }

        fun JSON_Parse(obj:JSONObject, data : String): String{
            return try{
                obj.getString(data)
            } catch(e:Exception){ "해당 의약품에 대한 정보가 없습니다."}
        }
    }

    override fun onDestroy() {
        // onDestroy 에서 binding class 인스턴스 참조를 정리해주어야 함
        mBinding = null
        super.onDestroy()
    }
}
