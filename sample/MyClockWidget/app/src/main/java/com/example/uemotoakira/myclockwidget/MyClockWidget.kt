package com.example.uemotoakira.myclockwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.app.PendingIntent
import android.app.AlarmManager
import android.net.Uri
import java.text.SimpleDateFormat
import java.util.*


@Suppress("DEPRECATION")
/**
 * 1.ウィジェットの起動時にAlarmManagerをスケジューリング
 * 2.ウィジェットの終了時にAlarmManagerを停止
 * 3.AlarmManagerでは、指定間隔でウィジェット自身に向けてブロードキャストインテントを発行してウィジェットを更新
 *
 * 【ウィジェットのライフサイクル】
 *  1.onEnabled()
 *  2.onUpdate()
 *  3.onDeleted()
 *  4.onDisabled()
 *  ex.onReceive()
 *
 *  AppWidgetProviderクラスもBroadcastReceiverを継承している
 *
 */
class MyClockWidget : AppWidgetProvider() {


    private val URI_SCHEME = "myclockwidget"

    /**
     * ウィジェットの最初のインスタンスが追加されたときに呼ばれる
     */
    override fun onEnabled(context: Context) {}

    /**
     * ウィジェットの最初のインスタンスが追加された時と、後は設定ファイルに記述された間隔で呼ばれる
     */
    override fun onUpdate(context: Context
                          , appWidgetManager: AppWidgetManager
                          , appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) updateAppWidget(context, appWidgetManager, appWidgetId)
    }

    /**
     * ウィジェットのインスタンスが削除されたときに呼ばれる
     */
    override fun onDeleted(context: Context, appWidgetIds: IntArray) {}

    /**
     * ウィジェットの最後のインスタンスが削除されたときに呼ばれる
     */
    override fun onDisabled(context: Context) {}

    /**
     * 何らかのブロードキャストインテントを受け取ったときに呼ばれる
     * 更新時の場合は初回起動か判定する(スキーマ利用)
     * Intentは、ブロードキャストインテント
     */
    override fun onReceive(context: Context, intent: Intent) {

        when(intent.action){
            AppWidgetManager.ACTION_APPWIDGET_DELETED -> deleteAlarm(context, intent)
            AppWidgetManager.ACTION_APPWIDGET_UPDATE -> if (URI_SCHEME != intent.scheme) setAlarm(context, intent)
                                                        else doProc(context, intent)
        }
    }

    /**
     * 【削除】----------------------------------------------------------------
     * ウィジェットが削除されたときに、インテントと一致するアラームを削除(cancel)
     */
    private fun deleteAlarm(context: Context, intent: Intent) {
        val appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID
                                             , AppWidgetManager.INVALID_APPWIDGET_ID)
        if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(PendingIntent.getBroadcast(context
                                                           , 0
                                                           , buildAlarmIntent(context, appWidgetId)
                                                           , PendingIntent.FLAG_UPDATE_CURRENT))
        }
    }

    /**
     * 【初回起動】----------------------------------------------------------------
     * void setRepeating (int type, long triggerAtMillis, long intervalMillis, PendingIntent operation)
     * 1秒(第2,3引数)ごとに繰り返す処理を指定する
     *
     * <<ペンディングインテント>>
     * 別のアプリケーションにIntentをアプリ自身のプロセスから実行したように使用できるパーミッションを付与すること
     *
     * <<AlarmManager>>
     * 指定された時間やタイミングでイベントを走らせるもの
     *
     * setRepeating:::繰り返しアラームを設定
     *
     * <<PendingIntent>>
     * intentを予約して指定したタイミングで発行する
     *
     * getBroadcast:::ブロードキャストを行うペンディングインテントを取得
     * getBroadcast(Context context, int requestCode, Intent intent, int flags)
     *
     */
    private fun setAlarm(context: Context, intent: Intent) {
        val intArr = intent.extras.getIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS)
        for (appWidgetId in intArr) {
            val interval: Long = 1
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            alarmManager.setRepeating(AlarmManager.RTC
                                      , System.currentTimeMillis()
                                      , interval * 1000
                                      , PendingIntent.getBroadcast(context
                                                                   , 0
                                                                   , buildAlarmIntent(context, appWidgetId)
                                                                   , PendingIntent.FLAG_UPDATE_CURRENT))
        }
    }

    /**
     * ブロードキャスト(システムやアプリが発生させたイベントを受信するための仕組み)へ送信するインテントを生成
     * action:::インテントにアクションを指定
     * data:::データをURI形式で指定(初回起動判定に利用)
     */
    private fun buildAlarmIntent(context: Context, appWidgetId: Int): Intent {
        return Intent(context, javaClass).apply {
            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            data = Uri.parse(URI_SCHEME + "://update/" + appWidgetId)
        }
    }

    /**
     * 【初回起動以外】-------------------------------------------------------------
     */
    private fun doProc(context: Context, intent: Intent) {
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager

        // バージョンチェック
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT_WATCH) {
            if ((!pm.isScreenOn) || (!pm.isInteractive)) return
        }

        val appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID
                                             , AppWidgetManager.INVALID_APPWIDGET_ID)

        if (AppWidgetManager.INVALID_APPWIDGET_ID != appWidgetId)
            updateAppWidget(context, AppWidgetManager.getInstance(context), appWidgetId)
    }

    /**
     * ウィジェットの表示を更新(時刻通りに画面に数字の画像を表示する)
     * RemoteViewsクラス:::ウィジェットを扱う
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     */
    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

        val views = RemoteViews(context.packageName, R.layout.my_clock_widget).apply{
                        val IMAGES = intArrayOf(R.drawable.char0, R.drawable.char1, R.drawable.char2
                                                , R.drawable.char3, R.drawable.char4, R.drawable.char5
                                                , R.drawable.char6, R.drawable.char7, R.drawable.char8
                                                , R.drawable.char9)
                        val VIEWS = intArrayOf(R.id.image0, R.id.image1, R.id.image2, R.id.image3)
                        val ch = SimpleDateFormat("hhmm").format(Date()).toCharArray()
                        for (i in ch.indices) setImageViewResource(VIEWS[i], IMAGES[ch[i] - '0'])
                        setImageViewResource(R.id.image_colon, R.drawable.charcolon)
                    }
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}
