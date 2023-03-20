package com.example.test1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.test1.databinding.FragmentFrag3Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Frag3.newInstance] factory method to
 * create an instance of this fragment.
 */
class Frag3 : Fragment() {
    var mBinding: FragmentFrag3Binding? = null
    // 매번 null 체크를 할 필요 없이 바인딩 변수 재선언
    val binding get() = mBinding!!
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    // val api = APIS.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        mBinding = FragmentFrag3Binding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentFrag3Binding.inflate(inflater,container,false)
        val view = binding.root

        binding.btnRank.setOnClickListener{     // 의약품 검색 순위 페이지로 이동
            //val intent_listView = Intent(applicationContext,frag3::class.java)
            //startActivity(intent_listView)
        }

        binding.btnGet.setOnClickListener {
            val intent_search = Intent(context,SearchActivity::class.java)
            startActivity(intent_search)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Frag3.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Frag3().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}