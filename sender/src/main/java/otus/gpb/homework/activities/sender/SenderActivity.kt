package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import otus.gpb.homework.activities.receiver.R

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sender)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun toGoogleMaps(view: View) {

        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("geo:0,0?q=restaurants")
        ).setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }

    fun sendEmail(view: View) {}
    fun openReceiver(view: View) {}
}