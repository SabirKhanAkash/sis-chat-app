import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.akash.sischatapp.R

fun showTopSideToast(context: Context, message: String, duration: String) {
    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val customToastView = inflater.inflate(R.layout.custom_toast_layout, null)

    val toast = Toast(context)
    toast.view = customToastView
    if(duration == "short")
        toast.duration = Toast.LENGTH_SHORT
    else if(duration == "long")
        toast.duration = Toast.LENGTH_LONG
    toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 25, 25)

    val toastText = customToastView.findViewById<TextView>(R.id.custom_toast_text)
    toastText.text = message

    toast.show()
}
