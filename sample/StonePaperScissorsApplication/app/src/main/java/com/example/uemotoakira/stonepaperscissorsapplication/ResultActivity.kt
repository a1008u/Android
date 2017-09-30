package com.example.uemotoakira.stonepaperscissorsapplication


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class ResultActivity : AppCompatActivity() {

    private val JANKEN_GU = 0
    private val JANKEN_CHOKI = 1
    private val JANKEN_PA = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        var myHandImageView: ImageView = findViewById(R.id.my_hand_image)
        var MyHand: Int = when (intent.getIntExtra("MY_HAND", 0)) {
            R.id.gu -> R.drawable.gu.let { myHandImageView.setImageResource(it)
                                           JANKEN_GU
                                         }

            R.id.choki -> R.drawable.choki.let{ myHandImageView.setImageResource(it)
                                                JANKEN_CHOKI
                                              }

            R.id.pa -> R.drawable.pa.let { myHandImageView.setImageResource(it)
                                           JANKEN_PA
                                         }
            else -> 0
        }

        // PCの値設定
        var comHand:Int = getPcHand()
        findViewById<ImageView>(R.id.com_hand_image).apply {
            when (comHand){
                JANKEN_GU    -> setImageResource(R.drawable.com_gu)
                JANKEN_CHOKI -> setImageResource(R.drawable.com_choki)
                JANKEN_PA    -> setImageResource(R.drawable.com_pa)
            }
        }

        // 勝敗を判定する
        var gameResult = (comHand - MyHand + 3) % 3
        findViewById<TextView>(R.id.result_label).apply {
            when (gameResult){
                0 -> setText(R.string.result_draw)
                1 -> setText(R.string.result_win)
                2 -> setText(R.string.result_lose)
            }
        }

        saveData(MyHand, comHand, gameResult)
    }

    /**
     * PCの手を決める
     */
    private fun getPcHand():Int {

        var hand:Int = (Math.random() * 3).toInt()

        var pref = PreferenceManager.getDefaultSharedPreferences(this)
        var lastComHand = pref.getInt("LAST_COM_HAND", 0)

        if (pref.getInt("GAME_COUNT", 0) == 1){
            when(pref.getInt("GAME_RESULT", -1)) {
                1 -> hand = (pref.getInt("LAST_MY_HAND", -1) - 1 + 3) % 3
                2 -> hand = hand.apply { if (lastComHand == this) (Math.random() * 3).toInt()}
            }
        }

        if (pref.getInt("WINNING_STREAK_COUNT", 0) > 0
                 && pref.getInt("BEFORE_LAST_COM_HAND", 0) == lastComHand)
            hand = hand.apply { if (lastComHand == this) (Math.random() * 3).toInt()}

        return hand
    }

    /**
     * じゃんけんの結果を保存する
     */
    private fun saveData(MyHand :Int, comHand:Int, gameResult:Int){

        PreferenceManager.getDefaultSharedPreferences(this).apply {
            edit().putInt("GAME_COUNT", getInt("GAME_COUNT", 0) + 1)
            edit().putInt("LAST_MY_HAND", MyHand)
            edit().putInt("LAST_COM_HAND", comHand)
            edit().putInt("BEFORE_LAST_COM_HAND", getInt("LAST_COM_HAND", 0))
            edit().putInt("GAME_RESULT",gameResult)

            if(gameResult === 2 && getInt("GAME_RESULT", -1) === 2)
                 edit().putInt("WINNING_STREAK_COUNT"
                                    , getInt("WINNING_STREAK_COUNT", 0) + 1)
            else edit().putInt("WINNING_STREAK_COUNT", 0)

            edit().commit()
        }
    }

    // Returnボタン押下
    fun onBackButtonTapped(view: View) = finish()
}
