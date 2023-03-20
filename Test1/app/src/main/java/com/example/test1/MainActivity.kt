package com.example.test1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.test1.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityMainBinding? = null
    // 매번 null 체크를 할 필요 없이 바인딩 변수 재선언
    private val binding get() = mBinding!!
    //val api = APIS.create()

    private var bottomNavigationView // 바텀 네비게이션 뷰
            : BottomNavigationView? = null
    private var fm: FragmentManager? = null
    private var ft: FragmentTransaction? = null
    private var frag1: Frag1? = null
    private var frag2: Frag2? = null
    private var frag3: Frag3? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        // 자동 생성된 뷰 바인딩 클래스에서의 inflate라는 메서드를 활용하여
        // 액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        // getRoot 메서드로 레이아웃 내부의 최상위 위치 뷰의 인스턴트를 활용해 생성된 뷰를 액티비티에 표시
        setContentView(binding.root)

        // 하단 탭이 눌렸을 때 화면을 전환하기 위해선 이벤트 처리하기 위해 BottomNavigationView 객체 생성
        var bnv_main = findViewById(R.id.bottomNavi) as BottomNavigationView

        // OnNavigationItemSelectedListener를 통해 탭 아이템 선택 시 이벤트를 처리
        // navi_menu.xml 에서 설정했던 각 아이템들의 id를 통해 알맞은 프래그먼트로 변경하게 한다.
        binding.bottomNavi.run { setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.action_menu -> {
                    // 다른 프래그먼트 화면으로 이동하는 기능
                    val homeFragment = Frag1()
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container, homeFragment).commit()
                }
                R.id.action_daily -> {
                    val boardFragment = Frag2()
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container, boardFragment).commit()
                }
                R.id.action_home -> {
                    val settingFragment = Frag3()
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container, settingFragment).commit()
                }
            }
            true
        }
            selectedItemId = R.id.action_home
        }

        // finish() // 자기 자신의 액티비티 파괴
        }



    override fun onDestroy() {
        // onDestroy 에서 binding class 인스턴스 참조를 정리해주어야 함
        mBinding = null
        super.onDestroy()
    }
}

