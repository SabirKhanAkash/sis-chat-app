import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.akash.sischatapp.R

fun showTopSideToast(context: Context, message: String) {
    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val customToastView = inflater.inflate(R.layout.custom_toast_layout, null)

    val toast = Toast(context)
    toast.view = customToastView
    toast.duration = Toast.LENGTH_SHORT
    toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 20, 20)

    val toastText = customToastView.findViewById<TextView>(R.id.custom_toast_text)
    toastText.text = message

    toast.show()
}
