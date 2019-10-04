
import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView
import com.example.recipesfornewbies.R

class WhatsInMyFridgeSearchAdapter(context: Context, cursor: Cursor) : CursorAdapter(context, cursor, 0){
    private val layoutInflater = LayoutInflater.from(context)

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return layoutInflater.inflate(R.layout.searchview_suggestion, parent, false)
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val suggestion = cursor?.getString(cursor.getColumnIndex("ingredient_name"))


        val textView = view!!.findViewById<TextView>(R.id.textview_suggestions)
        textView.text = suggestion
    }
}