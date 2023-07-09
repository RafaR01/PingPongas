package pt.ipt.dama.pingpongas.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import pt.ipt.dama.pingpongas.R
import pt.ipt.dama.pingpongas.model.StatsData

class UserListAdapter(context: Context, users: List<StatsData>) : ArrayAdapter<StatsData>(context, 0, users) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.list_item_user, parent, false)
        }

        val user = getItem(position)

        val textViewPosition: TextView = itemView!!.findViewById(R.id.textViewPosition)
        val textViewName: TextView = itemView!!.findViewById(R.id.textViewName)
        val textViewScore: TextView = itemView!!.findViewById(R.id.textViewScore)
        val textViewId: TextView = itemView!!.findViewById(R.id.textViewId)

        textViewId.text = user?.user_id.toString()
        textViewPosition.text = (position + 1).toString()
        textViewName.text = user?.user_name
        textViewScore.text = user?.score.toString()

        return itemView!!
    }
}