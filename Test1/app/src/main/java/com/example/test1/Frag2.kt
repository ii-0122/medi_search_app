package com.example.test1

import android.annotation.SuppressLint
import android.content.Context.MODE_NO_LOCALIZED_COLLATORS
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.test1.databinding.FragmentFrag2Binding
import java.io.FileInputStream
import java.io.FileOutputStream

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Frag2.newInstance] factory method to
 * create an instance of this fragment.
 */
class Frag2 : Fragment() {
    var mBinding: FragmentFrag2Binding? = null
    // 매번 null 체크를 할 필요 없이 바인딩 변수 재선언
    val binding get() = mBinding!!
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var fname2: String =""
    var str2: String = ""

    lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        mBinding = FragmentFrag2Binding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentFrag2Binding.inflate(inflater,container,false)
        val view = binding.root
        // Inflate the layout for this fragment

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            binding.buttonStore.visibility = View.VISIBLE     // 저장 버튼 보이기
            binding.importSchedule.visibility = View.VISIBLE
            binding.scheduleContent.visibility = View.INVISIBLE   // 저장 데이터 텍스트 숨기기
            binding.buttonFix.visibility = View.INVISIBLE     // 수정,
            binding.buttonDelete.visibility = View.INVISIBLE  // 삭제 버튼 안보이기

            binding.importSchedule.setText("")
            checkedDay(year,month,dayOfMonth)
        }
        binding.buttonStore.setOnClickListener{
            saveDiary(fname2)
            Toast.makeText(mainActivity, fname2 + " 의 데이터를 저장했습니다.",Toast.LENGTH_LONG).show();
            str2 = binding.importSchedule.getText().toString()

            binding.scheduleContent.text = "${str2}"
            binding.buttonStore.visibility = View.INVISIBLE
            binding.buttonFix.visibility = View.VISIBLE
            binding.buttonDelete.visibility = View.VISIBLE
            binding.importSchedule.visibility = View.INVISIBLE
            binding.scheduleContent.visibility = View.VISIBLE
        }

        return view
    }

    fun checkedDay(cYear: Int, cMonth: Int, cDay: Int) {
        fname2 = "" + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt" // 저장할 파일 이름 설정. Ex) 2019-01-20.txt
        var fis: FileInputStream? = null // FileStream fis 변수 설정

        try {
            fis = mainActivity.openFileInput(fname2) // fname 파일 오픈!!

            val fileData = ByteArray(fis.available()) // fileData에 파이트 형식으로 저장
            fis.read(fileData) // fileData를 읽음
            fis.close()

            str2 = String(fileData) // str 변수에 fileData를 저장

            binding.importSchedule.visibility = View.INVISIBLE
            binding.scheduleContent.visibility = View.VISIBLE
            binding.scheduleContent.text = "${str2}" // textView에 str 출력

            binding.buttonStore.visibility = View.INVISIBLE
            binding.buttonFix.visibility = View.VISIBLE
            binding.buttonDelete.visibility = View.VISIBLE

            binding.buttonFix.setOnClickListener { // 수정 버튼을 누를 시
                binding.importSchedule.visibility = View.VISIBLE
                binding.scheduleContent.visibility = View.INVISIBLE
                binding.importSchedule.setText(str2)
                binding.buttonStore.visibility = View.VISIBLE
                binding.buttonFix.visibility = View.INVISIBLE
                binding.buttonDelete.visibility = View.INVISIBLE
                binding.scheduleContent.text = "${binding.importSchedule.getText()}"
            }

            binding.buttonDelete.setOnClickListener {
                binding.scheduleContent.visibility = View.INVISIBLE
                binding.importSchedule.setText("")
                binding.importSchedule.visibility = View.VISIBLE
                binding.buttonStore.visibility = View.VISIBLE
                binding.buttonFix.visibility = View.INVISIBLE
                binding.buttonDelete.visibility = View.INVISIBLE
                removeDiary(fname2)
                Toast.makeText(mainActivity, fname2 + " 의 데이터를 삭제했습니다.",Toast.LENGTH_LONG).show();
            }

            if(binding.scheduleContent.getText() == ""){
                binding.scheduleContent.visibility = View.INVISIBLE
                //diaryTextView.visibility = View.VISIBLE
                binding.buttonStore.visibility = View.VISIBLE
                binding.buttonFix.visibility = View.INVISIBLE
                binding.buttonDelete.visibility = View.INVISIBLE
                binding.importSchedule.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("WrongConstant")
    fun saveDiary(readyDay: String) {
        var fos: FileOutputStream? = null

        try {
            fos = mainActivity.openFileOutput(readyDay, MODE_NO_LOCALIZED_COLLATORS)
            var content: String = binding.importSchedule.getText().toString()
            fos.write(content.toByteArray())
            fos.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @SuppressLint("WrongConstant")
    fun removeDiary(readyDay: String) {
        var fos: FileOutputStream? = null

        try {
            fos = mainActivity.openFileOutput(readyDay, MODE_NO_LOCALIZED_COLLATORS)
            var content: String = ""
            fos.write(content.toByteArray())
            fos.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

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
         * @return A new instance of fragment Frag2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Frag2().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}