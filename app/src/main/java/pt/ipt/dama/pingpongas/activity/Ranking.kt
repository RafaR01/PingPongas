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
import pt.ipt.dama.pingpongas.R
import pt.ipt.dama.pingpongas.adapter.UserListAdapter
import pt.ipt.dama.pingpongas.model.StatsData
import pt.ipt.dama.pingpongas.retrofit.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Ranking : AppCompatActivity() {
    private lateinit var adapter: UserListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        adapter = UserListAdapter(this, mutableListOf())

        // Set the adapter on the ListView
        val listViewUsers: ListView = findViewById(R.id.listViewUsers)
        listViewUsers.adapter = adapter

        usersData()

        val loggedId = intent.getStringExtra("loggedId")

        listViewUsers.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val textViewId = view.findViewById<TextView>(R.id.textViewId).text.toString()

            val intent = Intent(this, Perfil::class.java)
            if (textViewId == loggedId)
                intent.putExtra("loggedId", "$textViewId")
            else
                intent.putExtra("otherId", "$textViewId")
            startActivity(intent)
        }
    }

    fun usersData(){
        val call = RetrofitInitializer().noteService().usersStats()

        call.enqueue(object : Callback<List<StatsData>> {
            override fun onResponse(call: Call<List<StatsData>>, response: Response<List<StatsData>>) {
                if (response.isSuccessful) {
                    val userData = response.body()
                    if (userData != null) {

                        val sortedData = userData.sortedByDescending { it.score }

                        adapter.clear()
                        adapter.addAll(sortedData)
                        adapter.notifyDataSetChanged()

                        Toast.makeText(this@Ranking, "Dados Obtidos", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@Ranking, "Erro ao obter dados", Toast.LENGTH_LONG).show()
                    }
                } else {
                    // Handle non-successful response (e.g., 404 or 500)
                    Toast.makeText(this@Ranking, "Erro ao obter dados", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<StatsData>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}
