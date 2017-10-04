package com.example.uemotoakira.application03.dynamic2


import android.os.Bundle
import com.example.uemotoakira.application03.R
import android.support.v7.app.AppCompatActivity

/**
 * 一覧 → 項目　表示へ
 */
class Main3Activity : AppCompatActivity(), ListSelectionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        fragmentManager.beginTransaction().run { add(R.id.fragment3, MyFragment3())
                                                 commit()
                                               }
    }

    override fun onListSelection(index: Int) {
        val myFragment4 = MyFragment4()

        // トランザクションを開始
        // → MyFragmentの置き換え
        // →「戻る」ボタンを押したときの処理用にFragmentTransactionをbackstackに追加
        // → トランザクションを終了
        // → 念のためトランザクションを明示的かつ強制的に実行する
        myFragment4.let {
            fragmentManager.beginTransaction().run { replace(R.id.fragment3, it)
                                                     addToBackStack(null)
                                                     commit()
                                                   }
            fragmentManager.executePendingTransactions()
        }

        // MyFragment4の表示を書き換える
        myFragment4.setContentAtIndex(index)
    }
}
