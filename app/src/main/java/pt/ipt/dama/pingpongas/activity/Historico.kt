package pt.ipt.dama.pingpongas.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import pt.ipt.dama.pingpongas.R
import pt.ipt.dama.pingpongas.activity.UserListAdapter
import pt.ipt.dama.pingpongas.model.MatchData
import pt.ipt.dama.pingpongas.model.StatsData
import pt.ipt.dama.pingpongas.retrofit.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Historico : AppCompatActivity() {
    private lateinit var adapter: MatchListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historico)

        adapter = MatchListAdapter(this, mutableListOf())

        // Set the adapter on the ListView
        val listViewMatches: ListView = findViewById(R.id.listViewMatches)
        listViewMatches.adapter = adapter

        getMatches()

    }

    fun getMatches(){
        val call = RetrofitInitializer().noteService().getMatches()

        call.enqueue(object : Callback<List<MatchData>> {
            override fun onResponse(call: Call<List<MatchData>>, response: Response<List<MatchData>>) {
                if (response.isSuccessful) {
                    val matchData = response.body()
                    if (matchData != null) {
                        if (matchData.size == 0) {
                            val textView : TextView = findViewById(R.id.textView18)
                            textView.visibility = View.VISIBLE
                        }
                        else {
                            val sortedData = matchData.sortedByDescending { it.id }

                            adapter.clear()
                            adapter.addAll(sortedData)
                            adapter.notifyDataSetChanged()
                            Toast.makeText(this@Historico, "Dados Obtidos", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(this@Historico, "Erro ao obter dados", Toast.LENGTH_LONG).show()
                    }
                } else {
                    // Handle non-successful response (e.g., 404 or 500)
                    Toast.makeText(this@Historico, "Erro ao obter dados", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<MatchData>>, t: Throwable) {
                Toast.makeText(this@Historico, "Erro de conexão", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }
        })
    }
}
