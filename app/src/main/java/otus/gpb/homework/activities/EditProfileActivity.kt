package otus.gpb.homework.activities

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private var cameraAccessTime = 0

    val takeImageUri = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            populateImage(uri)
        }
    }

    val permissionCameraStatus = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        when {
            granted -> {
                imageView.setImageResource(R.drawable.cat)
            }

            else -> {

                when (cameraAccessTime) {
                    0 -> ++cameraAccessTime
                    1 -> {
                        ++cameraAccessTime
                        showRationaleDialog()
                    }

                    else -> if (cameraAccessTime >= 2) {
                        showOpenSettingsDialog()
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)

        imageView.setOnClickListener {
            showAlertDialog()
        }

        findViewById<Toolbar>(R.id.toolbar).apply {
            inflateMenu(R.menu.menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.send_item -> {
                        openSenderApp()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun showAlertDialog() {
        val choosingActions = arrayOf("Сделать фото", "Выбрать фото")
        MaterialAlertDialogBuilder(this).apply {
            setTitle("Take a picture")
            setSingleChoiceItems(choosingActions, -1) { dialog, item ->
                when (item) {
                    0 -> permissionCameraStatus.launch(
                        Manifest.permission.CAMERA
                    )

                    1 -> takeImageUri.launch(input = "image/*")
                }
            }.show()
        }
    }

    private fun showRationaleDialog() {

        MaterialAlertDialogBuilder(this).apply {
            setMessage("The camera should take a picture")
            setPositiveButton("Дать доступ") { dialog, which ->
                permissionCameraStatus.launch(
                    Manifest.permission.CAMERA
                )
            }
            setNegativeButton("Отмена") { dialog, which -> return@setNegativeButton }.show()
        }
    }

    private fun showOpenSettingsDialog() {

        MaterialAlertDialogBuilder(this).apply {
            setPositiveButton("Открыть настройки") { dialog, which ->
                openAppSettings()
            }.show()
        }
    }

    fun openAppSettings() {
        val dialogIntent = Intent(android.provider.Settings.ACTION_SETTINGS)
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(dialogIntent)
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        TODO("В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. В качестве extras передайте заполненные строки и картинку")
    }
}