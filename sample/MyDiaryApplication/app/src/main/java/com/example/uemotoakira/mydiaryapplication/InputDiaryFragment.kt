package com.example.uemotoakira.mydiaryapplication


import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.design.widget.Snackbar
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import io.realm.Realm
import java.io.IOException


class InputDiaryFragment : Fragment() {
    private var mDiaryId: Long = 0
    private lateinit var mRealm: Realm
    private lateinit var mTitleEdit: EditText
    private lateinit var mBodyEdit: EditText
    private lateinit var mDiaryImage: ImageView

    /**
     * kotlinでクラスのfactoryメソッドを実装する
     */
    companion object {
        private val DIARY_ID = "DIARY_ID"
        private val REQUEST_CODE = 1
        private val PERMISSION_REQUEST_CODE = 2
        fun newInstance(diaryId: Long): InputDiaryFragment {
            return InputDiaryFragment().apply { arguments = Bundle().apply{ putLong(DIARY_ID, diaryId) } }
        }
    }

    /**
     *　初期処理
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDiaryId = arguments.getLong(DIARY_ID)
        mRealm = Realm.getDefaultInstance()
    }

    /**
     * 日記のIDを取り出し、情報をプロパティへ
     * TextWatcher:::文字列が入力された時のコールバック処理
     */
    override fun onCreateView(inflater: LayoutInflater?
                              , container: ViewGroup?
                              , savedInstanceState: Bundle?): View? {

        val v = inflater!!.inflate(R.layout.fragment_input_diary, container, false).apply {
            mDiaryImage = findViewById<View>(R.id.diary_photo) as ImageView
            mTitleEdit = findViewById<View>(R.id.title) as EditText
            mBodyEdit = findViewById<View>(R.id.bodyEditText) as EditText
        }

        mDiaryImage.setOnClickListener({ view -> requestReadStorage(view) })

        mTitleEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                mRealm.executeTransactionAsync { realm ->
                    realm.where(Diary::class.java).equalTo("id", mDiaryId)
                                                  .findFirst().apply { title = s.toString() }

                }
            }
        })

        mBodyEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                mRealm.executeTransactionAsync { realm ->
                    realm.where(Diary::class.java).equalTo("id", mDiaryId)
                                                  .findFirst().apply { bodyText = s.toString() }
                }
            }
        })

        return v
    }

    /**
     *  ImageViewをタップした時、権限の確認ウィンドウを表示する
     *  checkSelfPermission:::パーミッションが許可されているか確認する
     *  shouldShowRequestPermissionRational:::権限の許可を要求する論理的根拠を示すUIを表示するかどうかを取得する
     *  requestPermissions:::アプリが要求された権限を持っていない場合、ユーザにそれを受け入れるためのUIを提示する
     *
     *  <処理>
     *      外部Storageにアクセスする際、スナックバーで「端末内の画像を読み取って日記に表示するには許可をタップします」を表示させる
     */
    private fun requestReadStorage(view: View) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE))
                Snackbar.make(view, R.string.rationale, Snackbar.LENGTH_LONG).show()
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
        } else {
            pickImage()
        }
    }

    /**
     * requestPermissionsで表示したパーミッションを要求するダイアログで、
     * ユーザが「許可or許可しない」をタップしたときに呼び出される
     */
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.size != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(mDiaryImage as View, R.string.permission_deny, Snackbar.LENGTH_LONG).show()
            } else {
                pickImage()
            }
        }
    }

    /**
     * 画像を選択できるアプリケーションを呼び出すインテントを用意する
     * startActivityForResult:::指定したアクティビティを起動　→　onActivityResultメソッドが呼ばれる
     */
    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply { type = "image/*" }
        startActivityForResult(Intent.createChooser(intent, getString(R.string.pick_image)), REQUEST_CODE)
    }

    /**
     * 画像を選択可能なアプリを起動し、そこで選択した画像を受け取る
     * 画面表示(Bitmap)とDBへの格納(バイト配列)
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            data.data.let {
                try {
                    mDiaryImage.setImageBitmap(MyUtils.getImageFromStream(activity.contentResolver, it))
                } catch (e: IOException) { e.printStackTrace() }
            }

            mRealm.executeTransactionAsync { realm ->
                val bitmap = mDiaryImage.drawable as BitmapDrawable
                MyUtils.getByteFromImage(bitmap.bitmap).takeIf{ it != null && it.isNotEmpty() }
                                                       .let { realm.where(Diary::class.java).equalTo("id", mDiaryId)
                                                                                            .findFirst()
                                                       .apply { image = it }
                }
            }
        }
    }

    /**
     *
     */
    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }
}
