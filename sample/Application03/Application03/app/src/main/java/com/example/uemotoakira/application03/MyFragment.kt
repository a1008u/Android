package com.example.uemotoakira.application03

import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

/**
 * ライフサイクル
 * 【Created】
 *   onAttach()
 *   onCreateView()
 *   onActivityCreated()
 * 【Start】
 *   onStart()
 * 【Resumed】
 *   onResume()
 * 【Paused】
 *   onPause()
 * 【Stopped】
 *   onStop()
 * 【Destroyed】
 *   onDestroyView()
 *   onDestroy()
 *   onDetach()
 */
class MyFragment : Fragment() {

    /**
     * Fragment内部のUI部品が作成される時
     */
    override fun onCreateView(inflater: LayoutInflater
                              , container: ViewGroup?
                              , savedInstanceState: Bundle?): View? {
        // inflate(レイアウト用xmlのリソースid, レイアウトを作成する場所のViewGroup, 作成したviewをrootの位置に追加するかどうかを指定)
       return inflater.inflate(R.layout.fragment_my, container, false)
    }

    /**
     * このFragmentを含むActivityのonCreateメソッドの実行完了時
     * (静的なFragment) xmlにて定義
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val button = activity.findViewById<View>(R.id.button1) as Button
        val editText1 = activity.findViewById<View>(R.id.editText1) as EditText
        button.setOnClickListener({ editText1.setText("Button Clicked!") })
    }


}
