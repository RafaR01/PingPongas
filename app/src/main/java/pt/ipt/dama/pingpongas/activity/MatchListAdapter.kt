package pt.ipt.dama.pingpongas.activity

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import pt.ipt.dama.pingpongas.R
import pt.ipt.dama.pingpongas.model.MatchData

class MatchListAdapter(context: Context, matches: List<MatchData>) : ArrayAdapter<MatchData>(context, 0, matches) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_partida, parent, false)
        }

        val match = getItem(position)

        val textViewVencedorName: TextView = itemView!!.findViewById(R.id.textViewVencedorUsername)
        val textViewVencedorScore: TextView = itemView!!.findViewById(R.id.textViewVencedorScore)
        val imageViewVencedor: ImageView = itemView!!.findViewById(R.id.imageViewVencedor)

        val imageUrlVencedor = "https://rafaelr2001.pythonanywhere.com/images/${match?.vencedor_image}" // Replace with your image URL

        Picasso.get()
            .load(imageUrlVencedor)
            .networkPolicy(NetworkPolicy.NO_STORE)
            .into(imageViewVencedor)

        textViewVencedorName.text = match?.vencedor.toString()
        textViewVencedorScore.text = match?.scoreVencedor.toString()

        val textViewPerdedorName: TextView = itemView!!.findViewById(R.id.textViewPerdedorUsername)
        val textViewPerdedorScore: TextView = itemView!!.findViewById(R.id.textViewPerdedorScore)
        val imageViewPerdedor: ImageView = itemView!!.findViewById(R.id.imageViewPerdedor)

        val imageUrlPerdedor = "https://rafaelr2001.pythonanywhere.com/images/${match?.perdedor_image}" // Replace with your image URL

        Picasso.get()
            .load(imageUrlPerdedor)
            .networkPolicy(NetworkPolicy.NO_STORE)
            .into(imageViewPerdedor)

        textViewPerdedorName.text = match?.perdedor.toString()
        textViewPerdedorScore.text = match?.scorePerdedor.toString()

        return itemView!!
    }
}